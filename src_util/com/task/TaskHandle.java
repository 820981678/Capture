package com.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import com.inter.PropertiesSource;
import com.util.LogsUtil;
import com.util.ObjectUtil;
import com.util.StringUtil;
import com.util._Util;

/**
 * <pre>
 * <b>计划调度任务管理器.</b>
 * <b>Description:</b> 主要提供的对配置的ComonJob、StatefulJob进行自动装载、暂停、关闭调度服务等工作.
 *   
 * <b>Author:</b> liuuhong@yeah.net
 * <b>Date:</b> 2014-1-1 上午10:00:01
 * <b>Copyright:</b> Copyright &copy;2006-2015 firefly.org Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *   ----------------------------------------------------------------------
 *   1.0   2014-01-01 10:00:01    liuuhong@yeah.net
 *         new file.
 * </pre>
 */
public final class TaskHandle {

    /**
     * 自动启动参数Key=task.auto.start, 主要从全局配置文件中获取.
     */
    private static final String AUTO_START_KEY = "task.auto.start";

    /**
     * Job和Tigger默认的组别: default.
     */
    public static final String DEFAULT_GROUP = _Util.DEFAULT;

    /**
     * 默认的调度时间表达式, 默认: 0 * * * * ?.
     */
    public static final String DEFAULT_CRON = "0 * * * * ?";

    /**
     * 当前待初始化的调度任务集合.
     */
    private Set<AbsJob> jobs;

    /**
     * 缓存映射当前调度器中运行的Job.
     */
    private static Map<String, AbsJob> mapping = new HashMap<String, AbsJob>(0);

    /**
     * Task 调度器.
     */
    private static Scheduler scheduler = null;

    /**
     * 多线程并发控制锁.
     */
    private static final Object _lock = new Object();

    /**
     * 日志记录器.
     */
    protected static final Log logger = LogFactory.getLog("init.tasks");
    
    /**
     * 配置文件获取接口
     */
    private PropertiesSource propertiesSource;

	/**
     * 私有构造方法, 禁止实例化.
     */
    private TaskHandle() {
        super();
    }

    /**
     * 业务初始化方法.
     */
    public void init() {
        logger.info(LogsUtil.LINE);
        logger.info(LogsUtil.PREFIX2 + "TaskHandle init ...");

        // 获取全局配置的是否自动启动调度器.
        boolean autoStart = propertiesSource.getProperties().get(AUTO_START_KEY).equals("true") ? true : false;
        logger.info(LogsUtil.PREFIX3 + LogsUtil.debugInfo("autoStart", autoStart));
        if (ObjectUtil.isEmpty(jobs)) {
            logger.warn("jobs container is empaty!!!");
            return;
        }

        // 确保scheduler 调度器初始化以及装载Job唯一有序.
        synchronized (_lock) {
            if (null == scheduler) {
                SchedulerFactory factory = new StdSchedulerFactory();
                try {
                    scheduler = factory.getScheduler();
                } catch (SchedulerException e) {
                    String message = "scheduler instance fail, " + e.getMessage();
                    logger.error(message);
                    throw new RuntimeException(message, e);
                }
            }

            // 转载配置持有的Job到调度中.
            for (AbsJob job : jobs) {
                logger.info(">>> add " + job.toString());
                try {
                    // 将提供的调度任务提交到调度中心.
                    addJob(job);
                } catch (TaskException e) {
                    logger.error(e, e);
                    continue;
                }
                // 将成功添加的job进行全局持有.
                mapping.put(job.getName(), job);
            }
            // 判断是否自动启动定时任务调度器.
            if (autoStart) {
                try {
                    startup();
                } catch (TaskException e) {
                    throw new RuntimeException(e);
                }
            } else {
                logger.warn(">>> scheduler automatic startup is disable!!!");
                try {
                    scheduler.pauseAll();
                } catch (SchedulerException e) {
                    logger.error("scheduler pause fail, " + e.getMessage(), e);
                }
            }
            logger.info("TashHandle init... is complete");
        }
    }

