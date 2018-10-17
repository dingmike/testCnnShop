package com.platform.service;

import com.platform.dao.ApiCnnUserIntergralLogMapper;
import com.platform.entity.UserIntergralLogVo;
import com.platform.entity.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-04 21:13:26
 */
@Service
public class ApiUserIntergralLogService {


    @Autowired
    private  ApiCnnUserIntergralLogMapper cnnIntergralLogDao;
    @Autowired
    private ApiUserService userService;

    public UserIntergralLogVo queryObject(Integer id) {
        return cnnIntergralLogDao.queryObject(id);
    }


    public List<UserIntergralLogVo> queryList(Map<String, Object> map) {
        return cnnIntergralLogDao.queryList(map);
    }


    public int queryTotal(Map<String, Object> map) {
        return cnnIntergralLogDao.queryTotal(map);
    }


  /*  public int save(UserIntergralLogVo cnnIntergralLog) {
        return cnnIntergralLogDao.save(cnnIntergralLog);
    }*/
    public void save(UserIntergralLogVo cnnIntergralLog, UserVo loginUser) {

        BigDecimal increased = new BigDecimal(1);
        loginUser.setIntergral(increased);
        userService.update(loginUser);
        cnnIntergralLog.setPlusMins(1); // 1加 0减
//        cnnIntergralLog.setMemo("每日阅读打卡获得");
        cnnIntergralLog.setPoints(increased);
        cnnIntergralLogDao.save(cnnIntergralLog);
    }


    public int update(UserIntergralLogVo cnnIntergralLog) {
        return cnnIntergralLogDao.update(cnnIntergralLog);
    }

    public int delete(Integer id) {
        return cnnIntergralLogDao.delete(id);
    }


    public int deleteBatch(Integer[] ids) {
        return cnnIntergralLogDao.deleteBatch(ids);
    }


}
