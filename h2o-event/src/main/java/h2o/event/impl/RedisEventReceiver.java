package h2o.event.impl;

import java.util.List;

import h2o.common.Tools;
import h2o.common.redis.JedisCallBack;
import h2o.common.util.collections.builder.ListBuilder;
import h2o.event.Event;
import redis.clients.jedis.Jedis;

/**
 * Created by zhangjianwei on 16/7/2.
 */
public class RedisEventReceiver extends AbstractDispatcherEventReceiver {

    private final RedisEventHelper redisHelper;

    public RedisEventReceiver( RedisEventHelper redisHelper ) {
        super( 30000 , 10 , 300000 );
        this.redisHelper = redisHelper;
    }


    @Override
    protected List<Event> recvEvent() {

        final List<Event> events = ListBuilder.newList();

        redisHelper.jedisUtil.callback(new JedisCallBack<Void>() {

            @Override
            public Void doCallBack(Jedis jedis) throws Exception {

                for(int i = 0 ; i < 5 ; i++ ) {
                    String strEvent = jedis.rpop(redisHelper.eventQueueName);
                    if( strEvent == null ) {
                        break;
                    }
                    Event event = redisHelper.parse(strEvent);
                    if( event != null ) {
                        events.add( event );
                    }
                }



                return null;
            }

        });

        Tools.log.debug("recvEvent ========= {} " , events );

        return events;
    }




}
