package cn.dlbdata.dj.common.core.util.net;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


/**
 * 测试接口
 * @author zhaoyunlei
 *
 */
public class HttpClientUtil {
	
	private String accept = "application/json";
	
	private String contextType = "application/json; charset=utf-8";
	
	public HttpClientUtil() {}
	
	public HttpClientUtil(String accept, String contextType) {
		this.accept = accept;
		this.contextType = contextType;
	}
	
	public String send(String methodName, String url, Map<String, String> header, String params) {
		String[] methods = {"POST", "GET", "PUT", "PATCH", "DELETE"};
		if(methodName == null) return "Method is Worng";
		if(StringUtils.isEmpty(url)) return "url is null";
		boolean methodIsWrong = true;
		int methodIndex = 0;
		for(int i=0; i<methods.length; i++) {
			if(methods[i].equals(methodName.toUpperCase())) {
				methodIsWrong = false;
				methodIndex = i;
				break;
			}
		}
		if(methodIsWrong) return "Method is Worng";
		HttpRequestBase method = null;
		switch (methodIndex) {
			case 0: method = new HttpPost(url); ; break;
			case 1: method = new HttpGet(url); break;
			case 2: method = new HttpPut(url); break;
			case 3: method = new HttpPatch(url); break;
			case 4: method = new HttpDelete(url); break;
			default: method = new HttpPost(url);
				break;
		}
		method.addHeader("Content-type",this.contextType);  
		method.setHeader("Accept", this.accept);  
		if(methodIndex == 0) {
			((HttpPost) method).setEntity(new StringEntity(params, Charset.forName("UTF-8")));
		}else if(methodIndex == 2) {
			((HttpPut) method).setEntity(new StringEntity(params, Charset.forName("UTF-8")));
		}else if(methodIndex == 3) {
			((HttpPatch) method).setEntity(new StringEntity(params, Charset.forName("UTF-8")));
		}
		HttpResponse  httpResponse = null ;
		try {
    		httpResponse = new DefaultHttpClient().execute(method);
		} catch (IOException e) {
			e.printStackTrace();
			return "发送"+methods[methodIndex]+"请求失败";
		}
    	String result= "";
    	if(httpResponse.getStatusLine().getStatusCode() == 200)  
        {  
            HttpEntity httpEntity = httpResponse.getEntity();  
            try {
				result = EntityUtils.toString(httpEntity);
			} catch (ParseException | IOException e) {
				e.printStackTrace();
				return "接收"+methods[methodIndex]+"请求返回值异常";
			}
            //取出应答字符串  
            // 一般来说都要删除多余的字符   
            result.replaceAll("\r", "");//去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格    
        }  
        else {
        	method.abort(); 
        	return httpResponse.toString();
        }
    	return result;
	}
	
	public  String sendGet(String url, Map<String, String> header) {
		//实例化httpclient  
		if(url == null) {
			System.out.println("url con't null");
		}
		HttpGet get = new HttpGet(url);
		if(header != null) {
			this.setHeader(get, header);
		}
		HttpResponse  httpResponse = null ;
    	try {
    		httpResponse = new DefaultHttpClient().execute(get);
		} catch (IOException e) {
			e.printStackTrace();
			return "发送get请求失败";
		}
    	String result= "";
    	if(httpResponse.getStatusLine().getStatusCode() == 200)  
        {  
            HttpEntity httpEntity = httpResponse.getEntity();  
            try {
				result = EntityUtils.toString(httpEntity);
			} catch (ParseException | IOException e) {
				e.printStackTrace();
				return "接收get返回值异常";
			}
            //取出应答字符串  
            // 一般来说都要删除多余的字符   
            result.replaceAll("\r", "");//去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格    
        }  
        else {
        	System.out.println(httpResponse.toString());
        	get.abort(); 
        }
    	System.out.println(result);
    	return result;
	}
	
	public  String sendPost(String url, Map<String, String> header, String params) {
		if(url == null) {
			System.out.println("url con't null");
		}
		HttpPost post = new HttpPost(url);
		if(header != null) {
			this.setHeader(post, header);
		}
		post.addHeader("Content-type",this.contextType);  
		post.setHeader("Accept", this.accept);  
		post.setEntity(new StringEntity(params, Charset.forName("UTF-8")));
		HttpResponse  httpResponse = null ;
    	try {
    		httpResponse = new DefaultHttpClient().execute(post);
		} catch (IOException e) {
			e.printStackTrace();
			return "发送post请求失败";
		}
    	String result= "";
    	if(httpResponse.getStatusLine().getStatusCode() == 200)  
        {  
            HttpEntity httpEntity = httpResponse.getEntity();  
            try {
				result = EntityUtils.toString(httpEntity);
			} catch (ParseException | IOException e) {
				e.printStackTrace();
				return "发送post请求失败";
			}
            //取出应答字符串  
            // 一般来说都要删除多余的字符   
            result.replaceAll("\r", "");//去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格    
        }  
        else {
        	post.abort(); 
        	return httpResponse.toString();
        }
    	return result;
	}
	
