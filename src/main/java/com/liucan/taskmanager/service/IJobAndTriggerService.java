package com.liucan.taskmanager.service;

import com.github.pagehelper.PageInfo;
import com.liucan.taskmanager.entity.JobAndTrigger;

public interface IJobAndTriggerService {
    PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize);
}
