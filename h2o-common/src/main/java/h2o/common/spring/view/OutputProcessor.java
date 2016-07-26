package h2o.common.spring.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OutputProcessor {
	
	void output(Object data, HttpServletRequest req, HttpServletResponse res) throws Exception;

}
