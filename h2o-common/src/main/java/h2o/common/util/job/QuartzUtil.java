package h2o.common.util.job;

import h2o.common.Tools;
import h2o.common.concurrent.OneTimeInitVar;
import h2o.common.exception.ExceptionUtil;
import h2o.common.spring.util.Assert;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.Map;

/**
 * Created by zhangjianwei on 2017/2/4.
 */
public class QuartzUtil {

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
            Tools.log.debug(e);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }

    public QuartzUtil( String schedName ) {

        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            this.sched = sf.getScheduler(schedName);
        } catch (SchedulerException e) {
            e.printStackTrace();
            Tools.log.debug(e);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }

    public QuartzUtil( SchedulerFactory sf ) {

        try {
            this.sched = sf.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
            Tools.log.debug(e);
            throw ExceptionUtil.toRuntimeException(e);
        }
    }

    public QuartzUtil( SchedulerFactory sf , String schedName ) {

        try {
            this.sched = sf.getScheduler(schedName);
        } catch (SchedulerException e) {
            e.printStackTrace();
            Tools.log.debug(e);
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
        
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, jobKey.getGroup() ).withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();

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
            Tools.log.debug(e);
            throw ExceptionUtil.toRuntimeException(e);
        }

        Tools.log.info("{} will run at: {}" , jobKey , ft );

        return ft;
    }

    public boolean deleteJob(JobKey jobKey) throws SchedulerException {
        return sched.deleteJob(jobKey);
    }

    public void pauseJob(JobKey jobKey) throws SchedulerException {
        sched.pauseJob(jobKey);
    }

    public void resumeJob(JobKey jobKey) throws SchedulerException {
        sched.resumeJob(jobKey);
    }

    public void pauseAll() throws SchedulerException {
        sched.pauseAll();
    }

    public void resumeAll() throws SchedulerException {
        sched.resumeAll();
    }

    public void clear() throws SchedulerException {
        sched.clear();
    }


    public void start() throws SchedulerException {
        sched.start();
    }

    public void startDelayed(int seconds) throws SchedulerException {
        sched.startDelayed(seconds);
    }

    public boolean isStarted() throws SchedulerException {
        return sched.isStarted();
    }

    public void shutdown() throws SchedulerException {
        sched.shutdown();
    }

    public void shutdown(boolean waitForJobsToComplete) throws SchedulerException {
        sched.shutdown(waitForJobsToComplete);
    }

    public boolean isShutdown() throws SchedulerException {
        return sched.isShutdown();
    }
}