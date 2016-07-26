package h2o.common.util.runtime.shell;

import h2o.common.util.collections.tuple.Tuple2;

import java.util.concurrent.Callable;

public class ShellProcess implements Callable<Tuple2<Boolean, String[]>> {

	private final String sh;
	private final RunCallback rc;

	public ShellProcess(String sh, RunCallback rc) {
		this.sh = sh;
		this.rc = rc;
	}

//	@Override
	public Tuple2<Boolean, String[]> call() throws Exception {		
		return Shell.call(sh, rc);
	}

}
