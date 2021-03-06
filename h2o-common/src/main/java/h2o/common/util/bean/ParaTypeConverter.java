package h2o.common.util.bean;

import h2o.common.collections.builder.MapBuilder;
import h2o.common.collections.tuple.Tuple2;
import h2o.common.collections.tuple.TupleUtil;
import h2o.common.exception.ExceptionUtil;
import h2o.common.util.lang.Null;
import h2o.common.util.lang.type.TypeConverter;
import h2o.common.util.lang.type.converter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;


@SuppressWarnings({ "rawtypes","unchecked" })
public class ParaTypeConverter {

    private static final Logger log = LoggerFactory.getLogger( ParaTypeConverter.class.getName() );


    private final Map<Tuple2<Class,Class>,TypeConverter> typeConverterMap;
	
	
	public ParaTypeConverter() {
		
		typeConverterMap = MapBuilder.newConcurrentHashMap();		
		
		typeConverterMap.put( new Tuple2( Null.class, Object.class), new Null2ObjectConverter());
		
		typeConverterMap.put( new Tuple2( String.class, Integer.class), new String2IntegerConverter());
		typeConverterMap.put( new Tuple2( String.class, Long.class), new String2LongConverter());
		typeConverterMap.put( new Tuple2( String.class, Double.class), new String2DoubleConverter());
		typeConverterMap.put( new Tuple2( String.class, BigDecimal.class), new String2BigDecimalConverter());
	
	}
	
	public ParaTypeConverter( Map<Tuple2<Class,Class>,TypeConverter> tcMap ) {
		typeConverterMap = MapBuilder.newConcurrentHashMap();
		typeConverterMap.putAll( tcMap );
	}
	
	public void regTypeConverter( Tuple2<Class,Class> k ,TypeConverter tc )  {
		typeConverterMap.put(k, tc);
	}
	


	public Map<String,Object> convert( Map<String,?> m , Map<String,Tuple2<Class,Object>> t ) {

		if ( m == null ) {
			return null;
		} else if ( m.isEmpty() ) {
			return MapBuilder.newEmptyMap();
		}
		
		Map<String,Object> nm = MapBuilder.newMap(m.size());
		for( Map.Entry<String, ?> e : m.entrySet() ) {
			
			String k = e.getKey();
			Object v = e.getValue();
			
			Object nv = v;
			if( t.containsKey(k) ) {
				nv = convertNv( k , v , t );			
			}			
			
			nm.put(k, nv);		
			
			
		}
		
		for( Map.Entry<String , Tuple2<Class,Object>> e : t.entrySet() ) {
			String k = e.getKey();			
			if( !m.containsKey(k) ) {
				nm.put(k, convertNv( k , null , t ) );
			}			
		}
				
		return nm;
		
	}
	
	private Object convertNv( String k , Object v ,  Map<String,Tuple2<Class,Object>> t ) {
		
		Class sc = ( v == null ) ? Null.class : v.getClass();
		
		Tuple2<Class,Object> tt = t.get(k);				
		
		log.debug("Convert [{}]{}-->{}:{}" , v , sc.getName() , tt.e0.getName() , tt.e1  );
		
		Object nv;
		TypeConverter typeConverter = typeConverterMap.get( TupleUtil.t(sc, tt.e0));
		if( typeConverter != null ) {
			try {
				nv = typeConverter.convert( v , tt.e1);
			} catch( Exception ex ) {
				throw ExceptionUtil.toRuntimeException(ex);
			}
		} else {
			nv = v;
		}
				
		log.info("Convert:{} ===> [{}]{}-->[{} {}]{}:{}" , typeConverter , v , sc.getName() , nv == null ? "Null" : nv.getClass().getName() ,  nv , tt.e0.getName() , tt.e1  );
		
		return nv;
	}

}
