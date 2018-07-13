package com.platform.dao;

import com.platform.entity.UserLearnVo;
import org.apache.ibatis.annotations.Param;

/**
 * Dao
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-04 21:13:26
 */
public interface ApiUserLearnMapper extends BaseDao<UserLearnVo> {
    UserLearnVo queryObjectByUserId(@Param("userId") Integer userId);
}