	public  String sendPut(String url, Map<String, String> header, String params) {
		if(url == null) {
			System.out.println("url con't null");
		}
		HttpPut put = new HttpPut(url);
		put.addHeader("Content-type",this.contextType);  
		put.setHeader("Accept", this.accept);  
		if(header != null) {
			this.setHeader(put, header);
		}
		put.setEntity(new StringEntity(params, Charset.forName("UTF-8")));
		HttpResponse  httpResponse = null ;
    	try {
    		httpResponse = new DefaultHttpClient().execute(put);
		} catch (IOException e) {
			e.printStackTrace();
			return "发送put请求失败";
		}
    	String result= "";
    	if(httpResponse.getStatusLine().getStatusCode() == 200)  
        {  
            HttpEntity httpEntity = httpResponse.getEntity();  
            try {
				result = EntityUtils.toString(httpEntity);
			} catch (ParseException | IOException e) {
				e.printStackTrace();
				return "接收put返回值异常";
			}
            //取出应答字符串  
            // 一般来说都要删除多余的字符   
            result.replaceAll("\r", "");//去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格    
        }  
        else {
        	System.out.println(httpResponse.toString());
        	put.abort(); 
        }
    	System.out.println(result);
    	return result;
	}
	
	public  String sendPatch(String url, Map<String, String> header, String params) {
		if(url == null) {
			System.out.println("url con't null");
		}
		HttpPatch patch = new HttpPatch(url);
		patch.addHeader("Content-type",this.contextType);  
		patch.setHeader("Accept", this.accept); 
		if(header != null) {
			this.setHeader(patch, header);
		}
		patch.setEntity(new StringEntity(params, Charset.forName("UTF-8")));
		HttpResponse  httpResponse = null ;
    	try {
    		httpResponse = new DefaultHttpClient().execute(patch);
		} catch (Exception e) {
			e.printStackTrace();
			return "发送patch请求失败";
		}
    	String result= "";
    	if(httpResponse.getStatusLine().getStatusCode() == 200)  
        {  
            HttpEntity httpEntity = httpResponse.getEntity();  
            try {
				result = EntityUtils.toString(httpEntity);
			} catch (ParseException | IOException e) {
				e.printStackTrace();
				return "接收patch返回值异常";
			}
            //取出应答字符串  
            // 一般来说都要删除多余的字符   
            result.replaceAll("\r", "");//去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格    
        }  
        else {
        	System.out.println(httpResponse.toString());
        	patch.abort(); 
        }
    	System.out.println(result);
    	return result;
	}
	
	public  String sendDelete(String url, Map<String, String> header) {
		if(url == null) {
			System.out.println("url con't null");
		}
		HttpDelete del = new HttpDelete(url);
		if(header != null) {
			this.setHeader(del, header);
		}
		HttpResponse  httpResponse = null ;
    	try {
    		httpResponse = new DefaultHttpClient().execute(del);
		} catch (IOException e) {
			e.printStackTrace();
			return "发送delete请求失败";
		}
    	String result= "";
    	if(httpResponse.getStatusLine().getStatusCode() == 200)  
        {  
            HttpEntity httpEntity = httpResponse.getEntity();  
            try {
				result = EntityUtils.toString(httpEntity);
			} catch (ParseException | IOException e) {
				e.printStackTrace();
				return "接收delete返回值异常";
			}
            //取出应答字符串  
            // 一般来说都要删除多余的字符   
            result.replaceAll("\r", "");//去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格    
        }  
        else {
        	System.out.println(httpResponse.toString());
        	del.abort(); 
        }
    	System.out.println(result);
    	return result;
	}
	
	public void setHeader(HttpRequestBase method, Map<String, String> header) {
		if(method == null || header == null) {
			return ;
		}
		for(Entry<String, String> entry : header.entrySet()) {
			if(entry == null || "accept".equals(entry.getKey().toLowerCase()) 
					|| "context-type".equals(entry.getKey().toLowerCase())) {
				continue;
			}
			method.addHeader(entry.getKey(), entry.getValue());
		}
	}
}
