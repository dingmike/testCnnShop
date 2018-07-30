package com.platform.util;

import com.platform.dao.ScheduleJobDao;
import com.platform.entity.UserVo;
import com.platform.entity.ScheduleJobEntity;

import com.platform.service.ApiUserService;
import com.platform.service.ScheduleJobService;
import com.platform.service.SysUserService;
import com.platform.utils.ScheduleUtils;
import com.platform.validator.ValidatorUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/7/28.
 */
//@Component("remindTask")
@Service
public class UserRemindTask {

    private  static Logger logger = LoggerFactory.getLogger(UserRemindTask.class);

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ScheduleJobDao schedulerJobDao;
    @Autowired
    private ApiUserService userService;
    @Autowired
    private ScheduleJobService scheduleJobService;



    @Transactional
    public void run(ScheduleJobEntity job) {
        ScheduleUtils.run(scheduler, job);
    }



    public void test(String params) {
        logger.info("我是带参数的UserRemindTask的test方法，正在被执行，参数为：" + params);
        ScheduleJobEntity scheduleJob = new ScheduleJobEntity();
//        Map scheduleJobParams = new HashMap();

//        scheduleJobParams.put("beanName", "remindTask");
//        scheduleJobParams.put("MethodName", params);
//        scheduleJobParams.put("cronExpression", "*/5 * * * *");
//        scheduleJobParams.put("remark", "定时提醒");/
//        ScheduleJobEntity scheduleJobEntity  =new ScheduleJobEntity();
        scheduleJob.setBeanName("remindTask");
        Long l2 = new Long(7);
//        scheduleJob.setJobId(l2);
        scheduleJob.setMethodName(params);
        scheduleJob.setRemark("定时提醒");
//        scheduleJob.setStatus(1);
        scheduleJob.setParams("rererer");
//        scheduleJob.setCreateTime(new Date());
        String cornStr = "*/5 * * * * ?";
        scheduleJob.setCronExpression(cornStr); // */5 * * * * ?   ///  上午10：15 "0 15 10 ? * *"
        ValidatorUtils.validateEntity(scheduleJob);

        scheduleJobService.save(scheduleJob);

        ScheduleJobEntity newScheduleJob =scheduleJobService.queryObjectByMethodName("remindTask", params);
        //Long[] jobsId= new Long[]{newScheduleJob.getJobId()};

        //Long jobId = newScheduleJob.getJobId();
        run(newScheduleJob);
//        scheduleJobService.run(jobsId);// 立即执行

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }



}
