package h2o.common.util.dao.rowproc;

public interface Row2BeanProcessor<T> {
	public T toBean(Object row) ;
}
