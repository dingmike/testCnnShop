package com.platform.service;

        import com.platform.entity.UserReadNewsEntity;

        import java.util.List;
        import java.util.Map;

/**
 * Service接口
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-10-12 12:46:27
 */
public interface UserReadNewsService {

    /**
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     */
        UserReadNewsEntity queryObject(Integer id);

    /**
     * 分页查询
     *
     * @param map 参数
     * @return list
     */
    List<UserReadNewsEntity> queryList(Map<String, Object> map);

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
     * @param userReadNews 实体
     * @return 保存条数
     */
    int save(UserReadNewsEntity userReadNews);

    /**
     * 根据主键更新实体
     *
     * @param userReadNews 实体
     * @return 更新条数
     */
    int update(UserReadNewsEntity userReadNews);

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
}
