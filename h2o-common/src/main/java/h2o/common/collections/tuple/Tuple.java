package h2o.common.collections.tuple;

public interface Tuple extends java.io.Serializable {
	
	int size();
	
	<T> T getE(int i);

}
