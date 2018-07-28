package com.platform.dao;

import com.platform.entity.SysMacroEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通用字典表Dao
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2017年11月16日 下午10:43:36
 */
public interface SysMacroDao extends BaseDao<SysMacroEntity> {

    /**
     * 查询数据字段
     *
     * @param value
     * @return
     */
    List<SysMacroEntity> queryMacrosByValue(@Param("value") String value);
}
