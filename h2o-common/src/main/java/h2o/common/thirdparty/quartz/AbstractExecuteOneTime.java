package h2o.common.thirdparty.quartz;

import h2o.common.thirdparty.redis.JedisCallBack;
import h2o.common.thirdparty.redis.JedisProvider;
import h2o.common.thirdparty.spring.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Created by zhangjianwei on 2017/6/18.
 */
public abstract class AbstractExecuteOneTime<C> {

    private static final Logger log = LoggerFactory.getLogger( AbstractExecuteOneTime.class.getName() );

    protected JedisProvider jedisProvider;

    protected String jobId;

    protected int timeout = 2 * 60;

    public void execute( C context ) {

        try {

            this.init( context );

            Assert.notNull(jedisProvider, "jedisProvider must not be null");


            Boolean jr =  jedisProvider.callback(new JedisCallBack<Boolean>() {

                @Override
                public Boolean doCallBack(Jedis jedis) throws Exception {

                    if ( jedis.setnx( jobId , "1" ) == 1 ) {
                        jedis.expire( jobId , timeout );
                        return true;
                    }

                    return false;
                }

            } );

            this.execOneTime( context , jr , null );

        } catch ( Exception e ) {

            e.printStackTrace();
            log.error("",e);

            this.execOneTime( context , null , e );

        }

    }

    protected abstract void init( C context );

    protected void execOneTime( C context , Boolean jr , Exception e ) {

        if ( jr != null && jr ) {

            log.debug( "执行任务:{}" , jobId );

            this.execOneTime( context );

        } else {

            log.debug( "跳过任务:{}" ,jobId );

        }
    }

    protected abstract void execOneTime( C context );

}
