package com.platform.dao;

import com.platform.entity.GoodsAttributeEntity;

/**
 * @author admin
 * @email 2252277509@qq.com
 * @date 2017-08-13 10:41:08
 */
public interface GoodsAttributeDao extends BaseDao<GoodsAttributeEntity> {

    int updateByGoodsIdAttributeId(GoodsAttributeEntity goodsAttributeEntity);
}
