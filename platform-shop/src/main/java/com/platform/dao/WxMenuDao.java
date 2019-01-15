package com.platform.dao;

import com.platform.entity.GoodsSpecificationEntity;
import com.platform.entity.WxMenuEntity;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2019-01-15 15:06:35
 */
public interface WxMenuDao extends BaseDao<WxMenuEntity> {
    List<WxMenuEntity> queryListByParentId(Map<String, Object> map);
    Integer queryCountByPid(String pid);
}
