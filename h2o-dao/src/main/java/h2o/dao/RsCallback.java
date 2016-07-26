package h2o.dao;

import java.sql.ResultSet;


public interface RsCallback<T> {
	
	void init(ResultSet rs, Dao dao) throws Exception;

	void process(ResultSet rs, Dao dao) throws Exception;
	
	T getResult() throws Exception ;
	

}
