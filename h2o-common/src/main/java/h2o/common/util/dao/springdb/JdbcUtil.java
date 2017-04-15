package h2o.common.util.dao.springdb;

import h2o.common.util.collections.Args;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;


public class JdbcUtil {

	private volatile JdbcTemplate t;
	private volatile NamedParameterJdbcTemplate st;

	private volatile Args a;
	
	public void setDataSource(DataSource dataSource) {
		
		Args a0 = new Args();
		a0.a0 = dataSource;		
		a = a0;
		
		this.t  = this.createT();
		this.st = new NamedParameterJdbcTemplate(this.t);
		
	}
	
	public JdbcTemplate getT() {
		return t;
	}
	
	public NamedParameterJdbcTemplate getST() {
		return st;
	}
	
	public JdbcTemplate createT() {		
		return new JdbcTemplate((DataSource)a.a0);
	}
	
	public NamedParameterJdbcTemplate createST() {
		return new NamedParameterJdbcTemplate( this.createT() );
	} 
	



}
