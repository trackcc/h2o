package h2o.common.remote;

import h2o.common.collections.tuple.Tuple2;
import h2o.common.collections.tuple.TupleUtil;
import h2o.common.ioc.ObjectFactory;
import h2o.jodd.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteCall implements RemoteCallI {

    private static final Logger log = LoggerFactory.getLogger( RemoteCall.class.getName() );

//	@Override
	public RemoteRes call(RemoteReq req) {
		
		try {
			Tuple2<Boolean, Exception> c = this.check(req);
			if( !c.e0) {
				return new RemoteRes(null,c.e1);
			}
			
			Object service = this.getService( req.getServiceId() );
			
			MethodSignature methodSignature = req.getMethodSignature();
			
			Object r = ReflectUtil.invoke(service, methodSignature.getMethodName() , methodSignature.getParameterTypes() , req.getArgs());		

			return new RemoteRes(r,null);
			
		} catch( Exception e ) {
			e.printStackTrace();
			log.debug("" , e);
			return new RemoteRes(null,e);
		}
		
		
	}
	
	protected Tuple2<Boolean,Exception> check( RemoteReq req ) {
		return TupleUtil.t(true, null);
	}
	
	protected Object getService( String serviceId) {
		return ObjectFactory.get( serviceId );
	}

}
