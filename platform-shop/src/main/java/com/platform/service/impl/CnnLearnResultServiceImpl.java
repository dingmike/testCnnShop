package com.platform.service.impl;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.Map;

        import com.platform.dao.CnnLearnResultDao;
        import com.platform.entity.CnnLearnResultEntity;
        import com.platform.service.CnnLearnResultService;

/**
 * Service实现类
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-09-18 10:48:08
 */
@Service("cnnLearnResultService")
public class CnnLearnResultServiceImpl implements CnnLearnResultService {
    @Autowired
    private CnnLearnResultDao cnnLearnResultDao;

    @Override
    public CnnLearnResultEntity queryObject(Integer id) {
        return cnnLearnResultDao.queryObject(id);
    }

    @Override
    public List<CnnLearnResultEntity> queryList(Map<String, Object> map) {
        return cnnLearnResultDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return cnnLearnResultDao.queryTotal(map);
    }

    @Override
    public int save(CnnLearnResultEntity cnnLearnResult) {
        return cnnLearnResultDao.save(cnnLearnResult);
    }

    @Override
    public int update(CnnLearnResultEntity cnnLearnResult) {
        return cnnLearnResultDao.update(cnnLearnResult);
    }

    @Override
    public int delete(Integer id) {
        return cnnLearnResultDao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[] ids) {
        return cnnLearnResultDao.deleteBatch(ids);
    }
}