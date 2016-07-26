package h2o.common.web.action;

public class FileField implements java.io.Serializable {
	

	private static final long serialVersionUID = -2298200286238300936L;

	private String name;
	
	private String url;
	
	private String realPath;
	
	private long size;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "FileField [name=" + name + ", url=" + url + ", realPath=" + realPath + ", size=" + size + "]";
	}
	

}
