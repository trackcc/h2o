package h2o.common.thirdparty.quartz;

import h2o.common.util.lang.StringUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;

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
