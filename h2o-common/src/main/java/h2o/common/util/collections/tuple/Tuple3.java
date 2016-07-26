package h2o.common.util.collections.tuple;

public class Tuple3<A,B,C> implements Tuple {
	

	private static final long serialVersionUID = 8515896572423195899L;
	
	
	public final A e0;	
	public final B e1;
	public final C e2;
	
	public Tuple3( A e0 , B e1 , C e2) {
		this.e0 = e0;
		this.e1 = e1;
		this.e2 = e2;
	}



	public A getE0() {
		return e0;
	}



	public B getE1() {
		return e1;
	}



	public C getE2() {
		return e2;
	}



	public int size() {		
		return 3;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((e0 == null) ? 0 : e0.hashCode());
		result = prime * result + ((e1 == null) ? 0 : e1.hashCode());
		result = prime * result + ((e2 == null) ? 0 : e2.hashCode());
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
		@SuppressWarnings("rawtypes")
		Tuple3 other = (Tuple3) obj;
		if (e0 == null) {
			if (other.e0 != null)
				return false;
		} else if (!e0.equals(other.e0))
			return false;
		if (e1 == null) {
			if (other.e1 != null)
				return false;
		} else if (!e1.equals(other.e1))
			return false;
		if (e2 == null) {
			if (other.e2 != null)
				return false;
		} else if (!e2.equals(other.e2))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return String.format("Tuple3[e0=%s, e1=%s, e2=%s]", e0, e1, e2);
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public <T> T getE(int i) {
		if( i < 0 || i > 2 ) {
			throw new IndexOutOfBoundsException();
		}
		switch( i ) {
		case 0:
			return (T)e0;
		case 1:
			return (T)e1;
		case 2:
			return (T)e2;
		}
		
		return null;
	}
	
	
	

}
