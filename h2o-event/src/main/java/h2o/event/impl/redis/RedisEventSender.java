package h2o.event.impl.redis;

import h2o.common.redis.JedisCallBack;
import h2o.event.Event;
import h2o.event.EventSender;
import redis.clients.jedis.Jedis;

/**
 * Created by zhangjianwei on 16/7/3.
 */
public class RedisEventSender implements EventSender {

    private final RedisEventHelper helper;

    public RedisEventSender( RedisEventHelper redisHelper ) {
        this.helper = redisHelper;
    }


    @Override
    public void putEvents( final Event... events) {

        helper.jedisUtil.callback(new JedisCallBack<Void>() {

            @Override
            public Void doCallBack(Jedis jedis) throws Exception {

                for( Event event : events ) {
                    String strEvent = helper.encode(event);
                    if( strEvent != null ) {
                        jedis.lpush( helper.eventQueueName , strEvent );
                    }
                }

                return null;
            }

        } );

    }



}
