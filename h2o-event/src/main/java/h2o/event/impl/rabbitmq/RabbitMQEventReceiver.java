package h2o.event.impl.rabbitmq;

import com.rabbitmq.client.*;
import h2o.common.Tools;
import h2o.event.Event;
import h2o.event.EventReceiver;
import h2o.event.impl.AbstractEventReceiver;
import h2o.event.impl.NothingEventContext;

import java.io.IOException;


/**
 * Created by zhangjianwei on 2017/5/20.
 */
public class RabbitMQEventReceiver extends AbstractEventReceiver implements EventReceiver {


    protected final RabbitMQEventHelper helper;

    public RabbitMQEventReceiver(RabbitMQEventHelper helper) {
        this.helper = helper;
    }



    protected volatile Channel channel;


    @Override
    public void start() {

        try {

            channel = helper.connection.createChannel();
            this.recv();

        } catch ( Exception e ) {

            e.printStackTrace();
            Tools.log.error(e);

            this.close();

        }
    }

    protected void recv() throws Exception {
        channel.basicConsume( helper.queue, true, callback(channel) );
    }


    protected Consumer callback( Channel channel ) {

       return new DefaultConsumer(channel) {

           @Override
           public void handleDelivery( String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body ) throws IOException {
               Event event = helper.parse(body);
               eventProcessor.proc( new NothingEventContext() , event );
           }

       };
    }


    @Override
    public void stop() {
        this.close();
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
