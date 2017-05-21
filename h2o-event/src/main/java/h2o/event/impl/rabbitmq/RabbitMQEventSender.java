package h2o.event.impl.rabbitmq;

import com.rabbitmq.client.Channel;
import h2o.common.exception.ExceptionUtil;
import h2o.event.Event;
import h2o.event.EventSender;

/**
 * Created by zhangjianwei on 2017/5/20.
 */
public class RabbitMQEventSender implements EventSender {

    private final RabbitMQEventHelper helper;

    public RabbitMQEventSender(RabbitMQEventHelper helper) {
        this.helper = helper;
    }

    @Override
    public void putEvents( Event... events ) {

        Channel channel = null;
        try {
            channel = helper.connection.createChannel();

            for ( Event event : events ) {
                byte[] body = helper.encode(event);
                channel.basicPublish(helper.exchange, helper.routingKey, null, body );
            }

        } catch ( Exception e ) {
            e.printStackTrace();
            throw ExceptionUtil.toRuntimeException(e);
        } finally {
            if ( channel != null ) {
                try {
                    channel.close();
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
        }

    }



}
