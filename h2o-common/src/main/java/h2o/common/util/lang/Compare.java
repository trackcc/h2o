package h2o.common.util.lang;

public class Compare {

	public static Integer nullcompare(Object a0, Object a1) {

		if (a0 == null && a1 == null) {
			return 0;
		}

		if (a0 == null) {
			return -1;
		} else if (a1 == null) {
			return 1;
		} else {
			return null;
		}

	}

	public static int stringCompare(String a0, String a1, int type) {
		Integer r = nullcompare(a0, a1);
		if (r == null) {
			if ((type & 1) != 0) {
				a0 = a0.trim();
				a1 = a1.trim();
			}
			if ((type & 2) != 0) {
				a0 = a0.toUpperCase();
				a1 = a1.toUpperCase();
			}

			r = a0.compareTo(a1);
		}

		return r;
	}

}
