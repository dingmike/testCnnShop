package com.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.platform.dao.CnnUserCardDao;
import com.platform.entity.CnnUserCardEntity;
import com.platform.service.CnnUserCardService;

/**
 * Service实现类
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-21 17:16:22
 */
@Service("cnnUserCardService")
public class CnnUserCardServiceImpl implements CnnUserCardService {
    @Autowired
    private CnnUserCardDao cnnUserCardDao;

    @Override
    public CnnUserCardEntity queryObject(Integer id) {
        return cnnUserCardDao.queryObject(id);
    }

    @Override
    public List<CnnUserCardEntity> queryList(Map<String, Object> map) {
        return cnnUserCardDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return cnnUserCardDao.queryTotal(map);
    }

    @Override
    public int save(CnnUserCardEntity cnnUserCard) {
        return cnnUserCardDao.save(cnnUserCard);
    }

    @Override
    public int update(CnnUserCardEntity cnnUserCard) {
        return cnnUserCardDao.update(cnnUserCard);
    }

    @Override
    public int delete(Integer id) {
        return cnnUserCardDao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[]ids) {
        return cnnUserCardDao.deleteBatch(ids);
    }
}
