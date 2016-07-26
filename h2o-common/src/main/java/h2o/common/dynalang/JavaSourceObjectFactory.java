package h2o.common.dynalang;

import h2o.common.exception.ExceptionUtil;
import h2o.common.util.lang.InstanceUtil;

public class JavaSourceObjectFactory {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T instance( JavaSourceClassUtil javaSourceClassUtil , String name ,  Class[] argsTypes ,  Object[] args ) {
		
		try {
			
			Class<T> clazz = javaSourceClassUtil.getClass( name );
			
			return InstanceUtil.newInstance(clazz, argsTypes, args);
			
		} catch (ClassNotFoundException e) {
			
			throw ExceptionUtil.toRuntimeException(e);
			
		} 
	}
	
	
	public <T> T instance( JavaSourceClassUtil javaSourceClassUtil , String name ,   Object... args ) {
		return instance(javaSourceClassUtil,name,null,args);
	}
	
	public <T> T instance( JavaSourceClassUtil javaSourceClassUtil , String name ) {
		return instance(javaSourceClassUtil,name,null,null);
	}

}
