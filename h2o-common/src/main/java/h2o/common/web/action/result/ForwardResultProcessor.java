package h2o.common.web.action.result;

import h2o.common.web.action.Result;
import h2o.common.web.action.ResultProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ForwardResultProcessor implements ResultProcessor {
	
	public void response( Result r, HttpServletRequest request,	HttpServletResponse response ) throws ServletException, IOException {
		request.getRequestDispatcher((String) r.resData).forward(request, response);

	}

}
