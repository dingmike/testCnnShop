package com.platform.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体
 * 表名 cnn_intergral_log
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-09-28 16:54:10
 */
public class UserIntergralLogVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //积分记录Id
    private Integer id;
    //积分来源学习类型
    private Integer learnTypeId;
    //用户ID
    private Integer userid;
    //用户名
    private String username;
    //微信昵称
    private String nickname;
    //积分记录
    private BigDecimal points;
    // 目前总积分
    private BigDecimal nowPoints;
    //打卡记录Id
    private Integer cardId;
    //加减积分1加,0减
    private Integer plusMins;
    //记录说明
    private String memo;
    //添加时间
    private Date addTime;
    //更新时间
    private Date updateTime;

    /**
     * 设置：积分记录Id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：积分记录Id
     */
    public Integer getId() {
        return id;
    }
    /**
     * 设置：积分来源学习类型
     */
    public void setLearnTypeId(Integer learnTypeId) {
        this.learnTypeId = learnTypeId;
    }

    /**
     * 获取：积分来源学习类型
     */
    public Integer getLearnTypeId() {
        return learnTypeId;
    }
    /**
     * 设置：用户ID
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * 获取：用户ID
     */
    public Integer getUserid() {
        return userid;
    }
    /**
     * 设置：用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取：用户名
     */
    public String getUsername() {
        return username;
    }
    /**
     * 设置：微信昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取：微信昵称
     */
    public String getNickname() {
        return nickname;
    }
    /**
     * 设置：积分记录
     */
    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    /**
     * 获取：积分记录
     */
    public BigDecimal getPoints() {
        return points;
    }

    /**
     * 设置：总积分记录
     */
    public void setNowPoints(BigDecimal nowPoints) {
        this.nowPoints = nowPoints;
    }

    /**
     * 获取：总积分记录
     */
    public BigDecimal getNowPoints() {
        return nowPoints;
    }

    /**
     * 设置：打卡记录Id
     */
    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    /**
     * 获取：打卡记录Id
     */
    public Integer getCardId() {
        return cardId;
    }
    /**
     * 设置：加减积分1加,0减
     */
    public void setPlusMins(Integer plusMins) {
        this.plusMins = plusMins;
    }

    /**
     * 获取：加减积分1加,0减
     */
    public Integer getPlusMins() {
        return plusMins;
    }
    /**
     * 设置：记录说明
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 获取：记录说明
     */
    public String getMemo() {
        return memo;
    }
    /**
     * 设置：添加时间
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * 获取：添加时间
     */
    public Date getAddTime() {
        return addTime;
    }
    /**
     * 设置：更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取：更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }
}
