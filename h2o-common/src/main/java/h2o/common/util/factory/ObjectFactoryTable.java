package h2o.common.util.factory;

import h2o.common.concurrent.factory.InstanceFactory;
import h2o.common.concurrent.factory.InstanceTable;
import h2o.common.util.cache.support.ThreadCache;

import java.util.concurrent.ConcurrentHashMap;



public class ObjectFactoryTable<T> {
	
	private boolean isSilently;	
	
	
	public ObjectFactoryTable<T> setSilently(boolean isSilently) {
		this.isSilently = isSilently;
		return this;
	}

	private final ConcurrentHashMap<String,ObjectBuilder<T>> builderMap = new ConcurrentHashMap<String , ObjectBuilder<T>>();
	
	private final InstanceTable<String,ThreadCache<T>> threadCacheInstanceTable = new InstanceTable<String,ThreadCache<T>>(new InstanceFactory<ThreadCache<T>>() {

		public ThreadCache<T> create(Object id) {
			return new ThreadCache<T>();
		}

		public void free(Object id, ThreadCache<T> t) {
		}
		
	} );
	
	public ObjectFactoryTable<T> addBuilder( String key , ObjectBuilder<T> builder ) {
		builderMap.put(key, builder);
		return this;
	}
	
	public T getObject( String key , Object... args) {
		ObjectBuilder<T> builder = builderMap.get(key);
		if( builder == null ) {
			if( isSilently ) {
				return null;
			} else {
				throw new RuntimeException( "Builder[" + key + "] not found." );
			}
		} else {
			return builder.build( args );
		}
	}
	
	public T getObjectFromThreadCache( String key , Object... args ) {
		ThreadCache<T> threadCache = threadCacheInstanceTable.get(key,true);
		T t = threadCache.get();
		if( t == null ) {
			t = this.getObject( key , args );
			threadCache.set(t);
		}
		return t;
	}
	
	public T clearThreadCache( String key ) {
		ThreadCache<T> threadCache = threadCacheInstanceTable.get(key,false);		
		if( threadCache == null ) {
			return null;
		} else {
			return threadCache.clear();	
		}
	}

}
