package h2o.common.ioc;

import h2o.common.Tools;

public class ButterflyFactory implements Factory {
	
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


    public void init() {
        bf.init();
    }

    public void execPhase(String phase) {
        bf.execPhase(phase);
    }

    public void execPhase(String phase, String name) {
        bf.execPhase(phase, name);
    }

    public void dispose() {
		bf.dispose();
	}

}
