package h2o.common.dao.rowproc.support;

import h2o.common.dao.rowproc.RowDataProcessor;

import java.util.Map;

public class NonOpRowDataProcessor implements RowDataProcessor {

	public Map<String, Object> dataProc(Map<String, Object> m) {		
		return m;
	}

}
