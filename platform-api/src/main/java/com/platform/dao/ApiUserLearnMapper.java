package com.platform.dao;

import com.platform.entity.UserLearnVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Dao
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-04 21:13:26
 */
public interface ApiUserLearnMapper extends BaseDao<UserLearnVo> {
    UserLearnVo queryObjectByUserId(@Param("userId") Integer userId);
    int queryTotalByLearnTypeId(Map<String, Object> map);
    UserLearnVo queryObjectByUserIdAndLearnTypeId(@Param("userId") Integer userId, @Param("learnTypeId") Integer learnTypeId);
    void updateUnlocks(@Param("totalUnlocks") Integer totalUnlocks);
    void updateFormId(@Param("userId") Integer userId, @Param("updateFormIds") String updateFormIds);
}
