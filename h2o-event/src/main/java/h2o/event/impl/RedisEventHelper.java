package h2o.event.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import h2o.common.Tools;
import h2o.common.redis.JedisUtil;
import h2o.common.util.collections.builder.MapBuilder;
import h2o.event.Event;
import h2o.event.EventEncoder;

/**
 * Created by zhangjianwei on 16/7/3.
 */
public class RedisEventHelper {

    final String eventQueueName;

    final JedisUtil jedisUtil;

    public RedisEventHelper(JedisUtil jedisUtil ) {
        this(jedisUtil, "EventQueue");
    }

    public RedisEventHelper(JedisUtil jedisUtil , String eventQueue ) {
        this.jedisUtil = jedisUtil;
        this.eventQueueName = eventQueue;
    }

    private final Map<String,EventEncoder<String>> ees = MapBuilder.newConcurrentHashMap();


    Event parse(String strEvent ) {

        String type = StringUtils.substringBefore( strEvent , ":" );

        EventEncoder<String> eventEncoder = ees.get( type );
        if( eventEncoder == null ) {
            Tools.log.error("没有对应的解码处理器[{}]",type);
            return null;
        }

        String se = StringUtils.substringAfter( strEvent , ":" );

        return eventEncoder.parse( se );

    }

    String encode( Event event ) {

        if( event == null ) {
            return null;
        }

        String type = event.getEventType();
        EventEncoder<String> eventEncoder = ees.get( type );
        if( eventEncoder == null ) {
            Tools.log.error("没有对应的编码处理器[{}]",type);
            return null;
        }

        return type + ":" + eventEncoder.encode( event );

    }


    public RedisEventHelper regEventEncoder(String eventType, EventEncoder<String> eventEncoder ) {
        ees.put(eventType,eventEncoder);
        return this;
    }


    // Spring
    public void setEventEncoders(Map<String, EventEncoder<String>> ees) {
        if( ees != null && !ees.isEmpty()) {
            for( Map.Entry<String, EventEncoder<String>> ee : ees.entrySet() ) {
                regEventEncoder(ee.getKey(),ee.getValue());
            }
        }
    }


}
