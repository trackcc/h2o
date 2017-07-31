package h2o.dao.advanced;

import h2o.common.Tools;
import h2o.dao.colinfo.ColInfoUtil;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Created by zhangjianwei on 2017/7/1.
 */
public abstract class AbstractEntity<E extends AbstractEntity , B> {

    private final String _tableName;

    private final String _w;

    private final String[] _pks;


    public AbstractEntity(String w, String[] pks ) {
        this._tableName = ColInfoUtil.getTableName(this.getClass());
        this._w = w;
        this._pks = pks;
    }

    public AbstractEntity(String tableName, String w, String[] pks ) {
        this._tableName = tableName;
        this._w = w;
        this._pks = pks;
    }


    public E fill( Object bean ) {
        Tools.b.beanCopy( bean , this );
        return (E)this;
    }

    public B toBean( B bean ) {
        return (B) Tools.b.beanCopy( this , bean );
    }




    public String getTableName() {
        return _tableName;
    }

    public String getW() {
        return _w;
    }

    public String[] getPks() {
        return _pks;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


}
