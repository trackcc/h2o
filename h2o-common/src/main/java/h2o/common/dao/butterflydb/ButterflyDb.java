package h2o.common.dao.butterflydb;

import com.jenkov.db.PersistenceManager;
import com.jenkov.db.itf.IDaos;
import com.jenkov.db.itf.PersistenceException;
import com.jenkov.db.scope.ScopingDataSource;
import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ButterflyDb {
	
	private volatile boolean isRollBack = false;
	
	private volatile boolean autoClose = false;

	public void needRollBack(boolean isRollBack) {
		this.isRollBack = isRollBack;
	}

	public void setAutoClose(boolean autoClose) {
		this.autoClose = autoClose;
	}



	public final PersistenceManager persistenceManager;

	public ButterflyDb(DataSource dataSource) {		
		ScopingDataSource scopingDataSource = dataSource instanceof ScopingDataSource ? (ScopingDataSource)dataSource : new ScopingDataSource(dataSource);
		this.persistenceManager = new PersistenceManager(scopingDataSource);
	}

	public IDaos getDaos() {
		try {
			return this.persistenceManager.createDaos();
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public IDaos getDaos(Connection connection) {
		return this.persistenceManager.createDaos(connection);
	}

	public ButterflyDao getDao() {
		return this.getDao(autoClose);
	}

	public ButterflyDao getDao(boolean autoClose) {
		IDaos daos = this.getDaos();
		return new ButterflyDao(daos, autoClose);
	}



	public ButterflyDao getDao(Connection connection) {
		return this.getDao(connection,autoClose);

	}

	public ButterflyDao getDao(Connection connection, boolean autoClose) {
		IDaos daos = this.getDaos(connection);
		return new ButterflyDao(daos, autoClose);

	}


	public <T> T scopeCallback(ButterflyDbCallback<T> butterflyDbCallback) {
		
		this.persistenceManager.getScopingDataSource().beginConnectionScope();
		try {
			
			return  butterflyDbCallback.doCallBack(this, null);			
			
		} catch (Throwable e) {

			Tools.log.debug("", e);
			throw ExceptionUtil.toRuntimeException(e);
			
		} finally {
			this.persistenceManager.getScopingDataSource().endConnectionScope();
		}
		
		
	}
	

	public <T> T txCallback(ButterflyDbCallback<T> butterflyDbCallback) {

		try {
			this.persistenceManager.getScopingDataSource().beginTransactionScope();

			T r = butterflyDbCallback.doCallBack(this, null);

			if( this.isRollBack ) {
				
				Tools.log.debug("NeedRollBack!!!");
				
				try {
					this.persistenceManager.getScopingDataSource().abortTransactionScope(null);
				} catch( Throwable e ) {
					Tools.log.debug("", e);
				}
				
			} else {
				
				this.persistenceManager.getScopingDataSource().endTransactionScope();
				
			}

			return r;

		} catch (Throwable e) {

			Tools.log.debug("", e);

			this.persistenceManager.getScopingDataSource().abortTransactionScope(e);

			throw ExceptionUtil.toRuntimeException(e);
		}

	}

	public <T> T ctxCallback(ButterflyDbCallback<T> butterflyDbCallback, Connection connection , boolean close) {

		Boolean ac = null;

		try {

			ac = connection.getAutoCommit();

			connection.setAutoCommit(false);

			T r = butterflyDbCallback.doCallBack(this, connection);
			
			if( this.isRollBack ) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					Tools.log.debug("", e1);
				}
			} else {
				connection.commit();
			}

			return r;

		} catch (Exception e) {

			Tools.log.debug("", e);

			try {
				connection.rollback();
			} catch (SQLException e1) {
				Tools.log.debug("", e1);
			}

			throw ExceptionUtil.toRuntimeException(e);

		} finally {
			
			if( connection != null ) {
				if (ac != null) {
					try {
						if( close ) {
							connection.close();
						} else {
							connection.setAutoCommit(ac);
						}
					} catch (SQLException e) {
					}
				}				
			}			
			
		}

	}

}
