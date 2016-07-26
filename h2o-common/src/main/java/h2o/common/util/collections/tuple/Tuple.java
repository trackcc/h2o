package h2o.common.util.collections.tuple;

public interface Tuple extends java.io.Serializable {
	
	int size();
	
	<T> T getE(int i);

}
