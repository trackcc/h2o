package h2o.common.util.web;

import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class HttpClient {
	
	
	private final String contentType;
	
	private final String charset;

	private final CloseableHttpClient client;

	private volatile HttpEchoCallback callback = null;

    public void setCallback(HttpEchoCallback callback) {
        this.callback = callback;
    }

    public HttpClient() {
        this.contentType = null;
        this.charset = null;
        this.client = HttpClients.createDefault();
    }


    public HttpClient( String contentType , String charset , CloseableHttpClient client ) {
        this.contentType = contentType;
        this.charset = charset;
        this.client = client;
    }










    public  String get(URI uri) {

        HttpGet httpget = new HttpGet(uri);

        return echo( httpget );

    }

    public  String get(String url) {

        HttpGet httpget = new HttpGet(url);

        return echo( httpget );

    }



    public String post(URI uri) {

        return post( uri , (Map<String,String>)null  );
    }

    public String post(String url) {

        return post( url , (Map<String,String>)null );

    }



    public  String post(URI uri , Map<String,String> para ) {

        HttpPost httppost = new HttpPost(uri);

        return post(httppost , para );

    }

    public  String post(String url , Map<String,String> para ) {

        HttpPost httppost = new HttpPost(url);

        return post(httppost , para );

    }




    public String post(HttpPost httppost , Map<String,String> para ) {

        try {

            HttpEntity entity = null;

            if(para != null && !para.isEmpty()) {
                entity = new UrlEncodedFormEntity(HttpClientUtil.para2nvList(para));
            }

            return post(httppost , entity );

        } catch( Exception e ) {

            Tools.log.debug("echoPost",e);
            throw ExceptionUtil.toRuntimeException(e);

        }


    }





    public String post(URI uri , String data ) {

        HttpEntity entity = StringUtils.isBlank(contentType) ? new StringEntity(data , charset) : new StringEntity(data , ContentType.create(contentType, charset));

        return post(uri , entity);

    }

    public String post(String url , String data ) {

        HttpEntity entity = StringUtils.isBlank(contentType) ? new StringEntity(data , charset) : new StringEntity(data , ContentType.create(contentType, charset));

        return post(url , entity);

    }



    public String post(URI uri , HttpEntity entity ) {

        HttpPost httppost = new HttpPost(uri);

        return post(httppost , entity);

    }

    public String post(String url , HttpEntity entity) {

        HttpPost httppost = new HttpPost(url);

        return post(httppost , entity);

    }




    public String post(HttpPost httppost, HttpEntity entity ) {

        if (entity != null) {
            httppost.setEntity(entity);
        }

        return echo(httppost);
    }




    public String echo(HttpUriRequest request) {

        return HttpClientUtil.echo( client , false , request , charset , callback );

    }


    public void close() {

        if ( client != null ) try {

            client.close();

        } catch (IOException e) {
            Tools.log.debug( "", e );
        }
    }
	

}
