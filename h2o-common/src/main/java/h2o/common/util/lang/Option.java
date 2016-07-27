package h2o.common.util.lang;

import java.util.List;
import java.util.Map;


public class Option<E> {

    private volatile Object obj;
    private volatile E def;

    public Option() {
        this.def = null;
    }

    public Option( Object obj ) {
        this.obj = obj;
    }

    public Option<E> val(Object obj) {
        this.obj = obj;
        return this;
    }

    public Option<E> def(E def ) {
        this.def = def;
        return this;
    }


    public E get() {
        return value(obj);
    }

    public E get( Object key ) {
        return value( ((Map)obj).get(key) );
    }

    public E get( int index ) {
        return value( ((List)obj).get(index) );
    }

    private E value( Object o ) {
        if( valIsNone(o) ) {
            return def;
        } else {
            return (E) o;
        }
    }

    public boolean isNone() {
        return valIsNone(obj);
    }

    public boolean isNone( Object key ) {
        return valIsNone( ((Map)obj).get(key) );
    }

    public boolean isNone( int index ) {
        return valIsNone( ((List)obj).get(index) );
    }

    protected boolean valIsNone( Object o ) {
        return o == null;
    }



}
