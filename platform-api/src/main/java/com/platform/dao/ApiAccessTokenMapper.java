package com.platform.dao;

import com.platform.entity.AccessTokenEntity;

/**
 * 微信access_Token
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-03-23 15:22:07
 */
public interface ApiAccessTokenMapper extends BaseDao<AccessTokenEntity> {

    AccessTokenEntity queryByFirst();
}
