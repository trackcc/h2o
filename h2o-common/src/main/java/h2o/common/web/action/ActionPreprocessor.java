/**
 * @author 张建伟
 */
package h2o.common.web.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionPreprocessor {
	
	Result proc(AbstractAction action, HttpServletRequest request, HttpServletResponse response, HttpServlet servlet) throws ServletException, IOException;

}
