package h2o.common.cache;

public interface CacheFactory<T> {

	Cache<T> createCacheObject(T o);

}
