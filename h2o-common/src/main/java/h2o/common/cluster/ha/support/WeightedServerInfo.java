package h2o.common.cluster.ha.support;

import h2o.common.cluster.ha.ServerInfo;

public class WeightedServerInfo<T> extends ServerInfoImpl<T> implements ServerInfo<T> {
	
	private int weight;

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public WeightedServerInfo() {}
	
	public WeightedServerInfo( int weight ) {
		this.weight = weight;
	}

}
