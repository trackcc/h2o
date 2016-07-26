package h2o.common.util.collections.bdb;

import h2o.common.Tools;
import h2o.common.util.io.FileUtil;

import java.io.File;


import com.sleepycat.bind.EntryBinding;
import com.sleepycat.collections.StoredSortedMap;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

public class BdbMapUtil<K, V> {

	private volatile Environment env;
	private volatile Database db;

	private volatile String databaseRootDir = "/var/tmp/bdb";
	private volatile int cachePercent = 0;
	private volatile long cacheSize = 0;

	public void setDatabaseRootDir(String databaseRootDir) {
		this.databaseRootDir = databaseRootDir;
	}

	public void setCacheSize(long cacheSize) {
		this.cacheSize = cacheSize;
	}

	public void setCachePercent(int cachePercent) {
		this.cachePercent = cachePercent;
	}

	private final  String tableName;
	private final EntryBinding<K> keyBinding;
	private final EntryBinding<V> valueBinding;
	private final boolean duplicatesAllowed;

	public BdbMapUtil(String tableName, EntryBinding<K> keyBinding, EntryBinding<V> valueBinding, boolean duplicatesAllowed) {
		this.tableName = tableName;
		this.keyBinding = keyBinding;
		this.valueBinding = valueBinding;
		this.duplicatesAllowed = duplicatesAllowed;
	}

	public void open(boolean rebuild) {

		File f = new File(databaseRootDir + "/" + tableName);

		if (f.exists()) {
			if (rebuild) {
				FileUtil.rmdir(f);
				f.mkdirs();
			}
		} else {
			f.mkdirs();
		}

		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setTransactional(false);
		envConfig.setAllowCreate(true);

		if (cacheSize > 0) {
			envConfig.setCacheSize(cacheSize);
		}
		if (cachePercent > 0) {
			envConfig.setCachePercent(cachePercent);
		}

		try {

			env = new Environment(f, envConfig);

			DatabaseConfig dbConfig = new DatabaseConfig();
			dbConfig.setTransactional(false);
			dbConfig.setSortedDuplicates(duplicatesAllowed);
			dbConfig.setAllowCreate(true);

			dbConfig.setDeferredWrite(true);

			this.db = env.openDatabase(null, tableName, dbConfig);

		} catch (DatabaseException e) {
			Tools.log.debug("open", e);
			throw new BdbException(e);
		}

	}

	public void sync() {
		try {
			if (db != null) {
				db.sync();
			}
			if (env != null) {
				env.sync();
			}
		} catch (DatabaseException e) {
			Tools.log.debug("sync", e);
			throw new BdbException(e);
		}
	}

	public void close() {
		try {
			if (db != null) {
				db.sync();
				db.close();
				db = null;
			}
			if (env != null) {
				env.sync();
				env.close();
				env = null;
			}
		} catch (DatabaseException e) {
			Tools.log.debug("close", e);
			throw new BdbException(e);
		}

	}

	public StoredSortedMap<K, V> createMap(boolean writeAllowed) {
		return new StoredSortedMap<K, V>(db, keyBinding, valueBinding, writeAllowed);
	}

}
