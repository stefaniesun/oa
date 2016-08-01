package xyz.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import xyz.dao.CommonDao;
import xyz.filter.JSON;
import xyz.filter.ReturnUtil;
import xyz.model.base.TpsConfig;

@Component
public class TpsUtil {

	private static final String PROC="xztrip";//TPS接口请求PROC
	
	private static final String ENCRYPT="b0956f6056cb4fb1";//TPS提供的接口请求加密秘钥
	
	@Resource
	CommonDao commonDao;
	
	public TpsConfig getTpsConfig(){
		
		String sql="select date_start,date_end,encrypt,proc from tps_config";
		@SuppressWarnings("unchecked")
		List<Object[]> list=commonDao.getSqlQuery(sql).list();
		
		TpsConfig tpsConfig=new TpsConfig();
		
		if(list!=null&&list.size()>0){
			Object[] arr=list.get(0);
			tpsConfig.setDateStart(arr[0]==null?null:DateUtil.stringToDate(arr[0].toString()));
			tpsConfig.setDateEnd(arr[1]==null?null:DateUtil.stringToDate(arr[1].toString()));
			tpsConfig.setEncrypt(arr[2]==null?"":arr[2].toString());
			tpsConfig.setProc(arr[3]==null?"":arr[3].toString());
		}
	
		return tpsConfig;
	}
	
	public static void main(String[] args) throws Exception{
		
		Map<String, String> accessoryParam=new HashMap<String, String>();
		accessoryParam.put("dateStart", "2016-07-14 12:30:00");
		accessoryParam.put("dateEnd", "2016-07-14 12:33:00");
		
		String content = EncryptionUtil.doEncrypt(JSON.toJson(accessoryParam),"b0956f6056cb4fb1");
    	CloseableHttpClient httpClient = HttpClients.createDefault();
    	StringEntity httpEntity = new StringEntity(content, "utf-8");
    	httpEntity.setContentType("text/plain;charset=utf-8");
    	httpEntity.setContentEncoding("utf-8");
    	HttpPost httpPost = new HttpPost("http://192.168.1.13:8080/pberpserver/SyncWS/xztrip/queryOrderContentList.do");
    	httpPost.setHeader("X-Requested-With","XMLHttpRequest");
    	httpPost.setHeader("content-Type","text/plain;charset=utf-8");
    	httpPost.setEntity(httpEntity);
    	HttpResponse httpResponse = httpClient.execute(httpPost);
    	System.out.println("---"+EntityUtils.toString(httpEntity));
	}
	
	
	public Object loadData(String url,Map<String, String> accessoryParam){
		
		try {
			TpsConfig tspConfig=getTpsConfig();
			if(tspConfig==null){
				return ReturnUtil.returnMap(0,"TPS接口配置参数缺失");
			}
			String encrypt=(tspConfig.getEncrypt()==null||"".equals(tspConfig.getEncrypt()))?ENCRYPT:tspConfig.getEncrypt();
			
			String proc=(tspConfig.getProc()==null||"".equals(tspConfig.getProc()))?PROC:tspConfig.getProc();
			
			url=url.replace("{PROC}", proc);
			
	    	String content = EncryptionUtil.doEncrypt(JSON.toJson(accessoryParam),encrypt);
	    	CloseableHttpClient httpClient = HttpClients.createDefault();
	    	StringEntity httpEntity = new StringEntity(content, "utf-8");
	    	httpEntity.setContentType("text/plain;charset=utf-8");
	    	httpEntity.setContentEncoding("utf-8");
	    	
	    	HttpPost httpPost = new HttpPost(url);
	    	httpPost.setHeader("X-Requested-With","XMLHttpRequest");
	    	httpPost.setHeader("content-Type","text/plain;charset=utf-8");
	    	httpPost.setEntity(httpEntity);
	    	HttpResponse httpResponse = httpClient.execute(httpPost);
			Object result = null;
	    	if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity httpEntity2 = httpResponse.getEntity();
				byte[] bytes = EntityUtils.toByteArray(httpEntity2);
				String resultStr = new String(bytes,"utf-8");
				resultStr = resultStr.trim();
				if("".equals(resultStr)){
					result = "";
				}else if("[".equals(resultStr.substring(0, 1))){
					result = JSON.toObject(resultStr,List.class);
				}else if("{".equals(resultStr.substring(0, 1))){
					result = JSON.toObject(resultStr,Map.class);
				}else{
					result = "";
				}
			}else{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(Constant.result_status, 0);
				map.put(Constant.result_msg, "http连接错误("+httpResponse.getStatusLine().getStatusCode()+")");
				result = map;
			}
			httpClient.close();
			return result;
		} catch (Exception e) {
			//不抛出异常，防止排版任务事务回滚
			return ReturnUtil.returnMap(0,e.getMessage());
		}
	}
	
}
