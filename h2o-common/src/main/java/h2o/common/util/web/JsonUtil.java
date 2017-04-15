package h2o.common.util.web;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JsonUtil {

	private JsonUtil() {}
	
	
	public static String toJson( Object obj , Map<String,String[][]> filter ) {
		Object o = Beanfilter.filter( obj , filter );
		return toJson(o);
	}
	
	public static String toJson( Object obj , Beanfilter bf , Map<String,String[][]> filter ) {
		Object o = bf.filterObject( obj , filter );
		return toJson(o);
	}

	public static String toJson(Object obj) {
		if (obj.getClass().isArray() || obj instanceof Collection) {
			return JSONArray.fromObject(obj).toString();
		} else {
			return JSONObject.fromObject(obj).toString();
		}
	}

	@SuppressWarnings("rawtypes")
	public static Map json2Map(String json) {
		return JSONObject.fromObject(json);
	}
	
	@SuppressWarnings("rawtypes")
	public static List json2List(String json) {
		return JSONArray.fromObject(json);
	}
	

	public static Object json2Object( String json ) {
		if( json == null ) return null;
		if( json.trim().startsWith("[") ) {
			return json2List(json);
		} else {
			return json2Map(json);
		}
	}

}
