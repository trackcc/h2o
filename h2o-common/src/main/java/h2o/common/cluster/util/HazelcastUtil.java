package h2o.common.cluster.util;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public final class HazelcastUtil {
	
	private HazelcastUtil() {}
	
	public static HazelcastInstance instance( String name ) {
		
		HazelcastInstance hz = Hazelcast.getHazelcastInstanceByName(name);
		if( hz == null ) {
			synchronized(name) {
				try {
					hz = Hazelcast.getHazelcastInstanceByName(name);
					if( hz == null ) {
						Config hzConfig = new Config();
						hzConfig.setGroupConfig( new GroupConfig().setName(name));
						hzConfig.setInstanceName(name);
						hz = Hazelcast.newHazelcastInstance(hzConfig);
					}
				} catch( Exception e ) {
					hz = Hazelcast.getHazelcastInstanceByName(name);
				} 
			}
		}
		
		return hz;
		
	}

}
