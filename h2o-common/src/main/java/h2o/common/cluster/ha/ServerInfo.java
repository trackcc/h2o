package h2o.common.cluster.ha;

public interface ServerInfo<T> {
	
	String getType();
	
	String getId();
	
	T getServer();
	
	ServerState getServerState();
	
	void setServerState(ServerState state);

}
