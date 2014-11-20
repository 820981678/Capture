package com.task;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.util.StringUtil;

/**
 * <pre>
 * <b>抽象基础计划任务.</b>
 * <b>Description:</b> 
 * <b>Author:</b> liuuhong@yeah.net
 * <b>Date:</b> 2014-2-21 下午02:50:19
 * <b>Copyright:</b> Copyright &copy;2006-2014 chuying Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *    <b>Ver	  Date			Author		Detail</b>
 *    1.00	  2014-2-21 下午02:50:19	liuuhong@yeah.net	new file.
 * </pre>
 */
public abstract class AbsJob {

    // 组别.
    protected String group;

    // 名称.
    protected String name;

    // 优先级别, 默认: Thread.NORM_PRIORITY.
    protected Integer priority;

    // 调度时间表达式.
    protected String cron;

    // 启动延迟时间, 单位秒, 如果提供的参数小于2分钟, 则不生效.
    protected Long delay;

    // 是否自动启动.
    protected boolean auto;

    /**
     * 默认构造方法.
     */
    public AbsJob() {
        super();
    }

    /**
     * 获取Job具体实现的Class.
     * 
     * @return Class<Job>
     */
    public abstract Class<Job> getJobClass();

    /**
     * 获取jobName.
     * 
     * @return String
     */
    public String getJobName() {
        return this.group + "." + this.name;
    }

    /**
     * 获取JobKey.
     * 
     * @return String
     */
    public String getJobKey() {
        return this.name + ".job";
    }

    /**
     * 获取TriggerKey.
     * 
     * @return String
     */
    public String getTriggerKey() {
        return this.name + ".trigger";
    }

    /**
     * 获取JobDetail.
     * 
     * @return JobDetail
     */
    public JobDetail bulidJobDetail() {
        JobBuilder jobBuilder = JobBuilder.newJob(this.getJobClass());
        jobBuilder.withIdentity(getJobKey(), this.group);

        JobDetail jobDetail = jobBuilder.build();
        return jobDetail;
    }

    /**
     * 获取 CronTrigger.
     * 
     * @return Trigger
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Trigger bulidTrigger() {
        TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger();
        triggerBuilder.withIdentity(getTriggerKey(), this.group);
        // 小于30秒的则自动忽略设置.
        if (null != delay && 30 <= delay) {
            long currTime = System.currentTimeMillis() + (this.delay * 1000);
            triggerBuilder.startAt(new Date(currTime));
        }
        triggerBuilder.withPriority(this.priority);
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(this.cron);
        triggerBuilder.withSchedule(cronScheduleBuilder);

        Trigger trigger = triggerBuilder.build();
        return trigger;
    }

    /**
     * 初始化
     * 
     * @throws TaskException
     */
    public abstract void init() throws TaskException;

    /**
     * 获取调用job实例的自身.
     * 
     * @param context
     * @return T
     */
    @SuppressWarnings("unchecked")
    public <T> T getThis(JobExecutionContext context) {
        T t = null;
        if (null != context && null != context.getJobDetail() && null != context.getJobDetail().getJobDataMap()) {
            t = (T) context.getJobDetail().getJobDataMap().get("this");
        }
        return t;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        if (priority <= 0 || priority >= 11) {
            return;
        }
        this.priority = priority;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Long getDelay() {
        return delay;
    }

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("job:").append(StringUtil.keepLen(this.group + "." + this.name, 14, " ", false)).append(" priority:")
                .append(this.priority).append("  cron:").append(StringUtil.keepLen(this.cron, 18, " ", false))
                .append(" delay:").append(StringUtil.keepLen(this.delay.toString(), 4, " ", false)).append(" auto:")
                .append(this.auto);
        return sb.toString();
    }

}
