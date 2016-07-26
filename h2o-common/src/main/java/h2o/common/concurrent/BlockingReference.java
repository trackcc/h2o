package h2o.common.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class BlockingReference<T> {

	private final AtomicReference<T> reference = new AtomicReference<T>();

	private final CountDownLatch flag = new CountDownLatch(1);

	public boolean set(T t) {

		if (t == null) {
			throw new IllegalArgumentException("set(null)");
		}

		boolean r = reference.compareAndSet(null, t);
		if (r) {
			flag.countDown();
		}
		return r;
	}

	public boolean isOk() {
		return reference.get() != null;
	}

	public T getVal() {
		return reference.get();
	}

	public T get() throws InterruptedException {
		flag.await();
		return reference.get();
	}

	public T get(long timeout, TimeUnit unit) throws InterruptedException {
		flag.await(timeout, unit);
		return reference.get();
	}
	
	public T silentlyGet() {
		try {
			flag.await();
		} catch (InterruptedException e) {}
		
		return reference.get();
	}

	public T silentlyGet(long timeout, TimeUnit unit)  {
		try {
			flag.await(timeout, unit);
		} catch (InterruptedException e) {}
		
		return reference.get();
	}


}
