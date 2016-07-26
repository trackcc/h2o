package h2o.common.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class PageInfo implements Pageable, Serializable {

	private static final long serialVersionUID = -9096938113245080992L;

	private long totalRecord;
	private long totalPage;

	private long pageNo;
	private long pageRecordSize;

	public PageInfo() {}

	public PageInfo(long pageNo, long pageRecordSize) {
		this.pageNo = pageNo;
		this.pageRecordSize = pageRecordSize;
	}

	public PageInfo(long pageNo, long pageRecordSize, long totalRecord) {
		this(pageNo, pageRecordSize);
		this.totalRecord = totalRecord;

		this.totalPage = (this.totalRecord + this.pageRecordSize - 1) / this.pageRecordSize;
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public long getPageNo() {
		return pageNo;
	}

	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}

	public long getPageRecordSize() {
		return pageRecordSize;
	}

	public void setPageRecordSize(long pageRecordSize) {
		this.pageRecordSize = pageRecordSize;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
