package h2o.common.util.runtime.shell;

public interface RunCallback {
	
	int check(String line);
	
	void onFinish(int r);
	

}
