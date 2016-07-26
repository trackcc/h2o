package h2o.common.util.dao.butterflydb;

import javax.sql.DataSource;

public class ButterflyDbUtil { 
	
	private final DataSource dataSource;
	
	private final ThreadLocal<ButterflyDb> currThreadButterflyDb = new ThreadLocal<ButterflyDb>();
	
	public ButterflyDbUtil( DataSource dataSource ) {
		this.dataSource = dataSource;
	}
	
	public ButterflyDb create() {  
		return create( this.dataSource);
	}
	
	public static ButterflyDb create( DataSource dataSource) {
		return new ButterflyDb( dataSource );
	}
	
	
	public ButterflyDb getOrCreateCurrThreadButterflyDb() {
		ButterflyDb butterflyDb = currThreadButterflyDb.get();
		if (butterflyDb == null) {
			butterflyDb = create();
			currThreadButterflyDb.set(butterflyDb);
		}
		return butterflyDb;
	}

	public void removeCurrThreadButterflyDb() {
		currThreadButterflyDb.remove();
	}

}
