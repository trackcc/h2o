package h2o.event;

/**
 * Created by zjw on 16-6-30.
 */
public interface EventProcessor {

    void proc( EventContext context , Event event );

}
