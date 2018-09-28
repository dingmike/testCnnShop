package com.platform.service.impl;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.Map;

        import com.platform.dao.CnnIntergralLogDao;
        import com.platform.entity.CnnIntergralLogEntity;
        import com.platform.service.CnnIntergralLogService;

/**
 * Service实现类
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-09-28 16:54:10
 */
@Service("cnnIntergralLogService")
public class CnnIntergralLogServiceImpl implements CnnIntergralLogService {
    @Autowired
    private CnnIntergralLogDao cnnIntergralLogDao;

    @Override
    public CnnIntergralLogEntity queryObject(Integer id) {
        return cnnIntergralLogDao.queryObject(id);
    }

    @Override
    public List<CnnIntergralLogEntity> queryList(Map<String, Object> map) {
        return cnnIntergralLogDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return cnnIntergralLogDao.queryTotal(map);
    }

    @Override
    public int save(CnnIntergralLogEntity cnnIntergralLog) {
        return cnnIntergralLogDao.save(cnnIntergralLog);
    }

    @Override
    public int update(CnnIntergralLogEntity cnnIntergralLog) {
        return cnnIntergralLogDao.update(cnnIntergralLog);
    }

    @Override
    public int delete(Integer id) {
        return cnnIntergralLogDao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[] ids) {
        return cnnIntergralLogDao.deleteBatch(ids);
    }
}