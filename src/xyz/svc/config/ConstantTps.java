package xyz.svc.config;

import xyz.util.SysPropertyTool;
import xyz.util.TpsUtil;

public class ConstantTps {

	//订单同步增量接口
	public static final String tps_queryOrderContentList =  SysPropertyTool.getValue("serverip")+"/SyncWS/{PROC}/queryOrderContentList.do";
	
	//订单详细接口
	public static final String tps_getOrderContent=  SysPropertyTool.getValue("serverip")+"/SyncWS/{PROC}/getOrderContent.do";

	//出票通知接口
	public static final String tps_updateOrderContentFlagClient=  SysPropertyTool.getValue("serverip")+"/SyncWS/{PROC}/updateOrderContentFlagClient.do";

	//异常通知接口
	public static final String tps_exceptionNotice=  SysPropertyTool.getValue("serverip")+"/SyncWS/{PROC}/exceptionNotice.do";
	
	//退票通知接口
	public static final String tps_returnOrderContent=  SysPropertyTool.getValue("serverip")+"/SyncWS/{PROC}/returnOrderContent.do";
	
}
