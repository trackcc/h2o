package h2o.event.impl.socket;

import h2o.common.redis.JedisCallBack;
import h2o.common.util.web.SocketClient;
import h2o.event.Event;
import h2o.event.EventEncoder;
import h2o.event.EventSender;
import h2o.event.impl.socket.akka.Processor;
import redis.clients.jedis.Jedis;

/**
 * Created by zhangjianwei on 16/7/3.
 */
public class SocketEventSender implements EventSender {

    protected final EventEncoder<String> eventEncoder;


    private final SocketClient socketClient = new SocketClient();

    public SocketEventSender(EventEncoder<String> eventEncoder , String server , int port ) {
        this.eventEncoder = eventEncoder;
        this.socketClient.setServer( server );
        this.socketClient.setPort(port);
    }


    @Override
    public void putEvents( final Event... events) {

        for ( Event event : events ) {
            this.send(event);
        }

    }


    protected void send( Event event ) {

        String msg = eventEncoder.encode(event);

        this.socketClient.send( msg );

    }



    public void setTimeout(int timeout) {
        this.socketClient.setTimeout( timeout );
    }


}
