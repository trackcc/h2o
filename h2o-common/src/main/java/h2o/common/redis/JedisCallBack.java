package h2o.common.redis;

import redis.clients.jedis.Jedis;

public interface JedisCallBack<T> {
	
	T doCallBack(Jedis jedis) throws Exception;

}
