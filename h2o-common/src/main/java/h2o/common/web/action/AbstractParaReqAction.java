package h2o.common.web.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import h2o.common.Tools;
import h2o.common.util.web.ParameterUtil;

abstract public class AbstractParaReqAction extends AbstractAction implements Action {

	@Override
	protected  Result init(HttpServletRequest request, HttpServletResponse response,
			HttpServlet servlet) throws ServletException, IOException {
		
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);		
		
		if( !isMultipart ) {		
			this.fullPara(request, servlet);	
		}
		
		Tools.log.debug("para:{}" , this.getPara() );
		
		return null;
	}
	
	
	protected void fullPara( HttpServletRequest request, HttpServlet servlet ) throws ServletException, IOException {
		this.getPara().putAll( new ParameterUtil(request) );
	}
	

	
	abstract protected Result proc(HttpServletRequest request,
			HttpServletResponse response, HttpServlet servlet)
			throws ServletException, IOException;

}
