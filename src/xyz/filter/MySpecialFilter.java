package xyz.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.exception.MyExceptionForRole;
import xyz.model.security.LogOper;
import xyz.svc.security.LogSvc;

@Component
public class MySpecialFilter implements Filter{
	
	private static String[] publicUrls = new String[]{
		"/SellerWS/loginOper.xyz",
		"/InitWS/init_1239127awdasd_api.xyz",
		"/LoginWS/login.xyz",
		"/LoginWS/loginByImei.xyz",
		"/LoginWS/alterPassword.xyz"
	};

	@Autowired
	private LogSvc logSvc;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		;
	}

	@Override
	public void doFilter(
			ServletRequest request1, 
			ServletResponse response1,
			FilterChain chain) 
					throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)request1;
		String apikey = request.getParameter("apikey");
		if(apikey==null){
			Cookie[] ttt = request.getCookies();
			if(ttt!=null){
				for(Cookie cookie : ttt){
					if("XZ_LOGIN_KEY".equals(cookie.getName())){
						apikey = cookie.getValue();
					}
				}
			}
		}
		if(apikey==null){
			apikey = request1.getParameter("apikey");
		}
		String path = request.getServletPath();
		boolean flag = false;
		for(String url : publicUrls){
			if(path.equals(url)){
				flag = true;
			}
		}
		{
			LogOper logOper = new LogOper();
			logOper.setUsername("mySpecialFilter");
			logOper.setInterfacePath(path);
			logOper.setIsWork(1);
			logOper.setRemark("mySpecialFilter");
			@SuppressWarnings("rawtypes")
			Map jsonMap = request.getParameterMap();
			String jsonStr = JSON.toJson(jsonMap);
			logOper.setDataContent(jsonStr);
			logOper.setIpInfo(MyRequestUtil.getIp(request));
			logSvc.addLogOper(logOper);
		}
		if(flag){
			chain.doFilter(request1, response1);
		}else{
			throw new MyExceptionForRole("您无权访问！");
		}
	}
	
	@Override
	public void destroy() {
		;
	}
}
