package h2o.event.impl.socket.akka;

import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;
import akka.io.Tcp.ConnectionClosed;
import akka.io.Tcp.Received;
import akka.io.TcpMessage;
import akka.util.ByteString;

public class MessageHandler extends UntypedActor {


    private final Processor processor;
    private final Data data = new Data();

	public MessageHandler( Processor processor ) {
	    this.processor = processor;
    }

	@Override
	public void onReceive(Object msg) throws Exception {

		if (msg instanceof Received) {

			final ByteString in = ((Received) msg).data();

			if ( data.putBlock(in) ) {

				String req = data.toString();
				data.close();

                String r = processor.proc( req );

                ByteString rdata = data.encode2ByteString(r);

                getSender().tell(TcpMessage.write(rdata), getSelf());

			}

        } else if (msg instanceof ReceiveTimeout) {

            data.close();
            getSender().tell(TcpMessage.write( new Data().encode2ByteString("timeout")) , getSelf());
            getContext().stop(getSelf());

        } else if (msg instanceof ConnectionClosed) {

			data.close();
			getContext().stop(getSelf());

		}
	}



}
