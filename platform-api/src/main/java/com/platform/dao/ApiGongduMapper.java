package com.platform.dao;

import com.platform.entity.GongduVo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 
 * 
 * @author mike
 * @email 2252277509@qq.com
 * @date 2018-08-11 09:14:20
 */
public interface ApiGongduMapper extends BaseDao<GongduVo> {
      GongduVo queryObjectBytypeAndDays(@Param("genusDays") Integer days, @Param("learnTypeId") Integer type);
}
