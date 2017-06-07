package h2o.event.impl.local;

import h2o.common.concurrent.pac.ConsumersController;
import h2o.common.redis.JedisUtil;
import h2o.common.util.collections.tuple.Tuple2;
import h2o.event.Event;
import h2o.event.EventContext;
import h2o.event.EventEncoder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;

/**
 * Created by zhangjianwei on 16/7/3.
 */
public class LocalEventHelper {

    final ConsumersController<Event> eventController;
    final int n;

    public LocalEventHelper(int n ) {

        this.n = n;
        this.eventController = new ConsumersController<Event>(
                Executors.newFixedThreadPool(n),new ArrayBlockingQueue<Event>(n));

    }


}
