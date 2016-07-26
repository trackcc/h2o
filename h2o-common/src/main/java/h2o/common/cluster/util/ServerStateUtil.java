package h2o.common.cluster.util;

import h2o.common.Tools;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ServerStateUtil {
	
	private ServerStateUtil() {}
	

	public static String checkServerPort( String host , int port , int timeout ) {
		
		Socket s = null;
		
		try {
			
			s = new Socket();
			s.connect( new InetSocketAddress(host,port),timeout);
						
			return null;
			
		} catch (Exception e) {			
			String err = e.getMessage();
			return err == null ? "NULL" : err;
		} finally {
			if( s != null) {
				try {
					s.close();
				} catch (IOException e) {					
					e.printStackTrace();
					Tools.log.debug( "Socket.close()", e );
				}
			}
		}
	}
	
	public static boolean checkServerPortOk( String host , int port , int timeout ) {
		return checkServerPort(host, port, timeout) == null;
	}

}
