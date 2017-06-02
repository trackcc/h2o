package h2o.event.impl.redis;

import h2o.common.Tools;
import h2o.common.redis.JedisCallBack;
import h2o.common.util.collections.builder.ListBuilder;
import h2o.event.Event;
import h2o.event.EventReceiver;
import h2o.event.impl.AbstractDispatcherEventReceiver;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by zhangjianwei on 16/7/2.
 */
public class RedisEventReceiver extends AbstractDispatcherEventReceiver implements EventReceiver {

    private final RedisEventHelper helper;

    public RedisEventReceiver( RedisEventHelper redisHelper ) {
        super( 30000 , 10 , 300000 );
        this.helper = redisHelper;
    }


    @Override
    protected List<Event> recvEvent() {

        final List<Event> events = ListBuilder.newList();

        helper.jedisUtil.callback(new JedisCallBack<Void>() {

            @Override
            public Void doCallBack(Jedis jedis) throws Exception {

                for(int i = 0 ; i < 5 ; i++ ) {
                    String strEvent = jedis.rpop(helper.eventQueueName);
                    if( strEvent == null ) {
                        break;
                    }
                    Event event = helper.parse(strEvent);
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
