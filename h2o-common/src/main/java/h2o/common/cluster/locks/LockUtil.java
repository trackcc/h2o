package h2o.common.cluster.locks;

import h2o.common.Tools;
import h2o.common.cluster.locks.impl.HazelcastLockProvider;
import h2o.common.util.lang.InstanceUtil;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;

import java.util.concurrent.locks.Lock;

public class LockUtil {
	
	private static volatile LockProvider lockProvider;	
	
	
	public static void setLockProvider(LockProvider lockProvider) {
		lock.lock();
		try {
			LockUtil.lockProvider = lockProvider;
			Tools.log.info(" CluserLockProvider : {} " , LockUtil.lockProvider.getClass().getName() );
		} finally {
			lock.unlock();
		}
	}

	private static void init() {
		
			try {
				PropertiesConfiguration config = new PropertiesConfiguration("cluser.properties");
			
				String lockProviderClazz = config.getString("lockProvider", null);
				Tools.log.debug(" LockProvider in cluser.properties : {} " , lockProviderClazz );
				if( StringUtils.isNotBlank(lockProviderClazz) ) {					
					setLockProvider( (LockProvider)InstanceUtil.newInstance( Class.forName( lockProviderClazz ) ) );
				} else {
					Tools.log.debug(" Use default LockProvider ." );
					setLockProvider(new HazelcastLockProvider());
				}
				
				
			} catch (Throwable e) {	
				System.out.println(e.getMessage());
				Tools.log.debug("",e);
				
				setLockProvider(new HazelcastLockProvider());
				
			}
			
			
			
		
	}
	
	private static Lock lock = new java.util.concurrent.locks.ReentrantLock();

	public static Lock getLock( String id ) {
		LockProvider lp = lockProvider;
		if( lp == null ) {
			lock.lock();
			try {
				lp = lockProvider;
				if( lockProvider == null ) {
					init();
					lp = lockProvider;
				}
			} finally {
				lock.unlock();
			}
			
		}
		return lp.getLock(id);
	}
	
	
	
	public static void delLock(String id) {		
		if( lockProvider != null ) {
			lockProvider.delLock(id);
		}
	}

}