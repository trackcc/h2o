package h2o.common.spring.view;

import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class DirectOutputView implements View {
	
	private final OutputProcessor op;
	
	public DirectOutputView( OutputProcessor op ) {
		this.op = op;
	}

	public String getContentType() {
		return null;
	}

	@SuppressWarnings("rawtypes")
	public void render(Map model, HttpServletRequest req, HttpServletResponse res) throws Exception {
		Object data = model.get("data");
		op.output(data , req , res );
	}

}
