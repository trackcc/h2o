package h2o.common.util.lang.type.converter;

import h2o.common.util.lang.type.TypeConverter;
import org.apache.commons.lang.StringUtils;

public abstract class String2NumberConverter  implements TypeConverter {

	public Object convert(Object a, Object defVal) throws Exception {
		
		if( StringUtils.isBlank((String)a ) ) {
			if( defVal instanceof Exception ) {
				throw (Exception)defVal;
			} else {
				return defVal;
			}
		}
		
		return buildNumber((String)a );
	}
	
	abstract Object buildNumber( String a );
}