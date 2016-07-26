package h2o.common.spring.view.support;

import h2o.common.WebTools;
import h2o.common.spring.view.OutputProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonProcessor extends AbstractOutputProcessor implements OutputProcessor {	
	
	private final WebTools webTools;
	
	public JsonProcessor( String outCharacterEncoding ) {
		super( outCharacterEncoding );
		webTools = new WebTools( outCharacterEncoding );
	}

	public void output(Object data, HttpServletRequest req,	HttpServletResponse res) throws Exception {
		webTools.outJSON(res, data);
	}

}