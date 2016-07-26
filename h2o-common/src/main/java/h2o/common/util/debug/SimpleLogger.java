package h2o.common.util.debug;

import h2o.common.util.collections.builder.ListBuilder;

import java.io.PrintWriter;
import java.text.Format;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

public class SimpleLogger implements UserLogger {

	private volatile ConcurrentHashMap<String,PrintWriter> outs = new ConcurrentHashMap<String, PrintWriter>();
	
	public void addOut(String name , PrintWriter out) {
		this.outs.put(name, out);
	}
	
	public void removeOut(String name) {
		this.outs.remove(name);
	}


	public void setOuts(Map<String, PrintWriter> outs) {
		this.outs.putAll(outs);
	}



	public SimpleLogger() {}
	
	public SimpleLogger(String name , PrintWriter out) {
		this.addOut(name, out);
	}


	public void log( String[] outNames , String name, String level, String message, Throwable t ) {
		this.log(this.getOuts(outNames), name, level, message, t);
	}
	
	
	public void log( List<PrintWriter> outs  , String name, String level, String message, Throwable t) {
		

		String msg = this.buildLogStr(name, level, message);

		for (PrintWriter out : outs) {
			out.print(msg);
			out.flush();
		}

		if (t != null) {

			for (PrintWriter out : outs) {
				t.printStackTrace(out);
				out.flush();
			}
		}

	}
	
	
	private static final FastDateFormat longFmt = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS");
	protected Format getDateFormat() {
		return longFmt;
	}
	
	
	
	protected String buildLogStr( String name, String level, String message ) {
		StringBuffer buf = new StringBuffer();

		buf.append( getDateFormat().format( new Date() ) );

		buf.append(" [");
		buf.append(Thread.currentThread().getName());
		buf.append("] ");

		buf.append(level);
		buf.append(" ");

		buf.append(name);
		buf.append(" - ");

		buf.append(message);

		buf.append("\n");

		return buf.toString();
	}
	
	
	public void formatAndLog( String[] outNames , String name, String level, String format, Object... args ) {
		this.formatAndLog(this.getOuts(outNames), name, level, format, args);
	}
	

	public void formatAndLog( List<PrintWriter> outs , String name, String level, String format, Object... args ) {
		FormattingTuple tp = MessageFormatter.arrayFormat(format, args);
		log( outs , name,level, tp.getMessage(), tp.getThrowable());
	}
	
	

	public void log(String name, StackTraceElement[] stes, String level, boolean islog, String format, Object... args) {
		String[] outNames =  this.needLog(name, stes, level, islog);
		if( outNames != null ) {
			this.formatAndLog(outNames,name, level, format, args);
		}
		
	}

	public void exceptionLog(String name, StackTraceElement[] stes, String level, boolean islog, String msg, Throwable t) {	
		
		String[] outNames =  this.needLog(name, stes, level, islog);
		if( outNames != null ) {
			this.log(outNames , name, level, msg, t);
		}
	}
	
	
	protected  List<PrintWriter> getOuts( String[] outNames  ) {
		
		List<PrintWriter> pws = ListBuilder.newList();
		for( int i = 0 ; i < outNames.length ; i++ ) {
			PrintWriter pw = this.outs.get(outNames[i]);
			if( pw != null ) {
				pws.add(pw);
			}
		}
		
		return pws;
		
	}
	
	protected String[] needLog( String name, StackTraceElement[] stes, String level, boolean islog ) {
		if( this.outs != null && !this.outs.isEmpty()) {
			return this.outs.keySet().toArray(new String[this.outs.size()]);
		}
		
		return null;
	}

}
