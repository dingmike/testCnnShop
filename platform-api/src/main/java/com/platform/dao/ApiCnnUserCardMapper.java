package com.platform.dao;

import com.platform.entity.CnnUserCardVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author mike
 * @email 2252277509@qq.com
 * @date 2018-08-11 09:14:20
 */
public interface ApiCnnUserCardMapper extends BaseDao<CnnUserCardVo> {
      List<CnnUserCardVo> queryUserCardByUseridAndType(@Param("userid") Long userid, @Param("learnTypeId") Integer type);
      List<CnnUserCardVo> queryUserCardList(@Param("userid") Long userid, @Param("learnTypeId") Integer type);
}
