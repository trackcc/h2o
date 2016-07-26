package h2o.common.util.lang.type.converter;

import h2o.common.util.lang.type.TypeConverter;

import java.math.BigDecimal;

public class String2BigDecimalConverter extends String2NumberConverter implements TypeConverter {

	@Override
	Object buildNumber(String a) {		
		return new BigDecimal( a );
	}
	
	@Override
	public String toString() {
		return "String2BigDecimalConverter";
	}

}
