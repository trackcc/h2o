package h2o.common.util.cache.support;

import h2o.common.util.cache.Cache;
import h2o.common.util.cache.DataCache;


public class TimeCache<T>  implements Cache<T> {
	
	private final long timeout;
	
	private final int maxTimes;	
	
	private volatile DataCache<T> dataCache = null;
	
	public TimeCache( long timeout , int maxTimes ) {
		this.timeout = timeout;
		this.maxTimes = maxTimes;
	}
	
	public DataCache<T> getCache() {
		return dataCache;
	}

	public void set( T data ) {
		dataCache = new DataCache<T>( data );		
	}

	public T get() {
		DataCache<T> d = dataCache;
		if( d == null ) {
			return null;
		}
		return d.getCache(timeout, maxTimes);
	}

	public T clear() {
		DataCache<T> d = dataCache;
		if( d == null ) {
			return null;
		}
		dataCache = null;
		return d.getCache(timeout, maxTimes);
	}
	
	




}
