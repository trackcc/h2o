package h2o.dao;

public interface TxCallback<T> {
	
	T doCallBack(Dao dao) throws Exception ;

}
