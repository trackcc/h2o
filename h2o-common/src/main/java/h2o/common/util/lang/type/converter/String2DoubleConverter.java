package h2o.common.util.lang.type.converter;

import h2o.common.util.lang.type.TypeConverter;


public class String2DoubleConverter  extends String2NumberConverter implements TypeConverter {

	@Override
	Object buildNumber(String a) {		
		return new Double( a );
	}
	
	@Override
	public String toString() {
		return "String2DoubleConverter";
	}

}
