package h2o.common.web.action;

public class Result {
	
	public static class ResType {
		public static final String STRING 		= "string";
		public static final String JSON		 	= "json";
		public static final String FORWARD 		= "forward";
		public static final String REDIRECT 	= "redirect";
		public static final String NOTHING 		= "nothing";
		public static final String FILE 		= "file";
	}
	
	public final String resType;
	
	public final Object resData;
	
	public Result( String resType , Object resData ) {
		
		this.resType = resType;
		this.resData = resData;
	}

	@Override
	public String toString() {
		return "Result [resType=" + resType + ", resData=" + resData + "]";
	}
	
	

}
