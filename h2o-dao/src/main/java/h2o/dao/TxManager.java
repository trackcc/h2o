package h2o.dao;

public interface TxManager {
	
	void beginTransaction();
	
	void rollBack();
	
	void setRollbackOnly();
	
	void commit();	

}
