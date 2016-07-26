package h2o.common.web.action.result;

import h2o.common.web.action.Result;
import h2o.common.web.action.ResultProcessor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectResultProcessor implements ResultProcessor {
	
	public void response( Result r, HttpServletRequest request,	HttpServletResponse response ) throws ServletException, IOException {
		response.sendRedirect((String) r.resData);
	}

}
