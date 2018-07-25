package com.platform.service;

import com.platform.dao.ApiCnnUserCardMapper;
import com.platform.entity.CnnUserCardVo;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-21 17:16:22
 */
@Service("ApiCnnUserCardService")
public class ApiCnnUserCardService {
    @Autowired
    private ApiCnnUserCardMapper cnnUserCardDao;


    public List<CnnUserCardVo> queryUserCardByUseridAndType(Long userid, Integer type){
        return cnnUserCardDao.queryUserCardByUseridAndType(userid, type);
    }
    public List<CnnUserCardVo> queryUserCardList(Long userid, Integer type){
        return cnnUserCardDao.queryUserCardList(userid, type);
    }
    public CnnUserCardVo queryObjectByOther(Long userId, Integer day, Integer month, Integer year, Integer learnTypeId) {
        return cnnUserCardDao.queryObjectByOther(userId, day, month, year, learnTypeId);
    }

    public List<CnnUserCardVo> queryList(Map<String, Object> map) {
        return cnnUserCardDao.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return cnnUserCardDao.queryTotal(map);
    }

    public int save(CnnUserCardVo cnnUserCard) {
        return cnnUserCardDao.save(cnnUserCard);
    }

    public int update(CnnUserCardVo cnnUserCard) {
        return cnnUserCardDao.update(cnnUserCard);
    }

    public int delete(Integer id) {
        return cnnUserCardDao.delete(id);
    }

    public int deleteBatch(Integer[]ids) {
        return cnnUserCardDao.deleteBatch(ids);
    }
}
