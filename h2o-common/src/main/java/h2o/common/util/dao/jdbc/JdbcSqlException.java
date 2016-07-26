package h2o.common.util.dao.jdbc;

import h2o.common.exception.UncheckedException;

public class JdbcSqlException extends UncheckedException {

	private static final long serialVersionUID = 8662674639890266355L;

	public JdbcSqlException() {
		super();

	}

	public JdbcSqlException(String message, Throwable t, boolean showCauseDetails) {
		super(message, t, showCauseDetails);

	}

	public JdbcSqlException(String message, Throwable t) {
		super(message, t);

	}

	public JdbcSqlException(String message) {
		super(message);

	}

	public JdbcSqlException(Throwable t, boolean showCauseDetails) {
		super(t, showCauseDetails);

	}

	public JdbcSqlException(Throwable t) {
		super(t);

	}

}
