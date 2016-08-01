package xyz.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.exception.MyExceptionForRole;
import xyz.model.security.LogOper;
import xyz.svc.security.LogSvc;
import xyz.util.SysPropertyTool;

@Component
public class MyReleaseFilter implements Filter{
	
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
		String ip = MyRequestUtil.getIp(request);
		String path = request.getServletPath();
		{
			LogOper logOper = new LogOper();
			logOper.setUsername("myReleaseFilter");
			logOper.setInterfacePath(path);
			logOper.setIsWork(1);
			logOper.setRemark("myReleaseFilter");
			@SuppressWarnings("rawtypes")
			Map jsonMap = request.getParameterMap();
			String jsonStr = JSON.toJson(jsonMap);
			logOper.setDataContent(jsonStr);
			logOper.setIpInfo(ip);
			logSvc.addLogOper(logOper);
		}
		if(SysPropertyTool.getValue("allowIps")!=null && SysPropertyTool.getValue("allowIps").contains(ip)){
			chain.doFilter(request1, response1);
		}else{
			if(path.contains("JointCallbackWS/notice")){
				chain.doFilter(request1, response1);
			}else{
				throw new MyExceptionForRole("您所在的ip无此权限！");
			}
		}
	}
	
	@Override
	public void destroy() {
		;
	}
}
