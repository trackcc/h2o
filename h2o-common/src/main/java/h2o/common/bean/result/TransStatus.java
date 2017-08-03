package h2o.common.bean.result;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by zhangjianwei on 2017/6/7.
 */
public class TransStatus implements Serializable {

    private static final long serialVersionUID = -8626841949944329920L;

    private int status = -1;

    private boolean isFinal;

    private String code;

    private String msg;


    public int getStatus() {
        return status;
    }

    public TransStatus setStatus(int status) {
        this.status = status;
        return this;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public TransStatus setFinal(boolean aFinal) {
        isFinal = aFinal;
        return this;
    }

    public String getCode() {
        return code;
    }

    public TransStatus setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public TransStatus setMsg(String msg) {
        this.msg = msg;
        return this;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


}
