package com.liucan.taskmanager.mybatis.quartz.dao;


import com.liucan.taskmanager.entity.JobAndTrigger;

import java.util.List;

public interface JobAndTriggerMapper {
    List<JobAndTrigger> getJobAndTriggerDetails();
}
