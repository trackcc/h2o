package h2o.common.cluster;

import h2o.common.Tools;
import h2o.common.concurrent.RunUtil;
import h2o.common.redis.JedisCallBack;
import h2o.common.redis.JedisUtil;
import h2o.common.spring.util.Assert;
import h2o.common.util.id.UuidUtil;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

public class Candidate {

    private final ClusterLock lock;

    public Candidate( JedisUtil jedisUtil, String topic , int timeout ) {
       this.lock = new ClusterLock( jedisUtil , "H2OCandidate_" + topic , timeout );
    }

    public void attend() {

        Assert.isTrue( lock.run == true , "Exited" );

        RunUtil.call(new Runnable() {
            @Override
            public void run() {

                while ( lock.run ) {

                    lock.tryLock();

                    try {

                        TimeUnit.SECONDS.sleep(30 );

                    } catch ( InterruptedException e ) {
                        Thread.currentThread().interrupt();
                    }


                }

               lock.ulock();

            }
        });
    }


    public void exit() {
        lock.run = false;
    }


    public boolean isMe() {
        return lock.isLocked();
    }

}
