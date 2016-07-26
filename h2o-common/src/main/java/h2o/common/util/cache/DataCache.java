package h2o.common.util.cache;


public class DataCache<T> {
	
	private final long ct;
	
	private volatile int times = 0;

	private final T data;
	
	public DataCache( T data ) {
		this.data = data;
		this.ct = System.currentTimeMillis();
	}
	
	public T getCache( long timeout , int times ) {
		
		
		if( timeout > 0 && System.currentTimeMillis() - ct > timeout ) {
			return null;
		}
		
		if( this.times > times ) {
			return null;
		}
		
		this.times ++;
		
		return data;
		
		
	}
	


}
