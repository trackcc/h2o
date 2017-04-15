package h2o.common.web.action.result;

import h2o.common.WebTools;
import h2o.common.web.action.Result;
import h2o.common.web.action.ResultProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StringResultProcessor implements ResultProcessor {
	
	protected final WebTools webTools = new WebTools( "UTF-8" );

	public void response( Result r, HttpServletRequest request,	HttpServletResponse response ) throws ServletException, IOException {
		webTools.out(response, (String)r.resData);
	}

}
