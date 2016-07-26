package h2o.common.util.debug;

import h2o.common.concurrent.factory.InstanceFactory;
import h2o.common.concurrent.factory.InstanceTable;
import h2o.common.util.collections.tuple.Tuple3;
import h2o.common.util.collections.tuple.TupleUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
	
	private final String callClassName;
	
	public LogUtil() {
		this.callClassName = null;
	}
	
	public LogUtil( String callClassName ) {
		this.callClassName = callClassName;
	}


	private static final Logger DEF_LOG = LoggerFactory.getLogger(LogUtil.class.getName());
	private static final String DEFFMT = "{}";

	private static final String INFO_STR = "INFO";
	private static final String WARN_STR = "WARN";
	private static final String ERROR_STR = "ERROR";
	private static final String DEBUG_STR = "DEBUG";
	private static final String TRACE_STR = "TRACE";

	
	private final InstanceTable<String,Logger> loggers = new InstanceTable<String,Logger>( new InstanceFactory<Logger> () {

		@Override
		public Logger create(Object id) {
			String loggerName = (String)id;
			Logger log = LoggerFactory.getLogger(loggerName);
			return log;
		}

		@Override
		public void free(Object id, Logger i) {
		}
		
	});

	private Tuple3<Logger, String, StackTraceElement[]> getLoggerInfo() {

		StackTraceElement[] stes = Thread.currentThread().getStackTrace();

		String loggerName = null;

		int i = 0;

		String logUtilClassName = callClassName == null ? LogUtil.class.getName() : callClassName;
		for (StackTraceElement ste : stes) {
			if (ste.getClassName().equals(logUtilClassName)) {
				i++;
			} else if (i > 0) {
				loggerName = ste.getClassName();
				break;
			}
		}

		if (loggerName == null) {
			return TupleUtil.t3(DEF_LOG, logUtilClassName, stes);
		} else {
			Logger log = loggers.getAndCreateIfAbsent(loggerName);
			return TupleUtil.t3(log, loggerName, stes);
		}

	}

	private final List<UserLogger> userLoggers = new CopyOnWriteArrayList<UserLogger>();

	public List<UserLogger> getUserLoggers() {
		return userLoggers;
	}

	public void setUserLoggers(List<UserLogger> userLoggers) {
		this.userLoggers.addAll(userLoggers);
	}

	public void addUserLogger(UserLogger userLogger) {
		this.userLoggers.add(userLogger);
	}

	public void addUserLoggers(UserLogger... userLoggers) {
		for (UserLogger userLogger : userLoggers) {
			this.userLoggers.add(userLogger);
		}
	}

	private void fireUserLog(String name, StackTraceElement[] stes, String level, boolean islog, String format, Object... args) {
		if (!userLoggers.isEmpty()) {
			for (UserLogger userLogger : this.userLoggers) {
				try {
					userLogger.log(name, stes, level, islog, format, args);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void fireUserExceptionLog(String name, StackTraceElement[] stes, String level, boolean islog, String msg, Throwable t) {
		if (!userLoggers.isEmpty()) {
			for (UserLogger userLogger : this.userLoggers) {
				try {
					userLogger.exceptionLog(name, stes, level, islog, msg, t);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean isTraceEnabled() {
		return this.getLoggerInfo().e0.isTraceEnabled();
	}

	public void trace(Object message) {

		Tuple3<Logger, String, StackTraceElement[]> loggerInfo = this.getLoggerInfo();

		this.fireUserLog(loggerInfo.e1, loggerInfo.e2, TRACE_STR, loggerInfo.e0.isTraceEnabled(), DEFFMT, message);
		loggerInfo.e0.trace(DEFFMT, message);
	}

	public void trace(String fmt, Object... messages) {

		Tuple3<Logger, String, StackTraceElement[]> loggerInfo = this.getLoggerInfo();

		this.fireUserLog(loggerInfo.e1, loggerInfo.e2, TRACE_STR, loggerInfo.e0.isTraceEnabled(), fmt, messages);
		loggerInfo.e0.trace(fmt, messages);

	}

	public void trace(String s, Throwable throwable) {

		Tuple3<Logger, String, StackTraceElement[]> loggerInfo = this.getLoggerInfo();

		this.fireUserExceptionLog(loggerInfo.e1, loggerInfo.e2, TRACE_STR, loggerInfo.e0.isTraceEnabled(), s, throwable);
		loggerInfo.e0.trace(s, throwable);

	}

	public boolean isDebugEnabled() {
		return this.getLoggerInfo().e0.isDebugEnabled();
	}

	public void debug(Object message) {
		Tuple3<Logger, String, StackTraceElement[]> loggerInfo = this.getLoggerInfo();

		this.fireUserLog(loggerInfo.e1, loggerInfo.e2, DEBUG_STR, loggerInfo.e0.isDebugEnabled(), DEFFMT, message);
		loggerInfo.e0.debug(DEFFMT, message);
	}

	public void debug(String fmt, Object... messages) {
		Tuple3<Logger, String, StackTraceElement[]> loggerInfo = this.getLoggerInfo();

		this.fireUserLog(loggerInfo.e1, loggerInfo.e2, DEBUG_STR, loggerInfo.e0.isDebugEnabled(), fmt, messages);
		loggerInfo.e0.debug(fmt, messages);
	}

	public void debug(String s, Throwable throwable) {
		Tuple3<Logger, String, StackTraceElement[]> loggerInfo = this.getLoggerInfo();

		this.fireUserExceptionLog(loggerInfo.e1, loggerInfo.e2, DEBUG_STR, loggerInfo.e0.isDebugEnabled(), s, throwable);
		loggerInfo.e0.debug(s, throwable);
	}

	public boolean isInfoEnabled() {
		return this.getLoggerInfo().e0.isInfoEnabled();
	}

	public void info(Object message) {
		Tuple3<Logger, String, StackTraceElement[]> loggerInfo = this.getLoggerInfo();

		this.fireUserLog(loggerInfo.e1, loggerInfo.e2, INFO_STR, loggerInfo.e0.isInfoEnabled(), DEFFMT, message);
		loggerInfo.e0.info(DEFFMT, message);
	}

	public void info(String fmt, Object... messages) {
		Tuple3<Logger, String, StackTraceElement[]> loggerInfo = this.getLoggerInfo();

		this.fireUserLog(loggerInfo.e1, loggerInfo.e2, INFO_STR, loggerInfo.e0.isInfoEnabled(), fmt, messages);
		loggerInfo.e0.info(fmt, messages);
	}

	public void info(String s, Throwable throwable) {
		Tuple3<Logger, String, StackTraceElement[]> loggerInfo = this.getLoggerInfo();

		this.fireUserExceptionLog(loggerInfo.e1, loggerInfo.e2, INFO_STR, loggerInfo.e0.isInfoEnabled(), s, throwable);
		loggerInfo.e0.info(s, throwable);
	}

	public boolean isWarnEnabled() {
		return this.getLoggerInfo().e0.isWarnEnabled();
	}

	public void warn(Object message) {
		Tuple3<Logger, String, StackTraceElement[]> loggerInfo = this.getLoggerInfo();

		this.fireUserLog(loggerInfo.e1, loggerInfo.e2, WARN_STR, loggerInfo.e0.isWarnEnabled(), DEFFMT, message);
		loggerInfo.e0.warn(DEFFMT, message);
	}

	public void warn(String fmt, Object... messages) {
		Tuple3<Logger, String, StackTraceElement[]> loggerInfo = this.getLoggerInfo();

		this.fireUserLog(loggerInfo.e1, loggerInfo.e2, WARN_STR, loggerInfo.e0.isWarnEnabled(), fmt, messages);
		loggerInfo.e0.warn(fmt, messages);
	}

	public void warn(String s, Throwable throwable) {
		Tuple3<Logger, String, StackTraceElement[]> loggerInfo = this.getLoggerInfo();

		this.fireUserExceptionLog(loggerInfo.e1, loggerInfo.e2, WARN_STR, loggerInfo.e0.isWarnEnabled(), s, throwable);
		loggerInfo.e0.warn(s, throwable);
	}

	public boolean isErrorEnabled() {
		return this.getLoggerInfo().e0.isErrorEnabled();
	}

	public void error(Object message) {
		Tuple3<Logger, String, StackTraceElement[]> loggerInfo = this.getLoggerInfo();

		this.fireUserLog(loggerInfo.e1, loggerInfo.e2, ERROR_STR, loggerInfo.e0.isErrorEnabled(), DEFFMT, message);
		loggerInfo.e0.error(DEFFMT, message);
	}

	public void error(String fmt, Object... messages) {
		Tuple3<Logger, String, StackTraceElement[]> loggerInfo = this.getLoggerInfo();

		this.fireUserLog(loggerInfo.e1, loggerInfo.e2, ERROR_STR, loggerInfo.e0.isErrorEnabled(), fmt, messages);
		loggerInfo.e0.error(fmt, messages);
	}

	public void error(String s, Throwable throwable) {
		Tuple3<Logger, String, StackTraceElement[]> loggerInfo = this.getLoggerInfo();

		this.fireUserExceptionLog(loggerInfo.e1, loggerInfo.e2, ERROR_STR, loggerInfo.e0.isErrorEnabled(), s, throwable);
		loggerInfo.e0.error(s, throwable);
	}

}
