package h2o.common.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class SmartMap<K, V> implements Map<K, V> , Cloneable, Serializable {
	

	private static final long serialVersionUID = -4389818603436235559L;
	
	private Map<K,V> m0 = new HashMap<K,V>();
	private Map<K,V> m1 = new HashMap<K,V>();	
	
	public void init( Map<? extends K, ? extends V> m ) {
		this.putAll(m);
		this.commit();
	}
	
	public Map<K, V> getOldMap() {
		Map<K,V> m = new HashMap<K,V>();
		m.putAll(m0);
		return m;
	}
	
	public Map<K, V> toNormalMap() {
		Map<K,V> m = new HashMap<K,V>();
		m.putAll(m1);
		return m;
	}


	public int size() {
		return m1.size();
	}

	public boolean isEmpty() {
		return m1.isEmpty();
	}

	public boolean containsKey(Object key) {
		return m1.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return m1.containsValue(value);
	}

	public V get(Object key) {
		return m1.get(key);
	}

	public V put(K key, V value) {
		return m1.put(key, value);
	}

	public V remove(Object key) {
		return m1.remove(key);
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		m1.putAll(m);
	}

	public void clear() {
		m1.clear();
	}

	public Set<K> keySet() {
		return m1.keySet();
	}

	public Collection<V> values() {
		return m1.values();
	}

	public Set<Entry<K, V>> entrySet() {
		return m1.entrySet();
	}

	public boolean equals(Object o) {
		return m1.equals(o);
	}

	public int hashCode() {
		return m1.hashCode();
	}
	

	
	
	@Override
	public String toString() {
		return m1.toString();
	}
	
	
	

	public void commit() {
		m0.clear();
		m0.putAll(m1);
	}
	
	public void rollback() {		
		m1.clear();
		m1.putAll(m0);
	}

}
