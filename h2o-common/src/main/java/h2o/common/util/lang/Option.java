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

    public Option<E> def( E def ) {
        this.def = def;
        return this;
    }


    public E get() {
        return val(obj);
    }

    public E get( Object key ) {
        return val( ((Map)obj).get(key) );
    }

    public E get( int index ) {
        return val( ((List)obj).get(index) );
    }

    private E val( Object o ) {
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
