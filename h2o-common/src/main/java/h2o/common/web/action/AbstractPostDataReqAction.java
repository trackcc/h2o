package h2o.common.web.action;

import h2o.common.Tools;
import h2o.common.util.io.StreamUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractPostDataReqAction extends AbstractAction implements Action {
	
	
	private String postData;
	
	protected String getPostData() {
		return this.postData;
	}
	
	
	@Override
	protected Result init(HttpServletRequest request,	HttpServletResponse response, HttpServlet servlet)
			throws ServletException, IOException {		
				
		postData = StreamUtil.readReaderContent(request.getReader(), true);
		Tools.log.debug("postData:{}" , postData );
		
		return null;
	}

	abstract protected Result proc(HttpServletRequest request,	HttpServletResponse response, HttpServlet servlet)
			throws ServletException, IOException;

}

