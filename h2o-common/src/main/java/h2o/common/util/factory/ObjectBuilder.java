package h2o.common.util.factory;

public interface ObjectBuilder<T> {
	
	T build(Object... args);

}
