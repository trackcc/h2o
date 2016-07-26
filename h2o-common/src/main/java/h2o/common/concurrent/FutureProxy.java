package h2o.common.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.Validate;

public class FutureProxy<T> implements Future<T> {

	private static class FutureStatus<T> {

		public boolean fCancel = false;
		public boolean resultDone = false;

		public T result;
		public Throwable resultException;

		public Future<T> realFuture;

	}

	private final BlockingReference<FutureStatus<T>> futureStatusReference = new BlockingReference<FutureStatus<T>>();


	public boolean setResult(T result) {

		if (futureStatusReference.isOk()) {
			return false;
		}

		FutureStatus<T> f = new FutureStatus<T>();
		f.result = result;
		f.resultDone = true;

		return futureStatusReference.set(f);

	}

	public boolean setResultException(Throwable resultException) {

		Validate.notNull( resultException , "resultException == null" );
		

		if (futureStatusReference.isOk()) {
			return false;
		}

		FutureStatus<T> f = new FutureStatus<T>();
		f.resultException = resultException;
		f.resultDone = true;

		return futureStatusReference.set(f);

	}

	public boolean isFCancel() {

		FutureStatus<T> f2 = futureStatusReference.getVal();
		return f2 == null ? false : f2.fCancel;
	}

	public boolean setRealFuture(Future<T> realFuture) {

		Validate.notNull(realFuture ,"realFuture == null");
		

		if (futureStatusReference.isOk()) {
			return false;
		}

		FutureStatus<T> f = new FutureStatus<T>();
		f.realFuture = realFuture;

		return futureStatusReference.set(f);

	}

	public boolean cancel(boolean mayInterruptIfRunning) {

		if ( !futureStatusReference.isOk() ) {

			FutureStatus<T> f = new FutureStatus<T>();
			f.fCancel = true;

			futureStatusReference.set(f);
		}

		FutureStatus<T> f2 = futureStatusReference.getVal();

		if (f2 != null && f2.realFuture != null) {
			return f2.realFuture.cancel(mayInterruptIfRunning);
		} else {
			return true;
		}

	}

	public T get() throws InterruptedException, ExecutionException {

		FutureStatus<T> f2 = futureStatusReference.get();
		if (f2.resultDone) {

			if (f2.resultException != null) {
				throw new ExecutionException(f2.resultException);
			}

			return f2.result;
		}

		if (f2.fCancel) {
			throw new CancellationException();
		}

		if (f2.realFuture == null) {
			throw new ExecutionException(new RuntimeException("realFuture == null"));
		}

		return f2.realFuture.get();
	}

	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {

		long msTimeout = unit.toMillis(timeout);

		long ct = System.currentTimeMillis();

		FutureStatus<T> f2 = futureStatusReference.get(msTimeout, TimeUnit.MILLISECONDS);

		long msTo2 = msTimeout - (System.currentTimeMillis() - ct);
		

		if (f2 != null) {
			if (f2.resultDone) {

				if (f2.resultException != null) {
					throw new ExecutionException(f2.resultException);
				}

				return f2.result;
			}

			if (f2.fCancel) {
				throw new CancellationException();
			}

			if (f2.realFuture == null) {
				throw new ExecutionException(new RuntimeException("realFuture == null"));
			}

			if (msTo2 > 0) {
				return f2.realFuture.get(msTo2, TimeUnit.MILLISECONDS);
			}

		} 
		
		throw new TimeoutException();	

	}

	public boolean isCancelled() {

		FutureStatus<T> f2 = futureStatusReference.getVal();

		if (f2 == null) {
			return false;
		}

		if (f2.realFuture != null) {
			return f2.realFuture.isCancelled();
		}

		return f2.fCancel;

	}

	public boolean isDone() {

		FutureStatus<T> f2 = futureStatusReference.getVal();

		if (f2 == null) {
			return false;
		}

		if (f2.resultDone) {
			return true;
		}

		if (f2.realFuture != null) {
			return f2.realFuture.isDone();
		} else {
			return f2.fCancel;
		}

	}

}
