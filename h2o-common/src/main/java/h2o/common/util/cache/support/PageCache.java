package h2o.common.util.cache.support;

import h2o.common.bean.Page;
import h2o.common.bean.PageInfo;
import h2o.common.util.cache.DataCache;
import h2o.common.util.collections.builder.ListBuilder;

import java.util.List;

public class PageCache<T> extends DataCache<Page<T>> {
	
	
	public PageCache( Page<T> data ) {
		super(data);
	}
	
	public Page<T> getCachePage( int pageRowSize , long timeout , int times ) {		
		
		Page<T> data = this.getCache(timeout, times);		
		
		if( pageRowSize > data.getPageInfo().getPageRecordSize() ) {
			return null;
		}		
		
		if( pageRowSize == data.getPageInfo().getPageRecordSize() ) {
			return data;
		}
		
		PageInfo pageInfo = new PageInfo( 0 , pageRowSize ,data.getPageInfo().getTotalRecord() );
		List<T> records = data.getRecords();
		
		List<T> nrecords = ListBuilder.newList();
		for( int i = 0 ; i < pageRowSize ; i++ ) {
			nrecords.add( records.get(i) );
		}
		
		Page<T> page = new Page<T>();
		page.setPageInfo(pageInfo);
		page.setRecords(nrecords);
		
		return page;
		
		
	}
	
}