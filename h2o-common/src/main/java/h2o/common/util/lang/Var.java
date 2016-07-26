package h2o.common.util.lang;

public class Var<T> implements java.io.Serializable {	

	private static final long serialVersionUID = -8028944252330143592L;
	
	private T var;
	
	public Var() {}
	
	public Var( T var ) {
		this.var = var;
	}

	public T getVar() {
		return var;
	}

	public void setVar(T var) {
		this.var = var;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((var == null) ? 0 : var.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Var other = (Var) obj;
		if (var == null) {
			if (other.var != null)
				return false;
		} else if (!var.equals(other.var))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return var == null ? null : var.toString();
	}
	
	
	

}
