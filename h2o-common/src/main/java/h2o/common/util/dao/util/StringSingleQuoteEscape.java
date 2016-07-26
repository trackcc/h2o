package h2o.common.util.dao.util;

import h2o.common.util.bean.PreOperate;
import h2o.common.util.bean.support.AbstractPreOperate;

@SuppressWarnings("rawtypes")
public class StringSingleQuoteEscape extends AbstractPreOperate implements PreOperate , java.io.Serializable {


	private static final long serialVersionUID = -6040684981676573807L;

	@Override
	protected String doOperateImpl(Object o) {
		
		if( o == null ) {
			return null;
		}	
		
		return o.toString().replace("'", "''");		
		
	}

}
