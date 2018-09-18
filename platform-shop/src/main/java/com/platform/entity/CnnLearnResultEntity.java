package com.platform.entity;

        import java.io.Serializable;
        import java.util.Date;

/**
 * 实体
 * 表名 cnn_learn_result
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-09-18 10:48:08
 */
public class CnnLearnResultEntity implements Serializable {
    private static final long serialVersionUID = 1L;

            //用户打卡结果ID
        private Integer id;
            //
        private Integer userid;
            //
        private Integer learnTypeId;
            //
        private String username;
            //
        private String nickname;
            //21天后打卡结果1：成功，0失败
        private Integer result;
            //添加时间
        private Date addTime;
            //更新时间
        private Date updateTime;
    
            /**
         * 设置：用户打卡结果ID
         */
        public void setId(Integer id) {
            this.id = id;
        }

        /**
         * 获取：用户打卡结果ID
         */
        public Integer getId() {
            return id;
        }
            /**
         * 设置：
         */
        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        /**
         * 获取：
         */
        public Integer getUserid() {
            return userid;
        }
            /**
         * 设置：
         */
        public void setLearnTypeId(Integer learnTypeId) {
            this.learnTypeId = learnTypeId;
        }

        /**
         * 获取：
         */
        public Integer getLearnTypeId() {
            return learnTypeId;
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
         * 设置：
         */
        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        /**
         * 获取：
         */
        public String getNickname() {
            return nickname;
        }
            /**
         * 设置：21天后打卡结果1：成功，0失败
         */
        public void setResult(Integer result) {
            this.result = result;
        }

        /**
         * 获取：21天后打卡结果1：成功，0失败
         */
        public Integer getResult() {
            return result;
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
