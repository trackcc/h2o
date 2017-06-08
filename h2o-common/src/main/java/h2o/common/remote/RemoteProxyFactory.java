package h2o.common.remote;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RemoteProxyFactory implements InvocationHandler {
	
	private UserIdentity userIdentity;
	
	private String serviceId;
	
	private RemoteCallI rci; 
	
	public RemoteProxyFactory( UserIdentity userIdentity , String serviceId , RemoteCallI rci) {
		this.serviceId = serviceId;
		this.rci = rci;
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> T create( Class<T> inf ) {
		return (T)Proxy.newProxyInstance( inf.getClassLoader(), new Class[] {inf}, this );
	}
	
	
	
	public static <T> T create( UserIdentity userIdentity , Class<T> inf , String serviceId , RemoteCallI rci ) { 
		return new RemoteProxyFactory( userIdentity , serviceId , rci ).create(inf);
    } 

//	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		RemoteReq req = new RemoteReq( this.userIdentity , this.serviceId , new MethodSignature( method.getName() , method.getParameterTypes() ) , args );
		RemoteRes res = this.rci.call(req);
		
		if( res.getE() != null ) {
			throw res.getE();
		}
		
		return res.getR();
	}

}
