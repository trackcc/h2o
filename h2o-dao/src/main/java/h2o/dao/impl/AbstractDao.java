package h2o.dao.impl;

import h2o.dao.Dao;
import h2o.dao.ScopeManager;
import h2o.dao.TxManager;
import h2o.dao.orm.ArgProcessor;
import h2o.dao.orm.OrmProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDao implements Dao {

    private static final Logger log = LoggerFactory.getLogger( AbstractDao.class.getName() );

    protected volatile ArgProcessor argProcessor;
	
	protected volatile OrmProcessor ormProcessor;
	
	protected volatile TxManager txManager;
	
	protected volatile ScopeManager scopeManager;
	
	
	

	public void setArgProcessor(ArgProcessor argProcessor) {
		this.argProcessor = argProcessor;
	}

	public void setOrmProcessor(OrmProcessor ormProcessor) {
		this.ormProcessor = ormProcessor;
	}

	public void setTxManager(TxManager txManager) {
		this.txManager = txManager;
	}
	
	public TxManager getTxManager() {
		return txManager;
	}
	

	public ScopeManager getScopeManager() {
		return scopeManager;
	}

	public void setScopeManager(ScopeManager scopeManager) {
		this.scopeManager = scopeManager;
	}
	
	
	

	@Override
	public void beginTransaction() {
		if( this.txManager == null ) {
			log.warn("TxManager is null");
		} else {
			this.txManager.beginTransaction();
		}
	}

	@Override
	public void setRollbackOnly() {
		if( this.txManager == null ) {
			log.warn("TxManager is null");
		} else {
			this.txManager.setRollbackOnly();
		}
	}

	@Override
	public void rollBack() {
		if( this.txManager == null ) {
			log.warn("TxManager is null");
		} else {
			this.txManager.rollBack();
		}
	}

	@Override
	public void commit() {
		if( this.txManager == null ) {
			log.warn("TxManager is null");
		} else {
			this.txManager.commit();
		}
	}

	
	@Override
	public void beginScope() {
		if( this.txManager == null ) {
			log.warn("ScopeManager is null");
		} else {
			this.scopeManager.beginScope();;
		}
	}

	@Override
	public void endScope() {
		if( this.txManager == null ) {
			log.warn("ScopeManager is null");
		} else {
			this.scopeManager.endScope();;
		}
	}
	


}
