package h2o.common.util.web;

import org.apache.http.HttpResponse;

/**
 * Created by zhangjianwei on 2017/4/23.
 */
public interface HttpEchoCallback {

    String getString( HttpResponse response , String charset );

}
