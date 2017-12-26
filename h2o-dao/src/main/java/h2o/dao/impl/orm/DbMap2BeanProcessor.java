package h2o.dao.impl.orm;

import h2o.common.util.bean.Map2BeanProcessor;
import h2o.common.util.bean.support.DefaultMap2BeanProcessor;
import h2o.common.collections.builder.MapBuilder;
import h2o.dao.colinfo.ColInfo;
import h2o.dao.colinfo.ColInfoUtil;

import java.util.List;
import java.util.Map;

public class DbMap2BeanProcessor extends DefaultMap2BeanProcessor implements Map2BeanProcessor {
	
	
	private final Map<String,String> colAttrMap;
	
	public DbMap2BeanProcessor( Class<?> clazz ) {
	
		
		Map<String,String> caMap = null;
		if( ColInfoUtil.hasTableAnnotation( clazz ) ) {
			
			caMap = MapBuilder.newMap();
			
			List<ColInfo> cis = ColInfoUtil.getColInfos( clazz );
			for( ColInfo ci : cis ) {
				caMap.put( ci.attrName , ci.colName );
			}
			
		}
		
		colAttrMap = caMap;
				
	}

	@Override
	public <T> T map2bean(Map<?, ?> m, T bean) {
		
		if( colAttrMap == null ) {
			return super.map2bean(m, bean);
		}
		
		String[] prepNames = super.beanUtil.getPrepNames(bean);
		String[] srcpNames = new String[ prepNames.length ];
		
		for( int i = 0 ; i < prepNames.length ; i++ ) {
			String colName = colAttrMap.get( prepNames[i] );
			srcpNames[i] = colName == null ? prepNames[i] : colName;
		}	
		
		
		return super.beanUtil.map2JavaBean(m, bean, srcpNames, prepNames );
		
	}
	
	
	
}
