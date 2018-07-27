package h2o.event.impl;

import h2o.event.Event;
import h2o.event.EventContext;
import h2o.event.EventHandler;
import h2o.event.EventProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangjianwei on 2017/5/22.
 */
public abstract class AbstractEventProcessor implements EventProcessor {

    private static final Logger log = LoggerFactory.getLogger( AbstractEventProcessor.class.getName() );


    @Override
    public void proc( EventContext context , Event event ) {
        EventHandler eventHandler = getEventHandler(event);
        if( eventHandler == null ) {
            log.error("Not found event handler[{}]" , event );
        } else {
            try {
                eventHandler.onEvent( context , event );
            } catch ( Exception e ) {
                log.error("",e);
            }

        }
    }


    protected abstract EventHandler getEventHandler( Event event );

}
