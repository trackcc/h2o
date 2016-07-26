package h2o.common.concurrent;

import h2o.common.util.collections.builder.MapBuilder;
import h2o.common.util.collections.link.DNode;
import h2o.common.util.collections.link.DoublyLinkedList;
import h2o.common.util.collections.tuple.Tuple2;
import h2o.common.util.collections.tuple.TupleUtil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;




public class FixedLenCache<K,V> {
	
	
	
	private final int maxlen;

	private final int elasticSize;
	
	
	private volatile boolean readReg = true ;	

	private Lock lock = Locks.newLock();

	public FixedLenCache(int maxlen, int elasticSize) {
		this.maxlen = maxlen;
		this.elasticSize = elasticSize;
	}
	
	public FixedLenCache(int maxlen, int elasticSize , boolean readReg ) {
		this.maxlen = maxlen;
		this.elasticSize = elasticSize;
		this.readReg = readReg;
	}
	
	
	public void setReadReg(boolean readReg) {
		this.readReg = readReg;
	}




	private final ConcurrentHashMap<K, Tuple2<V,DNode<K>>> cache = MapBuilder.newConcurrentHashMap();
	
	private final DoublyLinkedList<K> dlink = new DoublyLinkedList<K>();

	
	@SuppressWarnings("unchecked")
	public void put(Object k, Object v) {

		lock.lock();
		try {

			if ( dlink.size() >= (maxlen + elasticSize)) {
				clear();
			}
			
			this.remove(k);

			DNode<K> node = new DNode<K>((K)k);
			cache.put((K)k, TupleUtil.t((V)v,node));
			dlink.addToTail(node);
			
			

		} finally {
			lock.unlock();
		}
		
	}



	public V get(Object k) {		
		
		if( this.readReg ) {
			
			lock.lock();
			try {
				
				Tuple2<V, DNode<K>> t = cache.remove(k);
				if( t == null ) {
					return null;
				}
				
				put(k,t.e0);
				
				return t.e0;
				
			} finally {
				lock.unlock();
			}
			
		} else {
			
			Tuple2<V, DNode<K>> t = cache.get(k);
			return t == null ? null : t.e0;
			
		}
	}

	public V remove(Object k) {
		lock.lock();
		try {
			Tuple2<V, DNode<K>> t = cache.remove(k);
			if( t == null ) {
				return null;
			}
			dlink.remove(t.e1);
			return t.e0;
		} finally {
			lock.unlock();
		}
	}

	private void clear() {
		for (int i = 0; i < elasticSize; i++) {		
			cache.remove( dlink.remove(dlink.getHead() ) );			
		}		
	}
	
	
	public int size() {		
		return dlink.size();
	}

	

}
