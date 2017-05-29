package h2o.event.impl.rabbitmq;

import com.rabbitmq.client.Channel;
import h2o.common.exception.ExceptionUtil;
import h2o.event.Event;
import h2o.event.EventSender;

/**
 * Created by zhangjianwei on 2017/5/20.
 */
public class RabbitMQEventSender implements EventSender {

    protected final RabbitMQEventHelper helper;

    public RabbitMQEventSender(RabbitMQEventHelper helper) {
        this.helper = helper;
    }

    protected Channel channel;

    @Override
    public void putEvents( Event... events ) {


        try {
            if ( channel == null ) {
                channel = helper.connection.createChannel();
            }

            for ( Event event : events ) {
                this.send( event );
            }

        } catch ( Exception e ) {

            e.printStackTrace();
            throw ExceptionUtil.toRuntimeException(e);

        }

    }


    protected void send( Event event ) throws Exception {
        byte[] body = helper.encode(event);
        channel.basicPublish(helper.exchange, helper.routingKey, null, body );
    }


    public void close() {

        if ( channel != null ) {

            try {
                channel.close();
            } catch ( Exception e ) {
                e.printStackTrace();
            }

            channel = null;
        }

        helper.close();


    }



}
