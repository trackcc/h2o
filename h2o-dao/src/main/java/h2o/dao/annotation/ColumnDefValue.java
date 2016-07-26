package h2o.dao.annotation;

public class ColumnDefValue {
	
	public static final String UNDEFINED = "__UNDEFINED__";	
	
	private final String val;
	private final boolean undefined;
	
	
	
	public ColumnDefValue( String defVal ) {
		this.val = defVal;
		this.undefined = UNDEFINED.equals(defVal);
	}

	public String getVal() {
		return val;
	}

	public boolean isUndefined() {
		return this.undefined;
	}
	

}
