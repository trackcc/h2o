package h2o.common.thirdparty.caucho;

import com.caucho.hessian.client.HessianProxyFactory;
import h2o.common.exception.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HessianFactory {

    private static final Logger log = LoggerFactory.getLogger( HessianFactory.class.getName() );

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
			log.error("",e);
			return null;
		}
	}

}
