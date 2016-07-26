package h2o.common.util.dao.util;

import h2o.common.util.bean.BeanUtil;
import h2o.common.util.bean.support.JoddBeanUtilVOImpl;
import h2o.common.util.collections.CollectionUtil;
import h2o.common.util.collections.builder.ListBuilder;
import h2o.common.util.collections.builder.MapBuilder;
import h2o.common.util.collections.tuple.Tuple2;
import h2o.common.util.collections.tuple.Tuple3;
import h2o.common.util.collections.tuple.TupleUtil;
import h2o.common.util.dao.util.namedparam.NamedParameterUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SqlParameterUtil {
	
	private final BeanUtil beanUtil;
	
	public SqlParameterUtil() {
		this.beanUtil = new BeanUtil( new JoddBeanUtilVOImpl(true,false) , null );
	}
	
	
	public SqlParameterUtil( BeanUtil beanUtil ) {
		this.beanUtil = beanUtil;
	}
	

	public Map<String,Object> toMap( Object... args ) {
		
		Map<String,Object> m = MapBuilder.newMap();
		
		if( !CollectionUtil.java5ArgsIsBlank(args) ) for( int i = 0 ; i < args.length ; i++ ) {
			
			Object a = args[i];
			
			if( a == null ) {
				continue;
			}
			
			if( otherToMapProc( a , m ) ) {
			} else if( a instanceof Map ) {
				
				for( Map.Entry<?, ?> e : ( (Map<?, ?>)a ).entrySet()) {
					m.put( e.getKey().toString() , valConvert(e.getValue()) );
				}
				
			} else if( a instanceof String ) {
				
				Object val = args[++i];
				
				m.put( (String)a , valConvert(val) );
				
			} else {
				
				Map<String, Object> sqlPara = beanUtil.javaBean2Map(a);
				for( Map.Entry<String, Object> e : sqlPara.entrySet()) {
					m.put( e.getKey() , valConvert(e.getValue()) );
				}
			}	
			
		}
		
		return m;
		
	}
	
	@SuppressWarnings("rawtypes")
	protected Object valConvert( Object val ) {
		return  (val != null && val instanceof Enum ) ? ((Enum)val).name() : val;
	}
	
	
	protected boolean otherToMapProc( Object arg , Map<String,Object> m ) {
		return false;
	}
	
	
	@SuppressWarnings("rawtypes")
	public static Tuple2<String,Object[]> toPreparedSqlAndPara( String sql, Map paramMap ) {
		
		Tuple3<String,Object[],int[]> sqlAndPara = NamedParameterUtils.map2Objects(sql, paramMap);
		
		List<Object> para = ListBuilder.newList();
		for( Object value : sqlAndPara.e1 ) {
			if (value instanceof Collection) {
				Iterator entryIter = ((Collection) value).iterator();
				
				while (entryIter.hasNext()) {
					
					Object entryItem = entryIter.next();
					if (entryItem instanceof Object[]) {
						Object[] expressionList = (Object[]) entryItem;
						
						for (int m = 0; m < expressionList.length; m++) {							
							para.add(expressionList[m]);
						}
						
					}
					else {
						para.add(entryItem);
					}
				}
			}
			else {
				para.add(value);
			}
		}
		
		return TupleUtil.t( sqlAndPara.e0 , para.toArray() );
		
	}
	
	
	
	
}
