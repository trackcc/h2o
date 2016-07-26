package h2o.common.cluster.locks;

import java.util.concurrent.locks.Lock;

public interface LockProvider {
	
	Lock getLock(String id);
	
	void delLock(String id);

}
