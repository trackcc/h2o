package h2o.common.util.dao.rowproc;

import java.util.Map;

public interface RowDataProcessor {
	
	Map<String,Object> dataProc(Map<String, Object> m);

}
