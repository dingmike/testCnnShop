package com.platform.service;


import com.platform.dao.ApiAccessTokenMapper;
import com.platform.entity.AccessTokenEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by admin on 2018/8/2.
 */
@Service("AccessTokenService")
public class AccessTokenService {

    @Autowired
    private ApiAccessTokenMapper accessTokenMapper;


    //12小时后过期
    private final static int EXPIRE = 3600 * 12;


    public AccessTokenEntity queryByFirst() {
        return accessTokenMapper.queryByFirst();
    }

    public void save(AccessTokenEntity token) {
        accessTokenMapper.save(token);
    }

    public void update(AccessTokenEntity token) {
        accessTokenMapper.update(token);
    }


}
