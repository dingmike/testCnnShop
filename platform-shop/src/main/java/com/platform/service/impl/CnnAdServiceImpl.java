package com.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.platform.dao.CnnAdDao;
import com.platform.entity.CnnAdEntity;
import com.platform.service.CnnAdService;

/**
 * Service实现类
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-02 11:42:33
 */
@Service("cnnAdService")
public class CnnAdServiceImpl implements CnnAdService {
    @Autowired
    private CnnAdDao cnnAdDao;

    @Override
    public CnnAdEntity queryObject(Integer id) {
        return cnnAdDao.queryObject(id);
    }

    @Override
    public List<CnnAdEntity> queryList(Map<String, Object> map) {
        return cnnAdDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return cnnAdDao.queryTotal(map);
    }

    @Override
    public int save(CnnAdEntity cnnAd) {
        return cnnAdDao.save(cnnAd);
    }

    @Override
    public int update(CnnAdEntity cnnAd) {
        return cnnAdDao.update(cnnAd);
    }

    @Override
    public int delete(Integer id) {
        return cnnAdDao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[]ids) {
        return cnnAdDao.deleteBatch(ids);
    }
}
