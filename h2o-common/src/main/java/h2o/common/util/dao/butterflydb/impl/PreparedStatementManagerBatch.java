package h2o.common.util.dao.butterflydb.impl;

import static h2o.common.util.dao.util.SqlParameterUtil.toPreparedSqlAndPara;
import h2o.common.Tools;
import h2o.common.util.collections.tuple.Tuple2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import com.jenkov.db.itf.IPreparedStatementManager;
import com.jenkov.db.itf.PersistenceException;
import com.jenkov.db.util.JdbcUtil;

public class PreparedStatementManagerBatch implements IPreparedStatementManager {
	
	
	private final Collection<?> batch_parameters;
	
	private int[] updateRows;
	
	private String sql;	
	
	
	
	
	public Collection<?> getBatch_parameters() {
		return batch_parameters;
	}

	public int[] getUpdateRows() {
		return updateRows;
	}

	public String getSql() {
		return sql;
	}
	
	

	public PreparedStatementManagerBatch( Collection<?> batch_parameters ) {
		this.batch_parameters = batch_parameters;
	}
	
	public PreparedStatementManagerBatch( Object[][] batch_parameters ) {
		this.batch_parameters = Arrays.asList(batch_parameters);
	}
	
	

	public PreparedStatement prepare(String sql, Connection connection) throws SQLException, PersistenceException {
		
		this.sql = sql;
		
		if( batch_parameters != null && !batch_parameters.isEmpty() ) {
			
			Object parameters = batch_parameters.iterator().next();			
			if( parameters instanceof Map ) {
				
				@SuppressWarnings("unchecked")
				Tuple2<String,Object[]> sqlAndPara = toPreparedSqlAndPara(sql, (Map<String,Object>)parameters );				
				
				sql = sqlAndPara.e0;
				
			}
		}
		
		Tools.log.info("updateBatch--sql:{}" , sql );
		
		return connection.prepareStatement(sql);
		
	}
	

	@SuppressWarnings("rawtypes")
	public void init(PreparedStatement paramPreparedStatement) throws SQLException, PersistenceException {
		
		for( Object parameters : this.batch_parameters ) {
			
			Object[] para;
			if( parameters instanceof Map ) {				
				Tuple2<String,Object[]> sqlAndPara = toPreparedSqlAndPara(sql, (Map)parameters );
				para = sqlAndPara.e1;				
			} else if( parameters instanceof Collection ) {				
				para = ((Collection)parameters).toArray();				
			} else {				
				para = (Object[]) parameters;				
			}
			
			Tools.log.debug("updateBatch--para:{}" ,  Arrays.asList(para) );
			
			JdbcUtil.insertParameters(paramPreparedStatement, para);
			paramPreparedStatement.addBatch();
		}
		
	}

	public Object execute(PreparedStatement paramPreparedStatement) throws SQLException, PersistenceException {
		
		
		Tools.log.debug("executeBatch..." );
		
		this.updateRows = paramPreparedStatement.executeBatch();		
		paramPreparedStatement.clearBatch();
		
		return this.updateRows.length;
	}



	public void postProcess(PreparedStatement paramPreparedStatement) throws SQLException, PersistenceException {		
		
	}



}
