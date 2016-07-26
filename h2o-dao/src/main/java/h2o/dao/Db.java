package h2o.dao;


public interface Db {
	
	Dao getDao();
	
	Dao getDao(boolean autoClose);
	
	<T> T q(TxCallback<T> txCallback);
	
	<T> T tx(TxCallback<T> txCallback);

}
