package h2o.common.collections;

import h2o.common.collections.builder.MapBuilder;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class DefaultValueMapProxy<K,V> implements Map<K,V> , java.io.Serializable {

	private static final long serialVersionUID = -4166293604583296163L;

	private final Map<K,V> realMap;
	
	private final V defVal;
	private final boolean transContainsNullValue;
	
	public DefaultValueMapProxy( V defVal , boolean transContainsNullValue ) {
		this.defVal = defVal;
		this.transContainsNullValue = transContainsNullValue;
		this.realMap = MapBuilder.newMap();
	}
	
	public DefaultValueMapProxy( Map<K,V> realMap, V defVal , boolean transContainsNullValue ) {
		this.defVal = defVal;
		this.transContainsNullValue = transContainsNullValue;
		this.realMap = realMap;
	}
	


	public int size() {
		return realMap.size();
	}

	public boolean isEmpty() {
		return realMap.isEmpty();
	}

	public boolean containsKey(Object key) {
		return realMap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return realMap.containsValue(value);
	}

	public V get(Object key) {
		V v = realMap.get(key);
		if( v == null ) {
			if( transContainsNullValue || !realMap.containsKey(key) ) {
				return this.defVal;
			}
		}
		
		return v;
	}

	public V put(K key, V value) {
		return realMap.put(key, value);
	}

	public V remove(Object key) {
		return realMap.remove(key);
	}

	public void putAll(Map<? extends K, ? extends V> t) {
		realMap.putAll(t);
	}

	public void clear() {
		realMap.clear();
	}

	public Set<K> keySet() {
		return realMap.keySet();
	}

	public Collection<V> values() {
		return realMap.values();
	}

	public Set<Entry<K, V>> entrySet() {
		return realMap.entrySet();		
	}

	public boolean equals(Object o) {
		return realMap.equals(o);
	}

	public int hashCode() {
		return realMap.hashCode();
	}

	@Override
	public String toString() {
		return realMap.toString();
	}
	
	

}
