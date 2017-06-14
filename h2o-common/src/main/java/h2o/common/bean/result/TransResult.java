package h2o.common.bean.result;

import java.io.Serializable;

/**
 * Created by zhangjianwei on 2017/6/7.
 */
public class TransResult<R> extends TransStatus implements Serializable {

    private static final long serialVersionUID = -1448116426261181686L;


    private R result;

    private Throwable e;


    public R getResult() {
        return result;
    }

    public TransResult setResult( R result ) {
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

    @Override
    public TransResult setStatus(int status) {
        super.setStatus(status);
        return this;
    }

    @Override
    public TransResult setFinal(boolean aFinal) {
        super.setFinal(aFinal);
        return this;
    }

    @Override
    public TransResult setCode(String code) {
        super.setCode(code);
        return this;
    }

    @Override
    public TransResult setMsg(String msg) {
        super.setMsg(msg);
        return this;
    }
}
