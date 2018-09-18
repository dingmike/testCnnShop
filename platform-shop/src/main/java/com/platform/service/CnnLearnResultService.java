package com.platform.service;

        import com.platform.entity.CnnLearnResultEntity;

        import java.util.List;
        import java.util.Map;

/**
 * Service接口
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-09-18 10:48:08
 */
public interface CnnLearnResultService {

    /**
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     */
        CnnLearnResultEntity queryObject(Integer id);

    /**
     * 分页查询
     *
     * @param map 参数
     * @return list
     */
    List<CnnLearnResultEntity> queryList(Map<String, Object> map);

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
     * @param cnnLearnResult 实体
     * @return 保存条数
     */
    int save(CnnLearnResultEntity cnnLearnResult);

    /**
     * 根据主键更新实体
     *
     * @param cnnLearnResult 实体
     * @return 更新条数
     */
    int update(CnnLearnResultEntity cnnLearnResult);

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
