package com.platform.entity;


import java.io.Serializable;
import java.util.Date;


/**
 * 类名: accessTokenEntity </br>
 * 描述: 凭证 </br>
 * author： admin </br>
 * 创建时间：  2018-7-30 </br>
 * 发布版本：V1.0  </br>
 */
public class AccessTokenEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    // 接口访问凭证
    private String accessToken;
    // 凭证有效期，单位：秒
    // access_token的有效期是7200秒（两小时），在有效期内，可以一直使用，只有当access_token过期时，才需要再次调用接口 获取access_token。
    // 在理想情况下，一个7x24小时运行的系统，每天只需要获取12次access_token，
    // 即每2小时获取一次。如果在 有效期内，再次获取access_token，那么上一次获取的access_token将失效。
    private Integer expiresIn;

    private Date createTime;

    /**
     * 设置：主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：主键
     */
    public Integer getId() {
        return id;
    }

    public String getAccessToken() {
        return accessToken;
    }


    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public int getExpiresIn() {
        return expiresIn;
    }


    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}