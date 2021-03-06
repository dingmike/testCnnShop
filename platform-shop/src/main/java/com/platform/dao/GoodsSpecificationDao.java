package com.platform.dao;

import com.platform.entity.GoodsSpecificationEntity;

import java.util.List;

/**
 * 商品对应规格表值表Dao
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-31 11:15:55
 */
public interface GoodsSpecificationDao extends BaseDao<GoodsSpecificationEntity> {
    //    Integer queryGoodsSpec();
    List<GoodsSpecificationEntity> queryGoodsSpec(Integer goods_id);
}
