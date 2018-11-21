package com.platform.entity;

        import java.io.Serializable;
        import java.util.Date;
        import java.math.BigDecimal;

/**
 * 实体
 * 表名 cnn_sys_params
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-11-21 17:07:03
 */
public class CnnSysParamsVo implements Serializable {
    private static final long serialVersionUID = 1L;

            //
        private Integer id;
            //参数名称
        private String paramsname;
            //阅读券参数
        private BigDecimal increaseparams;
    
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
         * 设置：参数名称
         */
        public void setParamsname(String paramsname) {
            this.paramsname = paramsname;
        }

        /**
         * 获取：参数名称
         */
        public String getParamsname() {
            return paramsname;
        }
            /**
         * 设置：阅读券参数
         */
        public void setIncreaseparams(BigDecimal increaseparams) {
            this.increaseparams = increaseparams;
        }

        /**
         * 获取：阅读券参数
         */
        public BigDecimal getIncreaseparams() {
            return increaseparams;
        }
    }
