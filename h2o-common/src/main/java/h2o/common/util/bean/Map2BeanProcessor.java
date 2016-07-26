package h2o.common.util.bean;

import java.util.Map;

public interface Map2BeanProcessor {

	<T> T map2bean(Map<?, ?> m, T bean);

}
