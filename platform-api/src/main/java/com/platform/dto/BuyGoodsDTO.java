package com.platform.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BuyGoodsDTO implements Serializable {
    private Integer goodsId;
    private Integer productId;
    private Integer number;
    private String name;

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getProductId() {
        return productId;
    }
    public Integer getNumber() {
        return number;
    }
    public Integer getGoodsId() {
        return goodsId;
    }
    public String getName() {
        return name;
    }
}
