/**
 * @author 张建伟
 */
package h2o.flow.pvm;

public interface FlowData {
	
	void marge(FlowData otherNodeData);
	
	FlowData copy();

}
