package h2o.dao.impl.orm;

import h2o.dao.DbUtil;
import h2o.dao.exception.DaoException;
import h2o.dao.orm.OrmProcessor;

import java.util.Map;



public class DefaultOrmProcessor implements OrmProcessor {	


	@Override
	public <T> T proc( Map<String, Object> row , Class<T> clazz ) throws DaoException {
		try {
			return DbUtil.map2BeanUtil.map2Bean( row , clazz );
		} catch( Exception  e ) {
			throw new DaoException(e);
		}
	}

}
