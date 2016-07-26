package h2o.event;

/**
 * Created by zjw on 16-6-30.
 */
public interface EventReceiver {

    void setEventManager(EventManager em);

    void start();

    void stop();

}
