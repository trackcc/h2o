package h2o.common.freemarker.model;

import java.util.List;

public interface ParaGetter {

	Object get(String key);
	
	List<String> getKeys();

}
