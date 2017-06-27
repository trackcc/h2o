package h2o.utils.log;

/**
 * Created by zhangjianwei on 16/8/18.
 */
public interface Logger {

    LogMeta getLogMeta();

    void log(LogLevel level, String prompt, Object log);

}
