package h2o.common.bean.result;


public class ResultBool implements java.io.Serializable {

    private static final long serialVersionUID = 2317776198697479575L;

    public final boolean success;

    public final String msg;

    public ResultBool( boolean success , String msg ) {
        this.success = success;
        this.msg = msg;
    }


    public static ResultBool success() {
        return new ResultBool(true,null);
    }

    public static ResultBool success( String msg ) {
        return new ResultBool(true,msg);
    }

    public static ResultBool error( String msg ) {
        return new ResultBool(false,msg);
    }


    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }
}
