package com.cj.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.*;

/**
 * @author : Flon
 * @version V1.0
 * @Description: HTTP、 HTTPS 工具类
 * @date Date : 2019/05/08 11:28
 */
@Slf4j
public class HTTPTool {

	public static String doGet(String url, Map<String, String> param) {

		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		String result = "";
		CloseableHttpResponse response = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();

			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);

			// 执行请求
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String doPost(String url, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)) {
					});
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static String doPost(String url,Map<String,String> params,Map<String,File> fileParams){
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (params != null) {
				HttpEntity entity = makeMultipartEntity(params,fileParams);
				httpPost.addHeader(entity.getContentType());
				httpPost.setEntity(entity);

			}
			System.out.println("executing request " + httpPost.getRequestLine());
			// 执行http请求
			response = httpClient.execute(httpPost);
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static HttpEntity makeMultipartEntity(Map<String,String> params, final Map<String, File> files) {
		List<NameValuePair> paramList = new ArrayList<>();
		for (String key : params.keySet()) {
			paramList.add(new BasicNameValuePair(key, params.get(key)) {
			});
		}
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		//如果有SocketTimeoutException等情况，可修改这个枚举
		builder.setMode(HttpMultipartMode.STRICT);
		//builder.setCharset(Charset.forName("UTF-8")); //不要用这个，会导致服务端接收不到参数
		if (paramList != null && paramList.size() > 0) {
			for (NameValuePair p : paramList) {
				builder.addTextBody(p.getName(), p.getValue(), ContentType.TEXT_PLAIN.withCharset("UTF-8"));
			}
		}
		if (files != null && files.size() > 0) {
			Set<Map.Entry<String, File>> entries = files.entrySet();
			for (Map.Entry<String, File> entry : entries) {
				builder.addPart(entry.getKey(), new FileBody(entry.getValue()));
			}
		}
		return builder.build();
	}
}
