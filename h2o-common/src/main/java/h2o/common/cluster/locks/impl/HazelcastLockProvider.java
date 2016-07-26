package h2o.common.cluster.locks.impl;

import h2o.common.cluster.locks.LockProvider;
import h2o.common.cluster.util.HazelcastUtil;

import java.util.concurrent.locks.Lock;


public class HazelcastLockProvider implements LockProvider {
	
	private static final String HZ_NAME = "h2o_lock";

	public Lock getLock( String id) {		
		return HazelcastUtil.instance(HZ_NAME).getLock( id );
	}

	public void delLock(String id) {
		HazelcastUtil.instance(HZ_NAME).getLock( id ).destroy();
	}
	
	



}

