package h2o.dao.impl.sql;

import h2o.common.util.collections.builder.MapBuilder;
import h2o.dao.DbUtil;
import h2o.dao.SqlSource;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class TSql extends Sql implements SqlSource {	

	private Map<String,?> tdata;
	
	public TSql( String sql ) {
		super( sql );
	}
	

	public TSql setData(Map<String, Object> data) {
		this.tdata = data;
		return this;
	}
	


	@Override
	public String getSql( Map<String,Object> data ) {
		
		String sql = this.getSql();
		
		if( StringUtils.isBlank(sql) ) {
			return sql;
		}
		
		if( data == null ) {
			data = MapBuilder.newMap();
		}
		
		Map<String,Object> tSqldata; {
			
			if( this.tdata == null ) {
				tSqldata = data;
			} else {
				tSqldata = MapBuilder.newMap();				
				tSqldata.putAll( this.tdata );
				tSqldata.putAll( data );
			}
			
		}
		
		return DbUtil.sqlTemplateUtil.process( tSqldata , sql ) ;
	}

}
