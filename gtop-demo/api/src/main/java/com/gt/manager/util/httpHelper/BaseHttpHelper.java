package com.gt.manager.util.httpHelper;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.stereotype.Component;


/**
 * @author uimagine
 * BaseHttpHelper
 */
@Component("baseHttpHelper")
public class BaseHttpHelper {
	
	/**
	 * ReadStream
	 * @param
	 * @return byte[]
	 * @throws Exception
	 */
	protected byte[] readStream(InputStream inStream) throws Exception{
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while( (len = inStream.read(buffer)) != -1){
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}
	
	/**
	 * Post Data
	 * @param path
	 * @param params
	 * @param encode
	 * @param timeout
	 * @return byte[]
	 * @throws Exception
	 */
	protected byte[] post(String path, Map<String, String> params, String encode, int timeout) throws Exception{
		byte[] resultBuffer = null;
		StringBuilder parambuilder = new StringBuilder("");
		if(params != null && !params.isEmpty()) {
			for(Map.Entry<String, String> entry : params.entrySet()){
				parambuilder.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), encode)).append("&");
			}
			parambuilder.deleteCharAt(parambuilder.length()-1);
		}
		byte[] data = parambuilder.toString().getBytes();
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setConnectTimeout(timeout * 1000);
		conn.setReadTimeout(timeout * 1000);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		conn.setRequestProperty("Accept-Language", "zh-CN");
//		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X; en-us) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		conn.setRequestProperty("Connection", "Keep-Alive");
		DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
		outStream.write(data);
		outStream.flush();
		outStream.close();
		if(conn.getResponseCode() == 200){
			resultBuffer = readStream(conn.getInputStream());
		}
		System.out.println("请求状态码："+conn.getResponseCode());
		conn.disconnect();
		return resultBuffer;
	}
	
}