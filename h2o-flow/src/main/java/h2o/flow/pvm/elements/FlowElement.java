/**
 * @author 张建伟
 */
package h2o.flow.pvm.elements;

import java.util.Map;

public interface FlowElement {
	
	Object getId();
	
	String getName();
	
	Object getProperty(String name);
	
	Map<String,Object> getProperties();

}
