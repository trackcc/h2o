package h2o.common.dao.butterflydb;

import java.sql.Connection;

public interface ButterflyDbCallback<T> {

	T doCallBack(ButterflyDb butterflyDb, Connection connection) throws Exception ;
	
}
