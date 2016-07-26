package h2o.common.util.bean;

public interface BeanCallback<S , T> {	
	T source2Target(BeanUtil bu, S s);
}
