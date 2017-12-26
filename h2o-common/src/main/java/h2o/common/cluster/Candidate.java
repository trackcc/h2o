package h2o.common.cluster;

import h2o.common.concurrent.RunUtil;
import h2o.common.thirdparty.redis.JedisUtil;
import h2o.common.thirdparty.spring.util.Assert;

import java.util.concurrent.TimeUnit;

public class Candidate {

    private final ClusterLock lock;

    private final int timeout;

    public Candidate( JedisUtil jedisUtil, String topic , int timeout ) {

        Assert.isTrue( timeout > 0  , "'timeout' value must be greater than 0" );
        this.timeout = timeout;
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

                        TimeUnit.MILLISECONDS.sleep(500 );

                    } catch ( InterruptedException e ) {
                    }


                }

                lock.unlock();


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
