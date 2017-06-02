package h2o.event.impl.socket;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.io.Tcp;
import com.typesafe.config.ConfigFactory;
import h2o.common.Tools;
import h2o.event.Event;
import h2o.event.EventEncoder;
import h2o.event.EventReceiver;
import h2o.event.impl.AbstractEventReceiver;
import h2o.event.impl.NothingEventContext;
import h2o.event.impl.socket.akka.Processor;
import h2o.event.impl.socket.akka.SocketServer;

/**
 * Created by zhangjianwei on 16/7/2.
 */
public class SocketEventReceiver extends AbstractEventReceiver implements EventReceiver , Processor {


    protected final EventEncoder<String> eventEncoder;

    private final int port;

    private final String configPath;

    public SocketEventReceiver( EventEncoder<String> eventEncoder , int port ) {
        this( eventEncoder, port , null );
    }

    public SocketEventReceiver( EventEncoder<String> eventEncoder  , int port , String configPath ) {
        this.eventEncoder = eventEncoder;
        this.configPath = configPath;
        this.port = port;
    }


    // 创建系统
    private ActorSystem system = null;

    @Override
    public void start() {

        if ( this.configPath == null ) {
            system = ActorSystem.create("EventServer");
        } else {
            system = ActorSystem.create("EventServer", ConfigFactory.load( this.configPath ) );
        }

        final ActorRef tcpManager = Tcp.get(system).getManager();

        // 创建一个接待者
        system.actorOf(Props.create(SocketServer.class,tcpManager,port , this ), "EventServer");

    }

    @Override
    public void stop() {

        if ( system != null ) {
            system.shutdown();
        }

    }

    @Override
    public String proc(String msg) {

        try {

            Event event = eventEncoder.parse(msg);

            eventProcessor.proc( new NothingEventContext() , event );

        } catch ( Exception e ) {
            Tools.log.error(e);
        }

        return "ok";

    }
}
