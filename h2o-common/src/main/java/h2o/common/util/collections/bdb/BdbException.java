package h2o.common.util.collections.bdb;

public class BdbException extends RuntimeException {

	private static final long serialVersionUID = 3828142797370201725L;

	public BdbException() {
		super();
	}

	public BdbException(String message, Throwable cause) {
		super(message, cause);
	}

	public BdbException(String message) {
		super(message);
	}

	public BdbException(Throwable cause) {
		super(cause);
	}

}
