package h2o.dao;

import h2o.common.util.ioc.ButterflyFactory;
import h2o.common.util.ioc.Factory;

/**
 * Created by zhangjianwei on 2016/11/14.
 */
public final class DbConfigProvider {

    public static Factory getDbConfig() {
        return new ButterflyFactory( "db" , "db.bcs").get("dbConfig");
    }

}
