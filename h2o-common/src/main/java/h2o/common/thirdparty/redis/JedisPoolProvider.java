package h2o.common.thirdparty.redis;

import h2o.common.Tools;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisPoolProvider extends AbstractJedisProvider implements JedisProvider{


    private final JedisPool jedisPool;

    public JedisPoolProvider(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public Jedis getJedis() {

        try {

            return jedisPool.getResource();

        } catch ( Exception e ) {
            Tools.log.error(e);
            return null;
        }

    }

    @Override
    public void release(Jedis jedis) {
        JedisUtil.close(jedis);
    }
}
