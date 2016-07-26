package h2o.common.dynalang;

import java.lang.reflect.Method;

public class JavaSourceCall {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		if (args.length == 0 || args[0].equals("-help")) {
			System.out.println("Usage:  JavaSourceCall [-src <path>[:<path>]...] classname { <argument> }");
			System.out.println("        JavaSourceCall -help");
			System.exit(0);
		}

		int i = 0;

		String[] srcDir = null;
		if (args[0].equals("-src")) {
			srcDir = args[1].split(":");
			i = 2;
		}

		String[] arguments = new String[args.length - i - 1];
		System.arraycopy(args, i + 1, arguments, 0, arguments.length);

		JavaSourceClassUtil ju = new JavaSourceClassUtil(srcDir);
		Class c = ju.getClass(args[i]);

		@SuppressWarnings("unchecked")
		Method m = c.getMethod("main", new Class[] { String[].class });
		m.invoke(null, new Object[] { arguments });

	}

}
