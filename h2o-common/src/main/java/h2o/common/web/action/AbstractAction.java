package h2o.common.web.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

abstract public class AbstractAction implements Action {
	
	private String characterEncoding = "UTF-8";	
	
	private Map<String,Object> para;
	
	private ActionPreprocessor actionPreprocessor;
	
	

	
	

	final public Result procRequest(HttpServletRequest request,
			HttpServletResponse response, HttpServlet servlet)
			throws ServletException, IOException {	
		
		if( characterEncoding != null ) {
			request.setCharacterEncoding(characterEncoding); 
		}
		
		if( actionPreprocessor != null ) {
			Result r = actionPreprocessor.proc( this , request, response, servlet);
			if( r != null ) {
				return r;
			}
		}	
		
		Result r = init(request, response, servlet);
		if( r != null ) {
			return r;
		}
		
		return proc(request, response, servlet);
	}
	
	

	protected Result init(HttpServletRequest request, HttpServletResponse response,	HttpServlet servlet) throws ServletException, IOException {
		return null;
	}
	
	abstract protected Result proc(HttpServletRequest request,
			HttpServletResponse response, HttpServlet servlet)
			throws ServletException, IOException;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}
	
	public String getCharacterEncoding() {
		return characterEncoding;
	}




	public void setPara( Map<String,Object> para ) {
		this.para = para;
	}
	
	public Map<String, Object> getPara() {
		return para;
	}



	public void setActionPreprocessor(ActionPreprocessor actionPreprocessor) {
		this.actionPreprocessor = actionPreprocessor;
	}
	

	public ActionPreprocessor getActionPreprocessor() {
		return actionPreprocessor;
	}
	
	

}
