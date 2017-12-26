package h2o.common.cache.support;


import h2o.common.concurrent.TimesOut;
import h2o.common.cache.Cache;

public class TimeCache<T> implements Cache<T> {


    private final long timeout;

    private final int times;

    private volatile TimesOut timesOut;


    private volatile T data;

    public TimeCache(long timeout, int times) {
        this.timeout = timeout;
        this.times = times;
    }

    @Override
    public void set(T o) {
        this.data = o;
        this.timesOut = new TimesOut( timeout , times );
    }

    public T get() {

        if ( data == null ) {
            return null;
        }
		
		if ( timesOut.out() ) {
		    return null;
        }
		
		return data;

	}

    @Override
    public T clear() {

        T d = data;
        this.data = null;
        this.timesOut = null;

        return d;
    }


}
