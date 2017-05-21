package h2o.event.impl.redis;

import h2o.common.redis.JedisUtil;
import h2o.common.util.collections.builder.MapBuilder;
import h2o.event.Event;
import h2o.event.EventEncoder;
import h2o.event.impl.StringEventEncoderProxy;

import java.util.Map;

/**
 * Created by zhangjianwei on 16/7/3.
 */
public class RedisEventHelper {

    final String eventQueueName;

    final JedisUtil jedisUtil;

    private final StringEventEncoderProxy eventEncoderProxy;

    public RedisEventHelper( StringEventEncoderProxy eventEncoderProxy , JedisUtil jedisUtil ) {
        this(eventEncoderProxy , jedisUtil, "EventQueue");
    }

    public RedisEventHelper(StringEventEncoderProxy eventEncoderProxy , JedisUtil jedisUtil , String eventQueue ) {
        this.eventEncoderProxy = eventEncoderProxy;
        this.jedisUtil = jedisUtil;
        this.eventQueueName = eventQueue;
    }

    private final Map<String,EventEncoder<String>> ees = MapBuilder.newConcurrentHashMap();


    Event parse(String strEvent ) {
        return eventEncoderProxy.parse( strEvent );
    }

    String encode( Event event ) {
        return eventEncoderProxy.encode( event );
    }

}
