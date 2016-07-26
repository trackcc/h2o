package h2o.common.util.collections;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentNullAbleHashMap<K,V> implements ConcurrentMap<K,V> , java.io.Serializable {
	

	private static final long serialVersionUID = -4547703074737762251L;


	private final ConcurrentHashMap<K,V>  concurrentHashMap = new ConcurrentHashMap<K,V>();
	
	
	private final K defk;
	private final V defv;
	


	@SuppressWarnings("unchecked")
	private K getK( Object k ) {
		if( k == null ) {
			return defk;
		}
		
		return (K)k;
	}
	
	@SuppressWarnings("unchecked")
	private V getV( Object v ) {
		if( v == null ) {
			return defv;
		}
		
		return (V)v;
	}
	
	private V tranV( V v ) {
		if( v == null || v == defv || v.equals(defv) ) {
			return null;
		}
		
		return v;
	}
	
	
	public ConcurrentNullAbleHashMap( K defk , V defv ) {
		this.defk = defk;
		this.defv = defv;
	}


	public boolean equals(Object o) {
		return concurrentHashMap.equals(o);
	}


	public int hashCode() {
		return concurrentHashMap.hashCode();
	}




	public boolean isEmpty() {
		return concurrentHashMap.isEmpty();
	}


	public int size() {
		return concurrentHashMap.size();
	}


	public V get(Object key) {		
		return tranV( concurrentHashMap.get( getK( key ) ) );
	}


	public boolean containsKey(Object key) {
		return concurrentHashMap.containsKey( getK( key ) );
	}


	public boolean containsValue(Object value) {		
		return concurrentHashMap.containsValue( getV( value ) );
	}


	public boolean contains(Object value) {
		return concurrentHashMap.contains( getV( value ) );
	}


	public V put(K key, V value) {
		return tranV(concurrentHashMap.put( getK( key ) ,  getV( value ) ));
	}


	public V putIfAbsent(K key, V value) {
		return concurrentHashMap.putIfAbsent( getK( key ) ,  getV( value ) );
	}


	public void putAll(Map<? extends K, ? extends V> m) {
		for (Entry<? extends K, ? extends V> e : m.entrySet())
            put(e.getKey(), e.getValue());
	}


	public V remove(Object key) {
		return tranV(concurrentHashMap.remove( getK( key ) ));
	}


	public boolean remove(Object key, Object value) {
		return concurrentHashMap.remove( getK( key ) ,  getV( value ) );
	}


	public boolean replace(K key, V oldValue, V newValue) {
		return concurrentHashMap.replace( getK( key ) ,  getV( oldValue )  , getV( newValue )   );
	}


	public V replace(K key, V value) {
		return tranV(concurrentHashMap.replace( getK( key ) , getV( value ) ));
	}


	public void clear() {
		concurrentHashMap.clear();
	}


	public Set<K> keySet() {
		throw new UnsupportedOperationException();
	}


	public Collection<V> values() {
		throw new UnsupportedOperationException();
	}


	public Set<Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}


	public Enumeration<K> keys() {
		throw new UnsupportedOperationException();
	}


	public Enumeration<V> elements() {
		throw new UnsupportedOperationException();
	}
	
	
	
	public String toString() {
		return concurrentHashMap.toString();
	}


}
