package h2o.common.cluster.util.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Weight {
	
	private static final Random RND = new Random();
	
	private List<Integer> ws = new ArrayList<Integer>() ;	
	
	private int sum = 0;
	private int size = 0;
	
	public Weight() {}
	
	public Weight( int... w ) {		
		this.addWeight(w);
	}
	
	public void addWeight( int... w ) {
		for( int wi : w ) {
			sum += wi;
			ws.add(sum);
			size++;
		}
	}
	
	public void removeWeight( int index ) {
		int w = ws.remove(index);		
		this.sum -= w;
		this.size--;
	}
	
	public int getWeightedSum() {
		return sum;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getIndex( int w ) {
		int i = 0;
		for( int wi : ws ) {			
			if( w < wi  ) {
				break;
			}
			
			i++;
		}
		
		return i;
	}
	
	public int getIndex() {
		return this.getIndex( RND.nextInt(sum) );
	}

	

}
