package h2o.common.util.bean;

import jodd.bean.BeanUtil;

/**
 * Created by zhangjianwei on 2017/5/15.
 */
public class DataGetter {

    private final BeanUtil beanUtil ;

    private final Object data;

    public DataGetter(Object data) {
        this( data , false );
    }

    public DataGetter(Object data , boolean silent ) {
        this.data = data;
        beanUtil = silent ? BeanUtil.forcedSilent : BeanUtil.forced;
    }


    public <T> T get(String pname ) {
        return (T)beanUtil.getProperty(data,pname);
    }

}
