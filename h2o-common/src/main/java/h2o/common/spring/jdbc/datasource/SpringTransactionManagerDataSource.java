package h2o.common.spring.jdbc.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import h2o.common.Tools;
import h2o.common.util.dao.datasource.AbstractDataSourceProxy;
import h2o.common.util.dao.jdbc.AbstractConnectionProxy;

public class SpringTransactionManagerDataSource extends AbstractDataSourceProxy {

	public SpringTransactionManagerDataSource(DataSource realDataSource) {
		super(realDataSource);		
	}

	@Override
	public Connection getConnection() throws SQLException {
		
		Tools.log.debug("getConnection() ...");
		
		Connection c = DataSourceUtils.getConnection(this.getRealDataSource());	
		
		return new AbstractConnectionProxy(c) {

			@Override
			public void close() throws SQLException {
				Tools.log.debug("close() ...");
				DataSourceUtils.releaseConnection(this.getRealConnection(), SpringTransactionManagerDataSource.this.getRealDataSource());
			}
			
		};
	}
	
	
	
	
	

}
