package com.platform.dao;

import com.platform.entity.CnnUserFormidEntity;
import org.apache.ibatis.annotations.Param;

/**
 * Dao
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-11-05 14:46:55
 */
public interface CnnUserFormidDao extends BaseDao<CnnUserFormidEntity> {
    CnnUserFormidEntity queryObjectByUserid(@Param("userid") Integer userid);
}
