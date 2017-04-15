package h2o.common.util.collections;

/**
 * Created by zhangjianwei on 16/8/23.
 */
public interface EachCallback<E,T> {

    T init();

    void doEach( E e , T t );

    T result( T t );

}
