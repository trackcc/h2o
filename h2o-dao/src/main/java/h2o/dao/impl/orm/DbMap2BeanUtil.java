package h2o.dao.impl.orm;

import h2o.common.util.bean.Map2BeanProcessor;
import h2o.common.util.bean.Map2BeanUtil;

public class DbMap2BeanUtil extends Map2BeanUtil {
	
	@Override
	protected Map2BeanProcessor createMap2BeanProcessor(Class<?> clazz) {
		
		return new DbMap2BeanProcessor(clazz);
	}

}
