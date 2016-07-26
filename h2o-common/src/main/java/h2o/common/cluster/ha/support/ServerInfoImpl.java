package h2o.common.cluster.ha.support;

import h2o.common.cluster.ha.ServerInfo;
import h2o.common.cluster.ha.ServerState;

public class ServerInfoImpl<T> implements ServerInfo<T> {
	
	
	private String type;
	
	private String id;
	
	private ServerState serverState = ServerState.RUNNING;
	
	private T server;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ServerState getServerState() {
		return serverState;
	}

	public void setServerState(ServerState serverState) {
		this.serverState = serverState;
	}

	public T getServer() {
		return server;
	}

	public void setServer(T server) {
		this.server = server;
	}

	

}
