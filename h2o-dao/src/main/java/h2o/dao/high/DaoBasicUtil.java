package h2o.dao.high;

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
        return dao.update( DbUtil.sqlBuilder.buildUpdateSql3( entity , entity.getW() , entity.getPks() ) , entity );
    }

    public int edit(Object entity , String w , String... skip ) {
        return dao.update( DbUtil.sqlBuilder.buildUpdateSql3( entity , w , skip ) , entity );
    }

    public int del( AbstractEntity entity ) {
        return dao.update( "delete from " + entity.getTableName() +  " where " + entity.getW() , entity );
    }

    public <T extends AbstractEntity> T get( T entity ) {
        return (T)dao.get( entity.getClass() , "select * from " + entity.getTableName() +  " where " + entity.getW() , entity );
    }

}
