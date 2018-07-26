package h2o.event.impl;

import h2o.common.util.bean.serialize.Base64StrSerializeProxy;
import h2o.common.util.bean.serialize.BeanStrSerializer;
import h2o.common.util.bean.serialize.HessianBeanSerializer;
import h2o.event.Event;
import h2o.event.EventEncoder;

/**
 * Created by zhangjianwei on 2017/5/21.
 */
public class StringEventEncoder implements EventEncoder<String> {

    private final BeanStrSerializer beanSerialize = new Base64StrSerializeProxy( new HessianBeanSerializer() );

    @Override
    public Event parse(String sEvent) {
        return (Event) beanSerialize.string2bean( sEvent );
    }

    @Override
    public String encode(Event event) {
        return beanSerialize.bean2string( event );
    }

}
