package h2o.common.util.collections.link;

public class DNode<T> {

	DNode<T> prev;
	
	DNode<T> next;
	
	public final T data;
	
	public DNode( T data ) {
		this.data = data;
	}
	

}
