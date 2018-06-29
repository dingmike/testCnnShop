package com.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.platform.dao.CnnNewsDao;
import com.platform.entity.CnnNewsEntity;
import com.platform.service.CnnNewsService;

/**
 * Service实现类
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-06-29 09:17:59
 */
@Service("cnnNewsService")
public class CnnNewsServiceImpl implements CnnNewsService {
    @Autowired
    private CnnNewsDao cnnNewsDao;

    @Override
    public CnnNewsEntity queryObject(Integer id) {
        return cnnNewsDao.queryObject(id);
    }

    @Override
    public List<CnnNewsEntity> queryList(Map<String, Object> map) {
        return cnnNewsDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return cnnNewsDao.queryTotal(map);
    }

    @Override
    public int save(CnnNewsEntity cnnNews) {

        return cnnNewsDao.save(cnnNews);
    }

    @Override
    public int update(CnnNewsEntity cnnNews) {
        return cnnNewsDao.update(cnnNews);
    }

    @Override
    public int delete(Integer id) {
        return cnnNewsDao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[]ids) {
        return cnnNewsDao.deleteBatch(ids);
    }
}
