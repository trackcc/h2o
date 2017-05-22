package h2o.event.impl.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import h2o.common.Tools;
import h2o.event.Event;
import h2o.event.EventReceiver;
import h2o.event.impl.AbstractEventReceiver;

import java.io.IOException;


/**
 * Created by zhangjianwei on 2017/5/20.
 */
public class RabbitMQEventReceiver extends AbstractEventReceiver implements EventReceiver {


    private final RabbitMQEventHelper helper;

    public RabbitMQEventReceiver(RabbitMQEventHelper helper) {
        this.helper = helper;
    }




    private volatile boolean stop = false;

    private final Thread recvThread = new Thread(new Runnable() {

        @Override
        public void run() {

            while ( !stop ) {

                Channel channel = null;
                try {

                    channel = helper.connection.createChannel();

                    channel.basicConsume(helper.queue, true, new DefaultConsumer(channel) {

                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                            Event event = helper.parse(body);
                            eventManager.onEvent(event);
                        }

                    });

                } catch ( Exception e ) {

                    e.printStackTrace();
                    Tools.log.error(e);

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
    });




    @Override
    public void start() {

        this.recvThread.start();
    }

    @Override
    public void stop() {

        this.stop = true;
        this.recvThread.interrupt();

    }


}