    /**
     * 将提供的调度任务提交到调度中心.
     * 
     * @param job 调度任务.
     * @throws TaskException
     */
    public static void addJob(AbsJob job) throws TaskException {
        JobDetail jobDetail = job.bulidJobDetail();

        Trigger trigger = job.bulidTrigger();
        try {
            // 判断任务是否已经存在调度器中.
            if (scheduler.checkExists(jobDetail.getKey())) {
                logger.warn(jobDetail.getKey() + " job is exist!");
                throw new TaskException();
            }
            // 判断当前调度器中是否已经存在系统的触发器.
            if (scheduler.checkExists(trigger.getKey())) {
                logger.warn(trigger.getKey() + " trigger is exist!");
                throw new TaskException();
            }
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
            throw new TaskException(e);
        }

        // 将调度任务在任务执行中可以持有并获取.
        jobDetail.getJobDataMap().put("this", job);

        // 将当前job 和 trigger添加到调度中心.
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            // 判断job是否自动启动
            if (!job.isAuto()) {
                scheduler.pauseTrigger(trigger.getKey());
                scheduler.pauseJob(jobDetail.getKey());
            }
        } catch (SchedulerException e) {
            logger.error(job.getName() + " job add to scheduler fail, " + e.getMessage(), e);
            throw new TaskException(e);
        }
    }

    /**
     * 启动调度任务.
     * 
     * @throws TaskException
     */
    public static void startup() throws TaskException {
        if (null == scheduler) {
            logger.warn("scheduler invalid!");
            return;
        }
        // 锁定当前 scheduler 调度器实例, 防止其他线程并发操作.
        synchronized (scheduler) {
            try {
                if (scheduler.isStarted()) {
                    logger.info("scheduler is Started!");
                    return;
                }
                // 启动 scheduler 任务调度
                scheduler.start();
            } catch (SchedulerException e) {
                logger.error("scheduler start fail, " + e.getMessage(), e);
            }
        }
    }

    /**
     * 暂停调度任务.
     * 
     * @throws TaskException
     */
    public static void pause() throws TaskException {
        if (null == scheduler) {
            logger.warn("scheduler invalid!");
            return;
        }
        // 锁定当前 scheduler 调度器实例, 防止其他线程并发操作.
        synchronized (scheduler) {
            try {
                scheduler.pauseAll();
            } catch (SchedulerException e) {
                logger.error("scheduler pause fail, " + e.getMessage(), e);
            }
        }
    }

    /**
     * 暂停调度任务.
     * 
     * @throws TaskException
     */
    public static void pause(AbsJob job) throws TaskException {
        if (null == scheduler) {
            logger.warn("scheduler invalid!");
            return;
        }
        JobKey jobKey = new JobKey(job.getJobKey(), job.getGroup());
        TriggerKey triggerKey = new TriggerKey(job.getTriggerKey(), job.getGroup());
        // 锁定当前 scheduler 调度器实例, 防止其他线程并发操作.
        synchronized (scheduler) {
            try {
                scheduler.pauseJob(jobKey);
                scheduler.pauseTrigger(triggerKey);
            } catch (SchedulerException e) {
                logger.error("scheduler pause fail, " + e.getMessage(), e);
            }
        }
    }

    /**
     * 暂停调度任务.
     * 
     * @param groups 任务组.
     * @throws TaskException
     */
    public static void pauseGroup(String... groups) throws TaskException {
        if (null == scheduler) {
            logger.warn("scheduler invalid!");
            return;
        }
        // 锁定当前 scheduler 调度器实例, 防止其他线程并发操作.
        synchronized (scheduler) {
            for (String group : groups) {
                try {
                    GroupMatcher<TriggerKey> triggerMatcher = GroupMatcher.groupStartsWith(group);
                    scheduler.pauseTriggers(triggerMatcher);
                    GroupMatcher<JobKey> jobMatcher = GroupMatcher.groupStartsWith(group);
                    scheduler.pauseJobs(jobMatcher);
                } catch (SchedulerException e) {
                    logger.error("scheduler pause fail, " + e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 开启调度任务.
     * 
     * @throws TaskException
     */
    public static void resume() throws TaskException {
        if (null == scheduler) {
            logger.warn("scheduler invalid!");
            return;
        }
        // 锁定当前 scheduler 调度器实例, 防止其他线程并发操作.
        synchronized (scheduler) {
            try {
                scheduler.resumeAll();
            } catch (SchedulerException e) {
                logger.error("scheduler resume fail, " + e.getMessage(), e);
            }
        }
    }

    /**
     * 开启调度任务.
     * 
     * @throws TaskException
     */
    public static void resume(String key) throws TaskException {
        if (StringUtil.isBlank(key) || !mapping.containsKey(key)) {
            logger.warn("job key is invalid or unexits!");
        }
        AbsJob job = mapping.get(key);
        resume(job);
    }

    /**
     * 开启调度任务.
     * 
     * @throws TaskException
     */
    public static void resume(AbsJob job) throws TaskException {
        if (null == scheduler) {
            logger.warn("scheduler invalid!");
            return;
        }
        JobKey jobKey = new JobKey(job.getJobKey(), job.getGroup());
        TriggerKey triggerKey = new TriggerKey(job.getTriggerKey(), job.getGroup());
        // 锁定当前 scheduler 调度器实例, 防止其他线程并发操作.
        synchronized (scheduler) {
            try {
                scheduler.resumeJob(jobKey);
                scheduler.resumeTrigger(triggerKey);
            } catch (SchedulerException e) {
                logger.error("scheduler pause fail, " + e.getMessage(), e);
            }
        }
    }

    /**
     * 开启调度任务.
     * 
     * @param groups 任务组.
     * @throws TaskException
     */
    public static void resumeGroup(String... groups) throws TaskException {
        if (null == scheduler) {
            logger.warn("scheduler invalid!");
            return;
        }
        // 锁定当前 scheduler 调度器实例, 防止其他线程并发操作.
        synchronized (scheduler) {
            for (String group : groups) {
                try {
                    GroupMatcher<TriggerKey> triggerMatcher = GroupMatcher.groupStartsWith(group);
                    scheduler.resumeTriggers(triggerMatcher);
                    GroupMatcher<JobKey> jobMatcher = GroupMatcher.groupStartsWith(group);
                    scheduler.resumeJobs(jobMatcher);
                } catch (SchedulerException e) {
                    logger.error("scheduler resume fail, " + e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 重写GC回收实现.
     */
    @Override
    protected void finalize() throws Throwable {
        scheduler.pauseAll();
        super.finalize();
    }

    /**
     * 返回指定的AbsJob
     * 
     * @param key
     * @return AbsJob
     */
    public AbsJob getJob(String key) {
        return mapping.get(key);
    }

    /**
     * 设置 当前待初始化的调度任务集合.
     * 
     * @param jobs 当前待初始化的调度任务集合.
     */
    public void setJobs(Set<AbsJob> jobs) {
        this.jobs = jobs;
    }
    
    public PropertiesSource getPropertiesSource() {
		return propertiesSource;
	}

	public void setPropertiesSource(PropertiesSource propertiesSource) {
		this.propertiesSource = propertiesSource;
	}

}
