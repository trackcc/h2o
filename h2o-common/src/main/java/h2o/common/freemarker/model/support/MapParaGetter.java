package h2o.common.freemarker.model.support;

import h2o.common.freemarker.model.ParaGetter;
import h2o.common.util.collections.builder.ListBuilder;
import h2o.common.util.collections.builder.MapBuilder;

import java.util.List;
import java.util.Map;



public class MapParaGetter implements ParaGetter {

	private volatile Map<String, Object> paraMap;
	private volatile List<String> keys;	

	public void setParaMap(Map<String, Object> paraMap) {
		
		Map<String, Object> paraMap0 = MapBuilder.newConcurrentHashMap();
		paraMap0.putAll(paraMap);
		
		this.paraMap = paraMap0;
	}

	public Object get(String key) {
		return paraMap.get(key);
	}

	public List<String> getKeys() {
		
		List<String> ks = ListBuilder.newList();
		
		if( keys == null ) {			
			ks.addAll(paraMap.keySet());			
		} else {		
			ks.addAll( keys );
		}
		
		return ks;
		
		
	}

	public void setKeys(List<String> keys) {
		List<String> keys0 = ListBuilder.newCopyOnWriteArrayList();
		keys0.addAll(keys);
		this.keys = keys0;
	}
	
	
	

}
