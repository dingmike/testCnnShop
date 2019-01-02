package com.platform.dao;

import com.platform.entity.OrderGoodsEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author admin
 * @email 2252277509@qq.com
 * @date 2017-08-13 10:41:09
 */
public interface OrderGoodsDao extends BaseDao<OrderGoodsEntity> {
//    queryObjectByOrderId
    OrderGoodsEntity queryObjectByOrderId(@Param("orderId") Integer orderId);

}
