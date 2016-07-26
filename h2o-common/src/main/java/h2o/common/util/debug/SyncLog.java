package h2o.common.util.debug;

public class SyncLog {
	
	private SyncLog() {};
	
	public static final Object SYNC_OBJECT = new Object();
	
	public static void log( LogCallback lcb ) {
		synchronized(SYNC_OBJECT) {
			lcb.doLogs();
		}
	}

}
