package com.platform.service;

import com.platform.dao.ApiCnnAdMapper;
import com.platform.entity.CnnAdVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ApiCnnAdService {
    @Autowired
    private ApiCnnAdMapper cnnAdDao;


    public CnnAdVo queryObject(Integer id) {
        return cnnAdDao.queryObject(id);
    }


    public List<CnnAdVo> queryList(Map<String, Object> map) {
        return cnnAdDao.queryList(map);
    }


    public int queryTotal(Map<String, Object> map) {
        return cnnAdDao.queryTotal(map);
    }


    public void save(CnnAdVo brand) {
        cnnAdDao.save(brand);
    }


    public void update(CnnAdVo brand) {
        cnnAdDao.update(brand);
    }


    public void delete(Integer id) {
        cnnAdDao.delete(id);
    }


    public void deleteBatch(Integer[] ids) {
        cnnAdDao.deleteBatch(ids);
    }

}
