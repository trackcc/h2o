package h2o.utils.log.impl;


import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;
import h2o.common.util.date.DateUtil;
import h2o.common.util.io.FileUtil;
import h2o.common.util.io.StreamUtil;
import h2o.common.util.lang.StringUtil;
import h2o.utils.log.LogLevel;
import h2o.utils.log.LogMeta;
import h2o.utils.log.Logger;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

/**
 * Created by zhangjianwei on 16/8/18.
 */
public class FileLoggerImpl implements Logger {

    private final String baseDir;

    private final LogMeta logMeta;

    public FileLoggerImpl( LogMeta logMeta , String baseDir ) {
        this.logMeta = logMeta;
        this.baseDir = baseDir;
    }

    @Override
    public LogMeta getLogMeta() {
        return logMeta;
    }

    @Override
    public void log(LogLevel level, String prompt, Object log ) {

        StringBuilder sbm = new StringBuilder();
        sbm.append(prompt);

        if( log != null ) {

            sbm.append(  log instanceof Throwable ?
                    this.exceptionToString( (Throwable)log ) :
                    log.toString() );

        }
        String msg = sbm.toString();

        StringBuilder sb = new StringBuilder( baseDir );
        for( String p : logMeta.getPath() ) {
            sb.append("/");
            sb.append(p);
        }

        String path = sb.toString();

        FileUtil.mkdirs(path);

        Writer w = null;
        try {
            w = StreamUtil.writeFile( path + "/" + logMeta.getId() + ".txt" , true );
            w.write( formatLog( level , msg ) );
            w.flush();

        } catch (IOException e) {

            Tools.log.error("日志纪录错误",e);

        } finally {
            StreamUtil.close(w);
        }
    }

    protected String formatLog( LogLevel level , String msg ) {
        return StringUtil.build("\n" , DateUtil.toLongString( new Date() ) , " : " , level , " -- " ,  msg , "\n");
    }

    protected String exceptionToString( Throwable e ) {
        return ExceptionUtil.exceptionChainToString(e);
    }

}
