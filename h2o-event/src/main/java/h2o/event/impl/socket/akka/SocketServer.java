package h2o.event.impl.socket.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.io.Tcp;
import akka.io.Tcp.Bound;
import akka.io.Tcp.CommandFailed;
import akka.io.Tcp.Connected;
import akka.io.TcpMessage;

import java.net.InetSocketAddress;

public class SocketServer extends UntypedActor {

 	private final int port;

	private final ActorRef manager;

	private final Processor processor;

	public SocketServer( ActorRef manager, int port , Processor processor ) {
		this.manager = manager;
		this.port = port;
		this.processor = processor;
	}


	@Override
	public void preStart() throws Exception {
		System.out.println("start...");
		final ActorRef tcp = Tcp.get(getContext().system()).manager();
		tcp.tell(TcpMessage.bind(getSelf(), new InetSocketAddress("0.0.0.0", this.port), 100), getSelf());
	}



	@Override
	public void onReceive(Object msg) throws Exception {

		if (msg instanceof Bound) {
			manager.tell(msg, getSelf());

		} else if (msg instanceof CommandFailed) {
			getContext().stop(getSelf());

		} else if (msg instanceof Connected) {
			final Connected conn = (Connected) msg;
			manager.tell(conn, getSelf());
			final ActorRef handler = getContext().actorOf(Props.create( MessageHandler.class , processor ));
			getSender().tell(TcpMessage.register(handler), getSelf());
		}

	}
	

}
