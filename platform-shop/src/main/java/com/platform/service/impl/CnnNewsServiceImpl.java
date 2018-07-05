package com.platform.service.impl;

import com.platform.entity.SysUserEntity;
import com.platform.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.platform.dao.CnnNewsDao;
import com.platform.entity.CnnNewsEntity;
import com.platform.service.CnnNewsService;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public int save(CnnNewsEntity cnnNews) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        cnnNews.setCreateUserId(user.getUserId());
        cnnNews.setUpdateUserId(user.getUserId());
        return cnnNewsDao.save(cnnNews);
    }

    @Override
    @Transactional
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