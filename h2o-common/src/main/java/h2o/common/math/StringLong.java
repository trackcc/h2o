package h2o.common.math;

public final class StringLong {
	
	private StringLong() {}
	
	public static String add( String v1, String v2) {

		long l1 = Long.parseLong(v1);

		long l2 = Long.parseLong(v2);

		return Long.toString( l1 + l2 );

	}
	
	public static String sub( String v1, String v2) {

		long l1 = Long.parseLong(v1);

		long l2 = Long.parseLong(v2);

		return Long.toString( l1 - l2 );

	}
	
	public static String mul(String v1, String v2) {

		long l1 = Long.parseLong(v1);

		long l2 = Long.parseLong(v2);

		return Long.toString( l1 * l2 );

	}
	
	
	public static String div(String v1, String v2) {

		long l1 = Long.parseLong(v1);

		long l2 = Long.parseLong(v2);

		return Long.toString( l1 / l2 );

	}
	
	
	
	public static int compareTo( String v1, String v2 ) {
		
		return new Long(v1).compareTo(new Long(v1));

	}
	


}
