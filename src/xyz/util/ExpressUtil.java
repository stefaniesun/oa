package xyz.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import xyz.filter.JSON;


public class ExpressUtil {
	private static ExpressUtil instance;
	
	private static Map<String, String> expressMap=new HashMap<String, String>();
	
	private ExpressUtil(){
		String json=readFile(WebRootPath.getInstance().getPath()+"jsp_core/express.json");
		System.out.println(json);
		@SuppressWarnings("unchecked")
		Map<String, Object> map=JSON.toObject(json, Map.class);
		@SuppressWarnings("unchecked")
		Map<String, Object>[] arr=JSON.toObject(JSON.toJson(map.get("content")), Map[].class);
		for(@SuppressWarnings("rawtypes") Map m:arr){
			expressMap.put(m.get("code").toString(), m.get("name").toString());
		}
	}
	
	public static synchronized ExpressUtil getInstance(){
		if(instance==null){
			instance = new  ExpressUtil();
		}
		return instance;
	}
	
	public String getExpressNameCn(String code){
		return expressMap.get(code);
	}
	
	private String readFile(String path){
		System.out.println(path);
	    File file = new File(path);
	    InputStream inputStream = null;
	    BufferedReader bufferedReader = null;
	    StringBuffer buffer = new StringBuffer("");
	    try {
		    inputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);
		    String text;
		    while ((text = bufferedReader.readLine()) != null) {
		    	buffer.append(text);
		    }
	    } catch (IOException e) {
	    	e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e1) {
				}
			}
			if(bufferedReader!=null){
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	    return buffer.toString();
	}
	
}
