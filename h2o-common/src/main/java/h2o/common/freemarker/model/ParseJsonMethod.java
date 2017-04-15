package h2o.common.freemarker.model;


import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import h2o.common.util.web.JsonUtil;

import java.util.List;

public class ParseJsonMethod implements TemplateMethodModelEx {


	@SuppressWarnings("rawtypes")
	public Object exec(List args) throws TemplateModelException {

		String jstr = (String) args.get(0);

		if (jstr != null) {
			jstr = jstr.trim();
			if (!"".equals(jstr) && !"null".equals(jstr)) {
				return JsonUtil.json2Map(jstr);
			}
		}

		return NullModel.NULLMODEL;

	}

}