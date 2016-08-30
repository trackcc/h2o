package h2o.common.util.collections;

import java.util.Collection;

/**
 * Created by zhangjianwei on 16/8/23.
 */
public interface EachCallback<E,T> {

    T init();

    void doEach( E e , T t );

    T result( T t );

}
