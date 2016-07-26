package h2o.event.impl;

import h2o.common.redis.JedisCallBack;
import h2o.event.Event;
import h2o.event.EventSender;
import redis.clients.jedis.Jedis;

/**
 * Created by zhangjianwei on 16/7/3.
 */
public class RedisEventSender implements EventSender {

    private RedisEventHelper redisHelper;

    public RedisEventSender( RedisEventHelper redisHelper ) {
        this.redisHelper = redisHelper;
    }


    @Override
    public void putEvents( final Event... events) {

        redisHelper.jedisUtil.callback(new JedisCallBack<Void>() {

            @Override
            public Void doCallBack(Jedis jedis) throws Exception {

                for( Event event : events ) {
                    String strEvent = redisHelper.encode(event);
                    if( strEvent != null ) {
                        jedis.lpush( redisHelper.eventQueueName , strEvent );
                    }
                }

                return null;
            }

        } );

    }



}
