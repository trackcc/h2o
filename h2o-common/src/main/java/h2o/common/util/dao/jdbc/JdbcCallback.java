package h2o.common.util.dao.jdbc;

import java.sql.Connection;


public interface JdbcCallback<T> {
	
	T doCallBack(Connection conn) throws Exception ;

}
