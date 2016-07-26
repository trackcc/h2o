package h2o.common.util.lang.type.converter;

import h2o.common.util.lang.type.TypeConverter;


public class String2IntegerConverter extends String2NumberConverter implements TypeConverter {

	@Override
	Object buildNumber(String a) {		
		return new Integer( a );
	}
	
	@Override
	public String toString() {
		return "String2IntegerConverter";
	}

}