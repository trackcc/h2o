package h2o.common.bean.page;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class ResultInfo implements Serializable {

	private static final long serialVersionUID = 1607703985782895368L;

	private long firstResult;
	private long maxResult;

    private List<SortInfo> sorts;

	public ResultInfo() {}
	
	public ResultInfo(int maxResult) {
		this.setMaxResult(maxResult);
		this.setFirstResult(0);
	}

	public ResultInfo(long firstResult, long maxResult) {
		this.setMaxResult(maxResult);
		this.setFirstResult(firstResult);
	}

	public ResultInfo(Pageable pageInfo) {
		this.setMaxResult(pageInfo.getPageRecordSize());
		this.setFirstResult((pageInfo.getPageNo() - 1) * pageInfo.getPageRecordSize());
	}

    public ResultInfo( PageRequest pageRequest ) {
        this.setMaxResult(pageRequest.getPageRecordSize());
        this.setFirstResult((pageRequest.getPageNo() - 1) * pageRequest.getPageRecordSize());
        this.sorts = pageRequest.getSorts();
    }

    public long getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(long maxResult) {
		this.maxResult = maxResult;
	}

	public long getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(long firstResult) {
		this.firstResult = firstResult;
	}

    public List<SortInfo> getSorts() {
        return sorts;
    }

    public void setSorts(List<SortInfo> sorts) {
        this.sorts = sorts;
    }

    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
