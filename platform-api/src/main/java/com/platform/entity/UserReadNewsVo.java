package com.platform.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 * 表名 user_read_news
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-10-12 12:46:27
 */
public class UserReadNewsVo implements Serializable {
    private static final long serialVersionUID = 1L;

            //
        private Integer id;
            //用户ID
        private Integer userid;
            //
        private String username;
            //微信昵称
        private String nickname;
            //新闻ID
        private Integer newsid;
        //新闻图片
        private String imageUrl;
        //新闻标题
        private String title;
        //新闻简述
        private String descript;
            //阅读使用时间
        private Integer useTime;
            //添加时间
        private Date addTime;
    
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
         * 设置：
         */
        public void setUsername(String username) {
            this.username = username;
        }

        /**
         * 获取：
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
         * 设置：新闻ID
         */
        public void setNewsid(Integer newsid) {
            this.newsid = newsid;
        }

        /**
         * 获取：新闻ID
         */
        public Integer getNewsid() {
            return newsid;
        }
            /**
         * 设置：阅读使用时间
         */
        public void setUseTime(Integer useTime) {
            this.useTime = useTime;
        }

        /**
         * 获取：阅读使用时间
         */
        public Integer getUseTime() {
            return useTime;
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
     * 设置：新闻图片
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 获取：新闻图片
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置：新闻标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取：新闻标题
     */
    public String getTitle() {
        return title;
    }


    /**
     * 设置：新闻简述
     */
    public void setDescript(String descript) {
        this.descript = descript;
    }

    /**
     * 获取：新闻简述
     */
    public String getescript() {
        return descript;
    }


    }
