package h2o.common.util.lang;

import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;
import org.apache.commons.lang.ClassUtils;

import java.lang.reflect.Constructor;

public class InstanceUtil {
	
	private InstanceUtil() {}
	
	@SuppressWarnings("rawtypes")
	public static <T> T newInstance( Class<T> clazz , Class[] argsTypes ,  Object[] args ) {
		
		try {
		
			if( args == null || args.length == 0 ) {
				 return clazz.newInstance();
			}
			
			
			if( argsTypes == null || argsTypes.length == 0 ) {
				argsTypes = h2o.jodd.util.ReflectUtil.getClasses(args);
			}
			
			
			Constructor<T> c = clazz.getConstructor(argsTypes);
			
			return c.newInstance(args);		
		
		} catch( Exception e ) {
			throw ExceptionUtil.toRuntimeException(e);
		}
	}
	
	
	
	public static <T> T newInstance( Class<T> clazz ,  Object... args ) {
		return newInstance(clazz,null,args);
	}
	
	
	public static <T> T newInstance( Class<T> clazz ) {
		return newInstance(clazz,null,null);
	}
	
	
	@SuppressWarnings("rawtypes")
	public static Class getClass(String clazzName) {
		try {
			return ClassUtils.getClass(clazzName);
		} catch (ClassNotFoundException e) {
			Tools.log.error("getClass", e);
			throw ExceptionUtil.toRuntimeException(e);
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	public static Class getClass(ClassLoader classLoader , String clazzName) {
		try {
			return ClassUtils.getClass(classLoader, clazzName);
		} catch (ClassNotFoundException e) {
			Tools.log.error("getClass", e);
			throw ExceptionUtil.toRuntimeException(e);
		}
	}


}
