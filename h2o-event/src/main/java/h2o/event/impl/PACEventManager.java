package h2o.event.impl;


import h2o.common.Tools;
import h2o.common.concurrent.pac.Consumer;
import h2o.common.concurrent.pac.ConsumersController;
import h2o.common.util.collections.builder.MapBuilder;
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


    private final Map<String,EventHandler>  ehs = MapBuilder.newConcurrentHashMap();

    private final ConsumersController<Event> cc;

    public PACEventManager( int n ) {
    	
        cc = new ConsumersController<Event>(Executors.newFixedThreadPool(n),new ArrayBlockingQueue<Event>(n));
        cc.addConsumers( new Consumer<Event>() {

            @Override
            public void consume(Event event) {
                EventHandler eventHandler = ehs.get(event.getEventType());
                if( eventHandler == null ) {
                    Tools.log.error("没有对应的事件处理器[{}]",event);
                } else {
                    eventHandler.proc(event);
                }
            }

        } , n );
    }


    @Override
    public void onEvent(Event event) {
        cc.offerProduct(event);
    }

    @Override
    public void regEventHandler(String eventType, EventHandler eventHandler) {
        ehs.put(eventType,eventHandler);
    }



    // Spring
    public void setEventHandlers(Map<String, EventHandler> ehs) {
        this.ehs.putAll(ehs);
    }

    public void done() {
        cc.done();
    }
}
