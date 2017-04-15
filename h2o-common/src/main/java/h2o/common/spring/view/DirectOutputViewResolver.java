package h2o.common.spring.view;

import h2o.common.util.collections.builder.MapBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;
import java.util.Map;

public class DirectOutputViewResolver implements ViewResolver , Ordered {
	
	private int order = Integer.MAX_VALUE;
	
	private final Map<String,OutputProcessor> outputProcessorMap = MapBuilder.newConcurrentHashMap();

	public void setOutputProcessorMap( Map<String, OutputProcessor> outputProcessorMap ) {
		this.outputProcessorMap.putAll(outputProcessorMap);		
	}	

	public void setOrder(int order) {
		this.order = order;
	}

	public View resolveViewName(String name, Locale l) throws Exception {
		
		if( ! StringUtils.startsWith( name , "direct:") ) {
			return null;
		}
		
		String type = StringUtils.substringAfter(name, ":");
		if( StringUtils.isBlank(type) ) {
			throw new Exception("Type is null");
		}
		
		OutputProcessor op = outputProcessorMap.get(type);
		
		if( op == null ) {
			throw new Exception("No search OutputProcessor by type[" + type + "]");
		}
		
		return new DirectOutputView( op );
		
	}

	public int getOrder() {
		return order;
	}

}
