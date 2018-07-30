package com.platform.dao;

import com.platform.entity.ScheduleJobEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 定时任务
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2016年12月1日 下午10:29:57
 */
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {

    /**
     * 批量更新状态
     */
    int updateBatch(Map<String, Object> map);


    ScheduleJobEntity queryObjectByMethodName(@Param("beanName") String beanName, @Param("methodName") String methodName);
}
