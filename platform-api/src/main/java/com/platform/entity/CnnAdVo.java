package com.platform.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:40
 */
public class CnnAdVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //cnn广告图片ID
    private Integer id;
    //cnn媒体广告类型
    private Integer mediaType;
    //cnn广告名称
    private String name;
    //cnn广告链接
    private String link;
    //cnn广告图片链接
    private String imageUrl;
    //cnn广告文字类容
    private String content;
    //是否启用广告
    private Integer enabled;
    //添加时间
    private Date addTime;
    //更新时间
    private Date updateTime;

    /**
     * 设置：cnn广告图片ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：cnn广告图片ID
     */
    public Integer getId() {
        return id;
    }
    /**
     * 设置：cnn媒体广告类型
     */
    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }

    /**
     * 获取：cnn媒体广告类型
     */
    public Integer getMediaType() {
        return mediaType;
    }
    /**
     * 设置：cnn广告名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：cnn广告名称
     */
    public String getName() {
        return name;
    }
    /**
     * 设置：cnn广告链接
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * 获取：cnn广告链接
     */
    public String getLink() {
        return link;
    }
    /**
     * 设置：cnn广告图片链接
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 获取：cnn广告图片链接
     */
    public String getImageUrl() {
        return imageUrl;
    }
    /**
     * 设置：cnn广告文字类容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取：cnn广告文字类容
     */
    public String getContent() {
        return content;
    }
    /**
     * 设置：是否启用广告
     */
    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    /**
     * 获取：是否启用广告
     */
    public Integer getEnabled() {
        return enabled;
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
