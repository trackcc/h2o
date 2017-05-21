package h2o.event.encoder;

import h2o.common.util.bean.serialize.BeanStrSerialize;
import h2o.common.util.bean.serialize.JsonBeanSerialize;
import h2o.event.Event;
import h2o.event.EventEncoder;

/**
 * Created by zhangjianwei on 2017/5/21.
 */
public class StringEventEncoder implements EventEncoder<String> {

    private final BeanStrSerialize beanSerialize = new JsonBeanSerialize();

    @Override
    public Event parse(String sEvent) {
        return (Event) beanSerialize.string2bean( sEvent );
    }

    @Override
    public String encode(Event event) {
        return beanSerialize.bean2string( event );
    }
}
