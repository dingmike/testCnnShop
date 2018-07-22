package com.platform.service;


import com.platform.dao.ApiGongduMapper;
import com.platform.entity.GongduVo;
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
public class ApiGongduService {

    @Autowired
    private ApiGongduMapper gongduDao;


   /* public GongduVo queryObject(Map<Integer, Object> map) {
        return gongduDao.queryObjectBytypeAndDays(map);
    }*/


    public GongduVo queryObjectBytypeAndDays(Integer days, Integer type){
        return  gongduDao.queryObjectBytypeAndDays(days, type);
    }
    public GongduVo queryObject(Integer id) {
        return gongduDao.queryObject(id);
    }


    public List<GongduVo> queryList(Map<String, Object> map) {
        return gongduDao.queryList(map);
    }


    public int queryTotal(Map<String, Object> map) {
        return gongduDao.queryTotal(map);
    }


    public void save(GongduVo brand) {
        gongduDao.save(brand);
    }


    public void update(GongduVo brand) {
        gongduDao.update(brand);
    }


    public void delete(Integer id) {
        gongduDao.delete(id);
    }


    public void deleteBatch(Integer[] ids) {
        gongduDao.deleteBatch(ids);
    }
}
