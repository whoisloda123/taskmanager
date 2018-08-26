package com.liucan.taskmanager.task;

import com.liucan.taskmanager.common.basetask.BaseTask;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author liucan
 * @date 2018/8/26
 * @brief
 */
@Slf4j
public class HelloTask implements BaseTask {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("HelloJob执行了");
    }
}
