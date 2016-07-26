package h2o.dao.impl;

import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;
import h2o.dao.Dao;
import h2o.dao.Db;
import h2o.dao.TxCallback;


public abstract class AbstractDb implements Db {
	

	@Override
	public abstract Dao getDao();

	@Override
	public <T> T q( TxCallback<T> txCallback) {
		
		Dao dao = this.getDao(false);
		
		dao.beginScope();
		try {
			
			return txCallback.doCallBack(dao);
			
		} catch (Exception e) {
			Tools.log.debug("doCallBack",e);			
			throw ExceptionUtil.toRuntimeException(e);			
		} finally {
			
			try {
				dao.endScope();
			} catch( Exception e ) {
				Tools.log.error("dao.endScope()",e);
			}
			try {
				dao.close();
			} catch( Exception e ) {
				Tools.log.error("dao.close()",e);
			}
			
			
		}
		
	}

	@Override
	public <T> T tx( TxCallback<T> txCallback) {
		
	
		Dao dao = this.getDao( false );
		
		dao.beginTransaction();
		try {
			
			T t = txCallback.doCallBack(dao);
			
			dao.commit();
			
			return t;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			Tools.log.debug("doCallBack",e);
			
			dao.rollBack();
			
			throw ExceptionUtil.toRuntimeException(e);
			
		} finally {
			
			try {
				dao.close();
			} catch( Exception e ) {
				Tools.log.error("dao.close()",e);
			}
			
			
		}
	}


	

}
