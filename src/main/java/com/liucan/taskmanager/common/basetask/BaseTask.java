package com.liucan.taskmanager.common.basetask;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author liucan
 * @date 2018/8/21
 * @brief 任务基类
 */
public interface BaseTask extends Job {
    @Override
    void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException;
}
