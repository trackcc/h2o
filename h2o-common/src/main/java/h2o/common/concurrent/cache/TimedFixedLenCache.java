package h2o.common.concurrent.cache;


public class TimedFixedLenCache<K,V> {
	
	private final long timeout;
	
	private final boolean get_reclocking;
	
	private final FixedLenCache<K,CacheObject> cache;
	
	
	private class CacheObject {
		public final V v;
		public long t;
		
		CacheObject( V v ) {
			this.v = v;
			this.t = System.currentTimeMillis();
		}
	}
	
	
	public TimedFixedLenCache( int maxlen, int elasticSize , long timeout ) {
		this( maxlen , elasticSize , timeout , false );
	}
	
	public TimedFixedLenCache( int maxlen, int elasticSize , long timeout , boolean get_reclocking ) {
		 cache = new FixedLenCache<K,CacheObject>( maxlen , elasticSize , get_reclocking );
		 this.timeout = timeout * 1000;
		 this.get_reclocking = get_reclocking;
	}

	@SuppressWarnings("unchecked")
	public void put(Object k, Object v) {		
		cache.put(k, new CacheObject((V)v));
	}

	private V get0( Object k , CacheObject o , boolean isRemove ) {
		if( o == null ) {
			return null;
		}		
		long t = System.currentTimeMillis();		
		if(  t > o.t + this.timeout ) {
			if( !isRemove  ) {
				cache.remove(k);
			}
			return null;
		} else {
			if( get_reclocking && !isRemove ) {
				o.t = System.currentTimeMillis();
			}
			return o.v;
		}		
	}
	
	public V get( Object k ) {
		return get0( k , cache.get(k) , false );
	}

	public V remove(Object k) {
		return get0( k , cache.remove(k) , true );
	}

	public int size() {
		return cache.size();
	}
	
	

}
