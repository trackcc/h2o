package h2o.dao.impl;

import h2o.common.util.dao.butterflydb.ButterflyDb;
import h2o.dao.Dao;
import h2o.dao.Db;
import h2o.dao.DbUtil;

import javax.sql.DataSource;



public class DbImpl extends AbstractDb implements Db {

	private final DataSource dataSource;

	public DbImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Dao getDao() {
		return this.getDao(true);
	}

	@Override
	public Dao getDao(boolean autoClose) {

		ButterflyDb bdb = new ButterflyDb(this.dataSource);
		Dao dao = createDao( bdb , autoClose );

		return dao;

	}
	
	protected Dao createDao( ButterflyDb bdb , boolean autoClose ) {
		return DbUtil.dbConfig.get("dao", bdb, autoClose);
	}

}
