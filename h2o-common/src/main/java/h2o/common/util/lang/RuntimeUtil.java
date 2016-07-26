package h2o.common.util.lang;

public final class RuntimeUtil {
	
	private RuntimeUtil() {}
	
	public static String getCallClassName( String runClassName ) {
		
		StackTraceElement[] stes = Thread.currentThread().getStackTrace();
		
		String callClassName = null;
		
		int i = 0;
		for (StackTraceElement ste : stes) {
			if (ste.getClassName().equals(runClassName)) {
				i++;
			} else if (i > 0) {
				callClassName = ste.getClassName();
				break;
			}
		}
		
		return callClassName;
	}

}
