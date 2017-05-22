package h2o.event.impl;


import h2o.common.Tools;
import h2o.common.concurrent.pac.Consumer;
import h2o.common.concurrent.pac.ConsumersController;
import h2o.common.exception.ExceptionUtil;
import h2o.event.Event;
import h2o.event.EventProcessor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;

/**
 * Created by zhangjianwei on 16/7/2.
 */
public class PACEventProcessor extends BasicEventProcessor implements EventProcessor {


    private final ConsumersController<Event> cc;

    public PACEventProcessor(int n ) {
    	
        cc = new ConsumersController<Event>(Executors.newFixedThreadPool(n),new ArrayBlockingQueue<Event>(n));
        cc.addConsumers( new Consumer<Event>() {

            @Override
            public void consume(Event event) {
                procEvent( event );
            }

        } , n );

    }


    private void procEvent( Event event ) {
        super.onEvent(event);
    }


    @Override
    public void onEvent(Event event) {

        try {
            cc.putProduct(event);
        } catch ( Exception e ) {
            Tools.log.error(e);
            throw ExceptionUtil.toRuntimeException(e);
        }


    }


    public void done() {
        cc.done();
    }


}
