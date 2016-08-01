package xyz.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpResponse;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.commons.io.IOUtils;

public class HttpUtil {
	
	public static String sendHttpStream(String url, String content){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		StringEntity httpEntity =  new StringEntity(content, "utf-8");
		httpEntity.setContentType("text/plain;charset=utf-8");
		httpEntity.setContentEncoding("utf-8");
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("X-Requested-With","XMLHttpRequest");
		httpPost.setHeader("content-Type","text/plain;charset=utf-8");
		httpPost.setEntity(httpEntity);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(240000).setConnectTimeout(1000).build();
		httpPost.setConfig(requestConfig);
		String resultStr = null;
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity httpEntity2 = httpResponse.getEntity();
				byte[] bytes = EntityUtils.toByteArray(httpEntity2);
				resultStr = new String(bytes,"utf-8");
				resultStr = resultStr.trim();
			}
			httpClient.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultStr;
	}
	
	
	public static String parseHttpStream(HttpServletRequest request){
		try {
			return IOUtils.toString(request.getInputStream(),"utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
