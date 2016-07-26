package h2o.common.cluster.ha.support;

import h2o.common.cluster.ha.LoadBalance;
import h2o.common.cluster.ha.ServerInfo;
import h2o.common.cluster.ha.ServerState;
import h2o.common.cluster.util.algorithm.WeightGroup;
import h2o.common.concurrent.Locks;
import h2o.common.util.collections.builder.MapBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class WeightedLoadBalance<T> implements LoadBalance<T> {
	
	private final ReadWriteLock rwlock = Locks.newReadWriteLock();
	
	private final Map<String,List<ServerInfo<T>>> smap = MapBuilder.newConcurrentHashMap();
	
	private final Map<String,WeightGroup<ServerInfo<T>>> wmap = MapBuilder.newConcurrentHashMap();

	private final ServerLoad<T> serverLoad = new ServerLoad<T>();
	
	public ServerInfo<T> getServer(String type) {
		
		Lock rlock = rwlock.readLock();
		rlock.lock();
		try {	

			WeightGroup<ServerInfo<T>> w = wmap.get(type);
	
			return w.draw();
		
		} finally {
			rlock.unlock();
		}
		

	}
	
	
	public ServerInfo<T> getServer(String type, String id) {
		
	
		Lock rlock = rwlock.readLock();
		rlock.lock();
		try {	
		
			List<ServerInfo<T>> sl = smap.get(type);
			for( ServerInfo<T> s : sl ) {
				if( s.getId().equals(id) ) {
					return s;
				}
			}
			return null;
			
		} finally {
			rlock.unlock();
		}
		
	}
	
	public ServerState getServerState(String type, String id) {		
		return this.getServer(type,id).getServerState();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void regServer(ServerInfo<T> s) {
		
		Lock lock = rwlock.writeLock();
		lock.lock();
		try {		
			List<ServerInfo<T>> sl = smap.get(s.getType());
			WeightGroup<ServerInfo<T>> w = wmap.get(s.getType());
			if( sl == null ) {
				sl = new ArrayList<ServerInfo<T>>();
				smap.put(s.getType() , sl);
				
				w = new WeightGroup<ServerInfo<T>>();
				w.setBalance(serverLoad);
				wmap.put(s.getType() , w);
			}
			
			sl.add(s);
			w.addWeight(s);
			
		
		} finally {
			lock.unlock();
		}
		
		
	}
	

	public void setServerState(String type, String id, ServerState state) {
		
			ServerInfo<T> s = this.getServer(type,id);			
			s.setServerState(state);		
		
	}


	

}
