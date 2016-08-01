package xyz.util;

import java.util.Map;

import xyz.filter.JSON;




public class StringUtil {
	private StringUtil(){}
	public static final String XZTP_CLIENT_CODE="XZTP";//西藏智慧旅游票单前缀
	private static long orderNum = System.currentTimeMillis();//订单编号
	private static long clientCode = System.currentTimeMillis();//票号
	private static long product = System.currentTimeMillis()/1000;//产品编号
	
	public synchronized static String get_new_orderNum(){
		long curt = System.currentTimeMillis();
		if(curt>orderNum){
			orderNum=curt;
		}else{
			curt = ++orderNum;
		}
		String cOrderNum = "N"+String.valueOf(curt+1000000000000l);
		return cOrderNum;
	}
	
	/**
	 * 
	 * Description  ：西藏智慧旅游票单号
	 *
	 * @return String
	 * @exception   ：〈描述可能的异常〉
	
	 * @author      ：huanghao
	 * @date        ：2016-6-23下午3:28:06
	 */
	public synchronized static String get_xztp_heXiao_clientCode(){
		long curt = System.currentTimeMillis();
		if(curt>clientCode){
			clientCode=curt;
		}else{
			curt = ++clientCode;
		}
		String cOrderNum = XZTP_CLIENT_CODE+String.valueOf(curt+1000000000000l);
		return cOrderNum;
	}
	
	/**
	 * 
	 * Description  ：西藏智慧旅游子订单号
	 *
	 * @return String
	 * @exception   ：〈描述可能的异常〉
	
	 * @author      ：huanghao
	 * @date        ：2016-6-23下午3:28:06
	 */
	public synchronized static String get_clientCode(){
		long curt = System.currentTimeMillis();
		if(curt>clientCode){
			clientCode=curt;
		}else{
			curt = ++clientCode;
		}
		String cOrderNum = "P"+String.valueOf(curt+1000000000000l);
		return cOrderNum;
	}
	
	public synchronized static String get_new_product(String type){
		long curt = System.currentTimeMillis()/1000;
		if(curt>product){
			product=curt;
		}else{
			curt = ++product;
		}
		String cClientCode = type+Long.toString(curt, 36).toUpperCase();
		return cClientCode;
	}
	
	public synchronized static String get_new_provider(){
		long curt = System.currentTimeMillis()/1000;
		if(curt>product){
			product=curt;
		}else{
			curt = ++product;
		}
		String cClientCode = "PR"+Long.toString(curt, 36).toUpperCase();
		return cClientCode;
	}
	
	public static String getRandomStr(int scale){
		String temp = "";
		while(temp.length()<scale){
			temp = temp+(int)(Math.random()*10);
		}
		return temp;
	}
	
	public static String getScaleStr(int scale,int number){
		String temp = number+"";
		while(temp.length()<scale){
			temp = 0+temp;
		}
		return temp;
	}
	
	public static boolean yqqQueryExists(String queryJson,String key){
        key = key==null||"".equals(key)?"queryCore":key;
        if(queryJson!=null && !"".equals(queryJson)){
            @SuppressWarnings("unchecked")
            Map<String,Object> tt = JSON.toObject(queryJson, Map.class);
            if(tt.containsKey(key)){
                return true;
            }
        }
        return false;
    }
	
	public static String yqqQueryString(String queryJson,String key,int...isText){
        key = key==null||"".equals(key)?"queryCore":key;
        if(queryJson!=null && !"".equals(queryJson)){
            @SuppressWarnings("unchecked")
            Map<String,Map<String, Object>> tt = JSON.toObject(queryJson, Map.class);
            if(isText.length>0 && isText[0]==1){
                return tt.get(key).get("queryText")==null?"":tt.get(key).get("queryText").toString();
            }else{
                return tt.get(key).get("queryId")==null?"":tt.get(key).get("queryId").toString();

            }
        }
        return "";
    }
}
