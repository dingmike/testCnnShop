package com.platform.service;

import com.platform.dao.ApiCnnLearnTypeMapper;
import com.platform.entity.CnnLearnTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-04 20:11:47
 */
@Service("ApiCnnLearnTypeService")
public class ApiCnnLearnTypeService {
    @Autowired
    private ApiCnnLearnTypeMapper cnnLearnTypeDao;


    public CnnLearnTypeVo queryObject(Integer id) {
        return cnnLearnTypeDao.queryObject(id);
    }


    public List<CnnLearnTypeVo> queryList(Map<String, Object> map) {
        return cnnLearnTypeDao.queryList(map);
    }


    public int queryTotal(Map<String, Object> map) {
        return cnnLearnTypeDao.queryTotal(map);
    }


    public int save(CnnLearnTypeVo cnnLearnType) {
        return cnnLearnTypeDao.save(cnnLearnType);
    }


    public int update(CnnLearnTypeVo cnnLearnType) {
        return cnnLearnTypeDao.update(cnnLearnType);
    }

    public int delete(Integer id) {
        return cnnLearnTypeDao.delete(id);
    }

    public int deleteBatch(Integer[]ids) {
        return cnnLearnTypeDao.deleteBatch(ids);
    }
}
