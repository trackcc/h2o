package h2o.common.util.cache.support;

import h2o.common.util.cache.Cache;
import h2o.common.util.cache.CacheFactory;

public class SoftCacheFactory<T> implements CacheFactory<T> {

	public Cache<T> createCacheObject(T o) {
		return new SoftCache<T>(o);
	}

}
