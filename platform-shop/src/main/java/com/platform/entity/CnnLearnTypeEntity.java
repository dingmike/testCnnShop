package com.platform.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 实体
 * 表名 cnn_learn_type
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-04 20:11:47
 */
public class CnnLearnTypeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //学习类型ID
    private Integer id;
    //学习类型名称
    private String learnType;

    //学习类型开启状态
    private String status;
    //添加时间
    private Date addTime;
    //更新时间
    private Date updateTime;

    /**
     * 设置：学习类型ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：学习类型ID
     */
    public Integer getId() {
        return id;
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
     * 设置：学习类型状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取：学习类型状态
     */
    public String getStatus() {
        return status;
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
