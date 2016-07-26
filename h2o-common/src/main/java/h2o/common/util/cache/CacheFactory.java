package h2o.common.util.cache;

public interface CacheFactory<T> {

	Cache<T> createCacheObject(T o);

}
