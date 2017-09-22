package h2o.dao.colinfo;

import h2o.dao.annotation.ColumnDefValue;

public class ColInfo {

	public final String attrName;
	public final String colName;
	public final ColumnDefValue defVal;

	public final boolean pk;
	public final String[] uniqueNames;


    public ColInfo( ColInfoVar civ ) {
        this.attrName    = civ.attrName;
        this.colName     = civ.colName;
        this.defVal      = civ.defVal;
        this.pk          = civ.pk;
        this.uniqueNames = civ.uniqueNames;
    }

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