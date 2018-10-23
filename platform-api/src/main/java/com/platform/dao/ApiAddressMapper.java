package com.platform.dao;

import com.platform.entity.AddressVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-08-11 09:14:25
 */
public interface ApiAddressMapper extends BaseDao<AddressVo> {
    int updateByUserId(@Param("userId") Integer userId);
}
