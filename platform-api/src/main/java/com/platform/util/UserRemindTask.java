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

import com.platform.util.CronUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/7/28.
 */
@Component("remindTask")
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


    // 定时任务的方法
    public void remindTaskMethod(String params){
        logger.info("定时任务执行方法带参数：" + params);

        System.out.println("提醒用户时间到了该吃饭了！！！！！");
    }



    public void test(Long userId, String setupTime) {
        logger.info("我是带参数的UserRemindTask的test方法，正在被执行，参数为用户：" + userId + "提醒时间：" + setupTime);

       // 转换时间为cron //08:02
        String[] cronArr = setupTime.split(":");

        String newCorn = "0 " +cronArr[1]+" "+cronArr[0]+" ? "+"* * *";

        System.out.println("corn express++++++++++++++++++++++++");
        System.out.println(newCorn);
        ScheduleJobEntity scheduleJob = new ScheduleJobEntity();

//        ScheduleUtils.createScheduleJob(scheduler, );


        scheduleJob.setBeanName("remindTask");
        Long l2 = new Long(7);
//        scheduleJob.setJobId(l2);
        scheduleJob.setMethodName("remindTaskMethod");
        scheduleJob.setRemark("定时提醒");
//        scheduleJob.setStatus(1);
        scheduleJob.setParams("rererer");
//        scheduleJob.setCreateTime(new Date());
//        String cornStr = "*/5 * * * * ?";
        String cornStr = newCorn;
        scheduleJob.setCronExpression(cornStr); // */5 * * * * ?   ///  上午10：15 "0 15 10 ? * *"
        ValidatorUtils.validateEntity(scheduleJob);


        ScheduleJobEntity newScheduleJob =scheduleJobService.queryObjectByMethodName("remindTask");// 通过方法名去查询job
//        ScheduleJobEntity newScheduleJob =scheduleJobService.queryObject();// 通过jobId去查询job

        if(newScheduleJob!=null){
            Long[] jobsId= new Long[]{newScheduleJob.getJobId()};
            scheduleJobService.pause(jobsId);
            newScheduleJob.setCronExpression(cornStr);
            scheduleJobService.update(newScheduleJob);
            scheduleJobService.resume(jobsId);// 恢复任务
        }else{
            scheduleJobService.save(scheduleJob);
            ScheduleJobEntity newScheduleJobTwo =scheduleJobService.queryObjectByMethodName("remindTask");
            ScheduleUtils.run(scheduler, newScheduleJobTwo);// 立即执行定时任务
        }


        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



}
