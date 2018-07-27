package h2o.event.impl.local;

import h2o.common.concurrent.pac.Consumer;
import h2o.event.Event;
import h2o.event.EventReceiver;
import h2o.event.impl.AbstractEventReceiver;
import h2o.event.impl.NothingEventContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangjianwei on 16/7/2.
 */
public class LocalEventReceiver extends AbstractEventReceiver implements EventReceiver {

    private static final Logger log = LoggerFactory.getLogger( LocalEventReceiver.class.getName() );

    private final LocalEventHelper helper;

    public LocalEventReceiver(LocalEventHelper helper ) {
        this.helper = helper;
    }

    @Override
    public void start() {

        helper.eventController.addConsumers( new Consumer<Event>() {

            @Override
            public void consume(Event event) {
                proc( event );
            }

        } , helper.n );

        helper.eventController.done();

    }

    @Override
    public void stop() {
        helper.eventController.stop();
    }




    protected void proc( Event event ) {

        try {

            eventProcessor.proc( new NothingEventContext() , event );

        } catch ( Exception e ) {
            e.printStackTrace();
            log.error("",e);
        }

    }
}
