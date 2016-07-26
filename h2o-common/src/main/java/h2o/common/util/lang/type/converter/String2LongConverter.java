package h2o.common.util.lang.type.converter;

import h2o.common.util.lang.type.TypeConverter;


public class String2LongConverter extends String2NumberConverter implements TypeConverter {

	@Override
	Object buildNumber(String a) {		
		return new Long( a );
	}	
	
	@Override
	public String toString() {
		return "String2LongConverter";
	}

}