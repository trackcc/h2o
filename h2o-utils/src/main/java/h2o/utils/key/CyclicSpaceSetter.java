package h2o.utils.key;

/**
 * Created by zhangjianwei on 2017/6/23.
 */
public interface CyclicSpaceSetter {

    String getNewCyclicSpace(String oldCyclicSpace, String currValue);

}
