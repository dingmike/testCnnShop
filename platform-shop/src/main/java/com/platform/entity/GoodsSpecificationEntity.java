package com.platform.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 商品对应规格表值表实体
 * 表名 nideshop_goods_specification
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2017-08-31 11:15:55
 */
public class GoodsSpecificationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //商品
    private Integer goodsId;
    //规范Id
    private Integer specificationId;
    //规范说明
    private String value;
    //规范图片
    private String picUrl;

    /**
     * 翻译用字段
     */
    //商品
    private String goodsName;
    //规范
    private String specificationName;

    // 对应规格值列表
    List<GoodsSpecificationEntity> goodsSpecificationList = new ArrayList<>();

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSpecificationName() {
        return specificationName;
    }

    public void setSpecificationName(String specificationName) {
        this.specificationName = specificationName;
    }

    public List<GoodsSpecificationEntity> getGoodsSpecificationList() {
        return goodsSpecificationList;
    }

    public void setGoodsSpecificationList(List<GoodsSpecificationEntity> goodsSpecificationList) {
        this.goodsSpecificationList = goodsSpecificationList;
    }

    /**
     * 设置：主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置：商品
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取：商品
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * 设置：规范Id
     */
    public void setSpecificationId(Integer specificationId) {
        this.specificationId = specificationId;
    }

    /**
     * 获取：规范Id
     */
    public Integer getSpecificationId() {
        return specificationId;
    }

    /**
     * 设置：规范说明
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取：规范说明
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置：规范图片
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    /**
     * 获取：规范图片
     */
    public String getPicUrl() {
        return picUrl;
    }
}
