package h2o.event.impl.local;

import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;
import h2o.event.Event;
import h2o.event.EventSender;

/**
 * Created by zhangjianwei on 16/7/3.
 */
public class LocalEventSender implements EventSender {

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

                Tools.log.error(e);
                throw ExceptionUtil.toRuntimeException(e);

            }

        }

    }



}
