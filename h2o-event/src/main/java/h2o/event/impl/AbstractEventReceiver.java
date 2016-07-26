package h2o.event.impl;


import h2o.event.EventManager;
import h2o.event.EventReceiver;

/**
 * Created by zjw on 16-6-30.
 */
public abstract class AbstractEventReceiver implements EventReceiver {



    protected EventManager eventManager;

    @Override
    public void setEventManager(EventManager em) {
        this.eventManager = em;
    }



}
