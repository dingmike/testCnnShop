package com.platform.entity;

import java.io.Serializable;
import java.util.Map;


/**
 * 实体
 * 表名 WxTemplate
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-04 21:13:26
 */
public class TemplateData implements Serializable {

        private String value;

        private String color;

        public String getValue() {

            return value;

        }

        public void setValue(String value) {

            this.value = value;

        }

        public String getColor() {

            return color;

        }

        public void setColor(String color) {

            this.color = color;

        }

}

