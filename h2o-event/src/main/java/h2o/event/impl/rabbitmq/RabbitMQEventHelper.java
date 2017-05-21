package h2o.event.impl.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import h2o.common.exception.ExceptionUtil;
import h2o.event.Event;
import h2o.event.impl.StringEventEncoderProxy;

/**
 * Created by zhangjianwei on 16/7/3.
 */
public class RabbitMQEventHelper {

    private final ConnectionFactory connectionFactory;
    private final StringEventEncoderProxy eventEncoderProxy;

    final Connection connection;

    String exchange;
    String routingKey;
    String queue;


    public RabbitMQEventHelper( StringEventEncoderProxy eventEncoderProxy , ConnectionFactory connectionFactory ) {
        this.eventEncoderProxy = eventEncoderProxy;
        this.connectionFactory = connectionFactory;

        try {
            this.connection = connectionFactory.newConnection();
        } catch ( Exception e ) {
            throw ExceptionUtil.toRuntimeException(e);
        }

    }



    Event parse( byte[] bytesEvent ) {

        try {

            String strEvent = new String( bytesEvent , "UTF-8");

            return eventEncoderProxy.parse(strEvent);

        } catch ( Exception e ) {
            throw ExceptionUtil.toRuntimeException(e);
        }


    }

    byte[] encode( Event event ) {

        String strEvent = eventEncoderProxy.encode( event );

        try {

            return strEvent.getBytes("UTF-8");

        } catch ( Exception e ) {
            throw ExceptionUtil.toRuntimeException(e);
        }

    }


    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }
}
