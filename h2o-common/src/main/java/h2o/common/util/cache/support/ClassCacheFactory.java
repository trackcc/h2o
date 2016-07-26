package h2o.common.util.cache.support;

import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;
import h2o.common.util.cache.Cache;
import h2o.common.util.cache.CacheFactory;

public class ClassCacheFactory<T> implements CacheFactory<T> {

	private volatile Class<Cache<T>> cacheClass;

	public ClassCacheFactory(Class<Cache<T>> clazz) {
		this.cacheClass = clazz;
	}

	public Cache<T> createCacheObject(T o) {

		try {

			Cache<T> c = cacheClass.newInstance();
			c.set(o);

			return c;

		} catch (Exception e) {
			Tools.log.debug("createCacheObject", e);
			throw ExceptionUtil.toRuntimeException(e);
		}

	}

}
