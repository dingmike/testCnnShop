package com.platform.service;

import com.platform.dao.ApiUserReadNewsMapper;
import com.platform.entity.UserReadNewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/10/12.
 */
@Service
public class ApiUserReadNewsService {
    @Autowired
    private ApiUserReadNewsMapper userReadNewsDao;


    public UserReadNewsVo queryObject(Integer id) {
        return userReadNewsDao.queryObject(id);
    }


    public List<UserReadNewsVo> queryList(Map<String, Object> map) {
        return userReadNewsDao.queryList(map);
    }

    public List<UserReadNewsVo> queryListByUserId(Map<String, Object> map) {
        return userReadNewsDao.queryListByUserId(map);
    }


    public int queryTotal(Map<String, Object> map) {
        return userReadNewsDao.queryTotal(map);
    }

    public int save(UserReadNewsVo userReadNews) {
        return userReadNewsDao.save(userReadNews);
    }

    public int update(UserReadNewsVo userReadNews) {
        return userReadNewsDao.update(userReadNews);
    }

    public int delete(Integer id) {
        return userReadNewsDao.delete(id);
    }

    public int deleteBatch(Integer[] ids) {
        return userReadNewsDao.deleteBatch(ids);
    }
}
