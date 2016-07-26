package h2o.common.cluster.ha;

public interface LoadBalance<T> {
	
	void regServer(ServerInfo<T> serverInfo);
	
	ServerInfo<T> getServer(String type);	
	
	ServerInfo<T> getServer(String type, String id);
	
	ServerState getServerState(String type, String id);
	
	void setServerState(String type, String id, ServerState state);

}
