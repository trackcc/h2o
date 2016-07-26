package h2o.common.util.web;

import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	
	private HttpClientUtil() {}	
	
	public static String get(URI uri) {		
		HttpGet httpget = new HttpGet(uri);
		return echo(httpget,null);
	}

	public static String get(String url) {
		HttpGet httpget = new HttpGet(url);	
		return echo(httpget,null);
	}
	
	public static String get(URI uri , String charset) {		
		HttpGet httpget = new HttpGet(uri);
		return echo(httpget,charset);
	}

	public static String get(String url , String charset) {
		HttpGet httpget = new HttpGet(url);	
		return echo(httpget,charset);
	}
	
	
	public static String post(URI uri) {
		return post( uri , (Map<String,String>)null , null );
	}
	
	public static String post(String url) {
		return post( url , (Map<String,String>)null , null);
	}
	
	public static String post(URI uri , String charset) {
		return post( uri , (Map<String,String>)null , charset );
	}
	
	public static String post(String url , String charset) {
		return post( url , (Map<String,String>)null , charset);
	}
	
	public static String post(URI uri , Map<String,String> para ) {
		HttpPost httppost = new HttpPost(uri);		
		return post(httppost , para , null);
	}
	
	public static String post(String url , Map<String,String> para ) {		 
		HttpPost httppost = new HttpPost(url);		
		return post(httppost , para , null);
	}
	
	public static String post(URI uri , Map<String,String> para , String charset ) {
		HttpPost httppost = new HttpPost(uri);		
		return post(httppost , para , charset);
	}
	
	public static String post(String url , Map<String,String> para , String charset ) {		 
		HttpPost httppost = new HttpPost(url);		
		return post(httppost , para , charset);
	}
		
	
	
	public static String post(HttpPost httppost , Map<String,String> para , String charset) {		
		
		try {
			
			HttpEntity entity = null;
			
			if(para != null && !para.isEmpty()) {
				entity = new UrlEncodedFormEntity(para2nvList(para));
			}
			
			return post(httppost , entity , charset );
			
		} catch( Exception e ) {
			Tools.log.debug("echoPost",e);
			throw ExceptionUtil.toRuntimeException(e);
		}
		

	}	
	
	
	
	public static String post(URI uri , String data , String charset ) {
		return post(uri , data , null , charset);
	}
	
	public static String post(String url , String data , String charset ) {
		return post(url , data , null , charset);
	}
	
	
	public static String post(URI uri , String data , String contentType ,  String charset ) {
		HttpEntity entity = StringUtils.isBlank(contentType) ? new StringEntity(data , charset) : new StringEntity(data , ContentType.create(contentType, charset));
		return post(uri , entity , charset);
	}
	
	public static String post(String url , String data , String contentType ,String charset ) {		 
		HttpEntity entity = StringUtils.isBlank(contentType) ? new StringEntity(data , charset) : new StringEntity(data , ContentType.create(contentType, charset));
		return post(url , entity , charset);
	}



	public static String post(URI uri , HttpEntity entity , String charset ) {
		HttpPost httppost = new HttpPost(uri);		
		return post(httppost , entity , charset);
	}
	
	public static String post(String url , HttpEntity entity , String charset ) {		 
		HttpPost httppost = new HttpPost(url);		
		return post(httppost , entity , charset);
	}

	
	
	
	public static String post(HttpPost httppost, HttpEntity entity, String charset) {

		if (entity != null) {
			httppost.setEntity(entity);
		}

		return echo(httppost, charset);
	}
	
	
	private static List<NameValuePair> para2nvList( Map<String,String> para ) {
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		for( Map.Entry<String, String> pe : para.entrySet() ) {
			nameValuePairs.add(new BasicNameValuePair(pe.getKey(), pe.getValue()));
		}
		
		return nameValuePairs;
		
	}
	
	public static String echo(HttpUriRequest request , String charset ) {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {						
			
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();			
		
			return entity == null ? null : EntityUtils.toString(entity,charset);			
		
		} catch( Exception e ) {
			
			Tools.log.debug("echo",e);
			throw ExceptionUtil.toRuntimeException(e);
			
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				Tools.log.debug( "", e );
			}
		}
	}

	

}
