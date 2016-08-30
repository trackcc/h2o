package h2o.common.util.collections;

/**
 * Created by zhangjianwei on 16/8/30.
 */
public abstract class VoidEachCallback<E> implements EachCallback<E,Void> {

    @Override
    final public Void init() {
        return null;
    }

    @Override
    final public void doEach(E e, Void v) {
        this.doEach(e);
    }

    @Override
    final public Void result(Void aVoid) {
        return null;
    }

    abstract protected void doEach( E e );
}
