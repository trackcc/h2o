package h2o.common.util.dao.butterflydb;

import com.jenkov.db.itf.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("rawtypes")
public class ButterflyObjectDao implements IObjectDao {

	private final IObjectDao objectDao;

	public ButterflyObjectDao(IObjectDao objectDao) {
		this.objectDao = objectDao;
	}

	public IPersistenceConfiguration getConfiguration() {
		return objectDao.getConfiguration();
	}

	public Connection getConnection() {
		return objectDao.getConnection();
	}

	public void closeConnection() {
		try {
			objectDao.closeConnection();
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public void setAutoCommit(boolean autoCommit) {
		try {
			objectDao.setAutoCommit(autoCommit);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public void commit() {
		try {
			objectDao.commit();
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public void rollback() {
		try {
			objectDao.rollback();
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public List getUpdateResults() {
		return objectDao.getUpdateResults();
	}

	public UpdateResult getUpdateResult(int index) {
		return objectDao.getUpdateResult(index);
	}

	public UpdateResult getLastUpdateResult() {
		return objectDao.getLastUpdateResult();
	}

	public long getLastGeneratedKeyAsLong() {
		return objectDao.getLastGeneratedKeyAsLong();
	}

	public long getLastGeneratedKeyAsBigDecimal() {
		return objectDao.getLastGeneratedKeyAsBigDecimal();
	}

	public Object readByPrimaryKey(Object objectMappingKey, Object primaryKey) {
		try {
			return objectDao.readByPrimaryKey(objectMappingKey, primaryKey);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public <T> T readByPrimaryKey(Class<T> objectMappingKey, Object primaryKey) {
		try {
			return objectDao.readByPrimaryKey(objectMappingKey, primaryKey);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public Object read(Object objectMappingKey, String sql) {
		try {
			return objectDao.read(objectMappingKey, sql);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public <T> T read(Class<T> objectMappingKey, String sql) {
		try {
			return objectDao.read(objectMappingKey, sql);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public Object read(Object objectMappingKey, ResultSet result) {
		try {
			return objectDao.read(objectMappingKey, result);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public <T> T read(Class<T> objectMappingKey, ResultSet result) {
		try {
			return objectDao.read(objectMappingKey, result);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public Object read(Object objectMappingKey, Statement statement, String sql) {
		try {
			return objectDao.read(objectMappingKey, statement, sql);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public <T> T read(Class<T> objectMappingKey, Statement statement, String sql) {
		try {
			return objectDao.read(objectMappingKey, statement, sql);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public Object read(Object objectMappingKey, PreparedStatement statement) {
		try {
			return objectDao.read(objectMappingKey, statement);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public <T> T read(Class<T> objectMappingKey, PreparedStatement statement) {
		try {
			return objectDao.read(objectMappingKey, statement);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public Object read(Object objectMappingKey, String sql, Collection parameters) {
		try {
			return objectDao.read(objectMappingKey, sql, parameters);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public <T> T read(Class<T> objectMappingKey, String sql, Collection parameters) {
		try {
			return objectDao.read(objectMappingKey, sql, parameters);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public Object read(Object objectMappingKey, String sql, Object... parameters) {
		try {
			return objectDao.read(objectMappingKey, sql, parameters);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public <T> T read(Class<T> objectMappingKey, String sql, Object... parameters) {
		try {
			return objectDao.read(objectMappingKey, sql, parameters);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public List readListByPrimaryKeys(Object objectMappingKey, Collection primaryKeys) {
		try {
			return objectDao.readListByPrimaryKeys(objectMappingKey, primaryKeys);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public <T> List<T> readListByPrimaryKeys(Class<T> objectMappingKey, Collection primaryKeys) {
		try {
			return objectDao.readListByPrimaryKeys(objectMappingKey, primaryKeys);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public List readList(Object objectMappingKey, String sql) {
		try {
			return objectDao.readList(objectMappingKey, sql);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public <T> List<T> readList(Class<T> objectMappingKey, String sql) {
		try {
			return objectDao.readList(objectMappingKey, sql);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public List readList(Object objectMappingKey, ResultSet result) {
		try {
			return objectDao.readList(objectMappingKey, result);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public <T> List<T> readList(Class<T> objectMappingKey, ResultSet result) {
		try {
			return objectDao.readList(objectMappingKey, result);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public List readList(Object objectMappingKey, Statement statement, String sql) {
		try {
			return objectDao.readList(objectMappingKey, statement, sql);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public <T> List<T> readList(Class<T> objectMappingKey, Statement statement, String sql) {
		try {
			return objectDao.readList(objectMappingKey, statement, sql);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public List readList(Object objectMappingKey, PreparedStatement statement) {
		try {
			return objectDao.readList(objectMappingKey, statement);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public <T> List<T> readList(Class<T> objectMappingKey, PreparedStatement statement) {
		try {
			return objectDao.readList(objectMappingKey, statement);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public List readList(Object objectMappingKey, String sql, Collection parameters) {
		try {
			return objectDao.readList(objectMappingKey, sql, parameters);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public <T> List<T> readList(Class<T> objectMappingKey, String sql, Collection parameters) {
		try {
			return objectDao.readList(objectMappingKey, sql, parameters);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public List readList(Object objectMappingKey, String sql, Object... parameters) {
		try {
			return objectDao.readList(objectMappingKey, sql, parameters);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public <T> List<T> readList(Class<T> objectMappingKey, String sql, Object... parameters) {
		try {
			return objectDao.readList(objectMappingKey, sql, parameters);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public List readList(Object objectMappingKey, String sql, IReadFilter filter) {
		try {
			return objectDao.readList(objectMappingKey, sql, filter);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public <T> List<T> readList(Class<T> objectMappingKey, String sql, IReadFilter filter) {
		try {
			return objectDao.readList(objectMappingKey, sql, filter);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public List readList(Object objectMappingKey, ResultSet result, IReadFilter filter) {
		try {
			return objectDao.readList(objectMappingKey, result, filter);
		} catch (PersistenceException e) {

			throw new ButterflyDbException(e);
		}
	}

	public <T> List<T> readList(Class<T> objectMappingKey, ResultSet result, IReadFilter filter) {
		try {
			return objectDao.readList(objectMappingKey, result, filter);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public List readList(Object objectMappingKey, Statement statement, String sql, IReadFilter filter) {
		try {
			return objectDao.readList(objectMappingKey, statement, sql, filter);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public <T> List<T> readList(Class<T> objectMappingKey, Statement statement, String sql, IReadFilter filter) {
		try {
			return objectDao.readList(objectMappingKey, statement, sql, filter);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public List readList(Object objectMappingKey, PreparedStatement statement, IReadFilter filter) {
		try {
			return objectDao.readList(objectMappingKey, statement, filter);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public <T> List<T> readList(Class<T> objectMappingKey, PreparedStatement statement, IReadFilter filter) {
		try {
			return objectDao.readList(objectMappingKey, statement, filter);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public List readList(Object objectMappingKey, String sql, IReadFilter filter, Collection parameters) {
		try {
			return objectDao.readList(objectMappingKey, sql, filter, parameters);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public <T> List<T> readList(Class<T> objectMappingKey, String sql, IReadFilter filter, Collection parameters) {
		try {
			return objectDao.readList(objectMappingKey, sql, filter, parameters);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public List readList(Object objectMappingKey, String sql, IReadFilter filter, Object... parameters) {
		try {
			return objectDao.readList(objectMappingKey, sql, filter, parameters);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public <T> List<T> readList(Class<T> objectMappingKey, String sql, IReadFilter filter, Object... parameters) {
		try {
			return objectDao.readList(objectMappingKey, sql, filter, parameters);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public int insert(Object object) {
		try {
			return objectDao.insert(object);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public int insert(Object objectMappingKey, Object object) {
		try {
			return objectDao.insert(objectMappingKey, object);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public int[] insertBatch(Collection objects) {
		try {
			return objectDao.insertBatch(objects);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public int[] insertBatch(Object objectMappingKey, Collection objects) {
		try {
			return objectDao.insertBatch(objectMappingKey, objects);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public int update(Object object) {
		try {
			return objectDao.update(object);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public int update(Object objectMappingKey, Object object) {
		try {
			return objectDao.update(objectMappingKey, object);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public int updateByPrimaryKey(Object object, Object oldPrimaryKeyValue) {
		try {
			return objectDao.updateByPrimaryKey(object, oldPrimaryKeyValue);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public int updateByPrimaryKey(Object objectMappingKey, Object object, Object oldPrimaryKeyValue) {
		try {
			return objectDao.updateByPrimaryKey(objectMappingKey, object, oldPrimaryKeyValue);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public int[] updateBatch(Collection objects) {
		try {
			return objectDao.updateBatch(objects);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public int[] updateBatch(Object objectMappingKey, Collection objects) {
		try {
			return objectDao.updateBatch(objectMappingKey, objects);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public int[] updateBatchByPrimaryKeys(Collection objects, Collection oldPrimaryKeys) {
		try {
			return objectDao.updateBatchByPrimaryKeys(objects, oldPrimaryKeys);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public int[] updateBatchByPrimaryKeys(Object objectMappingKey, Collection objects, Collection oldPrimaryKeys) {
		try {
			return objectDao.updateBatchByPrimaryKeys(objectMappingKey, objects, oldPrimaryKeys);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public int delete(Object object) {
		try {
			return objectDao.delete(object);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public int delete(Object objectMappingKey, Object object) {
		try {
			return objectDao.delete(objectMappingKey, object);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public int[] deleteBatch(Collection objects) {
		try {
			return objectDao.deleteBatch(objects);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public int[] deleteBatch(Object objectMappingKey, Collection objects) {
		try {
			return objectDao.deleteBatch(objectMappingKey, objects);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public int deleteByPrimaryKey(Object objectMappingKey, Object primaryKey) {
		try {
			return objectDao.deleteByPrimaryKey(objectMappingKey, primaryKey);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

	public int[] deleteBatchByPrimaryKeys(Object objectMappingKey, Collection primaryKeys) {
		try {
			return objectDao.deleteBatchByPrimaryKeys(objectMappingKey, primaryKeys);
		} catch (PersistenceException e) {
			throw new ButterflyDbException(e);
		}
	}

}
