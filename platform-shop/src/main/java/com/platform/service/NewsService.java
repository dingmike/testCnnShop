package com.platform.service;

import com.platform.entity.NewsEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author zdj
 * @email mikeding@qq.com
 * @date 2018-06-27 21:19:49
 */
public interface NewsService {

    /**
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     */
    NewsEntity queryObject(Integer id);

    /**
     * 分页查询
     *
     * @param map 参数
     * @return list
     */
    List<NewsEntity> queryList(Map<String, Object> map);

    /**
     * 分页统计总数
     *
     * @param map 参数
     * @return 总数
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存实体
     *
     * @param goods 实体
     * @return 保存条数
     */
    int save(NewsEntity goods);

    /**
     * 根据主键更新实体
     *
     * @param goods 实体
     * @return 更新条数
     */
    int update(NewsEntity goods);

    /**
     * 根据主键删除
     *
     * @param id
     * @return 删除条数
     */
    int delete(Integer id);

    /**
     * 根据主键批量删除
     *
     * @param ids
     * @return 删除条数
     */
    int deleteBatch(Integer[] ids);

    /**
     * 商品从回收站恢复
     *
     * @param ids
     * @return
     */
    int back(Integer[] ids);

    /**
     * 上架
     *
     * @param id
     * @return
     */
    int enSale(Integer id);

    /**
     * 下架
     *
     * @param id
     * @return
     */
    int unSale(Integer id);
}
