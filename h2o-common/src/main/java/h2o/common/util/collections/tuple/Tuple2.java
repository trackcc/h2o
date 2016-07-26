package h2o.common.util.collections.tuple;

public class Tuple2<A,B> implements Tuple {

	private static final long serialVersionUID = 5786178318377966323L;
	
	public final A e0;	
	public final B e1;	
	
	public Tuple2( A e0 , B e1) {
		this.e0 = e0;
		this.e1 = e1;
	}

	public A getE0() {
		return e0;
	}

	public B getE1() {
		return e1;
	}

	public int size() {		
		return 2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((e0 == null) ? 0 : e0.hashCode());
		result = prime * result + ((e1 == null) ? 0 : e1.hashCode());
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
		Tuple2 other = (Tuple2) obj;
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
		return true;
	}

	@Override
	public String toString() {
		return String.format("Tuple2[e0=%s, e1=%s]", e0, e1);
	}

	@SuppressWarnings("unchecked")
	public <T> T getE(int i) {
		if( i < 0 || i > 1 ) {
			throw new IndexOutOfBoundsException();
		}
		switch( i ) {
		case 0:
			return (T)e0;
		case 1:
			return (T)e1;
		}
		
		return null;
	}
	
	
	
	
	 
	

}
