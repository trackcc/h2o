package h2o.event;

/**
 * Created by zhangjianwei on 16/7/2.
 */
public interface EventEncoder<S> {

    Event parse(S strEvent);

    S encode(Event event);

}
