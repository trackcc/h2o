package h2o.common.util.dao.datasource;

import h2o.common.Tools;
import h2o.common.concurrent.RunUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class H2oSimpleDataSource extends AbstractDataSourceProxy {
	
	private final BlockingQueue<Connection> cbq;

    private volatile boolean stop = false;

    public H2oSimpleDataSource( DataSource realDataSource , int size ) {
		this(realDataSource, size, 2);
	}
	

	public H2oSimpleDataSource( DataSource realDataSource , int size , int threadSize ) {
		
		super(realDataSource);		
		cbq = new ArrayBlockingQueue<Connection>(size);
		
		RunUtil.call( new Runnable() {

			public void run() {
				
				while ( !stop ) {
					
					Connection c = null;
					
					try {
						c = getConnection0();						
					} catch ( Exception e ) {
						Tools.log.debug("Get Connection Exception : {} ", e.getMessage());						
					}
					
					try {
						if( c == null ) {
							Thread.sleep(10000);
						} else {							
							cbq.put(c);
							c = null;
						}
					} catch (InterruptedException e) {
					} catch ( Exception e) {
						e.printStackTrace();
						Tools.log.error(e);
					} finally {
						if( c != null ) {
							try {
								c.close();
							} catch ( Exception e ) {}
							
						}
					}
				}
				
			}
			
		} , threadSize );
	}

	@Override
	public Connection getConnection() throws SQLException {
		Connection c = null;
		try {
			c = cbq.isEmpty() ? null : cbq.poll(10l,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		}
		if( c == null ) {
			return getConnection0();
		} else {
			return c;
		}
	}
	
	private Connection getConnection0() throws SQLException {
		
		long s = System.currentTimeMillis();
		try {
			return super.getConnection();
		} finally {
			long e = System.currentTimeMillis();
			Tools.log.debug("Get Connection : {}ms ", e - s );
		}
	}
	
	

	public void close() {
        this.stop = true;
    }


}
