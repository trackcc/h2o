package h2o.common.util.job;

import h2o.common.Tools;
import h2o.common.redis.JedisCallBack;
import h2o.common.redis.JedisUtil;
import h2o.common.spring.util.Assert;
import h2o.common.util.lang.StringUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import redis.clients.jedis.Jedis;

/**
 * Created by zhangjianwei on 2017/6/18.
 */
public abstract class AbstractOneTimeJob extends AbstractExecuteOneTime<JobExecutionContext> implements Job {

    @Override
    protected void init( JobExecutionContext context ) {

            JobKey jobKey = context.getJobDetail().getKey();
            jobId = StringUtil.build("H2oOneTimeJob_" ,
                    jobKey.getGroup() , '_' , jobKey.getName() );


    }

}
