package h2o.common.concurrent.pac;

import h2o.common.Tools;
import org.apache.commons.lang.Validate;

import java.util.Collection;
import java.util.concurrent.*;

public class ConsumersController<T> {
	
	
	private volatile boolean stop;
	
	private final BlockingQueue<T> basket;
	
	private final ExecutorService es;
	
	
	private class ConsumerThread implements Callable<String>{
		
		private Consumer<T> consumer;
		
		public ConsumerThread( final Consumer<T> consumer ) {
			this.consumer = consumer;
		}

		public String call() throws Exception {
			
			while( !stop || !(es.isShutdown() && basket.isEmpty() )) {
				
				try {

                    T p = basket.poll(60, TimeUnit.SECONDS);

                    if (p != null) {
                        this.consumer.consume(p);
                    }

                } catch ( InterruptedException e ) {
                    Thread.currentThread().interrupt();
				} catch( Throwable e) {
					Tools.log.debug("ConsumersController.ConsumerThread::call",e);					
				}
				
			}
			
			Tools.log.info("OK");
			
			return null;
			
			
		}
		
	}
	
	
	
	
	public ConsumersController(ExecutorService executorService ,  BlockingQueue<T> basket ) {

		Validate.notNull(executorService, "executorService==null");
		Validate.notNull(basket, "basket==null");
		
		this.es = executorService;
		this.basket = basket;
		
	}
	
	public ConsumersController(int threadPoolSize ,  BlockingQueue<T> basket ) {
		
		Validate.notNull(basket, "basket==null");
		
		this.es = Executors.newFixedThreadPool(threadPoolSize);
		this.basket = basket;
	}
	
	public void putProduct( T product ) throws InterruptedException {
		this.basket.put(product);
	}

	public boolean addProduct( T product )  {
		return this.basket.add(product);
	}
	
	public boolean offerProduct( T product )  {
		return this.basket.offer(product);
	}
	
	public boolean offerProduct( T product, long timeout, TimeUnit unit) throws InterruptedException	{
		return this.basket.offer(product, timeout, unit);
	}
	
	
	public void addConsumer(Consumer<T> consumer ) {
		
		if( this.stop ) {
			throw new RuntimeException("Controller stoped.");
		}
		
		if( this.es.isShutdown() ) {
			throw new RuntimeException("Controller done.");
		}
		
		this.es.submit(new ConsumerThread(consumer));
	}
	
	
	public void addConsumers(Consumer<T> consumer , int size ) {
		for(int i = 0 ; i < size ; i++ ) {
			this.addConsumer(consumer);
		}
	}
	
	
	public void addConsumers(Consumer<T>... consumers ) {
		for( Consumer<T> consumer : consumers ) {
			this.addConsumer(consumer);
		}
	}
	
	public void addConsumers(Collection<Consumer<T>> consumers ) {
		for( Consumer<T> consumer : consumers ) {
			this.addConsumer(consumer);
		}
	}

	
	public void done() {
		es.shutdown();
	}
	
	public void stop() {		
		stop = true;	
		es.shutdown();
	}
	
	

}
