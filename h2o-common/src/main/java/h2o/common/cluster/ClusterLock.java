package h2o.common.cluster;

import h2o.common.Tools;
import h2o.common.thirdparty.redis.JedisCallBack;
import h2o.common.thirdparty.redis.JedisUtil;
import h2o.common.util.id.UuidUtil;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

public class ClusterLock {

    private static final long DEFAULT_TIME_OUT = 30000;

    private final String id = UuidUtil.getUuid();

    private final JedisUtil jedisUtil;

    private final Jedis _jedis;

    private final String key;

    private final int expire;

    volatile boolean run = true;

    private volatile boolean locked = false;


    public ClusterLock( JedisUtil jedisUtil, String key, int expire ) {
        this.jedisUtil = jedisUtil;
        this._jedis = null;
        this.key = "H2OClusterLock_" + key;
        this.expire = expire;
    }

    public ClusterLock( Jedis jedis, String key, int expire ) {
        this.jedisUtil = null;
        this._jedis = jedis;
        this.key = "H2OClusterLock_" + key;
        this.expire = expire;
    }


    private void tryLock( Jedis jedis ) {

        if (jedis.setnx(key, id) == 1 || (id.equals(jedis.get(key)))) {

            jedis.expire(key, expire);
            locked = true;

        } else {

            locked = false;

        }

    }

    public boolean tryLock() {

        try {

            if ( jedisUtil == null ) {

                tryLock( this._jedis );

            } else jedisUtil.callback(new JedisCallBack<Void>() {

                @Override
                public Void doCallBack(Jedis jedis) throws Exception {

                    tryLock( jedis );

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

                TimeUnit.MILLISECONDS.sleep(50 );

            } catch (InterruptedException e) {
            }

        } while ( System.currentTimeMillis() - t < timeout);

        return false;

    }



    private void unlock( Jedis jedis ) {

        if ( id.equals( jedis.get(key) ) ) {
            jedis.del(key);
        }

    }

    public void unlock() {

        locked = false;

        try {

            if ( jedisUtil == null ) {

                unlock( this._jedis );

            } else jedisUtil.callback(new JedisCallBack<Void>() {

                @Override
                public Void doCallBack(Jedis jedis) throws Exception {

                    unlock( jedis );

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
