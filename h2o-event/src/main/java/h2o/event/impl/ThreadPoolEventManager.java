package h2o.event.impl;

import h2o.event.Event;
import h2o.event.EventManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangjianwei on 2017/5/21.
 */
public class ThreadPoolEventManager extends BasicEventManager implements EventManager {


    private final ExecutorService es;

    public ThreadPoolEventManager() {
        this.es = Executors.newCachedThreadPool();
    }

    public ThreadPoolEventManager(ExecutorService es) {
        this.es = es;
    }

    private void procEvent( Event event ) {
        super.onEvent(event);
    }


    @Override
    public void onEvent( final  Event event) {

        this.es.submit(new Runnable() {

            @Override
            public void run() {
                procEvent(event);
            }

        });

    }


}
