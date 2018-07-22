package com.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.platform.dao.CnnLearnQuestionDao;
import com.platform.entity.CnnLearnQuestionEntity;
import com.platform.service.CnnLearnQuestionService;

/**
 * Service实现类
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-22 14:52:32
 */
@Service("cnnLearnQuestionService")
public class CnnLearnQuestionServiceImpl implements CnnLearnQuestionService {
    @Autowired
    private CnnLearnQuestionDao cnnLearnQuestionDao;

    @Override
    public CnnLearnQuestionEntity queryObject(Integer id) {
        return cnnLearnQuestionDao.queryObject(id);
    }

    @Override
    public List<CnnLearnQuestionEntity> queryList(Map<String, Object> map) {
        return cnnLearnQuestionDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return cnnLearnQuestionDao.queryTotal(map);
    }

    @Override
    public int save(CnnLearnQuestionEntity cnnLearnQuestion) {
        return cnnLearnQuestionDao.save(cnnLearnQuestion);
    }

    @Override
    public int update(CnnLearnQuestionEntity cnnLearnQuestion) {
        return cnnLearnQuestionDao.update(cnnLearnQuestion);
    }

    @Override
    public int delete(Integer id) {
        return cnnLearnQuestionDao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[]ids) {
        return cnnLearnQuestionDao.deleteBatch(ids);
    }
}
