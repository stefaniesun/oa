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

@Component
public class MySecurityCustomerFilter implements Filter{
	
	private static String[] customerUrls = new String[]{
		"/BuyerOrderWS/createOrder.cus",
		"/BuyerOrderWS/queryOrderList.cus",
		"/BuyerOrderWS/getOrder.cus",
		"/BuyerOrderWS/deleteOrder.cus",
		"/BuyerOrderWS/editOrder.cus",
		"/CustomerWS/editCustomerLinkInfo.cus",
		"/CustomerWS/queryCustomerCollectionList.cus",
		"/CustomerWS/queryAllCustomerCollectionList.cus",
		"/CustomerWS/editCustomerCollectionFlag.cus",
		"/BuyerOrderWS/holdStockOper.cus",
		"/BuyerOrderWS/decideOrder.cus",
		"/PayWS/getWxPayOrderInfo.cus",
		"/PayWS/getAliPayOrderInfo.cus",
		"/CommentWS/addProviderComment.cus",
		"/CommentWS/getCommentByOrderNum.cus",
		"/WeixinWS/firstOrderRedPack.cus",
		"/BuyerRefundWS/addRefund.cus",
		"/BuyerRefundWS/queryRefundList.cus",
		"/BuyerRefundWS/addRefundMsg.cus",
		"/BuyerRefundWS/queryRefundMsgList.cus",
		"/BuyerRefundWS/editRefundReadFlag.cus",
		"/BuyerRefundWS/queryRefundListByOrderNum.cus",
		"/BuyerRefundWS/getUnreadCount.cus"
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
		String path = request.getServletPath();
		boolean flag = false;
		{
			LogOper logOper = new LogOper();
			logOper.setUsername("mySecurityCustomerFilter");
			logOper.setInterfacePath(path);
			logOper.setIsWork(1);
			logOper.setRemark("mySecurityCustomerFilter");
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
