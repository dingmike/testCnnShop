package com.platform.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 实体
 * 表名 cnn_learn_content
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-20 16:31:32
 */
public class CnnLearnContentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //学习内容ID
    private Integer id;
    //学习类型ID
    private Integer learnTypeId;
    //学习类型名称
    private String learnType;
    //标题
    private String title;
    //学习天数
    private Integer genusDays;
    //内容关键词
    private String keyNums;
    //学习内容详情
    private String oraleContent;
    //内容语音链接
    private String oraleSound;
    //合成图片路径
    private String scenceImg;
    //是否使用0：不，1：是
    private Integer isUse;
    //扩展内容title
    private String extendSen;
    //语音链接
    private String extendSound;
    //扩展内容
    private String extendWord;
    //添加时间
    private Date addTime;
    //修改时间
    private Date updateTime;

    /**
     * 设置：学习内容ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：学习内容ID
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
     * 设置：标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取：标题
     */
    public String getTitle() {
        return title;
    }
    /**
     * 设置：学习天数
     */
    public void setGenusDays(Integer genusDays) {
        this.genusDays = genusDays;
    }

    /**
     * 获取：学习天数
     */
    public Integer getGenusDays() {
        return genusDays;
    }
    /**
     * 设置：内容关键词
     */
    public void setKeyNums(String keyNums) {
        this.keyNums = keyNums;
    }

    /**
     * 获取：内容关键词
     */
    public String getKeyNums() {
        return keyNums;
    }
    /**
     * 设置：学习内容详情
     */
    public void setOraleContent(String oraleContent) {
        this.oraleContent = oraleContent;
    }

    /**
     * 获取：学习内容详情
     */
    public String getOraleContent() {
        return oraleContent;
    }
    /**
     * 设置：内容语音链接
     */
    public void setOraleSound(String oraleSound) {
        this.oraleSound = oraleSound;
    }

    /**
     * 获取：内容语音链接
     */
    public String getOraleSound() {
        return oraleSound;
    }
    /**
     * 设置：合成图片路径
     */
    public void setScenceImg(String scenceImg) {
        this.scenceImg = scenceImg;
    }

    /**
     * 获取：合成图片路径
     */
    public String getScenceImg() {
        return scenceImg;
    }
    /**
     * 设置：是否使用0：不，1：是
     */
    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    /**
     * 获取：是否使用0：不，1：是
     */
    public Integer getIsUse() {
        return isUse;
    }
    /**
     * 设置：扩展内容title
     */
    public void setExtendSen(String extendSen) {
        this.extendSen = extendSen;
    }

    /**
     * 获取：扩展内容title
     */
    public String getExtendSen() {
        return extendSen;
    }
    /**
     * 设置：语音链接
     */
    public void setExtendSound(String extendSound) {
        this.extendSound = extendSound;
    }

    /**
     * 获取：语音链接
     */
    public String getExtendSound() {
        return extendSound;
    }
    /**
     * 设置：扩展内容
     */
    public void setExtendWord(String extendWord) {
        this.extendWord = extendWord;
    }

    /**
     * 获取：扩展内容
     */
    public String getExtendWord() {
        return extendWord;
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
     * 设置：修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取：修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }
}
