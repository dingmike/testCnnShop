package com.platform.service;

import com.platform.dao.ApiCnnLearnResultMapper;
import com.platform.entity.CnnLearnResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-22 14:52:32
 */
@Service
public class ApiCnnLearnResultService {
    @Autowired
    private ApiCnnLearnResultMapper cnnLearnResultDao;


    public CnnLearnResultVo queryObject(Integer id) {
        return cnnLearnResultDao.queryObject(id);
    }


    public List<CnnLearnResultVo> queryList(Map<String, Object> map) {
        return cnnLearnResultDao.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return cnnLearnResultDao.queryTotal(map);
    }

    public int save(CnnLearnResultVo cnnLearnResult) {
        return cnnLearnResultDao.save(cnnLearnResult);
    }

    public int update(CnnLearnResultVo cnnLearnResult) {
        return cnnLearnResultDao.update(cnnLearnResult);
    }

    public int delete(Integer id) {
        return cnnLearnResultDao.delete(id);
    }

    public int deleteBatch(Integer[] ids) {
        return cnnLearnResultDao.deleteBatch(ids);
    }
}
