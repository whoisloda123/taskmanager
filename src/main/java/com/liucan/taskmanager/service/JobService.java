package com.liucan.taskmanager.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liucan.taskmanager.common.basetask.BaseTask;
import com.liucan.taskmanager.entity.JobAndTrigger;
import com.liucan.taskmanager.mybatis.quartz.dao.JobAndTriggerMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liucan
 * @date 2018/8/21
 * @brief quartz常用的定时器有
 *        1.SimpleTrigger：用来触发只需执行一次或者在给定时间触发并且重复N次且每次执行延迟一定时间的任务。
 *        2.CronTrigger：按照日历触发，例如“每个周五”，每个月10日中午或者10：15分。
 */
@Service
@Slf4j
public class JobService {
    private final Scheduler scheduler;
    private final JobAndTriggerMapper jobAndTriggerMapper;

    public JobService(Scheduler scheduler,
                      JobAndTriggerMapper jobAndTriggerMapper) {
        this.scheduler = scheduler;
        this.jobAndTriggerMapper = jobAndTriggerMapper;
    }

    public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<JobAndTrigger> list = jobAndTriggerMapper.getJobAndTriggerDetails();
        PageInfo<JobAndTrigger> page = new PageInfo<>(list);
        return page;
    }

    private static BaseTask getClass(String className) throws Exception {
        Class<?> class1 = Class.forName(className);
        return (BaseTask) class1.newInstance();
    }

    public void addJob(String className, String groupName, String cron, String description) {
        try {
            //启动调度器
            scheduler.start();

            //构建job信息
            BaseTask job = getClass(className);
            JobDetail jobDetail = JobBuilder.newJob(job.getClass())
                    .withDescription(description)
                    .withIdentity(className, groupName)
                    .build();

            //可加入自己的信息
            jobDetail.getJobDataMap().put("mykey", "myvalue");

            //表达式调度构建器(执行任务的时间)
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(className, groupName)
                    .withSchedule(cronScheduleBuilder)
                    .build();

            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (Exception e) {
            log.error("[任务管理]创建定时任务失败,className:{},groupName:{},cron:{}", className, groupName, cron, e);
        }
    }

    public void pauseJob(String className, String groupName) {
        try {
            scheduler.pauseJob(JobKey.jobKey(className, groupName));
        } catch (Exception e) {
            log.error("[任务管理]暂停定时任务失败,className:{},groupName:{}", className, groupName, e);
        }
    }

    public void resumeJob(String className, String groupName) {
        try {
            scheduler.resumeJob(JobKey.jobKey(className, groupName));
        } catch (Exception e) {
            log.error("[任务管理]恢复定时任务失败,className:{},groupName:{}", className, groupName, e);
        }
    }

    /**
     * 按照新的cron从新执行
     */
    public void rescheduleJob(String className, String groupName, String cron) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(className, groupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    .build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            log.error("[任务管理]从新定时任务失败,className:{},groupName:{}，cron：{}", className, groupName, cron, e);
        }
    }

    public void deleteJob(String className, String groupName) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(className, groupName);
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(JobKey.jobKey(className, groupName));
        } catch (Exception e) {
            log.error("[任务管理]删除定时任务失败,className:{},groupName:{}", className, groupName, e);
        }

    }
}
