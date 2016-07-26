package h2o.common.freemarker.model;

import h2o.common.util.collections.builder.MapBuilder;

import java.util.List;
import java.util.Map;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class ParaMethod implements TemplateMethodModelEx {

	private volatile Map<String, ParaGetter> paraGetterMap = MapBuilder.newConcurrentHashMap();

	public void setParaGetterMap(Map<String, ParaGetter> paraGetterMap) {
		Map<String, ParaGetter> paraGetterMap0 = MapBuilder.newConcurrentHashMap();
		paraGetterMap0.putAll(paraGetterMap);
		this.paraGetterMap = paraGetterMap0;
	}
	
	public ParaGetter getParaGetter(String key) {
		return this.paraGetterMap.get(key);
	}


	@SuppressWarnings("rawtypes")
	public Object exec(List args) throws TemplateModelException {

		String gkey = (String) args.get(0);
		String key = (String) args.get(1);

		ParaGetter pg = paraGetterMap.get(gkey);

		return pg == null ? NullModel.NULLMODEL : pg.get(key);

	}

}