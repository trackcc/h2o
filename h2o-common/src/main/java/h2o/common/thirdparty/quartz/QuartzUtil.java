package h2o.common.thirdparty.quartz;

import h2o.common.exception.ExceptionUtil;
import h2o.common.thirdparty.spring.util.Assert;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * Created by zhangjianwei on 2017/2/4.
 */
public class QuartzUtil {

    private static final Logger log = LoggerFactory.getLogger( QuartzUtil.class.getName() );

    private final Scheduler sched;

    public QuartzUtil( Scheduler sched ) {
        this.sched = sched;
    }


    public QuartzUtil() {
        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            this.sched = sf.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.debug("",e);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }

    public QuartzUtil( String schedName ) {

        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            this.sched = sf.getScheduler(schedName);
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.debug( "" , e);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }

    public QuartzUtil( SchedulerFactory sf ) {

        try {
            this.sched = sf.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.debug( "" , e);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }

    public QuartzUtil( SchedulerFactory sf , String schedName ) {

        try {
            this.sched = sf.getScheduler(schedName);
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.debug( "" , e);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }


    public Scheduler getSched() {
        return sched;
    }


    public Date scheduleJob(JobKey jobKey , Class<? extends Job> jobClass , String triggerName , String cron ) {
        return this.scheduleJob( jobKey , jobClass , triggerName , cron , null );
    }


    public Date scheduleJob(JobKey jobKey , Class<? extends Job> jobClass , String triggerName , String cron , Map<?,?> args ) {

        Assert.notNull( sched , "Scheduler is null" );
        
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, jobKey.getGroup() )
                .withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();

        return scheduleJob( jobKey , jobClass , trigger , args );

    }


    public Date scheduleJob( JobKey jobKey , Class<? extends Job> jobClass , Trigger trigger  ) {
        return this.scheduleJob( jobKey , jobClass , trigger , null );
    }


    public Date scheduleJob( JobKey jobKey , Class<? extends Job> jobClass , Trigger trigger , Map<?,?> args ) {
        
        Assert.notNull( sched , "Scheduler is null" );

        JobBuilder jobBuilder = JobBuilder.newJob(jobClass).withIdentity(jobKey);
        if( args != null ) {
            jobBuilder.setJobData( new JobDataMap(args) );
        }

        JobDetail job = jobBuilder.build();

        Date ft = null;
        try {
            ft = sched.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.debug("" , e);
            throw ExceptionUtil.toRuntimeException(e);
        }

        log.info("{} will run at: {}" , jobKey , ft );

        return ft;
    }

    public boolean deleteJob(JobKey jobKey)  {
        try {
            return sched.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.debug("" , e);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }

    public void pauseJob(JobKey jobKey) {
        try {
            sched.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.debug("" , e);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }

    public void resumeJob(JobKey jobKey) {
        try {
            sched.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.debug("" , e);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }

    public void pauseAll() {
        try {
            sched.pauseAll();
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.debug("" , e);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }

    public void resumeAll() {
        try {
            sched.resumeAll();
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.debug("" , e);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }

    public void clear() {
        try {
            sched.clear();
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.debug("" , e);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }


    public void start() {
        try {
            sched.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.debug("" , e);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }

    public void startDelayed(int seconds) {
        try {
            sched.startDelayed(seconds);
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.debug("" , e);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }

    public boolean isStarted() {
        try {
            return sched.isStarted();
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.debug("" , e);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }

    public void shutdown() {
        try {
            sched.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.debug("" , e);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }

    public void shutdown(boolean waitForJobsToComplete) {
        try {
            sched.shutdown(waitForJobsToComplete);
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.debug("" , e);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }

    public boolean isShutdown() {
        try {
            return sched.isShutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.debug("" , e);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }
}