package h2o.common.dao.jdbc;

import java.sql.Connection;


public interface JdbcCallback<T> {
	
	T doCallBack(Connection conn) throws Exception ;

}
