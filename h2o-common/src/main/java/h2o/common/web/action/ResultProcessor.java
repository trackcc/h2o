package h2o.common.web.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ResultProcessor {
	
	void response(Result r, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
