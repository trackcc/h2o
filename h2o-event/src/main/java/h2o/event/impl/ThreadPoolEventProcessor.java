package h2o.event.impl;

import h2o.event.Event;
import h2o.event.EventContext;
import h2o.event.EventProcessor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangjianwei on 2017/5/21.
 */
public class ThreadPoolEventProcessor extends BasicEventProcessor implements EventProcessor {


    private final ExecutorService es;

    public ThreadPoolEventProcessor() {
        this.es = Executors.newCachedThreadPool();
    }

    public ThreadPoolEventProcessor( int n ) {
        this.es = Executors.newFixedThreadPool(n);
    }

    public ThreadPoolEventProcessor( ExecutorService es ) {
        this.es = es;
    }

    private void _procEvent( EventContext context , Event event ) {
        super.proc( context , event );
    }


    @Override
    public void proc( final EventContext context ,  final  Event event) {

        this.es.submit(new Runnable() {

            @Override
            public void run() {
                _procEvent( context , event);
            }

        });

    }


}
