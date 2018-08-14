package com.platform.dao;

import com.platform.entity.AttributeEntity;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2017-08-17 16:48:17
 */
public interface AttributeDao extends BaseDao<AttributeEntity> {
    List<AttributeEntity> queryListByCateId(@Param("attributeCategoryId") Integer attributeCategoryId);
}
