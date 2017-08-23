package h2o.dao.advanced;

import h2o.common.util.collections.CollectionUtil;
import h2o.common.util.lang.StringUtil;
import h2o.dao.Dao;
import h2o.dao.DbUtil;

import java.util.List;

/**
 * Created by zhangjianwei on 2017/7/1.
 */
public final class DaoBasicUtil {

    private final Dao dao;

    public DaoBasicUtil() {
        this.dao = DbUtil.getDao();
    }

    public DaoBasicUtil( Dao dao ) {
        this.dao = dao;
    }


    public void add( Object entity ) {
        dao.update( DbUtil.sqlBuilder.buildInsertSql(entity) , entity );
    }

    public void batAdd( List<Object> entities ) {
        dao.update( DbUtil.sqlBuilder.buildInsertSqlIncludeNull(entities.get(0)) , entities );
    }

    public int edit( AbstractEntity entity ) {
        return dao.update( DbUtil.sqlBuilder.buildUpdateSql3( entity , entity.get_w() , entity.get_pks() ) , entity );
    }

    public int edit( AbstractEntity entity , String w , Object... args ) {

        Object[] para;
        if( CollectionUtil.argsIsBlank( args ) ) {
            para = new Object[] { entity };
        } else {
            para = new Object[ args.length + 1 ];
            para[0] = entity;

            System.arraycopy( args , 0 , para , 1 , args.length  );
        }

        return dao.update( DbUtil.sqlBuilder.buildUpdateSql3( entity , w , entity.get_pks() ) , para );
    }

    public int del( AbstractEntity entity ) {
        return dao.update( "delete from " + entity.get_tableName() +  " where " + entity.get_w() , entity );
    }

    public <T extends AbstractEntity> T get( T entity ) {
        return this.get( entity , false );
    }

    public <T extends AbstractEntity> T getAndLock( T entity ) {
        return this.get( entity , true );
    }

    public <T extends AbstractEntity> T get( T entity , boolean lock ) {

        StringBuilder sql = new StringBuilder();

        StringUtil.append( sql , "select * from " , entity.get_tableName() ,  " where " , entity.get_w() );
        if( lock ) {
            sql.append(" for update ");
        }

        return (T)dao.get( entity.getClass() , sql.toString() , entity );
    }

}
