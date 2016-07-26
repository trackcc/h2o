package h2o.common.schedule;

import h2o.common.Tools;
import h2o.common.concurrent.RunUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class InterruptAbleRepetitiveTask implements RepetitiveTask {

	private final long timeout;

	private final RepetitiveTask realRepetitiveTask;

	public InterruptAbleRepetitiveTask(long timeout) {
		this.timeout = timeout;
		this.realRepetitiveTask = null;		
	}

	public InterruptAbleRepetitiveTask(RepetitiveTask realRepetitiveTask, long timeout) {
		this.timeout = timeout;
		this.realRepetitiveTask = realRepetitiveTask;		
	}

	private volatile Future<Integer> future;
	
	private final RunUtil runUtil = new RunUtil();

	final public int doTask() throws Throwable {

		future = runUtil.submit( new Callable<Integer>() {

			public Integer call() throws Exception {
				try {
					return doInterruptedAbleTask();
				} catch (Exception e) {
					throw e;
				} catch (Throwable e) {
					Tools.log.error("", e);
					throw new Exception(e);
				}

			}

		});
		
				
		if(timeout > 0) {
			try {
				return future.get(timeout, TimeUnit.MILLISECONDS);
			} catch( TimeoutException e ) {
				future.cancel(true);
				throw e;
			}
		} else {
			 return future.get();
		}

	}

	protected int doInterruptedAbleTask() throws Throwable {
		return realRepetitiveTask.doTask();
	}

	public void stop() {

		Future<Integer> f = future;
		if (f == null) {
			return;
		} else {
			f.cancel(true);
		}

	}
	
	public void shutdown() {
		runUtil.shutdown();
	}

}
