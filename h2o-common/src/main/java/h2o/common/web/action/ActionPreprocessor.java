/**
 * @author 张建伟
 */
package h2o.common.web.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ActionPreprocessor {
	
	Result proc(AbstractAction action, HttpServletRequest request, HttpServletResponse response, HttpServlet servlet) throws ServletException, IOException;

}
