package xyz.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.exception.MyExceptionForLogin;
import xyz.svc.security.KeySvc;
import xyz.util.Constant;

@Component
public class MyLoginCustomerFilter implements Filter{

	@Autowired
	private KeySvc keySvc;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		;
	}

	@Override
	public void doFilter(
			ServletRequest request, 
			ServletResponse response,
			FilterChain chain) 
					throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		String apikey = httpServletRequest.getParameter("apikey");
		if(apikey==null){
			Cookie[] ttt = httpServletRequest.getCookies();
			if(ttt!=null){
				for(Cookie cookie : ttt){
					if("XZ_LOGIN_KEY".equals(cookie.getName())){
						apikey = cookie.getValue();
					}
				}
			}
		}
		if(apikey==null){
			apikey = request.getParameter("apikey");
		}
		if(apikey==null){
			throw new MyExceptionForLogin("不存在有效登录信息,请重新登录！");
		}
		chain.doFilter(httpServletRequest, httpServletResponse);
	}
	
	@Override
	public void destroy() {
		;
	}
}
