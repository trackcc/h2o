package h2o.utils.reliable;

/**
 * Created by zhangjianwei on 2016/12/27.
 */
public interface ReliableExecutor {

    /**
     * @return 是否执行成功, 不成功会再次尝试
     */
    boolean exec(int i) throws Exception;

    /**
     * @return 是否再次尝试
     */
    boolean exceptionHandler(int i, Exception e);

}
