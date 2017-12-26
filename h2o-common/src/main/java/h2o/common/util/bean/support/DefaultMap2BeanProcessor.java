package h2o.common.util.bean.support;

import h2o.common.util.bean.BeanUtil;
import h2o.common.util.bean.Map2BeanProcessor;
import h2o.common.collections.IgnoreCaseMap;

import java.util.Map;

public class DefaultMap2BeanProcessor implements Map2BeanProcessor {
	
	

	public volatile BeanUtil beanUtil = new BeanUtil( new JoddBeanUtilVOImpl( true , true ) , null ) {
		
		@SuppressWarnings("unchecked")
		private Map<?,?> toIgnoreCaseMap( Map<?, ?> m ) {
			if( m instanceof IgnoreCaseMap ) {
				return ( IgnoreCaseMap<?> )m;
			} else {
				return new IgnoreCaseMap<Object>((Map<String,Object>)m);
			}
		}

		@Override
		public <T> T map2JavaBean(Map<?, ?> m, T bean) {
			return super.map2JavaBean( toIgnoreCaseMap(m) , bean);
		}

		@Override
		public <T> T map2JavaBean(Map<?, ?> m, T bean, String... prepnames) {
			return super.map2JavaBean( toIgnoreCaseMap(m) , bean, prepnames);
		}

		@Override
		public <T> T map2JavaBean(Map<?, ?> m, T bean, String[] srcPrepnames, String[] prepnames) {
			return super.map2JavaBean( toIgnoreCaseMap(m) , bean, srcPrepnames, prepnames);
		}

		@Override
		public <T> T map2JavaBean3(Map<?, ?> m, T bean, String[] nkeys, String[] skeys, String[] dkeys) {
			return super.map2JavaBean3( toIgnoreCaseMap(m) , bean, nkeys, skeys, dkeys);
		}

		@Override
		public <T> T map2JavaBean(Map<?, ?> m, Class<T> beanClazz) {
			return super.map2JavaBean( toIgnoreCaseMap(m) , beanClazz);
		}

		@Override
		public <T> T map2JavaBean(Map<?, ?> m, Class<T> beanClazz, String... prepnames) {
			return super.map2JavaBean( toIgnoreCaseMap(m) , beanClazz, prepnames);
		}

		@Override
		public <T> T map2JavaBean(Map<?, ?> m, Class<T> beanClazz, String[] srcPrepnames, String[] prepnames) {
			return super.map2JavaBean( toIgnoreCaseMap(m) ,  beanClazz, srcPrepnames, prepnames);
		}

		@Override
		public <T> T map2JavaBean3(Map<?, ?> m, Class<T> beanClazz, String[] nkeys, String[] skeys, String[] dkeys) {
			return super.map2JavaBean3( toIgnoreCaseMap(m) , beanClazz, nkeys, skeys, dkeys);
		}

		
		
	}; {
		beanUtil.setProcNull(false);
	}


	public Map2BeanProcessor setBeanUtil(BeanUtil beanUtil) {

		this.beanUtil = beanUtil;
		
		return this;
	}

	public Map2BeanProcessor setCover(boolean cover) {

		this.beanUtil.setCover(cover);
		return this;
	}

	public Map2BeanProcessor setProcNull(boolean procNull) {

		this.beanUtil.setProcNull(procNull);
		return this;
	}

	

	public <T> T map2bean( Map<?, ?> m , T bean ) {		
		return this.beanUtil.map2JavaBean(m, bean);
	}

}
