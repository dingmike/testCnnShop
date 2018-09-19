package com.platform.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 实体
 * 表名 cnn_user_learn
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-04 21:13:26
 */
public class UserLearnVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //用户学习状态ID
    private Integer id;
    //学习类型ID
    private Integer learnTypeId;
    //用户ID
    private Integer userid;
    //已打卡阅读天数
    private Integer unlocks;
    //微信表单ID
    private String formId;

    //未打卡天数
    private Integer miss;
    //是否开始学习
    private Integer startStatus;
    //提醒打卡时间
    private String setupTime;
    //添加时间
    private Date addTime;
    //更新时间
    private Date updateTime;

    //用户名称
    private String username;
    //用户头像
    private String avatar;

    //用户头像
    private String nickname;
    //用户学习类型
    private String learnType;

    //用户学习类型天数
    private Integer genusdays;

    /**
     * 设置：用户学习状态ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：用户学习状态ID
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
     * 设置：已打卡阅读天数
     */
    public void setUnlocks(Integer unlocks) {
        this.unlocks = unlocks;
    }

    /**
     * 获取：已打卡阅读天数
     */
    public Integer getUnlocks() {
        return unlocks;
    }

    /**
     * 设置：微信表单ID
     */
    public void setFormId(String formId) {
        this.formId = formId;
    }

    /**
     * 获取：微信表单ID
     */
    public String getFormId() {
        return formId;
    }


    /**
     * 设置：错过打卡天数
     */
    public void setMiss(Integer miss) {
        this.miss = miss;
    }

    /**
     * 获取：错过打卡天数
     */
    public Integer getMiss() {
        return miss;
    }

    /**
     * 设置：是否开始学习
     */
    public void setStartStatus(Integer startStatus) {
        this.startStatus = startStatus;
    }

    /**
     * 获取：是否开始学习
     */
    public Integer getStartStatus() {
        return startStatus;
    }
    /**
     * 设置：提醒打卡时间
     */
    public void setSetupTime(String setupTime) {
        this.setupTime = setupTime;
    }

    /**
     * 获取：提醒打卡时间
     */
    public String getSetupTime() {
        return setupTime;
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


    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getLearnType() {
        return learnType;
    }

    public void setLearnType(String learnType) {
        this.learnType = learnType;
    }

    public Integer getGenusdays() {
        return genusdays;
    }

    public void setGenusdays(Integer genusdays) {
        this.genusdays = genusdays;
    }
}
