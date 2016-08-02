package xyz.util;

import java.util.Map;

import xyz.filter.JSON;




public class StringUtil {
	private StringUtil(){}
	public static final String XZTP_CLIENT_CODE="XZTP";//西藏智慧旅游票单前缀
	private static long orderNum = System.currentTimeMillis();//订单编号
	private static long numberCode = System.currentTimeMillis();

	
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
	
	public synchronized static String get_new_numberCode(){
		long curt = System.currentTimeMillis();
		if(curt>numberCode){
			numberCode=curt;
		}else{
			curt = ++numberCode;
		}
		String cOrderNum = String.valueOf(curt+1000000000000l);
		return cOrderNum;
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
