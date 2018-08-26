package com.liucan.taskmanager.job;

import com.liucan.taskmanager.common.BaseJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author liucan
 * @date 2018/8/26
 * @brief
 */
@Slf4j
public class HelloJob implements BaseJob {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("HelloJob执行了");
    }
}
