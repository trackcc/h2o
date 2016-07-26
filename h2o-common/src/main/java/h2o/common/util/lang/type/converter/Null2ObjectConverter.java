package h2o.common.util.lang.type.converter;

import h2o.common.util.lang.type.TypeConverter;


public class Null2ObjectConverter implements TypeConverter {

	public Object convert(Object a, Object defVal) throws Exception {

		if (defVal instanceof Exception) {
			throw (Exception) defVal;
		} else {
			return defVal;
		}

	}

	@Override
	public String toString() {
		return "Null2ObjectConverter";
	}
	
	

}