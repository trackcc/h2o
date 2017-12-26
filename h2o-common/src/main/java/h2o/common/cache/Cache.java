package h2o.common.cache;

public interface Cache<T> {

	void set(T o);

	T get();
	
	T clear();

}
