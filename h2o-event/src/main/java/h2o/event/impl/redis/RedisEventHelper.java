package h2o.event.impl.redis;

import h2o.common.thirdparty.redis.JedisProvider;
import h2o.event.Event;
import h2o.event.EventEncoder;

/**
 * Created by zhangjianwei on 16/7/3.
 */
public class RedisEventHelper {

    public final String eventQueueName;

    public final JedisProvider jedisProvider;

    private final EventEncoder<String> eventEncoder;

    public RedisEventHelper( EventEncoder<String> eventEncoder , JedisProvider jedisProvider ) {
        this(eventEncoder , jedisProvider, "EventQueue");
    }

    public RedisEventHelper(EventEncoder<String> eventEncoder , JedisProvider jedisProvider , String eventQueue ) {
        this.eventEncoder = eventEncoder;
        this.jedisProvider = jedisProvider;
        this.eventQueueName = eventQueue;
    }


    public Event parse(String strEvent ) {
        return eventEncoder.parse( strEvent );
    }

    public String encode( Event event ) {
        return eventEncoder.encode( event );
    }

}
