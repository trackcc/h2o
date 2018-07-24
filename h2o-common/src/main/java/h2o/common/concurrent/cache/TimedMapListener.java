package h2o.common.concurrent.cache;

public interface TimedMapListener {
	
	boolean onPutNewValue(Object key, Object value, final Object newValue);
	
	void onRemoved(Object key, Object value);

}
