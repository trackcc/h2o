package h2o.common.thirdparty.redis;

import redis.clients.jedis.Jedis;

public interface JedisProvider {

    Jedis getJedis();

    void release( Jedis jedis );

    <T> T callback( JedisCallBack<T> jedisCallBack);

    <T> T callback( JedisCallBack<T> jedisCallBack , boolean isSilently );

}
