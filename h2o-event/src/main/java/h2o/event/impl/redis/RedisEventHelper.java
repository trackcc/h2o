package h2o.event.impl.redis;

import h2o.common.thirdparty.redis.JedisUtil;
import h2o.event.Event;
import h2o.event.EventEncoder;

/**
 * Created by zhangjianwei on 16/7/3.
 */
public class RedisEventHelper {

    public final String eventQueueName;

    public final JedisUtil jedisUtil;

    private final EventEncoder<String> eventEncoder;

    public RedisEventHelper( EventEncoder<String> eventEncoder , JedisUtil jedisUtil ) {
        this(eventEncoder , jedisUtil, "EventQueue");
    }

    public RedisEventHelper( EventEncoder<String> eventEncoder , JedisUtil jedisUtil , String eventQueue ) {
        this.eventEncoder = eventEncoder;
        this.jedisUtil = jedisUtil;
        this.eventQueueName = eventQueue;
    }


    public Event parse(String strEvent ) {
        return eventEncoder.parse( strEvent );
    }

    public String encode( Event event ) {
        return eventEncoder.encode( event );
    }

}
