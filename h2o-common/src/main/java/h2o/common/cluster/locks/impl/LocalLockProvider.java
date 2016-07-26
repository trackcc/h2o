package h2o.common.cluster.locks.impl;

import h2o.common.cluster.locks.LockProvider;
import h2o.common.concurrent.LockMap;
import h2o.common.concurrent.Locks;

import java.util.concurrent.locks.Lock;

public class LocalLockProvider implements LockProvider {
	
	private final LockMap lockMap = Locks.newLockMap();

	public Lock getLock(String id) {
		return lockMap.getLock(id);
	}

	public void delLock(String id) {
		lockMap.removeLock(id);
	}

}
