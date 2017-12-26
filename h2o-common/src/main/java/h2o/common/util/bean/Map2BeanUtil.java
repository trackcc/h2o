package h2o.common.util.bean;

import h2o.common.util.bean.support.DefaultMap2BeanProcessor;
import h2o.common.collections.builder.ListBuilder;
import h2o.common.util.lang.InstanceUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Map2BeanUtil {

	private volatile Map2BeanProcessor map2BeanProcessor = new DefaultMap2BeanProcessor();
	
	public Map2BeanUtil setMap2BeanProcessor(Map2BeanProcessor map2BeanProcessor) {
		this.map2BeanProcessor = map2BeanProcessor;
		return this;
	}

	protected Map2BeanProcessor createMap2BeanProcessor( Class<?> clazz ) {
		return map2BeanProcessor;
	}

	public static <T> T map2Bean(Map<?, ?> map, T bean, Map2BeanProcessor map2BeanProcessor) {

		if (map == null) {
			return null;
		}

		return (T) map2BeanProcessor.map2bean(map, bean);

	}

	public static <T> T map2Bean(Map<?, ?> map, Class<T> beanClass, Map2BeanProcessor map2BeanProcessor) {

		if (map == null) {
			return null;
		}

		T bean = InstanceUtil.newInstance(beanClass);
		return (T) map2BeanProcessor.map2bean(map, bean);

	}

	public <T> T map2Bean(Map<?, ?> map, T bean) {

		if (map == null) {
			return null;
		}

		return map2Bean(map, bean, createMap2BeanProcessor( bean.getClass() ) );

	}

	public <T> T map2Bean(Map<?, ?> map, Class<T> beanClass) {

		if (map == null) {
			return null;
		}


		return map2Bean(map, beanClass, createMap2BeanProcessor( beanClass ) );
	}

	


	public <T> List<T> maps2BeanList(Collection<?> maps, T beanTemplte) {

		if (maps == null) {
			return null;
		}

		List<T> beanList = ListBuilder.newList();

		

		Map2BeanProcessor map2BeanProcessor = createMap2BeanProcessor( beanTemplte.getClass() );

		for (Object map : maps) {

			T bean = BeanUtil.cloneBean(beanTemplte);
			beanList.add( map2Bean((Map<?, ?>) map, bean, map2BeanProcessor));

		}

		return beanList;
	}

	public <T> List<T> maps2BeanList(Collection<?> maps, Class<T> beanClass) {

		if (maps == null) {
			return null;
		}

		List<T> beanList = ListBuilder.newList();

		
		Map2BeanProcessor map2BeanProcessor = createMap2BeanProcessor( beanClass );

		for (Object map : maps) {

			beanList.add( map2Bean((Map<?, ?>) map, beanClass, map2BeanProcessor));

		}

		return beanList;
	}

	
}
