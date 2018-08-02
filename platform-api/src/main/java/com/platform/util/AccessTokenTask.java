package com.platform.util;


import com.platform.entity.AccessTokenEntity;
import com.platform.entity.ScheduleJobEntity;
import com.platform.service.AccessTokenService;

import com.platform.service.ScheduleJobService;

import com.platform.utils.ResourceUtil;
import com.platform.utils.ScheduleUtils;
import com.platform.validator.ValidatorUtils;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2018/7/28.
 */
@Component("accessTokenTask")
@Service
public class AccessTokenTask {

    private  static Logger logger = LoggerFactory.getLogger(AccessTokenTask.class);

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private AccessTokenService accessTokenService;
    @Autowired
    private ScheduleJobService scheduleJobService;

    private static String appid = ResourceUtil.getConfigByName("wx.appId");
    private static String appsecret =  ResourceUtil.getConfigByName("wx.secret");
    public static AccessTokenEntity accessToken = null;
    // 定时任务调用的方法accessTokenTaskMethod
    public void accessTokenTaskMethod(String params){
        logger.info("正在执行获取微信access Token的方法accessTokenTaskMethod，参数名为accessToken：" + params);
        accessToken = CommonUtil.getWxAccessToken(appid, appsecret);// 首先获取access_token
        if (null != accessToken) {
            //调用存储到数据库
            System.out.println("my accessToken  :=========================" );
            System.out.println(accessToken);
            // 调用service的方法
            accessTokenService.save(accessToken);
            logger.info("获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), accessToken.getAccessToken());

    }else{
           // 如果access_token为null，60秒后再获取
         //  Thread.sleep(60 * 1000);
            try {
                Thread.sleep(60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void askAccessToken() {
        logger.info("获取微信access Token 的定时任务");

        ScheduleJobEntity newScheduleJob =scheduleJobService.queryObjectByMethodName("accessTokenTask");// 通过bean名去查询job

        if(newScheduleJob==null){
          //  accessToken = CommonUtil.getWxAccessToken(appid, appsecret);
            ScheduleJobEntity scheduleJob = new ScheduleJobEntity();
            scheduleJob.setBeanName("accessTokenTask");
            scheduleJob.setMethodName("accessTokenTaskMethod");// 指定执行方法
            scheduleJob.setRemark("获取accessToken任务");
            scheduleJob.setParams("accessTokenParam");
            scheduleJob.setCronExpression("0 25 0/1 * * ?"); // */5 * * * * ?   //每个五秒执行一次
            ValidatorUtils.validateEntity(scheduleJob);
            scheduleJobService.save(scheduleJob);
            ScheduleJobEntity newScheduleJobTwo =scheduleJobService.queryObjectByMethodName("accessTokenTask");
            ScheduleUtils.run(scheduler, newScheduleJobTwo);// 立即执行定时任务循环刷新access_token
        }else{
            return;
        }
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



}
