package com.platform.service;

        import com.platform.entity.WxMenuEntity;

        import java.util.List;
        import java.util.Map;

/**
 * Service接口
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2019-01-15 15:06:35
 */
public interface WxMenuService {




    /**
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     */
        WxMenuEntity queryObject(String id);

    /**
     * 分页查询
     *
     * @param map 参数
     * @return list
     */
    List<WxMenuEntity> queryList(Map<String, Object> map);


    /**
     * 查询所有菜单
     *
     * @param map 参数
     * @return list
     */
    List<WxMenuEntity> queryListByParentId(Map<String, Object> map);

    /**
     * 分页统计总数
     *
     * @param map 参数
     * @return 总数
     */
    int queryTotal(Map<String, Object> map);
    /**
     * 统计总数
     *
     * @param map 参数pid
     * @return 总数
     */
    Integer queryCountByPid(String pid);

    /**
     * 保存实体
     *
     * @param wxMenu 实体
     * @return 保存条数
     */
    int save(WxMenuEntity wxMenu);

    /**
     * 根据主键更新实体
     *
     * @param wxMenu 实体
     * @return 更新条数
     */
    int update(WxMenuEntity wxMenu);

    /**
     * 根据主键删除
     *
     * @param id
     * @return 删除条数
     */
    int delete(String id);

    /**
     * 根据主键批量删除
     *
     * @param ids
     * @return 删除条数
     */
    int deleteBatch(String[] ids);
}
