package h2o.common.caucho;

import com.caucho.hessian.client.HessianProxyFactory;
import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;

public final class HessianFactory {
	
	private HessianFactory() {}
	
	@SuppressWarnings("unchecked")
	public static <T> T create( Class<T> api , String url ) {
		HessianProxyFactory factory = new HessianProxyFactory();
		try {
			return (T) factory.create(api, url);
		} catch (Throwable e) {			
			e.printStackTrace();
			throw ExceptionUtil.toRuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T silentlyCreate( Class<T> api , String url ) {
		HessianProxyFactory factory = new HessianProxyFactory();
		try {
			return (T) factory.create(api, url);
		} catch (Throwable e) {			
			e.printStackTrace();
			Tools.log.error("",e);
			return null;
		}
	}

}
