package h2o.common.dao.jdbc;

import java.sql.ResultSet;

public interface JdbcRsCallback<T> {
	T doCallBack(ResultSet rs) throws Exception ;
}
