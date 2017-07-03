package h2o.common.redis;

/**
 * Created by zhangjianwei on 2017/7/3.
 */
public class RedisConfig {

    public final String host;

    public final int port;

    public final String pass;

    public final Integer timeout;

    public RedisConfig(String host, int port) {
        this.host = host;
        this.port = port;
        this.pass = null;
        this.timeout = null;
    }

    public RedisConfig(String host, int port, String pass) {
        this.host = host;
        this.port = port;
        this.pass = pass;
        this.timeout = null;
    }

    public RedisConfig(String host, int port, String pass, Integer timeout) {
        this.host = host;
        this.port = port;
        this.pass = pass;
        this.timeout = timeout;
    }


}
