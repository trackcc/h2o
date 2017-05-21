package h2o.event.impl;

import h2o.common.Tools;
import h2o.common.util.collections.builder.MapBuilder;
import h2o.event.Event;
import h2o.event.EventHandler;
import h2o.event.EventManager;

import java.util.Map;

/**
 * Created by zhangjianwei on 2017/5/20.
 */
public class BasicEventManager implements EventManager {

    private final Map<String,EventHandler> ehs = MapBuilder.newConcurrentHashMap();


    @Override
    public void onEvent(Event event) {
        EventHandler eventHandler = ehs.get(event.getEventType());
        if( eventHandler == null ) {
            Tools.log.error("没有对应的事件处理器[{}]",event);
        } else {
            eventHandler.proc(event);
        }
    }

    @Override
    public void regEventHandler(String eventType, EventHandler eventHandler) {
        ehs.put(eventType,eventHandler);
    }

    // Spring
    public void setEventHandlers(Map<String, EventHandler> ehs) {
        this.ehs.putAll(ehs);
    }

}
