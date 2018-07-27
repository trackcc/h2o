package h2o.event.impl.local;

import h2o.common.exception.ExceptionUtil;
import h2o.event.Event;
import h2o.event.EventSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangjianwei on 16/7/3.
 */
public class LocalEventSender implements EventSender {

    private static final Logger log = LoggerFactory.getLogger( LocalEventSender.class.getName() );

    private final LocalEventHelper helper;

    public LocalEventSender(LocalEventHelper redisHelper ) {
        this.helper = redisHelper;
    }


    @Override
    public void putEvents( final Event... events) {

        for ( Event event : events ) {

            try {

                this.helper.eventController.putProduct(event);

            } catch ( Exception e ) {

                log.error("",e);
                throw ExceptionUtil.toRuntimeException(e);

            }

        }

    }



}
