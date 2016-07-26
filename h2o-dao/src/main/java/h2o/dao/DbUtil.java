package h2o.dao;


import h2o.common.Tools;
import h2o.common.freemarker.TemplateUtil;
import h2o.common.util.bean.Map2BeanUtil;
import h2o.common.util.collections.builder.MapBuilder;
import h2o.common.util.dao.SqlTable;
import h2o.common.util.dao.butterflydb.ButterflyDb;
import h2o.common.util.ioc.ButterflyFactory;
import h2o.dao.exception.DaoException;

import java.util.Map;

import javax.sql.DataSource;

public final class DbUtil {
	
	
	public static final ButterflyFactory dbConfig = new ButterflyFactory( "db" , "db.bcs"); 
	
	
	public static final SqlTable sqlTable = newSqlTable();

	public static SqlTable newSqlTable() {
		return dbConfig.get("sqlTable");
	}
	
	
	public static final SqlBuilder 		sqlBuilder 			= dbConfig.get("sqlBuilder");	
	public static final Map2BeanUtil 	map2BeanUtil 		= dbConfig.get("map2BeanUtil");	
	public static final TemplateUtil 	sqlTemplateUtil 	= dbConfig.get("sqlTemplateUtil");

	
	private static class S {
		public static final DbUtil dbUtil = dbConfig.get("dbUtil");
	}
	
	
	
	private final Map<String,DataSource> dsMap = MapBuilder.newConcurrentHashMap();

	
	public void setDataSources( Map<String,DataSource> dataSources ) {
		dsMap.putAll(dataSources);
	}
	
	public void addDataSource( String name , DataSource ds ) {
		dsMap.put( name , ds );
	}
	
	
	
	public static DataSource getDataSource() {		
		return getDataSource("default");
	}
	
	public static DataSource getDataSource(String name) {
		
		Tools.log.debug(" getDataSource('{}') ... " , name );
		
		DataSource ds =  S.dbUtil.dsMap.get( name );
		if( ds == null ) {
			throw new DaoException( "DataSource [" + name + "] undefined." );
		}
		
		return ds;
	}
	
	
	
	public static Db getDb(){
		return dbConfig.get( "db" , getDataSource() );
	}
	
	public static Db getDb( String dsName ){
		return dbConfig.get( "db" , getDataSource( dsName ) );
	}
	
	
	

	public static Dao getDao(){
		return getDb().getDao();
	}
	
	public static Dao getDao( String dsName ){
		return getDb( dsName ).getDao();
	}
	
	

	public static ButterflyDb getButterflyDb() {
		return new ButterflyDb( getDataSource() );
	}

	public static ButterflyDb getButterflyDb( String dsName ) {
		return new ButterflyDb( getDataSource( dsName ) );
	}
	


}
