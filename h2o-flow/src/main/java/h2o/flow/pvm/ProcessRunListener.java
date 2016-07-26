/**
 * @author 张建伟
 */
package h2o.flow.pvm;

import h2o.flow.pvm.elements.Line;
import h2o.flow.pvm.elements.Node;
import h2o.flow.pvm.runtime.RunContext;
import h2o.flow.pvm.runtime.RunStatus;

import java.util.List;

public interface ProcessRunListener {
	
	void onStart(RunContext runContext, boolean signal);
	
	void inNode(RunContext runContext, Node node);
	
	void outNode(RunContext runContext, Node node, RunStatus runStatus, List<Line> lines);
	
	void passLine(RunContext runContext, Line line);
	
	void onEnd(RunContext runContext, RunStatus runStatus);
	
	void onException(RunContext runContext, Throwable e);

}
