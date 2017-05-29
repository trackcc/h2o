package h2o.event.impl.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import h2o.common.exception.ExceptionUtil;
import h2o.event.Event;
import h2o.event.EventEncoder;

/**
 * Created by zhangjianwei on 16/7/3.
 */
public class RabbitMQEventHelper {

    private final ConnectionFactory connectionFactory;
    private final EventEncoder<String> eventEncoder;

    public final Connection connection;

    public final String exchange;
    public final String routingKey;
    public final String queue;


    public RabbitMQEventHelper( EventEncoder<String> eventEncoder , ConnectionFactory connectionFactory , String exchange , String routingKey , final String queue ) {

        this.eventEncoder = eventEncoder;
        this.connectionFactory = connectionFactory;

        this.exchange = exchange;
        this.routingKey = routingKey;
        this.queue = queue;

        try {
            this.connection = connectionFactory.newConnection();
        } catch ( Exception e ) {
            throw ExceptionUtil.toRuntimeException(e);
        }

    }



    public Event parse( byte[] bytesEvent ) {

        try {

            String strEvent = new String( bytesEvent , "UTF-8");

            return eventEncoder.parse(strEvent);

        } catch ( Exception e ) {
            throw ExceptionUtil.toRuntimeException(e);
        }


    }

    public byte[] encode( Event event ) {

        String strEvent = eventEncoder.encode( event );

        try {

            return strEvent.getBytes("UTF-8");

        } catch ( Exception e ) {
            throw ExceptionUtil.toRuntimeException(e);
        }

    }


    public void close() {
        if ( this.connection != null ) {
            try {
                this.connection.close();
            } catch ( Exception e ) {}
        }
    }




}
