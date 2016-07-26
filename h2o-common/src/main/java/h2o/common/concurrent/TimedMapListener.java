package h2o.common.concurrent;

public interface TimedMapListener {
	
	boolean onPutNewValue(Object key, Object value, final Object newValue);
	
	void onRemoved(Object key, Object value);

}
