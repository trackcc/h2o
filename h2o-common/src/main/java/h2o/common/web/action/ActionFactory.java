package h2o.common.web.action;

import h2o.common.collections.builder.MapBuilder;
import h2o.common.ioc.ButterflyFactory;

import java.util.Map;

public class ActionFactory {
	
	private final ButterflyFactory factory;
	
	private final Map<String,Object> mapping;
	
	public ActionFactory( String name , String bcs ) {
		
		factory = new ButterflyFactory( name, bcs );
		
		
		Map<String,Object> mapping0 = factory.silentlyGet("mapping");
		if( mapping0 != null ) {
			mapping = MapBuilder.newConcurrentHashMap();
			mapping.putAll(mapping0);
		} else {
			mapping = null;
		}
	}
	
	public Action getAction( String actionId ) {
		
		if( mapping != null ) {
			
			Object a = mapping.get(actionId);
			if( a == null ) {
				a = mapping.get("_default");
			}
			if( a != null ) {
				if( a instanceof String ) {
					actionId = (String) a;
				} else {
					return (Action) a;
				}
			} else {
				return null;
			}
		}
		
		return factory.silentlyGet(actionId);
		
	}


}
