package h2o.common.util.ioc;

import h2o.common.Tools;

public class ButterflyFactory {
	
	private final String name;
	
	private final Butterfly bf;
	
	public ButterflyFactory( String name , String bcs ) {
		this.name = name;
		bf = new Butterfly( bcs );
	}
	
	
	public <T> T get( String id , Object... args) {
		
		Tools.log.debug("{}: get({})", name , id);
		
		return bf.instance(id , args );
	}
	
	
	public <T> T silentlyGet( String id , Object... args) {
		
		try {
			
			return get(id , args );
			
		} catch( Exception e ) {
			Tools.log.debug("",e);
		}
		
		return null;
	}
	
	public void dispose() {
		bf.dispose();
	}

}
