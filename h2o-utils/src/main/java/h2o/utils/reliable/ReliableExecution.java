package h2o.utils.reliable;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhangjianwei on 2016/12/27.
 */
public class ReliableExecution {

    private final int tryTimes; // 尝试次数

    private final long tryInterval; // 尝试时间间隔

    public ReliableExecution() {
        this.tryTimes = 3;
        this.tryInterval = 5000;
    }

    public ReliableExecution(int tryTimes, long tryInterval) {
        this.tryTimes = tryTimes;
        this.tryInterval = tryInterval;
    }

    public void exec(ReliableExecutor executor) {

        for (int i = 1; i <= tryTimes; i++) {

            try {

                if ( executor.exec(i) ) {
                    break;
                }

            } catch (Exception e) {

                if ( ! executor.exceptionHandler(i, e) ) {
                    break;
                }

            }

            try {
                TimeUnit.MILLISECONDS.sleep(tryInterval);
            } catch (Exception e1) {
            }

        }

    }

}
