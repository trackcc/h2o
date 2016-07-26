/**
 * @author 张建伟
 */
package h2o.flow.pvm.runtime;

public interface TransactionManager {
	
	void beginTransaction();
	
	void rollBack();
	
	void setRollbackOnly();
	
	void commit();	

}
