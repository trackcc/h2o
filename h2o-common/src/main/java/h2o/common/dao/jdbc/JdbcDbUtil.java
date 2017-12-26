package h2o.common.dao.jdbc;

import h2o.common.exception.ExceptionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcDbUtil {

	public static <T> T execCallBack(Connection conn, JdbcCallback<T> jcb) {

		try {
			conn.setAutoCommit(false);

			T r = jcb.doCallBack(conn);

			conn.commit();
			
			return r;
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new JdbcSqlException("conn.rollback()", e1);
			}
			
			
			if( e instanceof SQLException  ) {
				throw new JdbcSqlException(e);
			} else {
				throw ExceptionUtil.toRuntimeException(e);
			}
			
			
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e1) {
				throw new JdbcSqlException("conn.close()", e1);
			}
		}
	}
	
	
	
	
	public static int[] execute( Connection conn , String... sqls ) {
		
		Statement st = null;
		int[] r = new int[sqls.length];
		
		try {
			st = conn.createStatement();
			for( int i = 0 ; i < sqls.length ; i++) {
				r[i] = st.executeUpdate(sqls[i]);
			}
			
			return r;
			
		} catch (SQLException e) {				
			e.printStackTrace();
			throw new RuntimeException(e);		
		} finally {
			
			if( st != null ) {
				try {
					st.close();
				}catch (Exception e1) {
					throw new JdbcSqlException("st.close()", e1);
				}
			}
		}		
	}
	
	

	
	
	public static <T> T query( Connection conn , String sql , JdbcRsCallback<T> jrcb)  {
		Statement st = null;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);			
			T t = jrcb.doCallBack(rs);			
			rs.close();			
			return t;			
		} catch (Exception e) {				
			e.printStackTrace();
			throw new RuntimeException(e);		
		} finally {
			if( st != null ) {
				try {
					st.close();
				}catch (Exception e1) {
					throw new JdbcSqlException("st.close()", e1);
				}
			}
		}		
	}
	
	
	
	

}
