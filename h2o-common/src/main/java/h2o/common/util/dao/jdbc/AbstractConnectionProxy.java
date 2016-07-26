package h2o.common.util.dao.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public abstract class AbstractConnectionProxy implements Connection {

	private final Connection realConnection;
	
	public AbstractConnectionProxy(Connection realConnection) {
		this.realConnection = realConnection;
	}	

	protected Connection getRealConnection() {
		return realConnection;
	}

	public void clearWarnings() throws SQLException {
		this.realConnection.clearWarnings();
	}

	public void close() throws SQLException {
		this.realConnection.close();
	}

	public void commit() throws SQLException {
		this.realConnection.commit();
	}

	public Statement createStatement() throws SQLException {
		return this.realConnection.createStatement();
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		return this.realConnection.createStatement(resultSetType, resultSetConcurrency);
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return this.realConnection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public boolean getAutoCommit() throws SQLException {
		return this.realConnection.getAutoCommit();
	}

	public String getCatalog() throws SQLException {
		return this.realConnection.getCatalog();
	}

	public int getHoldability() throws SQLException {
		return this.realConnection.getHoldability();
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return this.realConnection.getMetaData();
	}

	public int getTransactionIsolation() throws SQLException {
		return this.realConnection.getTransactionIsolation();
	}

	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return this.realConnection.getTypeMap();
	}

	public SQLWarning getWarnings() throws SQLException {
		return this.realConnection.getWarnings();
	}

	public boolean isClosed() throws SQLException {
		return this.realConnection.isClosed();
	}

	public boolean isReadOnly() throws SQLException {
		return this.realConnection.isReadOnly();
	}

	public String nativeSQL(String sql) throws SQLException {
		return this.realConnection.nativeSQL(sql);
	}

	public CallableStatement prepareCall(String sql) throws SQLException {
		return this.realConnection.prepareCall(sql);
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return this.realConnection.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return this.realConnection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return this.realConnection.prepareStatement(sql);
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		return this.realConnection.prepareStatement(sql, autoGeneratedKeys);
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		return this.realConnection.prepareStatement(sql, columnIndexes);
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		return this.realConnection.prepareStatement(sql, columnNames);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return this.realConnection.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return this.realConnection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		this.realConnection.releaseSavepoint(savepoint);
	}

	public void rollback() throws SQLException {
		this.realConnection.rollback();
	}

	public void rollback(Savepoint savepoint) throws SQLException {
		this.realConnection.rollback(savepoint);
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		this.realConnection.setAutoCommit(autoCommit);
	}

	public void setCatalog(String catalog) throws SQLException {
		this.realConnection.setCatalog(catalog);
	}

	public void setHoldability(int holdability) throws SQLException {
		this.realConnection.setHoldability(holdability);
	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		this.realConnection.setReadOnly(readOnly);
	}

	public Savepoint setSavepoint() throws SQLException {
		return this.realConnection.setSavepoint();
	}

	public Savepoint setSavepoint(String name) throws SQLException {
		return this.realConnection.setSavepoint(name);
	}

	public void setTransactionIsolation(int level) throws SQLException {
		this.realConnection.setTransactionIsolation(level);
	}

	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		this.realConnection.setTypeMap(map);
	}

	
	// jdk6+
	
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		return realConnection.createArrayOf(typeName, elements);
	}

	public Blob createBlob() throws SQLException {
		return realConnection.createBlob();
	}

	public Clob createClob() throws SQLException {
		return realConnection.createClob();
	}

	public NClob createNClob() throws SQLException {
		return realConnection.createNClob();
	}

	public SQLXML createSQLXML() throws SQLException {
		return realConnection.createSQLXML();
	}

	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		return realConnection.createStruct(typeName, attributes);
	}

	public Properties getClientInfo() throws SQLException {
		return realConnection.getClientInfo();
	}

	public String getClientInfo(String name) throws SQLException {
		return realConnection.getClientInfo(name);
	}

	public boolean isValid(int timeout) throws SQLException {
		return realConnection.isValid(timeout);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return realConnection.isWrapperFor(iface);
	}

	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		realConnection.setClientInfo(properties);
	}

	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		realConnection.setClientInfo(name, value);
	}


	public void setSchema(String schema) throws SQLException {
		realConnection.setSchema(schema);
	}

	public String getSchema() throws SQLException {
		return realConnection.getSchema();
	}

	public void abort(Executor executor) throws SQLException {
		realConnection.abort(executor);
	}

	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		realConnection.setNetworkTimeout(executor,milliseconds);
	}

	public int getNetworkTimeout() throws SQLException {
		return realConnection.getNetworkTimeout();
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return realConnection.unwrap(iface);
	}
	
	
	

}
