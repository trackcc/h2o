package h2o.utils.key;

/**
 * Created by zhangjianwei on 2017/6/23.
 */
public class DefaultCyclicSpaceSetter implements CyclicSpaceSetter {

    protected final String newCyclicSpace;

    public DefaultCyclicSpaceSetter() {
        this.newCyclicSpace = KeyGen.DEFAULT_CYCLICSPACE;
    }

    public DefaultCyclicSpaceSetter(String newCyclicSpace) {
        this.newCyclicSpace = newCyclicSpace;
    }

    @Override
    public String getNewCyclicSpace( String oldCyclicSpace , String currValue ) {
        return this.newCyclicSpace;
    }
}
