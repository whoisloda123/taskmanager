package com.liucan.taskmanager.controller;

import com.github.pagehelper.PageInfo;
import com.liucan.taskmanager.entity.JobAndTrigger;
import com.liucan.taskmanager.service.IJobAndTriggerService;
import com.liucan.taskmanager.service.JobService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liucan
 * @date 2018/8/21
 * @brief
 */
@RestController
@RequestMapping(value = "job")
public class JobController {
    private IJobAndTriggerService iJobAndTriggerService;
    private final JobService jobService;

    public JobController(JobService jobService,
                         IJobAndTriggerService iJobAndTriggerService) {
        this.jobService = jobService;
        this.iJobAndTriggerService = iJobAndTriggerService;
    }

    /**
     * 添加任务
     */
    @PostMapping(value = "addjob")
    public void addJob(String jobClassName, String jobGroupName, String cronExpression) {
        jobService.addJob(jobClassName, jobGroupName, cronExpression);
    }

    /**
     * 暂停任务
     */
    @PostMapping(value = "pausejob")
    public void pausejob(String jobClassName, String jobGroupName) {
        jobService.pauseJob(jobClassName, jobGroupName);
    }

    /**
     * 恢复任务
     */
    @PostMapping(value = "resumejob")
    public void resumejob(String jobClassName, String jobGroupName) {
        jobService.resumeJob(jobClassName, jobGroupName);
    }

    /**
     * 从新进行任务
     */
    @PostMapping(value = "/reschedulejob")
    public void rescheduleJob(String jobClassName, String jobGroupName, String cronExpression) {
        jobService.rescheduleJob(jobClassName, jobGroupName, cronExpression);
    }

    /**
     * 删除任务
     */
    @PostMapping(value = "deletejob")
    public void deletejob(String jobClassName, String jobGroupName) {
        jobService.deleteJob(jobClassName, jobGroupName);
    }

    /**
     * 查询任务
     */
    @GetMapping(value = "queryjob")
    public Map<String, Object> queryjob(Integer pageNum, Integer pageSize) {
        PageInfo<JobAndTrigger> jobAndTrigger = iJobAndTriggerService.getJobAndTriggerDetails(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("JobAndTrigger", jobAndTrigger);
        map.put("number", jobAndTrigger.getTotal());
        return map;
    }
}