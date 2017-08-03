package h2o.common.cluster;

import h2o.common.Tools;
import h2o.common.redis.JedisCallBack;
import h2o.common.redis.JedisUtil;
import h2o.common.util.id.UuidUtil;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

public class ClusterLock {

    public static final long DEFAULT_TIME_OUT = 30000;

    private final String id = UuidUtil.getUuid();

    private final JedisUtil jedisUtil;

    private final String key;

    public  final int expire;

    volatile boolean run = true;

    private volatile boolean locked = false;


    public ClusterLock( JedisUtil jedisUtil, String key, int expire ) {
        this.jedisUtil = jedisUtil;
        this.key = "H2OClusterLock_" + key;
        this.expire = expire;
    }



    public boolean tryLock() {

        try {

            jedisUtil.callback(new JedisCallBack<Void>() {

                @Override
                public Void doCallBack(Jedis jedis) throws Exception {

                    if (jedis.setnx(key, id) == 1 || (id.equals(jedis.get(key)))) {

                        jedis.expire(key, expire);
                        locked = true;

                    } else {

                        locked = false;

                    }

                    return null;

                }

            });

        } catch (Exception e) {

            e.printStackTrace();
            Tools.log.error(e);

            locked = false;

        }

        return locked;

    }


    public boolean lock() {
        return lock( DEFAULT_TIME_OUT );
    }

    public boolean lock( long timeout ) {

        long t = System.currentTimeMillis();


        do {

            if ( tryLock() ) {
                return true;
            }

            try {

                TimeUnit.MILLISECONDS.sleep(500);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }


        } while ( System.currentTimeMillis() - t < timeout);

        return false;

    }



    public void unlock() {

        locked = false;

        try {

            jedisUtil.callback(new JedisCallBack<Void>() {

                @Override
                public Void doCallBack(Jedis jedis) throws Exception {

                    if ( id.equals( jedis.get(key) ) ) {
                        jedis.del(key);
                    }

                    return null;

                }

            });

        } catch ( Exception e ) {

            e.printStackTrace();
            Tools.log.error(e);

        }

    }

    public boolean isLocked() {
        return run && locked;
    }
}
