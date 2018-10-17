package com.platform.service;

import com.platform.dao.ApiCnnNewsMapper;
import com.platform.entity.CnnNewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-06-29 09:17:59
 */
@Service
public class ApiCnnNewsService {

    @Autowired
    private ApiCnnNewsMapper cnnNewsDao;


    public CnnNewsVo queryObject(Integer id) {
        return cnnNewsDao.queryObject(id);
    }
    public CnnNewsVo queryObjectByToday(Map<String, Object> map) {
        return cnnNewsDao.queryObjectByToday(map);
    }

    public List<CnnNewsVo> queryList(Map<String, Object> map) {
        return cnnNewsDao.queryList(map);
    }


    public int queryTotal(Map<String, Object> map) {
        return cnnNewsDao.queryTotal(map);
    }


    public void save(CnnNewsVo brand) {
        cnnNewsDao.save(brand);
    }


    public void update(CnnNewsVo brand) {
        cnnNewsDao.update(brand);
    }


    public void delete(Integer id) {
        cnnNewsDao.delete(id);
    }


    public void deleteBatch(Integer[] ids) {
        cnnNewsDao.deleteBatch(ids);
    }
}
