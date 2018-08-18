package com.platform.task;



import com.platform.service.ApiUserLearnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 更新解锁天数定时任务
 * <p>
 * unlocksTask  bean
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018年01月30日 下午1:34:24
 */
@Component("unlocksTask")
public class UnlocksTask {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ApiUserLearnService userLearnService;

    // 更新user_learn unlocks
    public void updateUnlocks(String params) {
        logger.info("我是携带参数"+params+"的updateUnlocks方法，正在被执行,更新爱学习用户的unlocks~");
        userLearnService.updateUnlocks(Integer.valueOf(params));
    }

}
