package h2o.common.util.debug;

import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;
import h2o.common.util.collections.CollectionUtil;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Mode {
	
	public static final String PROD     = "PROD";
	public static final String TEST     = "TEST";
    public static final String DEV      = "DEV";
	
	private static final String mode;
	
	public static final boolean prodMode;
	public static final boolean testMode;
    public static final boolean devMode;

    public static final boolean debugMode;
	
	private static final Set<String> userModeSet = new HashSet<String>();
	
	static {
		
		boolean p = false;
		boolean t = false;
        boolean d = false;

        boolean debug = false;

		String m;
		
		try {

			PropertiesConfiguration config = new PropertiesConfiguration("mode.properties");
			
			m = config.getString("mode", PROD ).trim().toUpperCase();
			try {
                debug  = config.getBoolean("debug" , false );
            } catch ( Exception e ) {

            }

			if ( PROD.equals( m ) ) {
			    p = true;
            } else if ( TEST.equals( m ) ) {
				t = true;				
			} else if ( DEV.equals( m ) ) {
				d = true;				
			} else {				
                throw new IllegalArgumentException(m);
			}
			
			String userModes = config.getString("userMode","").trim().toUpperCase();
			
			if( !"".equals(userModes) ) {

                List<String> ums =  CollectionUtil.string2List( false , userModes, new String[] {":",",",";"," ", "\t"} , null );
			    userModeSet.addAll( ums );

			}
			
			
			
		} catch (Throwable e) {

		    e.fillInStackTrace();
		    Tools.log.error( e );

		    throw ExceptionUtil.toRuntimeException(e);

		}
		
		prodMode    = p;
		devMode     = d;
		testMode    = t;

		debugMode   = debug;

		mode = m;
		
		Tools.log.info("Mode : {}"       , mode );
        Tools.log.info("Debug Mode : {}" , debugMode );
		Tools.log.info("User Mode : {}"  , userModeSet );
	}
	
	private Mode() {}
	
	
	public static boolean isMode( String m ) {

		if(m == null) {
			return false;
		}
		
		return m.trim().toUpperCase().equals(mode);
	}

    public static boolean isDebugMode() {
	    return debugMode;
    }
	
	public static boolean isUserMode( String m ) {

		if(m == null) {
			return false;
		}
		
		return userModeSet.contains( m.trim().toUpperCase() );
	}
	
	

}
