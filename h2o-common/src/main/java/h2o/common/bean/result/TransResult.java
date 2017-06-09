package h2o.common.bean.result;

import java.io.Serializable;

/**
 * Created by zhangjianwei on 2017/6/7.
 */
public class TransResult extends TransStatus implements Serializable {

    private static final long serialVersionUID = -1448116426261181686L;


    private Object result;

    private Throwable e;


    public <R> R getResult() {
        return (R)result;
    }

    public TransResult setResult(Object result) {
        this.result = result;
        return this;
    }

    public Throwable getE() {
        return e;
    }

    public TransResult setE(Throwable e) {
        this.e = e;
        return this;
    }

}
