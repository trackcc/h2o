package h2o.common.thirdparty.rabbitmq;

import com.rabbitmq.client.*;
import h2o.common.exception.ExceptionUtil;

import java.io.IOException;
import java.util.Map;

/**
 * Created by zhangjianwei on 2017/5/22.
 */
public class RabbitMQUtil {


    private final Connection connection;
    private final Channel channel;

    private final boolean closeConnection;

    public RabbitMQUtil(ConnectionFactory connectionFactory) {

        try {

            this.connection = connectionFactory.newConnection();
            this.channel = connection.createChannel();

            this.closeConnection = true;

        } catch ( Exception e ) {
            throw ExceptionUtil.toRuntimeException(e);
        }

    }

    public RabbitMQUtil( Connection connection ) {

        try {

            this.connection = connection;
            this.channel = connection.createChannel();

            this.closeConnection = false;

        } catch ( Exception e ) {
            throw ExceptionUtil.toRuntimeException(e);
        }

    }



    public AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, String type) throws IOException {
        return channel.exchangeDeclare(exchange, type);
    }

    public AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type) throws IOException {
        return channel.exchangeDeclare(exchange, type);
    }

    public AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, String type, boolean durable) throws IOException {
        return channel.exchangeDeclare(exchange, type, durable);
    }

    public AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable) throws IOException {
        return channel.exchangeDeclare(exchange, type, durable);
    }

    public AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments) throws IOException {
        return channel.exchangeDeclare(exchange, type, durable, autoDelete, arguments);
    }

    public AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, Map<String, Object> arguments) throws IOException {
        return channel.exchangeDeclare(exchange, type, durable, autoDelete, arguments);
    }

    public AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments) throws IOException {
        return channel.exchangeDeclare(exchange, type, durable, autoDelete, internal, arguments);
    }

    public AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments) throws IOException {
        return channel.exchangeDeclare(exchange, type, durable, autoDelete, internal, arguments);
    }

    public void exchangeDeclareNoWait(String exchange, String type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments) throws IOException {
        channel.exchangeDeclareNoWait(exchange, type, durable, autoDelete, internal, arguments);
    }

    public void exchangeDeclareNoWait(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments) throws IOException {
        channel.exchangeDeclareNoWait(exchange, type, durable, autoDelete, internal, arguments);
    }

    public AMQP.Exchange.DeclareOk exchangeDeclarePassive(String name) throws IOException {
        return channel.exchangeDeclarePassive(name);
    }

    public AMQP.Exchange.DeleteOk exchangeDelete(String exchange, boolean ifUnused) throws IOException {
        return channel.exchangeDelete(exchange, ifUnused);
    }

    public void exchangeDeleteNoWait(String exchange, boolean ifUnused) throws IOException {
        channel.exchangeDeleteNoWait(exchange, ifUnused);
    }

    public AMQP.Exchange.DeleteOk exchangeDelete(String exchange) throws IOException {
        return channel.exchangeDelete(exchange);
    }

    public AMQP.Exchange.BindOk exchangeBind(String destination, String source, String routingKey) throws IOException {
        return channel.exchangeBind(destination, source, routingKey);
    }

    public AMQP.Exchange.BindOk exchangeBind(String destination, String source, String routingKey, Map<String, Object> arguments) throws IOException {
        return channel.exchangeBind(destination, source, routingKey, arguments);
    }

    public void exchangeBindNoWait(String destination, String source, String routingKey, Map<String, Object> arguments) throws IOException {
        channel.exchangeBindNoWait(destination, source, routingKey, arguments);
    }

    public AMQP.Exchange.UnbindOk exchangeUnbind(String destination, String source, String routingKey) throws IOException {
        return channel.exchangeUnbind(destination, source, routingKey);
    }

    public AMQP.Exchange.UnbindOk exchangeUnbind(String destination, String source, String routingKey, Map<String, Object> arguments) throws IOException {
        return channel.exchangeUnbind(destination, source, routingKey, arguments);
    }

    public void exchangeUnbindNoWait(String destination, String source, String routingKey, Map<String, Object> arguments) throws IOException {
        channel.exchangeUnbindNoWait(destination, source, routingKey, arguments);
    }

    public AMQP.Queue.DeclareOk queueDeclare() throws IOException {
        return channel.queueDeclare();
    }

    public AMQP.Queue.DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) throws IOException {
        return channel.queueDeclare(queue, durable, exclusive, autoDelete, arguments);
    }

    public void queueDeclareNoWait(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) throws IOException {
        channel.queueDeclareNoWait(queue, durable, exclusive, autoDelete, arguments);
    }

    public AMQP.Queue.DeclareOk queueDeclarePassive(String queue) throws IOException {
        return channel.queueDeclarePassive(queue);
    }

    public AMQP.Queue.DeleteOk queueDelete(String queue) throws IOException {
        return channel.queueDelete(queue);
    }

    public AMQP.Queue.DeleteOk queueDelete(String queue, boolean ifUnused, boolean ifEmpty) throws IOException {
        return channel.queueDelete(queue, ifUnused, ifEmpty);
    }

    public void queueDeleteNoWait(String queue, boolean ifUnused, boolean ifEmpty) throws IOException {
        channel.queueDeleteNoWait(queue, ifUnused, ifEmpty);
    }

    public AMQP.Queue.BindOk queueBind(String queue, String exchange, String routingKey) throws IOException {
        return channel.queueBind(queue, exchange, routingKey);
    }

    public AMQP.Queue.BindOk queueBind(String queue, String exchange, String routingKey, Map<String, Object> arguments) throws IOException {
        return channel.queueBind(queue, exchange, routingKey, arguments);
    }

    public void queueBindNoWait(String queue, String exchange, String routingKey, Map<String, Object> arguments) throws IOException {
        channel.queueBindNoWait(queue, exchange, routingKey, arguments);
    }

    public AMQP.Queue.UnbindOk queueUnbind(String queue, String exchange, String routingKey) throws IOException {
        return channel.queueUnbind(queue, exchange, routingKey);
    }

    public AMQP.Queue.UnbindOk queueUnbind(String queue, String exchange, String routingKey, Map<String, Object> arguments) throws IOException {
        return channel.queueUnbind(queue, exchange, routingKey, arguments);
    }

    public AMQP.Queue.PurgeOk queuePurge(String queue) throws IOException {
        return channel.queuePurge(queue);
    }


    public void close() {

        if( this.channel != null ) {
            try {
                this.channel.close();
            } catch (Exception e) {
            }
        }

        if( this.closeConnection && this.connection != null ) {
            try {
                this.connection.close();
            } catch (Exception e) {
            }
        }

    }
}
