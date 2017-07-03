package h2o.common.redis;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RedisConfig that = (RedisConfig) o;

        return new EqualsBuilder()
                .append(port, that.port)
                .append(host, that.host)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(host)
                .append(port)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "RedisConfig{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
