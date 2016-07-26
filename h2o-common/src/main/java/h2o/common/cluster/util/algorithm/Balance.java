package h2o.common.cluster.util.algorithm;

public interface Balance<T> {
	
	int getWeight(T t);

}
