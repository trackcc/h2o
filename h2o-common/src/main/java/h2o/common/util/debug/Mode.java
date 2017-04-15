package h2o.common.util.debug;

import h2o.common.Tools;
import h2o.common.util.collections.CollectionUtil;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Mode {
	
	public static final String PRODUCTION = "PRODUCTION";
	public static final String DEBUG = "DEBUG";
	public static final String TEST = "TEST";
	
	private static final String mode;
	
	public static final boolean productionMode;
	public static final boolean debugMode;
	public static final boolean testMode;
	
	private static final Set<String> userModeSet = new HashSet<String>();
	
	static {
		
		boolean p = false;
		boolean d = false;
		boolean t = false;
		String m;
		
		try {
			PropertiesConfiguration config = new PropertiesConfiguration("mode.properties");
			
			m = config.getString("mode","PRODUCTION").trim().toUpperCase();			
			
			if( TEST.equals(m) ) {				
				t = true;				
			} else if( DEBUG.equals(m) ) {				
				d = true;				
			} else {				
				p = true;
				m = "PRODUCTION";
				
				Tools.log.info("default mode !!!");
			}
			
			
			
			String userModes = config.getString("userMode","").trim().toUpperCase();
			
			if( !"".equals(userModes) ) {

                List<String> ums =  CollectionUtil.string2List( false , userModes, new String[] {":",",",";"," ", "\t"} , null );
			    userModeSet.addAll( ums );

			}
			
			
			
		} catch (Throwable e) {	
			
			System.err.println(e.getClass().getName() + ":" + e.getMessage());
			
			p = true;
			d = false;
			t = false;
			
			m = "PRODUCTION";
		}
		
		productionMode = p;
		debugMode = d;
		testMode = t;
		mode = m;
		
		Tools.log.info("Mode : {}" , mode );
		Tools.log.info("User Mode : {}" , userModeSet );
	}
	
	private Mode() {}
	
	
	public static boolean isMode( String m ) {
		if(m == null) {
			return false;
		}
		
		return m.trim().toUpperCase().equals(mode);
	}
	
	public static boolean isUserMode( String m ) {
		if(m == null) {
			return false;
		}
		
		return userModeSet.contains( m.trim().toUpperCase() );
	}
	
	

}
