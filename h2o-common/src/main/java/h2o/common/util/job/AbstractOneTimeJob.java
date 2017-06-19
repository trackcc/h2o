package h2o.common.util.job;

import h2o.common.Tools;
import h2o.common.redis.JedisCallBack;
import h2o.common.redis.JedisUtil;
import h2o.common.spring.util.Assert;
import h2o.common.util.date.DateUtil;
import h2o.common.util.lang.StringUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import redis.clients.jedis.Jedis;

/**
 * Created by zhangjianwei on 2017/6/18.
 */
public abstract class AbstractOneTimeJob implements Job {

    protected JedisUtil jedisUtil;

    protected String jobId;

    protected int timeout = 2 * 60;



    @Override
    public final void execute( final JobExecutionContext context) throws JobExecutionException {

        try {

            this.init( context );

            Assert.notNull(jedisUtil , "JedisUtil must not be null");

            if( jobId == null ) {

                JobKey jobKey = context.getJobDetail().getKey();
                jobId = StringUtil.build("H2oOneTimeJob_" ,
                        jobKey.getGroup() , '_' , jobKey.getName() );

            }

            Boolean jr =  jedisUtil.callback( new JedisCallBack<Boolean>() {

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
            Tools.log.error(e);
            this.execOneTime( context , null , e );
        }

    }



    protected abstract void init( JobExecutionContext context );

    protected void execOneTime( JobExecutionContext context , Boolean jr , Exception e ) throws JobExecutionException {

        if ( jr != null && jr ) {

            Tools.log.debug( "执行任务:{}" , context.getJobDetail().getKey() );

            this.execOneTime( context );

        } else {

            Tools.log.debug( "跳过任务:{}" , context.getJobDetail().getKey() );

        }
    }

    protected abstract void execOneTime( JobExecutionContext context ) throws JobExecutionException;

}
