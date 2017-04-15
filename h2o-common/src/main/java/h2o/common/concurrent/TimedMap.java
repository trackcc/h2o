package h2o.common.concurrent;

import h2o.common.Tools;
import h2o.common.schedule.Dispatcher;
import h2o.common.schedule.RepetitiveTask;
import h2o.common.util.collections.tuple.Tuple2;
import h2o.common.util.collections.tuple.Tuple3;
import h2o.common.util.collections.tuple.TupleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;



public class TimedMap<K,V> {
	
	class LongV {
		
		public final AtomicLong v;
		
		public LongV( long l) {
			v = new AtomicLong(l);
		}
	}
	
	class ObjectV {
		
		public final Object v;
		
		public ObjectV( Object v) {
			this.v = v;
		}

		@Override
		public int hashCode() {
			return this.v.hashCode();
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ObjectV other = (ObjectV) obj;
			return this.v == other.v;
		}

		
	}
	
	private final ConcurrentHashMap<K, Tuple2<LongV, V>> m = new ConcurrentHashMap<K, Tuple2<LongV, V>>();
	
	private final ConcurrentHashMap<ObjectV , Tuple3<K ,LongV, V>> rm = new ConcurrentHashMap<ObjectV , Tuple3<K ,LongV, V>>();
	
	
	private final Dispatcher autoManager = new Dispatcher();
	
	
	private volatile TimedMapListener timedMapListener;	
	
	private final Lock lock = new java.util.concurrent.locks.ReentrantLock();
	

	public void setTimedMapListener(TimedMapListener timedMapListener) {
		this.timedMapListener = timedMapListener;
	}
	
	
	

	public TimedMap() {
		start( 30 * 60 );
	}
	
	public TimedMap( int timeout ) {
		start( timeout );
	}
	
	
	public boolean containsKey( K key ) {
		return m.containsKey(key);
	}
	
	public void put( K key , V value ) {
		
		lock.lock();
		try {
			rm.remove(new ObjectV( value ));
			
			Tuple2<LongV,V> t = m.get(key);
			if( t != null && timedMapListener != null && t.e1 != value && !timedMapListener.onPutNewValue(key, t.e1, value )) {
				rm.put(new ObjectV(t.e1), TupleUtil.t3(key, t.e0, t.e1));
			}
			
			m.put(key, TupleUtil.t( new LongV(System.currentTimeMillis()), value) );
			
		} finally {
			lock.unlock();
		}
		
		
	}
	
	public V putIfAbsent( K key , V value ) {
		return m.putIfAbsent( key, TupleUtil.t( new LongV(System.currentTimeMillis()), value) ).e1;
	}
	
	public V get(  K key ) {
		Tuple2<LongV,V> t = m.get(key);
		if( t == null ) {
			return null;
		} else {
			t.e0.v.set(System.currentTimeMillis());
			return t.e1;
		}
	}
	
	public V remove( K key ) {
		Tuple2<LongV,V> t = m.remove(key);
		if( t == null ) {
			return null;
		} else {
			fireRemoveEvent( key , t.e1 );
		}
		
		return t.e1;
	}
	
	
	private void fireRemoveEvent( K key , V value ) {
		if( timedMapListener != null ) {
			try {
				timedMapListener.onRemoved( key, value );
			} catch( Throwable e ) {
				e.printStackTrace();
				Tools.log.info("",e);
			}
		}
	}
	
	
	
	
	private void start( final int timeout ) {
		
		autoManager.start( new RepetitiveTask() {

			public int doTask() {
				if( m.isEmpty() && rm.isEmpty() ) {
					return 0;
				} else {
					
					List<K> ks = new ArrayList<K>( m.size() ); 
					
					long timeoutTimeMillis = System.currentTimeMillis() - timeout * 1000;
					
					for( Entry<K, Tuple2<LongV, V>> e : m.entrySet() ) {
						if( e.getValue().e0.v.longValue() <  timeoutTimeMillis ) {
							ks.add(e.getKey());
						}
					}
					
					
					
					if( ! ks.isEmpty() ) {
						for( K k : ks ) {
							remove( k );
						}
					}
					

					lock.lock();
					try {						
					
						List<ObjectV> rs = new ArrayList<ObjectV>(); 
						for( Entry<ObjectV, Tuple3<K, LongV, V>> e : rm.entrySet() ) {
							Tuple3<K, LongV, V> t = e.getValue();
							if( t.e1.v.longValue() <  timeoutTimeMillis ) {
								fireRemoveEvent( t.e0 , t.e2 );	
								rs.add(e.getKey());
							}						
						}
						
						
						if( ! rs.isEmpty() ) {
							for( ObjectV k : rs ) {
								rm.remove( k );
							}
						}
					
					} finally {
						lock.unlock();
					}
					
					
					return 1;
				}
			}
			
			
			
			
		} , timeout * 1000 , timeout * 200 , timeout * 1000);
	}
	
	
	public void stop() {
		autoManager.stop();
	}

}
