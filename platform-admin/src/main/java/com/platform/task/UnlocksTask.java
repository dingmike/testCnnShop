package com.platform.task;


import com.platform.annotation.LoginUser;


import com.platform.entity.UserVo;
import com.platform.service.ApiUserLearnService;

import com.platform.utils.R;
import com.platform.utils.ResourceUtil;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import cn.binarywang.wx.miniapp.api.WxMaMsgService;  // 发送消息，发送模板消息服务

import java.util.Map;
import java.util.TreeMap;

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
    public void updateUnlocks() {
        logger.info("我是不带参数的updateUnlocks方法，正在被执行~");
        userLearnService.updateUnlocks();
    }

}
