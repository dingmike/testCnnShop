package com.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.platform.dao.CnnLearnContentDao;
import com.platform.entity.CnnLearnContentEntity;
import com.platform.service.CnnLearnContentService;

/**
 * Service实现类
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-20 16:31:32
 */
@Service("cnnLearnContentService")
public class CnnLearnContentServiceImpl implements CnnLearnContentService {
    @Autowired
    private CnnLearnContentDao cnnLearnContentDao;

    @Override
    public CnnLearnContentEntity queryObject(Integer id) {
        return cnnLearnContentDao.queryObject(id);
    }

    @Override
    public List<CnnLearnContentEntity> queryList(Map<String, Object> map) {
        return cnnLearnContentDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return cnnLearnContentDao.queryTotal(map);
    }

    @Override
    public int save(CnnLearnContentEntity cnnLearnContent) {
        return cnnLearnContentDao.save(cnnLearnContent);
    }

    @Override
    public int update(CnnLearnContentEntity cnnLearnContent) {
        return cnnLearnContentDao.update(cnnLearnContent);
    }

    @Override
    public int delete(Integer id) {
        return cnnLearnContentDao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[]ids) {
        return cnnLearnContentDao.deleteBatch(ids);
    }
}
