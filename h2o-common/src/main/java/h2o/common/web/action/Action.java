package h2o.common.web.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	
	void setPara(Map<String, Object> para);
	
	Result procRequest(HttpServletRequest request, HttpServletResponse response, HttpServlet servlet) throws ServletException, IOException;
	
}
