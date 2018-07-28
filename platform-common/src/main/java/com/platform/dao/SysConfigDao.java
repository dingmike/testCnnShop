package com.platform.dao;

import com.platform.entity.SysConfigEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 系统配置信息
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2017年11月16日 下午10:43:36
 */
public interface SysConfigDao extends BaseDao<SysConfigEntity> {

    /**
     * 根据key，查询value
     */
    String queryByKey(String paramKey);

    /**
     * 根据key，更新value
     *
     * @param key
     * @param value
     * @return
     */
    int updateValueByKey(@Param("key") String key, @Param("value") String value);

}
