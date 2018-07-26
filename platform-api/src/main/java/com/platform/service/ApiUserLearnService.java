package com.platform.service;

import com.platform.dao.ApiUserLearnMapper;
import com.platform.entity.UserLearnVo;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ApiUserLearnService {

    @Autowired
    private ApiUserLearnMapper userLearnMapper;

    public UserLearnVo queryObject(Integer id) {
        return userLearnMapper.queryObject(id);
    }
    public void updateUnlocks() {}


    public UserLearnVo queryObjectByUserId(Integer userId) {
        return userLearnMapper.queryObjectByUserId(userId);
    }

    public List<UserLearnVo> queryList(Map<String, Object> map) {
        return userLearnMapper.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return userLearnMapper.queryTotal(map);
    }

    public int save(UserLearnVo userLearn) {
        return userLearnMapper.save(userLearn);
    }

    public int update(UserLearnVo userLearn) {
        return userLearnMapper.update(userLearn);
    }

    public int delete(Integer id) {
        return userLearnMapper.delete(id);
    }

    public int deleteBatch(Integer[]ids) {
        return userLearnMapper.deleteBatch(ids);
    }
}
