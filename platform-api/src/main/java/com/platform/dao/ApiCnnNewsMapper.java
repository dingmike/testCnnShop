package com.platform.dao;

import com.platform.entity.CnnNewsVo;

import java.util.Map;

/**
 * 
 * 
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-08-11 09:14:25
 */
public interface ApiCnnNewsMapper extends BaseDao<CnnNewsVo> {
        CnnNewsVo queryObjectByToday(Map<String, Object> map);
}
