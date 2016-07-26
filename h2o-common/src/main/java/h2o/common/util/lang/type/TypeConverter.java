package h2o.common.util.lang.type;

public interface TypeConverter {
	
	Object convert(Object a, Object defVal) throws Exception;

}
