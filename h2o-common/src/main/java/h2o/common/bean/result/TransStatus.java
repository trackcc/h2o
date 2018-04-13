package h2o.common.bean.result;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by zhangjianwei on 2017/6/7.
 */
public class TransStatus<S> implements Serializable {

    private static final long serialVersionUID = -8626841949944329920L;

    private S status;

    private boolean isFinal;

    private boolean success;

    private String code;

    private String msg;

    public TransStatus() {
    }

    public TransStatus( TransStatus<S> transStatus ) {
        this.setFinal( transStatus.isFinal() );
        this.setSuccess( transStatus.isSuccess() );
        this.setStatus( transStatus.getStatus() );
        this.setCode( transStatus.getCode() );
        this.setMsg( transStatus.getMsg() );
    }

    public boolean isFinal() {
        return isFinal;
    }

    public TransStatus<S> setFinal(boolean aFinal) {
        isFinal = aFinal;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public TransStatus<S> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public S getStatus() {
        return status;
    }

    public TransStatus<S> setStatus(S status) {
        this.status = status;
        return this;
    }

    public String getCode() {
        return code;
    }

    public TransStatus<S> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public TransStatus<S> setMsg(String msg) {
        this.msg = msg;
        return this;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


}
