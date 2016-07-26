package h2o.common.cluster.util.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightGroup<T> {

	private final List<T> ws = new java.util.concurrent.CopyOnWriteArrayList<T>();

	private volatile Balance<T> balance;

	public void setBalance(Balance<T> balance) {
		this.balance = balance;
	}

	public WeightGroup() {
	}

	public WeightGroup(Balance<T> b, T... ts) {
		this.balance = b;
		this.addWeight(ts);
	}

	public void addWeight(T... ts) {

		for (T t : ts) {
			this.ws.add(t);
		}

	}

	public void removeWeight(T t) {

		this.ws.remove(t);

	}

	public T draw() {

		Map<Integer, Integer> ii = new HashMap<Integer, Integer>();
		Weight w = new Weight();

		int i = 0;
		for (T t : ws) {

			int wi = this.balance.getWeight(t);

			if (wi > 0) {
				ii.put(w.getSize(), i);
				w.addWeight(wi);
			}

			i++;
		}

		int index = w.getIndex();

		return ws.get(ii.get(index));

	}

}
