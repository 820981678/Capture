package com.task;

import org.quartz.Job;

/**
 * <pre>
 * <b>有状态定时调度任务.</b>
 * <b>Description:</b> <font style="color:red">主用用于限制并发执行定时任务</font>.
 * <b>Author:</b> liuuhong@yeah.net
 * <b>Date:</b> 2014-2-21 下午02:50:19
 * <b>Copyright:</b> Copyright &copy;2006-2014 chuying Co., Ltd. All rights reserved.
 * <b>Changelog:</b> 
 *    <b>Ver	  Date			Author		Detail</b>
 *    1.00	  2014-2-21 下午02:50:19	liuuhong@yeah.net	new file.
 * </pre>
 */
@SuppressWarnings("deprecation")
public abstract class StatefulJob extends AbsJob implements org.quartz.StatefulJob {

    /**
     * 默认构造方法.
     */
    public StatefulJob() {
        super();
    }

    /**
     * 获取Job具体实现的Class.
     * 
     * @return Class<Job>
     */
    @SuppressWarnings("unchecked")
    public Class<Job> getJobClass() {
        return (Class<Job>) this.getClass();
    }
}
