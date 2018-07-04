package com.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.platform.dao.CnnLearnTypeDao;
import com.platform.entity.CnnLearnTypeEntity;
import com.platform.service.CnnLearnTypeService;

/**
 * Service实现类
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-04 20:11:47
 */
@Service("cnnLearnTypeService")
public class CnnLearnTypeServiceImpl implements CnnLearnTypeService {
    @Autowired
    private CnnLearnTypeDao cnnLearnTypeDao;

    @Override
    public CnnLearnTypeEntity queryObject(Integer id) {
        return cnnLearnTypeDao.queryObject(id);
    }

    @Override
    public List<CnnLearnTypeEntity> queryList(Map<String, Object> map) {
        return cnnLearnTypeDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return cnnLearnTypeDao.queryTotal(map);
    }

    @Override
    public int save(CnnLearnTypeEntity cnnLearnType) {
        return cnnLearnTypeDao.save(cnnLearnType);
    }

    @Override
    public int update(CnnLearnTypeEntity cnnLearnType) {
        return cnnLearnTypeDao.update(cnnLearnType);
    }

    @Override
    public int delete(Integer id) {
        return cnnLearnTypeDao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[]ids) {
        return cnnLearnTypeDao.deleteBatch(ids);
    }
}
