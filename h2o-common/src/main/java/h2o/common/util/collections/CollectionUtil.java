package h2o.common.util.collections;

import h2o.common.util.bean.PreOperate;
import org.apache.commons.lang.StringUtils;

import java.util.*;



public class CollectionUtil {

	private CollectionUtil() {}
	
	
	
	public static boolean java5ArgsIsBlank( Object[] args ) {
		return args == null || args.length == 0 || ( args.length == 1 && args[0] == null);
	}
	
	public static boolean isBlank( Collection<?> c ) {
		return c == null || c.isEmpty();
	}
	
	public static boolean isNotBlank( Collection<?> c ) {
		return c != null && !c.isEmpty();
	}
	
	public static boolean isBlank( Map<?,?> m ) {
		return m == null || m.isEmpty();
	}
	
	public static boolean isNotBlank( Map<?,?> m  ) {
		return m != null && !m.isEmpty();
	}
	

	@SuppressWarnings("rawtypes")
	public static String collection2s(Collection c) {
		return collection2s(c, true);
	}

	@SuppressWarnings("rawtypes")
	public static String collection2s(Collection c, boolean is_string) {
		if (c == null || c.isEmpty()) {
			return null;
		}

		StringBuilder r = new StringBuilder();
		Iterator itr = c.iterator();
		while (itr.hasNext()) {
			String id = itr.next().toString();
			if (is_string) {
				r.append(",'");
				r.append(id);
				r.append("'");
			} else {
				r.append(",");
				r.append(id);
			}
		}

		return r.substring(1);
	}

	public static <T> List<T> toList(T[] array, PreOperate<T>... pos) {
		return toList(Arrays.asList(array), pos);
	}

	public static <T> List<T> toList(Collection<T> c, PreOperate<T>... pos) {

		ArrayList<T> r = new ArrayList<T>();

		Iterator<T> itr = c.iterator();
		while (itr.hasNext()) {
			T ai = itr.next();
			if (pos != null) {
				for (PreOperate<T> po : pos) {
					ai = po.doOperate(ai);
				}
			}
			if (ai != null) {
				r.add(ai);
			}
		}
		return r;
	}
	
	
	public static List<String> string2List( String str, String[] tns , String def) {
		return string2List(true, str, tns , def);
	}
	
	public static List<String> string2List(  String str, String... tns) {
		return string2List(true, str, tns, "");
	}

	public static List<String> string2List( boolean isAll , String str ,  String[] tns , String def) {

		ArrayList<String> r = new ArrayList<String>();
		r.add(str);
		
		if(java5ArgsIsBlank(tns)) {
			tns = new String[] { null };
		}
		
		for( String tn : tns ) {
			ArrayList<String> r2 = new ArrayList<String>();
			
			
			for( String sr : r ) {
				
				if( sr == null || "".equals(sr) ) {
					if( isAll ) {
						r2.add(def);
					}
					
				} else {
					
					String[] ss = StringUtils.splitByWholeSeparatorPreserveAllTokens(sr, tn);
					
					for( String s : ss ) {
						if(s == null || "".equals(s)) {
							if( isAll ) {
								r2.add(def);
							}
						} else {
							r2.add(s);
						}
						
					}
				}
			}
			
			r = r2;
			
		}
		
		return r;		

	}





	public static <E,T> T each( Collection<E> c , EachCallback<E,T> ec ) {

	    T t = ec.init();

        for ( E e : c ) {
            ec.doEach( e , t );
        }

        return ec.result( t );

    }
	

}
