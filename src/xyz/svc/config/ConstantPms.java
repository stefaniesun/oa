package xyz.svc.config;

import xyz.util.SysPropertyTool;

public class ConstantPms {

	public static final String pms_getHotelList =  SysPropertyTool.getValue("pmsip")+"/XZWS/getHotelList.api";
	
	public static final String pms_getHotelRoomTypeByhotelid =  SysPropertyTool.getValue("pmsip")+"/XZWS/getHotelRoomTypeByhotelid.api";

	public static final String pms_getStockByDate=  SysPropertyTool.getValue("pmsip")+"/ProductStockWS/getStockByDate.api";
	
	public static final String pms_queryProductStockList=  SysPropertyTool.getValue("pmsip")+"/ProductStockWS/queryProductStockList.api";
	
	public static final String pms_createPmsOrder=  SysPropertyTool.getValue("pmsip")+"/XZWS/createPmsOrder.api";
	
	public static final String pms_cancelPmsOrder=  SysPropertyTool.getValue("pmsip")+"/XZWS/cancelPmsOrder.api";
	
	public static final String pms_modifyOrderPayStatus=  SysPropertyTool.getValue("pmsip")+"/XZWS/modifyOrderPayStatus.api";
	
	public static final String pms_modifyOrderCustomerInfo=  SysPropertyTool.getValue("pmsip")+"/XZWS/modifyOrderCustomerInfo.api";
	
	public static final String pms_getAllProductStock=  SysPropertyTool.getValue("pmsip")+"/ProductStockWS/getAllProductStock.api";
	
	public static final String pms_getRoomTypeProductStock=  SysPropertyTool.getValue("pmsip")+"/ProductStockWS/getRoomTypeProductStock.api";
	
	public static final String wxser_registerWxJs=  SysPropertyTool.getValue("wxserip")+"/WeixinWS/jsApiSignature.xyz";
	

}
