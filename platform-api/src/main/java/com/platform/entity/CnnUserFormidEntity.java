package com.platform.entity;

        import java.io.Serializable;
        import java.util.Date;

/**
 * 实体
 * 表名 cnn_user_formid
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-11-05 14:46:55
 */
public class CnnUserFormidEntity implements Serializable {
    private static final long serialVersionUID = 1L;

            //ID
        private Integer id;
            //wxml表单Id
        private String formid;
            //用户ID
        private Integer userid;
            //添加时间
        private Date addTime;
            //更新时间
        private Date updateTime;
    
            /**
         * 设置：ID
         */
        public void setId(Integer id) {
            this.id = id;
        }

        /**
         * 获取：ID
         */
        public Integer getId() {
            return id;
        }
            /**
         * 设置：wxml表单Id
         */
        public void setFormid(String formid) {
            this.formid = formid;
        }

        /**
         * 获取：wxml表单Id
         */
        public String getFormid() {
            return formid;
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
