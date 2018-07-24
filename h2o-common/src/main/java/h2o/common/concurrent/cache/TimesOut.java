package h2o.common.concurrent.cache;

import java.util.concurrent.atomic.AtomicLong;

public class TimesOut {

    private final long createTime;

    private AtomicLong times = new AtomicLong();

    private final long timeout;

    private final long maxTimes;

    public TimesOut( long timeout , long maxTimes) {
        this.createTime = System.currentTimeMillis();
        this.timeout = timeout;
        this.maxTimes = maxTimes;
    }

    public boolean out() {


        long t = times.incrementAndGet();

        if( t > maxTimes ) {
            return true;
        }

        if( timeout > 0 && System.currentTimeMillis() - createTime > timeout ) {
            return true;
        }

        return false;

    }

    public long getTimes() {
        return times.get();
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - createTime;
    }
}
