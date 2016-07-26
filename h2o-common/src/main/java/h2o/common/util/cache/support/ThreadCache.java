package h2o.common.util.cache.support;

import h2o.common.util.cache.Cache;

public class ThreadCache<T> implements Cache<T> {
	
	
	private final ThreadLocal<T> tc = new ThreadLocal<T>();
	

	public T clear() {
		T o = this.get();
		this.tc.remove();
		return o;
	}
	
	public T get() {
		return this.tc.get();
	}

	
	public void set(T o) {
		if(o != null) this.tc.set(o);
	}

}
