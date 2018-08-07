package com.platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author admin
 * @email 2252277509@qq.com
 * @date 2017-08-15 08:03:40
 */
public class GongDuOrderVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //订单序列号
    private String orderSn;
    //会员Id
    private Long userId;

    //学习类型ID
    private Integer learnTypeId;
    /*
    订单状态
    1xx 表示订单取消和删除等状态 0订单创建成功等待付款，　101订单已取消，　102订单已删除
    2xx 表示订单支付状态　201订单已付款，等待发货
    3xx 表示订单物流相关状态　300订单已发货， 301用户确认收货
    4xx 表示订单退换货相关的状态　401 没有发货，退款　402 已收货，退款退货
    */
    private Integer orderStatus;

    //付款状态 支付状态;0未付款;1付款中;2已付款;4退款
    private Integer payStatus;

    //实际需要支付的金额
    private BigDecimal actualPrice;
    //订单总价
    private BigDecimal orderPrice;
    //产品总价
    private BigDecimal goodsPrice;

    //付款时间
    private Date payTime;  //付款时间

    //添加时间
    private Date addTime;

    //付款
    private String payId;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getLearnTypeId() {
        return learnTypeId;
    }

    public void setLearnTypeId(Integer learnTypeId) {
        this.learnTypeId = learnTypeId;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }


    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }


    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
