package com.platform.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 实体
 * 表名 cnn_user_card
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-21 17:16:22
 */
public class CnnUserCardVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //用户打卡ID
    private Integer id;
    //学习类型ID
    private Integer learnTypeId;
    //学习类型
    private String learnType;
    //用户ID
    private Integer userid;
    //用户名称
    private String username;
    //用户昵称
    private String nickname;
    //打第几天卡
    private Integer cardDay;
    //打卡日
    private Integer day;
    //打卡月
    private Integer month;
    //打卡年
    private Integer year;
    //是否在规定时间打卡
    private Integer reasonable;
    //打卡时间
    private Date cardTime;
    //添加时间
    private Date addTime;
    //更新时间
    private Date updateTime;

    /**
     * 设置：用户打卡ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：用户打卡ID
     */
    public Integer getId() {
        return id;
    }
    /**
     * 设置：学习类型ID
     */
    public void setLearnTypeId(Integer learnTypeId) {
        this.learnTypeId = learnTypeId;
    }

    /**
     * 获取：学习类型ID
     */
    public Integer getLearnTypeId() {
        return learnTypeId;
    }
    /**
     * 设置：学习类型名称
     */
    public void setLearnType(String learnType) {
        this.learnType = learnType;
    }

    /**
     * 获取：学习类型名称
     */
    public String getLearnType() {
        return learnType;
    }
    /**
     * 设置：用户名称
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取：用户名称
     */
    public String getUsername() {
        return username;
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
     * 设置：打第几天卡
     */
    public void setCardDay(Integer cardDay) {
        this.cardDay = cardDay;
    }

    /**
     * 设置：用户昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取：用户昵称
     */
    public String getNickname() {
        return nickname;
    }


    /**
     * 获取：打第几天卡
     */
    public Integer getCardDay() {
        return cardDay;
    }
    /**
     * 设置：打卡日
     */
    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     * 获取：打卡日
     */
    public Integer getDay() {
        return day;
    }
    /**
     * 设置：打卡月
     */
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * 获取：打卡月
     */
    public Integer getMonth() {
        return month;
    }
    /**
     * 设置：打卡年
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * 获取：打卡年
     */
    public Integer getYear() {
        return year;
    }
    /**
     * 设置：是否在规定时间打卡
     */
    public void setReasonable(Integer reasonable) {
        this.reasonable = reasonable;
    }

    /**
     * 获取：是否在规定时间打卡
     */
    public Integer getReasonable() {
        return reasonable;
    }
    /**
     * 设置：打卡时间
     */
    public void setCardTime(Date cardTime) {
        this.cardTime = cardTime;
    }

    /**
     * 获取：打卡时间
     */
    public Date getCardTime() {
        return cardTime;
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
