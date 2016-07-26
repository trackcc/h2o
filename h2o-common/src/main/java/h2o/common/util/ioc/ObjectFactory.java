package h2o.common.util.ioc;


public class ObjectFactory {
	
	private ObjectFactory() {}	
	
	private static final ButterflyFactory bf = new ButterflyFactory( "app" , "app.bcs");
	
	
	public static <T> T get( String id) {		
		return bf.get(id);
	}
	
	
	public static <T> T silentlyGet( String id ) {
		return bf.silentlyGet(id);
	}
	
	public static void dispose() {
		bf.dispose();
	}
	

}
