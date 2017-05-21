package h2o.event.manager;


import h2o.common.Tools;
import h2o.common.concurrent.pac.Consumer;
import h2o.common.concurrent.pac.ConsumersController;
import h2o.common.exception.ExceptionUtil;
import h2o.event.Event;
import h2o.event.EventManager;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;

/**
 * Created by zhangjianwei on 16/7/2.
 */
public class PACEventManager extends BasicEventManager implements EventManager {


    private final ConsumersController<Event> cc;

    public PACEventManager( int n ) {
    	
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
