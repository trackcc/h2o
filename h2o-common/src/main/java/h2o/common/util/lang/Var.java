package h2o.common.util.lang;

public class Var<T> implements java.io.Serializable {	

	private static final long serialVersionUID = -8028944252330143592L;

	public volatile T v;

    public Var() {
    }

    public Var(T v) {
        this.v = v;
    }

    public T getV() {
        return v;
    }

    public void setV(T v) {
        this.v = v;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((v == null) ? 0 : v.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Var other = (Var) obj;
		if (v == null) {
			if (other.v != null)
				return false;
		} else if (!v.equals(other.v))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return v == null ? null : v.toString();
	}
	
	
	

}
