package h2o.common.util.ioc;

import h2o.common.util.collections.Args;

public abstract class Factory<T> {
	
	protected final Args vars = new Args();
	
	private volatile Args args;
	
	public Factory<T> setArgs( Args args ) {
		this.args = args;
		return this;
	}	

	
	public T create() {
		return this.create( this.args );
	}
	
	public abstract T create( Args args );

}
