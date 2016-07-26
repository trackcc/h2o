package h2o.event;

/**
 * Created by zjw on 16-6-30.
 */
public interface EventManager {

        void onEvent(Event event);

        void regEventHandler(String eventType, EventHandler eventHandler);

}
