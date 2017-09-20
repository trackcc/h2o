package h2o.dao.colinfo;

import h2o.common.Tools;
import h2o.common.util.collections.CollectionUtil;
import h2o.dao.annotation.*;
import h2o.dao.exception.DaoException;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

public class ColInfoUtil {
	
	private ColInfoUtil() {}
	

	
	public static boolean hasTableAnnotation( Object bean ) {
		
		if( bean == null ) {
			return false;
		}
		
		boolean isClass = (bean instanceof Class);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Class<Object> beanClass = isClass ? (Class)bean : bean.getClass();
		
		return beanClass.getAnnotation(Table.class) != null;
	}
	
	
	
	public static String getTableName( Object bean ) {
		
		if( bean == null ) {
			return null;
		}
		
		
		boolean isClass = (bean instanceof Class);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Class<Object> beanClass = isClass ? (Class)bean : bean.getClass();
		
		Table tableAnn = beanClass.getAnnotation(Table.class);
		
		if( tableAnn != null && !StringUtils.isBlank(tableAnn.name())) {
			return tableAnn.name();
		} else {
			return bean.getClass().getSimpleName();
		}
		
	}
	
	public static List<ColInfo> getColInfoInAttrNames( Object bean , boolean isAllAttr , String[] attrNames , String[] skipAttrNames , boolean isSilently ) throws DaoException  {
		
		List<ColInfo> colInfos = getColInfos(bean);
		if(colInfos.isEmpty()) {
			if( isSilently ) {
				return null;
			} else {
				throw new DaoException( "ColInfos is empty");
			}
		}
		
		
		if(!CollectionUtil.argsIsBlank(attrNames)) {
			
			Map<String,String> defValMap = new HashMap<String,String>();
			Set<String> attrNameSet = new HashSet<String>();
			
			for( String attr : attrNames) {
				int idx = attr.indexOf('=');
				if( idx != -1 ) {
					String nAttr = attr.substring(0,idx);
					defValMap.put( nAttr , attr.substring(idx + 1 , attr.length()));
					attrNameSet.add(nAttr);
				} else {
					attrNameSet.add(attr);
				}
			}
			
			
			if(!isAllAttr) {				
				
				List<ColInfo> colInfosTmp = new ArrayList<ColInfo>();
				for( ColInfo ci :  colInfos ) {
					if( attrNameSet.contains(ci.attrName) ) {
						colInfosTmp.add(ci);
					}		
					
				}
				
				if(colInfosTmp.isEmpty()) {
					if( isSilently ) {
						return null;
					} else {
						throw new DaoException( "ColInfos in attrNames is empty");
					}
				} else {
					colInfos = colInfosTmp;
				}
			
			}		
			
			
			for( ColInfo ci :  colInfos ) {
				if( defValMap.containsKey(ci.attrName) ) {
					ci.defVal = new ColumnDefValue( defValMap.get(ci.attrName) );
				}		
				
			}
					
			
			
		}
		
		
		if(!CollectionUtil.argsIsBlank(skipAttrNames)) {
			
			List<String> skipAttrNameList = Arrays.asList(skipAttrNames);
			
			List<ColInfo> colInfos2 = new ArrayList<ColInfo>();
			for( ColInfo ci :  colInfos ) {
				if( ! skipAttrNameList.contains(ci.attrName) ) {
					colInfos2.add(ci);
				}				
			}
			
			colInfos = colInfos2;
			
		}
		
		Tools.log.debug("colInfos ===== {}" , colInfos);
		
		return colInfos;
		
	}
	
	public static List<ColInfo> getColInfos( Object bean ) {
		
		boolean isClass = (bean instanceof Class);
		
		@SuppressWarnings("rawtypes")
		Class beanClass = isClass ? (Class)bean : bean.getClass();
		
		List<ColInfo> colInfos = new ArrayList<ColInfo>();
		
		Field[] fs = jodd.util.ReflectUtil.getSupportedFields(beanClass);
		for( Field f : fs ) {
			
			Column colAnn = f.getAnnotation(Column.class);			
			if( colAnn == null ) {
				continue;
			}
			
			ColInfo ci = new ColInfo();
			
			String fieldName = f.getName();
			
			ci.attrName =  colAnn.attrName();
			if( StringUtils.isBlank(ci.attrName) ) {
				ci.attrName = fieldName;
			}
			
			String colName = colAnn.name();
			ci.colName =  StringUtils.isBlank( colName ) ? fieldName : colName;		
			
			ci.defVal = new ColumnDefValue( colAnn.defaultValue() );

            Id id = f.getAnnotation( Id.class );
            if( id != null ) {
                ci.pk = true;
            }

            Unique unique = f.getAnnotation( Unique.class );
            if( unique != null ) {
                ci.uniqueNames = unique.value();
            }
			
			colInfos.add(ci);
		}
		
		
		return colInfos;
	}
	
	

}
