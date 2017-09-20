package h2o.dao.advanced;

import h2o.common.spring.util.Assert;
import h2o.common.util.collections.CollectionUtil;
import h2o.common.util.collections.builder.ListBuilder;
import h2o.common.util.collections.builder.MapBuilder;
import h2o.dao.colinfo.ColInfo;
import h2o.dao.colinfo.ColInfoUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EntityParser {

    private final Class<?> entityClazz;
    private final List<ColInfo> colInfos;

    private String tableName;

    private List<ColInfo> ids;

    private Map<String,List<ColInfo>> uniques = MapBuilder.newMap();

    public EntityParser( Class<?> entityClazz ) {
        this.entityClazz = entityClazz;
        this.colInfos = ColInfoUtil.getColInfos( entityClazz );
    }

    public String getTableName() {

        if ( tableName == null ) {
            tableName = ColInfoUtil.getTableName( entityClazz );
        }

        return tableName;
    }

    public List<ColInfo> getIds() {

        if ( ids == null ) {

            ids = ListBuilder.newList();

            for ( ColInfo ci : colInfos ) {
                if ( ci.pk ) {
                    ids.add(ci);
                }
            }
        }

        return ids;

    }

    public List<ColInfo> getUnique( String uniqueName ) {

        List<ColInfo> u = uniques.get( uniqueName );

        if ( u == null ) {

            u = ListBuilder.newList();

            for ( ColInfo ci : colInfos ) {
                if ( ci.uniqueNames != null && Arrays.asList(ci.uniqueNames).contains( uniqueName ) ) {
                    u.add(ci);
                }
            }

            uniques.put( uniqueName , u );

        }

        return u;

    }

    public List<ColInfo> getAttrs( String... attrNames ) {

        Assert.isTrue( !CollectionUtil.argsIsBlank(attrNames) );

        List<ColInfo> cis = ListBuilder.newList();
        List<String> ans = Arrays.asList( attrNames );

        for ( ColInfo ci : colInfos ) {
            if ( ans.contains( ci.attrName ) ) {
                cis.add(ci);
            }
        }

        return cis;

    }


}
