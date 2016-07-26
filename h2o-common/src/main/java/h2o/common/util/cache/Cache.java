package h2o.common.util.cache;

public interface Cache<T> {

	void set(T o);

	T get();
	
	T clear();

}
