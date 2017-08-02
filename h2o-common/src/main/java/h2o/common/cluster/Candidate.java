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

    private final JedisUtil jedisUtil;

    private final String topic;

    private final String id = UuidUtil.getUuid();


    private volatile boolean run = true;


    private volatile boolean locked;

    public static final int LOCK_TIME_OUT = 120;



    public Candidate( JedisUtil jedisUtil, String topic ) {
        this.jedisUtil = jedisUtil;
        this.topic = "H2OCandidate_" + topic;
    }

    public void attend() {

        Assert.isTrue( run == true , "Exited" );

        RunUtil.call(new Runnable() {
            @Override
            public void run() {

                while ( run ) {

                    try {

                        jedisUtil.callback(new JedisCallBack<Void>() {

                            @Override
                            public Void doCallBack(Jedis jedis) throws Exception {

                                if ( jedis.setnx( topic, id ) == 1 ||  ( id.equals( jedis.get(topic) ) ) ) {
                                    jedis.expire( topic, LOCK_TIME_OUT );
                                    locked = true;
                                } else {
                                    locked = false;
                                }

                                return null;
                            }

                        });

                    } catch ( Exception e ) {

                        e.printStackTrace();
                        Tools.log.error(e);

                        locked = false;

                    }

                    try {

                        TimeUnit.SECONDS.sleep(30 );

                    } catch ( InterruptedException e ) {
                        Thread.currentThread().interrupt();
                    }


                }

                try {

                    jedisUtil.callback(new JedisCallBack<Void>() {

                        @Override
                        public Void doCallBack(Jedis jedis) throws Exception {

                            if ( id.equals( jedis.get(topic) ) ) {
                                jedis.del(topic);
                            }

                            return null;

                        }

                    });

                } catch ( Exception e ) {

                    e.printStackTrace();
                    Tools.log.error(e);

                }

            }
        });
    }


    public void exit() {
        this.run = false;
    }


    public boolean isMe() {
        return locked;
    }

}
