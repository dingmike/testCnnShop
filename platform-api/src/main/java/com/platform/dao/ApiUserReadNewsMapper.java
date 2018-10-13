package com.platform.dao;

import com.platform.entity.UserReadNewsVo;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-10-12 12:46:27
 */
public interface ApiUserReadNewsMapper extends BaseDao<UserReadNewsVo> {
    List<UserReadNewsVo> queryListByUserId(Map<String, Object> params);
    int queryTotalByUserId(Map<String, Object> params);
}
