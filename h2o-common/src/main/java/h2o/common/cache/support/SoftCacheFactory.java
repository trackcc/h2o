package h2o.common.cache.support;

import h2o.common.cache.Cache;
import h2o.common.cache.CacheFactory;

public class SoftCacheFactory<T> implements CacheFactory<T> {

	public Cache<T> createCacheObject(T o) {
		return new SoftCache<T>(o);
	}

}
