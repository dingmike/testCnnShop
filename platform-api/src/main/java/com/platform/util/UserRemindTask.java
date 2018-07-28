package com.platform.util;

import com.platform.entity.UserVo;
import com.platform.service.ApiUserService;
import com.platform.service.SysUserService;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by admin on 2018/7/28.
 */
public class UserRemindTask {

    private  static Logger logger = LoggerFactory.getLogger(UserRemindTask.class);


    @Autowired
    private static ApiUserService userService;


    public static void test(String params) {
        logger.info("我是带参数的UserRemindTask的test方法，正在被执行，参数为：" + params);

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Long userid = new Long((long)24);
        UserVo user = userService.queryObject(userid);
        System.out.println(ToStringBuilder.reflectionToString(user));

    }



}
