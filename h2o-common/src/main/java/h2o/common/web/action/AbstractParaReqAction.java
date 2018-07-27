package h2o.common.web.action;

import h2o.common.util.web.ParameterUtil;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

abstract public class AbstractParaReqAction extends AbstractAction implements Action {

    private static final Logger log = LoggerFactory.getLogger( AbstractParaReqAction.class.getName() );

    @Override
	protected  Result init(HttpServletRequest request, HttpServletResponse response,
			HttpServlet servlet) throws ServletException, IOException {
		
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);		
		
		if( !isMultipart ) {		
			this.fullPara(request, servlet);	
		}
		
		log.debug("{} - para:{}" , this.getClass().getSimpleName() , this.getPara() );
		
		return null;
	}
	
	
	protected void fullPara( HttpServletRequest request, HttpServlet servlet ) throws ServletException, IOException {
		this.getPara().putAll( new ParameterUtil(request) );
	}
	

	
	abstract protected Result proc(HttpServletRequest request,
			HttpServletResponse response, HttpServlet servlet)
			throws ServletException, IOException;

}
