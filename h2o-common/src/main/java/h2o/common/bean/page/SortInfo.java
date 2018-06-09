package h2o.common.bean.page;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SortInfo implements java.io.Serializable {

    public enum Direction {
        ASC, DESC
    }


    private final String name;

    private final Direction direction;

    public SortInfo(String name) {
        this.name = name;
        this.direction = Direction.ASC;
    }

    public SortInfo(String name, Direction direction) {
        this.name = name;
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
