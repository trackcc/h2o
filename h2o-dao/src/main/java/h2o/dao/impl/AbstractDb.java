package h2o.dao.impl;

import h2o.common.exception.ExceptionUtil;
import h2o.dao.Dao;
import h2o.dao.Db;
import h2o.dao.TxCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractDb implements Db {

    private static final Logger log = LoggerFactory.getLogger( AbstractDb.class.getName() );

	@Override
	public abstract Dao getDao();

	@Override
	public <T> T q( TxCallback<T> txCallback) {
		
		Dao dao = this.getDao(false);
		
		dao.beginScope();
		try {
			
			return txCallback.doCallBack(dao);
			
		} catch (Exception e) {
			log.debug("doCallBack",e);
			throw ExceptionUtil.toRuntimeException(e);			
		} finally {
			
			try {
				dao.endScope();
			} catch( Exception e ) {
				log.error("dao.endScope()",e);
			}
			try {
				dao.close();
			} catch( Exception e ) {
				log.error("dao.close()",e);
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
			log.debug("doCallBack",e);
			
			dao.rollBack();
			
			throw ExceptionUtil.toRuntimeException(e);
			
		} finally {
			
			try {
				dao.close();
			} catch( Exception e ) {
				log.error("dao.close()",e);
			}
			
			
		}
	}


	

}
