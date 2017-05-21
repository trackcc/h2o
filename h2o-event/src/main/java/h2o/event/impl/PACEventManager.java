package h2o.event.impl;


import h2o.common.concurrent.pac.Consumer;
import h2o.common.concurrent.pac.ConsumersController;
import h2o.event.Event;
import h2o.event.EventHandler;
import h2o.event.EventManager;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;

/**
 * Created by zhangjianwei on 16/7/2.
 */
public class PACEventManager implements EventManager {


    private final DefaultEventManager defEventManager = new DefaultEventManager();

    private final ConsumersController<Event> cc;

    public PACEventManager( int n ) {
    	
        cc = new ConsumersController<Event>(Executors.newFixedThreadPool(n),new ArrayBlockingQueue<Event>(n));
        cc.addConsumers( new Consumer<Event>() {

            @Override
            public void consume(Event event) {
                defEventManager.onEvent( event );
            }

        } , n );
    }


    @Override
    public void onEvent(Event event) {
        cc.offerProduct(event);
    }

    @Override
    public void regEventHandler(String eventType, EventHandler eventHandler) {
        defEventManager.regEventHandler( eventType , eventHandler );
    }

    public void setEventHandlers(Map<String, EventHandler> ehs) {
        defEventManager.setEventHandlers(ehs);
    }

    public void done() {
        cc.done();
    }


}
