package h2o.common.thirdparty.smooks;

import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;
import h2o.common.util.io.StreamUtil;
import org.milyn.Smooks;
import org.milyn.container.ExecutionContext;
import org.milyn.payload.JavaResult;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.Reader;

public class SmooksUtil {

	private volatile Smooks smooks;

	private volatile JavaResult result;

	public JavaResult getResult() {
		return result;
	}

	public SmooksUtil() {
	}

	public SmooksUtil(String smooksConfigPath) {
		this.setSmooksConfig(smooksConfigPath);
	}

	public void setSmooksConfig(String smooksConfigPath) {

		try {
			this.smooks = new Smooks(smooksConfigPath);
		} catch (Exception e) {
			Tools.log.debug("setSmooksConfig", e);
			throw ExceptionUtil.toRuntimeException(e);
		}
	}

	public JavaResult filterString(String source) {
		java.io.StringReader sr = new java.io.StringReader(source);
		JavaResult jr = this.filterSource(sr);
		return jr;
	}

	public JavaResult filterSource(String srcPath) {
		return this.filterSource(srcPath, null);
	}

	public JavaResult filterSource(String srcPath, String encoding) {
		return this.filterSource(new StreamSource(StreamUtil.readFile(srcPath, encoding)));
	}

	public JavaResult filterSource(Reader r) {
		return this.filterSource(new StreamSource(r));
	}

	public JavaResult filterSource(Source source) {
		return this.filterSource( source , new JavaResult() );
	}
	
	public JavaResult filterSource(Source source , JavaResult javaResult ) {

		this.result = javaResult;
		smooks.filterSource(source, this.result);

		return this.result;

	}

	public void filterSource(ExecutionContextSetCallback executionContextSetCallback, Source source, Result... results) {

		if (executionContextSetCallback == null) {
			this.smooks.filterSource(source, results);
		} else {
			ExecutionContext executionContext = this.smooks.createExecutionContext();
			executionContextSetCallback.setExecutionContext(executionContext);

			this.smooks.filterSource(executionContext, source, results);
		}

	}

	public void filterSource(ExecutionContext executionContext, Source source, Result... results) {

		if (executionContext == null) {
			this.smooks.filterSource(source, results);
		} else {
			this.smooks.filterSource(executionContext, source, results);
		}

	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String name) {
		return (T) this.result.getBean(name);
	}

}
