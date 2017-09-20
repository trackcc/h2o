package h2o.dao.colinfo;

import h2o.dao.annotation.ColumnDefValue;
import h2o.dao.annotation.Unique;

public class ColInfo {

	public String attrName;
	public String colName;	
	public ColumnDefValue defVal;

	public boolean pk;
	public String[] uniqueNames;


	
	@Override
	public String toString() {			
		return "ColInfo:{colName:" + colName  + ",attrName:" + attrName + ",defVal:" + defVal.getVal() + "}";
	}
	
	public Object value( boolean isNull ) {
		if( isNull && !this.defVal.isUndefined() ) {
			return this.defVal.getVal();
		}
		return ":" + this.attrName;
	}
	
	


	public String getAttrName() {
		return attrName;
	}

	public String getColName() {
		return colName;
	}

	public ColumnDefValue getDefVal() {
		return defVal;
	}

    public boolean isPk() {
        return pk;
    }

    public String[] getUniqueNames() {
        return uniqueNames;
    }

}