package h2o.dao.impl.tx;


import h2o.common.util.dao.butterflydb.ButterflyDb;
import h2o.dao.TxManager;

public class TxManagerImpl implements TxManager {

	private final ButterflyDb bdb;

	private boolean isRollBack = false;

	public TxManagerImpl(ButterflyDb bdb) {
		this.bdb = bdb;
	}

	@Override
	public void beginTransaction() {
		this.bdb.persistenceManager.getScopingDataSource().beginTransactionScope();
	}

	@Override
	public void setRollbackOnly() {
		this.isRollBack = true;
	}

	@Override
	public void rollBack() {
		this.bdb.persistenceManager.getScopingDataSource().abortTransactionScope(null);
	}

	@Override
	public void commit() {
		if (this.isRollBack) {
			rollBack();
		} else {
			this.bdb.persistenceManager.getScopingDataSource().endTransactionScope();
		}
	}

}
