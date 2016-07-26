package h2o.common.util.cache.support;

import h2o.common.util.cache.Cache;

import java.lang.ref.SoftReference;



public class SoftCache<T> implements Cache<T> {

	private volatile SoftReference<T> sr;

	public SoftCache() {}

	public SoftCache(T obj) {
		this.set(obj);
	}

	public void set(T obj) {
		sr = obj == null ? null : new SoftReference<T>(obj);
	}

	public T get() {
		return sr == null ? null : sr.get();
	}
	
	public T clear() {
		T obj = this.get();
		sr.clear();
		sr = null;
		return obj;
	}

}
