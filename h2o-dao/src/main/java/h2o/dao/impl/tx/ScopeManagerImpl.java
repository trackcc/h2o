package h2o.dao.impl.tx;

import h2o.common.dao.butterflydb.ButterflyDb;
import h2o.dao.ScopeManager;


public class ScopeManagerImpl implements ScopeManager {
	
	private final ButterflyDb bdb;

	public ScopeManagerImpl( ButterflyDb bdb ) {
		this.bdb = bdb;
	}

	@Override
	public void beginScope() {
		this.bdb.persistenceManager.getScopingDataSource().beginConnectionScope();		
	}
	


	@Override
	public void endScope() {		
		this.bdb.persistenceManager.getScopingDataSource().endConnectionScope();
	}


}
