package com.liucan.taskmanager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liucan.taskmanager.entity.JobAndTrigger;
import com.liucan.taskmanager.mybatis.quartz.dao.JobAndTriggerMapper;
import com.liucan.taskmanager.service.IJobAndTriggerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobAndTriggerImpl implements IJobAndTriggerService {
    private final JobAndTriggerMapper jobAndTriggerMapper;

    public JobAndTriggerImpl(JobAndTriggerMapper jobAndTriggerMapper) {
        this.jobAndTriggerMapper = jobAndTriggerMapper;
    }

    public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<JobAndTrigger> list = jobAndTriggerMapper.getJobAndTriggerDetails();
        PageInfo<JobAndTrigger> page = new PageInfo<>(list);
        return page;
    }

}