package h2o.common.remote;

public class RemoteRes implements java.io.Serializable {
	

	private static final long serialVersionUID = -3287665955476895644L;

	private Object r;
	
	private Throwable e;
	
	public RemoteRes() {}
	
	public RemoteRes( Object r , Throwable e ) {
		this.r = r;
		this.e = e;
	}
	

	public Object getR() {
		return r;
	}

	public void setR(Object r) {
		this.r = r;
	}

	public Throwable getE() {
		return e;
	}

	public void setE(Throwable e) {
		this.e = e;
	}
	
	

}
