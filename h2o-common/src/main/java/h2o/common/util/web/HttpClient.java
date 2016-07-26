package h2o.common.util.web;

import java.net.URI;
import java.util.Map;

public class HttpClient {
	
	
	private final String contentType;
	
	private final String charset;
	
	public HttpClient() {
		this.contentType = null;
		this.charset = null;
	}
	
	public HttpClient( String charset ) {
		this.contentType = null;
		this.charset = charset;
	}
	
	public HttpClient( String contentType , String charset ) {
		this.contentType = contentType;
		this.charset = charset;
	}
	
	
	public  String get(URI uri) {		
		return HttpClientUtil.get(uri, charset);
	}

	public  String get(String url) {
		return HttpClientUtil.get(url, charset);
	}
	
	
	public String post(URI uri , Map<String,String> para ) {
		return HttpClientUtil.post(uri, para , charset);
	}
	
	public  String post(String url , Map<String,String> para ) {		 
		return HttpClientUtil.post(url, para , charset);
	}
	
	
	public String post(URI uri ,String data ) {
		return HttpClientUtil.post(uri, data, contentType , charset);
	}
	
	public  String post(String url , String data ) {
		return HttpClientUtil.post(url, data, contentType , charset);
	}
	

}
