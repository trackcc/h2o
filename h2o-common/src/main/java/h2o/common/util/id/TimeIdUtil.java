package h2o.common.util.id;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;

import h2o.common.util.security.RadixConvertUtil;

public class TimeIdUtil {
	
	private TimeIdUtil() {}
	
	

	
	public static String getDateTimeId() {
		return getDateTimeId( new Date() );
	}
	
	private static final FastDateFormat DATETIMEFMT = FastDateFormat.getInstance("yyyyMMddHHmmssSSS");
	public static String getDateTimeId( Date d ) {
		return DATETIMEFMT.format( d );
	}
	

	
	public String getTimeId() {
		return getTimeId( new Date() );
	}
	
	private static final FastDateFormat TIMEFMT = FastDateFormat.getInstance("HHmmssSSS");
	public static String getTimeId( Date d ) {
		return TIMEFMT.format( d );
	}
	
	
	
	
	
	public static String getDateTimeId64() {
		return getDateTimeId64( new Date() , null );
	}
	
	public static String getDateTimeId64( Date d ) {
		return getDateTimeId64( d , null );
	}
	
	
	public static String getDateTimeId64( Date d , String str64 ) {
		return getDateId64(d,str64) + getTimeId64(d,str64);
	}
	
	
	
	public static String getDateId64() {
		return getDateId64( new Date() , null );
	}
	
	public static String getDateId64( Date d ) {
		return getDateId64( d , null );
	}
	
	public static String getDateId64( Date d , String str64 ) {
		
		String timeId = getDateTimeId( d );
		
		long t10 = Integer.parseInt(timeId.substring(0,4))  * 12 * 32 + 
		Integer.parseInt(timeId.substring(4,6)) * 32 + 
		Integer.parseInt(timeId.substring(6,8)) ;		
		
		String t16 = Long.toHexString(  t10 );	
		
		return StringUtils.leftPad(RadixConvertUtil.hexTo64(t16 , str64) , 4 , '0' );
	}
	
	
	
	
	
	public static String getTimeId64() {
		return getTimeId64( new Date() , null );
	}
	
	public static String getTimeId64( Date d ) {
		return getTimeId64( d , null );
	}
	
	
	public static String getTimeId64( Date d , String str64 ) {
		
		String timeId = getTimeId( d );
		
		long t10 = (Integer.parseInt(timeId.substring(0,2))  * 60 * 60 + 
		Integer.parseInt(timeId.substring(2,4)) * 60 + 
		Integer.parseInt(timeId.substring(4,6)) ) * 1000  +
		Integer.parseInt(timeId.substring(6,9));		
		
		
		String t16 = Long.toHexString(  t10 );
		
		return StringUtils.leftPad(RadixConvertUtil.hexTo64(t16 , str64) , 5 , '0' );
	}	


}
