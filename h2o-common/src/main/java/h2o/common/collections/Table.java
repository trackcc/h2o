package h2o.common.collections;

import h2o.common.collections.builder.ListBuilder;
import h2o.common.collections.builder.MapBuilder;
import org.apache.commons.lang.Validate;

import java.util.List;
import java.util.Map;

public class Table implements java.io.Serializable {
	

	private static final long serialVersionUID = -4289717234754477350L;
	
	
	private String tableName;
	
	private String pageNo;
	
	private Map<String,Object> meta = MapBuilder.newMap();	
	
	private Map<String,Object> exData = MapBuilder.newMap();
	
	
	private int startIndexNo;
	
	private int maxColSize;
	
	
	private List<String> colTitles = ListBuilder.newList();
	private List<String> rowTitles = ListBuilder.newList();	
	
	
	private List<List<Object>> data = ListBuilder.newList();	
	
	public Table() {}
	
	public Table( String...colTitles ) {
		this.addColTitles(colTitles);
	}
	
	
	public void addRowTitles( String... rowTitles ) {
		for( String rowTitle : rowTitles ) {
			this.rowTitles.add( rowTitle );
		}
	}
	
	public void addColTitles( String... rowTitles ) {
		for( String rowTitle : rowTitles ) {
			this.colTitles.add( rowTitle );
			this.maxColSize++;
		}
	}
	
	public int rowSize() {
		return this.data.size();
	}
	
	public int colSize() {
		return this.maxColSize;
	}
	
	

	
	private void addRow0(int ri , List<Object> rd) {
		
		if( rd.size() > this.maxColSize ) {
			this.maxColSize = rd.size();
		}
		
		if( this.data.size() <= ri  ) {
			for( int c = this.data.size() ; c < ri   ; c++ ) {
				this.data.add(ListBuilder.newList());					
			}
			this.data.add(rd);
		} else {
			this.data.set( ri , rd);
		}
				
	}
	
	public void addRow(List<Object> rd) {
		if( rd.size()  > this.maxColSize ) {
			this.maxColSize = rd.size();
		}
		this.data.add(rd);		
	}
	
	public void addRow(Object... rd) {
		this.addRow( ListBuilder.start().add(rd).get() );
	}
	
	
	private void addCol0(int ci, List<Object> cd ) {		
		for( int ri = 0 , s = cd.size() ; ri < s ; ri ++ ) {
			this.setCell0(ri, ci, cd.get(ri));
		}
		
	}
	
	
	private void setCell0( int ri , int ci , Object val ) {
		if( ci + 1 > this.maxColSize ) {
			this.maxColSize = ci + 1;
		}
		if( this.data.size() <= ri ) {
			this.addRow0( ri , ListBuilder.newList() );
		}
		List<Object> row = this.data.get(ri);
		if( row == null ) {
			row = ListBuilder.newList();
			this.data.set( ri , row );
		}
		if( row.size() <= ci ) {
			for( int c = row.size() ; c < ci ; c++ ) {
				row.add(null);					
			}
			row.add( val );
		} else {
			row.set( ci , val );
		}
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getCell0( int ri , int ci ) {
		if( this.data.size() <= ri ) {
			return null;
		} 
		List<Object> row = this.data.get(ri);
		if( row == null ) {
			return null;
		}
		if( row.size() <= ci ) {
			return null;
		} else {
			return (T)row.get( ci );
		}
	}
	
	
	
	private int tranIndex( int i ) {
		return i - this.startIndexNo;
	}
	
	public void addRow(int ri , List<Object> rd) {
		
		ri = this.tranIndex( ri );
		
		this.addRow0(ri, rd);
		
	}
	
	public void addRow(int ri , Object... rd) {		
		this.addRow( ri, ListBuilder.start().add(rd).get() );
	}
	
	
	public void addCol(int ci, List<Object> cd ) {
		
		ci = this.tranIndex( ci );
		
		this.addCol0(ci, cd);		
		
	}
	
	public void addCol(int ci, Object... cd ) {		
		this.addCol( ci , ListBuilder.start().add(cd).get() );		
	}
	
	
	public void setCell( int ri , int ci , Object val ) {
		
		ri = this.tranIndex( ri );
		ci = this.tranIndex( ci );
		
		this.setCell0( ri, ci , val );
		
	}
	

	public <T> T getCell( int ri , int ci ) {
		
		ri = this.tranIndex( ri );
		ci = this.tranIndex( ci );
		
		return this.getCell0(ri, ci);
		
	}	
	
	
	public Table transpose() {
		
		Table tt = new Table();
		
		tt.tableName = this.tableName;
		tt.pageNo = this.pageNo;
		tt.exData = this.exData;
		
		tt.colTitles = this.rowTitles;
		tt.rowTitles = this.colTitles;
		
		
		
		tt.startIndexNo = this.startIndexNo;
		
		tt.maxColSize = this.rowSize();
		
		for( int i = this.rowSize() - 1 ; i >= 0 ; i-- ) {
			for( int j = this.colSize() - 1 ; j >= 0 ; j-- ) {
				tt.setCell0( j , i , this.getCell0( i , j ));
			}
		}
		
		
		return tt;
		
	}
	
	
	
	

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}


	
	public List<String> getColTitles() {
		return colTitles;
	}

	public void setColTitles(List<String> colTitles) {
		this.colTitles = colTitles;
	}
	
	public void setColTitles(String... colTitles) {
		this.colTitles = new ListBuilder<String>().add(colTitles).get();
	}

	public List<String> getRowTitles() {
		return rowTitles;
	}

	public void setRowTitles(List<String> rowTitles) {
		this.rowTitles = rowTitles;
	}
	
	public void setRowTitles(String... rowTitles) {
		this.rowTitles = new ListBuilder<String>().add(rowTitles).get();
	}

	public void setStartIndexNo(int startIndexNo) {
		Validate.isTrue(startIndexNo >= 0, " startIndexNo must be >= 0 ");
		this.startIndexNo = startIndexNo;
	}
	
	
	

	
	
	
	public Map<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}
	
	public Map<String, Object> getExData() {
		return exData;
	}

	public void setExData(Map<String, Object> exData) {
		this.exData = exData;
	}

	public List<List<Object>> getData() {
		return data;
	}

	public void setData(List<List<Object>> data) {
		this.data = data;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((meta == null) ? 0 : meta.hashCode());
		result = prime * result + ((pageNo == null) ? 0 : pageNo.hashCode());
		result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (meta == null) {
			if (other.meta != null)
				return false;
		} else if (!meta.equals(other.meta))
			return false;
		if (pageNo == null) {
			if (other.pageNo != null)
				return false;
		} else if (!pageNo.equals(other.pageNo))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Table [tableName=" + tableName + ", pageNo=" + pageNo + ", meta=" + meta + ", exData=" + exData + ", startIndexNo=" + startIndexNo + ", maxColSize=" + maxColSize + ", colTitles=" + colTitles + ", rowTitles=" + rowTitles + ", data=" + data + "]";
	}

	

}
