package h2o.common.bean;

import java.io.Serializable;

/**
 * Created by zhangjianwei on 2017/6/7.
 */
public class TransResult implements Serializable {

    private static final long serialVersionUID = -1448116426261181686L;

    private int status = -1;

    private boolean isFinal;

    private String code;

    private String msg;

    private Object result;

    private Throwable e;

    public int getStatus() {
        return status;
    }

    public TransResult setStatus(int status) {
        this.status = status;
        return this;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public TransResult setFinal(boolean aFinal) {
        isFinal = aFinal;
        return this;
    }

    public String getCode() {
        return code;
    }

    public TransResult setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public TransResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getResult() {
        return result;
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
