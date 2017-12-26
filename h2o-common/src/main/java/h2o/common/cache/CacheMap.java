package h2o.common.cache;

import h2o.common.cache.support.ClassCacheFactory;
import h2o.common.cache.support.SoftCacheFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CacheMap<K, V> implements Map<K, V> {

	private final ConcurrentHashMap<K, Cache<V>> cacheMap = new ConcurrentHashMap<K, Cache<V>>();

	private volatile CacheFactory<V> cf;

	private V getV(Cache<V> c) {
		return c == null ? null : c.get();
	}

	public CacheMap() {
		this(new SoftCacheFactory<V>());
	}

	public CacheMap(Class<Cache<V>> clazz) {
		this(new ClassCacheFactory<V>(clazz));
	}

	public CacheMap(CacheFactory<V> cf) {
		this.cf = cf;
	}

	public int size() {
		return cacheMap.size();
	}

	public boolean isEmpty() {
		return cacheMap.isEmpty();
	}

	public boolean containsKey(Object key) {
		return cacheMap.containsKey(key);
	}

	public V get(Object key) {
		return getV(cacheMap.get(key));
	}

	public V put(K key, V v) {
		cacheMap.put(key, v == null ? null : cf.createCacheObject(v));
		return v;
	}
	
	public V putIfAbsent(K key, V v) {
		return getV( cacheMap.putIfAbsent(key, v == null ? null : cf.createCacheObject(v)) );
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		for (Entry<? extends K, ? extends V> e : m.entrySet())
			put(e.getKey(), e.getValue());
	}

	public V remove(Object key) {
		return getV(cacheMap.remove(key));
	}

	public void clear() {
		cacheMap.clear();
	}

	public Set<K> keySet() {
		return cacheMap.keySet();
	}

	public boolean equals(Object o) {
		return cacheMap.equals(o);
	}

	public int hashCode() {
		return cacheMap.hashCode();
	}

	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	public Collection<V> values() {
		throw new UnsupportedOperationException();
	}

	public Set<Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}



}
