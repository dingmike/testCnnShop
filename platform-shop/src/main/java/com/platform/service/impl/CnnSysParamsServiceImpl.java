package com.platform.service.impl;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.Map;

        import com.platform.dao.CnnSysParamsDao;
        import com.platform.entity.CnnSysParamsEntity;
        import com.platform.service.CnnSysParamsService;

/**
 * Service实现类
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-11-21 17:07:03
 */
@Service("cnnSysParamsService")
public class CnnSysParamsServiceImpl implements CnnSysParamsService {
    @Autowired
    private CnnSysParamsDao cnnSysParamsDao;

    @Override
    public CnnSysParamsEntity queryObject(Integer id) {
        return cnnSysParamsDao.queryObject(id);
    }

    @Override
    public List<CnnSysParamsEntity> queryList(Map<String, Object> map) {
        return cnnSysParamsDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return cnnSysParamsDao.queryTotal(map);
    }

    @Override
    public int save(CnnSysParamsEntity cnnSysParams) {
        return cnnSysParamsDao.save(cnnSysParams);
    }

    @Override
    public int update(CnnSysParamsEntity cnnSysParams) {
        return cnnSysParamsDao.update(cnnSysParams);
    }

    @Override
    public int delete(Integer id) {
        return cnnSysParamsDao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[] ids) {
        return cnnSysParamsDao.deleteBatch(ids);
    }
}