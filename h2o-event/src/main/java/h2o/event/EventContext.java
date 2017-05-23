package h2o.event;

/**
 * Created by zhangjianwei on 2017/5/23.
 */
public interface EventContext {

    void confirm();

    void reply( Object r );

}
