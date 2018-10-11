package com.platform.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 实体
 * 表名 cnn_news
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-06-29 09:17:59
 */
public class CnnNewsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer id;
    //文章标题
    private String title;
    //文章简述
    private String descript;
    //文章详情
    private String newsDetail;
    //文章中文翻译
    private String chinese;
    //文章主图
    private String imageUrl;
    //文章音频
    private String voiceUrl;
    //是否使用0：不，1：是
    private Integer isUse;
    //添加时间
    private Date addTime;
    //修改时间
    private Date updateTime;
    //创建人ID
    private Long createUserId;
    //修改人ID
    private Long updateUserId;


    /**
     * 设置：
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：
     */
    public Integer getId() {
        return id;
    }
    /**
     * 设置：文章标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取：文章标题
     */
    public String getTitle() {
        return title;
    }
    /**
     * 设置：文章简述
     */
    public void setDescript(String descript) {
        this.descript = descript;
    }

    /**
     * 获取：文章简述
     */
    public String getDescript() {
        return descript;
    }
    /**
     * 设置：文章详情
     */
    public void setNewsDetail(String newsDetail) {
        this.newsDetail = newsDetail;
    }

    /**
     * 获取：文章详情
     */
    public String getNewsDetail() {
        return newsDetail;
    }
    /**
     * 设置：文章中文翻译
     */
    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    /**
     * 获取：文章中文翻译
     */
    public String getChinese() {
        return chinese;
    }
    /**
     * 设置：文章音频
     */
    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    /**
     * 获取：文章音频
     */
    public String getVoiceUrl() {
        return voiceUrl;
    }
    /**
     * 设置：文章主图
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 获取：文章主图
     */
    public String getImageUrl() {
        return imageUrl;
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
    /**
     * 设置：创建人ID
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取：创建人ID
     */
    public Long getCreateUserId() {
        return createUserId;
    }
    /**
     * 设置：修改人ID
     */
    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * 获取：修改人ID
     */
    public Long getUpdateUserId() {
        return updateUserId;
    }
}