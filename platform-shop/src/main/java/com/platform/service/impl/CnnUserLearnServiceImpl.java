package com.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.platform.dao.CnnUserLearnDao;
import com.platform.entity.CnnUserLearnEntity;
import com.platform.service.CnnUserLearnService;

/**
 * Service实现类
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-04 21:13:26
 */
@Service("cnnUserLearnService")
public class CnnUserLearnServiceImpl implements CnnUserLearnService {
    @Autowired
    private CnnUserLearnDao cnnUserLearnDao;

    @Override
    public CnnUserLearnEntity queryObject(Integer id) {
        return cnnUserLearnDao.queryObject(id);
    }

    @Override
    public List<CnnUserLearnEntity> queryList(Map<String, Object> map) {
        return cnnUserLearnDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return cnnUserLearnDao.queryTotal(map);
    }

    @Override
    public int save(CnnUserLearnEntity cnnUserLearn) {
        return cnnUserLearnDao.save(cnnUserLearn);
    }

    @Override
    public int update(CnnUserLearnEntity cnnUserLearn) {
        return cnnUserLearnDao.update(cnnUserLearn);
    }

    @Override
    public int delete(Integer id) {
        return cnnUserLearnDao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[]ids) {
        return cnnUserLearnDao.deleteBatch(ids);
    }
}
