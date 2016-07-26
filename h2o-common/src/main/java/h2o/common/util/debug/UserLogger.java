package h2o.common.util.debug;

public interface UserLogger {
	
	void log(String name, StackTraceElement[] stes, String level, boolean islog, String format, Object... args);

	void exceptionLog(String name, StackTraceElement[] stes, String level, boolean islog, String msg, Throwable t);

}
