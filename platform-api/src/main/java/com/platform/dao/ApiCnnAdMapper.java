package com.platform.dao;

import com.platform.entity.CnnAdVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 
 * 
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-11 09:14:25
 */
public interface ApiCnnAdMapper extends BaseDao<CnnAdVo> {

    List<CnnAdVo> queryListByMediaType(@Param("mediaType") Integer mediaType);
}
