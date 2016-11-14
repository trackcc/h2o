package h2o.common.util.ioc;


public class ObjectFactory {
	
	private ObjectFactory() {}	
	
	private static final ButterflyFactory bf = new ButterflyFactory( "app" , "app.bcs");
	
	
	public static <T> T get( String id , Object... args) {
		return bf.get(id,args);
	}
	
	
	public static <T> T silentlyGet( String id , Object... args  ) {
		return bf.silentlyGet(id,args);
	}

	
	public static void dispose() {
		bf.dispose();
	}
	

}
