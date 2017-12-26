package h2o.common.dao.datasource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public abstract class AbstractDataSourceProxy implements DataSource {
	
	private final DataSource realDataSource;
	

	public AbstractDataSourceProxy( DataSource realDataSource ) {
		this.realDataSource = realDataSource;
	}
	

	protected DataSource getRealDataSource() {
		return realDataSource;
	}
	
	
	

	public Connection getConnection() throws SQLException {
		return realDataSource.getConnection();
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		return realDataSource.getConnection(username, password);
	}

	public PrintWriter getLogWriter() throws SQLException {
		return realDataSource.getLogWriter();
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		realDataSource.setLogWriter(out);
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		realDataSource.setLoginTimeout(seconds);
	}

	public int getLoginTimeout() throws SQLException {
		return realDataSource.getLoginTimeout();
	}


	
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return realDataSource.unwrap(iface);
	}


	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return realDataSource.isWrapperFor(iface);
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return realDataSource.getParentLogger();
	}
}
