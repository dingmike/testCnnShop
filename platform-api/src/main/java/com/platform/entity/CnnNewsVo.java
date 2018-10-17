package com.platform.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:40
 */
public class CnnNewsVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer id;
    //文章标题
    private String title;
    //文章详情
    private String newsDetail;
    //文章简述
    private String descript;
    //文章翻译
    private String chinese;
    //文章图片
    private String imageUrl;
    //文章语音
    private String voiceUrl;
    //添加时间
    private Date addTime;
    //修改时间
    private Date updateTime;
    //是否当天阅读0：不，1：是
    private Integer isToday;
    //是否使用0：不，1：是
    private Integer isUse;

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





    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }


    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
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
     * 设置：是否当天阅读 0：不，1：是
     */
    public void setIsToday(Integer isToday) {
        this.isToday = isToday;
    }

    /**
     * 获取：是否使用0：不，1：是
     */
    public Integer getIsToday() {
        return isToday;
    }
}
