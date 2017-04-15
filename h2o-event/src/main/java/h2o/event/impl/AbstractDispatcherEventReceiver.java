package h2o.event.impl;


import h2o.common.Tools;
import h2o.common.schedule.Dispatcher;
import h2o.common.schedule.RepetitiveTask;
import h2o.event.Event;
import h2o.event.EventReceiver;

import java.util.List;

/**
 * Created by zjw on 16-6-30.
 */
public abstract class AbstractDispatcherEventReceiver extends AbstractEventReceiver implements EventReceiver {


    private final Dispatcher d = new Dispatcher();

    public AbstractDispatcherEventReceiver( long freeSleepTime, long okSleepTime, long errSleepTime ) {
        d.setFreeSleepTime(freeSleepTime);
        d.setOkSleepTime(okSleepTime);
        d.setErrSleepTime(errSleepTime);
    }

    @Override
    public void start() {

        Tools.log.info("EventReceiver start...");

        d.start( new RepetitiveTask() {
            @Override
            public int doTask() throws Throwable {

                List<Event> events = recvEvent();
                if( events == null || events.isEmpty() ) {
                    return 0;
                } else {
                    for( Event event : events ) {
                        eventManager.onEvent(event);
                    }
                    return 1;
                }


            }
        });
    }

    @Override
    public void stop() {
        d.stop();
    }


    protected abstract List<Event> recvEvent();


}
