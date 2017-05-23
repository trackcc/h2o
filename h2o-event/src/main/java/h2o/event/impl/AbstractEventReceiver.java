package h2o.event.impl;


import h2o.event.EventContext;
import h2o.event.EventProcessor;
import h2o.event.EventReceiver;

/**
 * Created by zjw on 16-6-30.
 */
public abstract class AbstractEventReceiver implements EventReceiver {



    protected EventProcessor eventProcessor;

    @Override
    public void setEventProcessor(EventProcessor ep) {
        this.eventProcessor = ep;
    }


}
