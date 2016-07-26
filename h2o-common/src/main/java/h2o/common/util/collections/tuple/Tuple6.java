package h2o.common.util.collections.tuple;

public final class Tuple6<A,B,C,D,E,F> implements Tuple {
	

	private static final long serialVersionUID = 8262800490705223200L;
	
	public final A e0;	
	public final B e1;
	public final C e2;
	public final D e3;
	public final E e4;
	public final F e5;
	
	public Tuple6( A e0 , B e1 , C e2 , D e3 , E e4 , F e5) {
		this.e0 = e0;
		this.e1 = e1;
		this.e2 = e2;
		this.e3 = e3;
		this.e4 = e4;
		this.e5 = e5;
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



	public D getE3() {
		return e3;
	}
	

	public E getE4() {
		return e4;
	}



	public F getE5() {
		return e5;
	}



	public int size() {		
		return 6;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((e0 == null) ? 0 : e0.hashCode());
		result = prime * result + ((e1 == null) ? 0 : e1.hashCode());
		result = prime * result + ((e2 == null) ? 0 : e2.hashCode());
		result = prime * result + ((e3 == null) ? 0 : e3.hashCode());
		result = prime * result + ((e4 == null) ? 0 : e4.hashCode());
		result = prime * result + ((e5 == null) ? 0 : e5.hashCode());
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
		Tuple6 other = (Tuple6) obj;
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
		if (e3 == null) {
			if (other.e3 != null)
				return false;
		} else if (!e3.equals(other.e3))
			return false;
		if (e4 == null) {
			if (other.e4 != null)
				return false;
		} else if (!e4.equals(other.e4))
			return false;
		if (e5 == null) {
			if (other.e5 != null)
				return false;
		} else if (!e5.equals(other.e5))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return String.format("Tuple6[e0=%s, e1=%s, e2=%s, e3=%s, e4=%s, e5=%s]", e0, e1, e2, e3, e4, e5);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getE(int i) {
		if( i < 0 || i > 5 ) {
			throw new IndexOutOfBoundsException();
		}
		switch( i ) {
		case 0:
			return (T)e0;
		case 1:
			return (T)e1;
		case 2:
			return (T)e2;
		case 3:
			return (T)e3;
		case 4:
			return (T)e4;
		case 5:
			return (T)e5;
		}
		
		return null;
	}

}
