package h2o.event.impl.rabbitmq;

import com.rabbitmq.client.*;
import h2o.common.exception.ExceptionUtil;
import h2o.event.Event;
import h2o.event.EventReceiver;
import h2o.event.impl.AbstractEventReceiver;

import java.io.IOException;


/**
 * Created by zhangjianwei on 2017/5/20.
 */
public class RabbitMQEventReceiver extends AbstractEventReceiver implements EventReceiver {


    private final RabbitMQEventHelper helper;
    private final Channel channel;

    public RabbitMQEventReceiver(RabbitMQEventHelper helper) {
        this.helper = helper;

        try {
            this.channel = helper.connection.createChannel();
        } catch ( Exception e ) {
            e.printStackTrace();
            throw ExceptionUtil.toRuntimeException(e);
        }
    }



    @Override
    public void start() {

        new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    channel.basicConsume(helper.queue, true, new Consumer() {

                        @Override
                        public void handleConsumeOk(String consumerTag) {

                        }

                        @Override
                        public void handleCancelOk(String consumerTag) {

                        }

                        @Override
                        public void handleCancel(String consumerTag) throws IOException {

                        }

                        @Override
                        public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {

                        }

                        @Override
                        public void handleRecoverOk(String consumerTag) {

                        }

                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                            Event event = helper.parse(body);
                            eventManager.onEvent(event);
                        }

                    });

                } catch ( Exception e ) {
                    e.printStackTrace();
                    throw ExceptionUtil.toRuntimeException(e);
                }

            }
        }).start();
    }

    @Override
    public void stop() {
        try {
            this.channel.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }


}
