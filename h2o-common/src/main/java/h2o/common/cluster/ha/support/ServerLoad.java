package h2o.common.cluster.ha.support;

import h2o.common.cluster.ha.ServerInfo;
import h2o.common.cluster.ha.ServerState;
import h2o.common.cluster.util.algorithm.Balance;

public class ServerLoad<T> implements Balance<ServerInfo<T>> {


	@SuppressWarnings("rawtypes")
	public int getWeight(ServerInfo<T> s) {
		
		int weight =  s instanceof WeightedServerInfo ? ((WeightedServerInfo)s).getWeight() : 10;
		
		
		ServerState state = s.getServerState();
		if( state == ServerState.DOWNTIME  ) {
			return -1;
		} else if( state == ServerState.BUSY  ) {
			return weight * 10;
		} else {
			return weight;
		}
		
	}



}
