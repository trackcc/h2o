package h2o.common.util.cache;


import h2o.common.concurrent.TimesOut;

public class DataCache<T> {

    private final TimesOut timesOut;

	private final T data;
	
	public DataCache( T data , long timeout , int times  ) {
		this.data = data;
	    this.timesOut = new TimesOut( timeout , times );
	}
	
	public T getCache() {
		
		if ( timesOut.out() ) {
		    return null;
        }
		
		return data;

	}
	


}
