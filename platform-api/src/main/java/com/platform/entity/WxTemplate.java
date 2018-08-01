package com.platform.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 实体
 * 表名 WxTemplate
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-04 21:13:26
 */
public class WxTemplate implements Serializable {
    private String touser;//接收人的openId
    private String template_id;//消息模版id
    private String form_id;//表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
    private String url;//点击模版访问url
    private String page;//点击模版访问的小程序页面
    private String topcolor;//消息头部颜色
    private Map<String,TemplateData> data;//消息内容
    public WxTemplate(){
        super();
    }
    public WxTemplate(String touser, String template_id, String url,
                    String topcolor, Map<String, TemplateData> data) {
        super();
        this.touser = touser;
        this.template_id = template_id;
        this.form_id = form_id;
        this.url = url;
        this.page = page;
        this.topcolor = topcolor;
        this.data = data;
    }
    public String getTouser() {
        return touser;
    }
    public void setTouser(String touser) {
        this.touser = touser;
    }
    public String getTemplate_id() {
        return template_id;
    }
    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }
    public String getForm_id() {
        return form_id;
    }
    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }
    public String getTopcolor() {
        return topcolor;
    }
    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }
    public Map<String, TemplateData> getData() {
        return data;
    }
    public void setData(Map<String, TemplateData> data) {
        this.data = data;
    }
}
