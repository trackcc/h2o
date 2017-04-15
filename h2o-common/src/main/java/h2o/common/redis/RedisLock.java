package h2o.common.redis;

import h2o.common.Tools;
import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @author Teaey
 */
public class RedisLock {
    //加锁标志
    public static final String LOCKED = "TRUE";
    public static final long ONE_MILLI_NANOS = 1000000L;
    //默认超时时间（毫秒）
    public static final long DEFAULT_TIME_OUT = 3000;
    
    public final Random random = new Random();

    //锁的超时时间（秒），过期删除
    public final int expire;
    
    private final Jedis jedis;
    private final String key;
    //锁状态标志
    private boolean locked = false;

    public RedisLock(String key , Jedis jedis ) {
        this(key , jedis , 5 * 60 );
    }
    
    public RedisLock(String key , Jedis jedis , int expire ) {
        this.key = key;
        this.jedis = jedis;
        this.expire = expire;
    }

    public boolean lock(long timeout) {
        long nano = System.nanoTime();
        timeout *= ONE_MILLI_NANOS;
        try {
            while ((System.nanoTime() - nano) < timeout) {
                if (jedis.setnx(key, LOCKED) == 1) {
                    jedis.expire(key, expire);
                    locked = true;
                    return locked;
                }
               
                Thread.sleep(50 + random.nextInt(50) );
            }
        } catch (Exception e) {
        	Tools.log.error("",e);
        }
        return false;
    }
    public boolean lock() {
        return lock(DEFAULT_TIME_OUT);
    }

    // 无论是否加锁成功，必须调用
	public void unlock() {
		if (locked) {
			jedis.del(key);
		}
	}
}