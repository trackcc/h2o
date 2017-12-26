package h2o.common.thirdparty.freemarker.model;

import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

public class NullModel implements TemplateHashModel, TemplateScalarModel {
	
	public static final NullModel NULLMODEL = new NullModel();

	public TemplateModel get(String key) throws TemplateModelException {
		return NULLMODEL;
	}

	public boolean isEmpty() throws TemplateModelException {
		return false;
	}

	public String getAsString() throws TemplateModelException {
		return "";
	}

	@Override
	public String toString() {
		return "";
	}	
	

	@Override
	public int hashCode() {
		return "".hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof NullModel;
	}
	
	

}
