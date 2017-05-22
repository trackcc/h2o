package h2o.event.impl;


import h2o.event.EventProcessor;
import h2o.event.EventReceiver;

/**
 * Created by zjw on 16-6-30.
 */
public abstract class AbstractEventReceiver implements EventReceiver {



    protected EventProcessor eventManager;

    @Override
    public void setEventManager(EventProcessor em) {
        this.eventManager = em;
    }



}
