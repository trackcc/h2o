package h2o.common.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class RunUtil {
	
	private final ExecutorService executorService;
	private final AtomicInteger count;
	private final boolean autoShutdown;
	
	public RunUtil() {
		
		count = null;
		autoShutdown = false;
		
		this.executorService = Executors.newSingleThreadExecutor();
	}
	
	public RunUtil( int n , boolean autoShutdown ) {
		
		this.autoShutdown = autoShutdown;
		if( autoShutdown ) {
			count = new AtomicInteger(n);			
		} else {
			count = null;
		}
		
		this.executorService = Executors.newFixedThreadPool(n);
	}
	
	
	public ExecutorService getExecutorService() {
		return executorService;
	}

	public <T> Future<T>  submit( Callable<T> task ) {
		
		Future<T> f = executorService.submit(task);
		
		this.ShutdownAuto();
		
		return f;
		
	}
	
	public Future<?> submit( Runnable task ) {
		
		Future<?> f = executorService.submit(task);	
		
		this.ShutdownAuto();
		
		return f;
	}
	
	
	private void ShutdownAuto() {
		if( this.autoShutdown ) {
			if( count.decrementAndGet() == 0 ) {
				executorService.shutdown();
			}
		}
		
	}
	
	public void shutdown() {
		executorService.shutdown();
	}
	
	
	public static <T> Future<T>  call( Callable<T> task ) {
		
		RunUtil runUtil = new RunUtil();
		
		Future<T> f = runUtil.submit(task);
		runUtil.shutdown();
		
		return f;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Future<T>[]  call( Callable<T> task , int n ) {
		
		if( n < 1) {
			return null;
		} else if( n == 1 ) {
			return new Future[] { call(task) };
		}
		
		RunUtil runUtil = new RunUtil(n,true);
		
		Future<T>[] fs = new Future[n];
		
		for( int i = 0 ; i < n ;  i++ ) {
			fs[i] = runUtil.submit(task);
		}
		
		return fs;
	}
	
	
	public  static  Future<?> call( Runnable task ) {
		
		RunUtil runUtil = new RunUtil();
		
		Future<?> f = runUtil.submit(task);
		runUtil.shutdown();
		
		return f;
	}
	
	

	public static  Future<?>[]  call( Runnable task , int n ) {
		
		if( n < 1) {
			return null;
		} else if( n == 1 ) {
			return new Future[] { call(task) };
		}
		
		RunUtil runUtil = new RunUtil(n,true);
		
		Future<?>[] fs = new Future[n];
		
		for( int i = 0 ; i < n ;  i++ ) {
			fs[i] = runUtil.submit(task);
		}
		
		return fs;
	}


    /**
     *
     * @param millis
     * @return interrupted
     */
	public static boolean sleep( long millis) {

        try {
            Thread.sleep( millis );
            return false;
        } catch (InterruptedException e) {
            return true;
        }

    }
	


}
