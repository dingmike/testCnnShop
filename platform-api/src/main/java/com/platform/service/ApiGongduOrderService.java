package com.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.platform.cache.J2CacheUtils;
import com.platform.dao.ApiCnnLearnTypeMapper;
import com.platform.entity.*;
import com.platform.util.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.platform.dao.ApiGongduOrderMapper;
import org.springframework.transaction.annotation.Transactional;

import com.platform.entity.CnnLearnTypeVo;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by admin on 2018/8/7.
 */

@Service
public class ApiGongduOrderService {

    @Autowired
    private ApiGongduOrderMapper cnngongduOrderDao;
    @Autowired
    private ApiCnnLearnTypeMapper apiCnnLearnTypeMapper;


    public GongDuOrderVo queryObject(String orderSn) {
        return cnngongduOrderDao.queryObject(orderSn);
    }


    public List<GongDuOrderVo> queryList(Map<String, Object> map) {
        return cnngongduOrderDao.queryList(map);
    }


    public int queryTotal(Map<String, Object> map) {
        return cnngongduOrderDao.queryTotal(map);
    }


    public int save(GongDuOrderVo cnngongduOrder) {
        return cnngongduOrderDao.save(cnngongduOrder);
    }


    public int update(GongDuOrderVo cnngongduOrder) {
        return cnngongduOrderDao.update(cnngongduOrder);
    }


    public int delete(Integer id) {
        return cnngongduOrderDao.delete(id);
    }

    public int deleteBatch(Integer[] ids) {
        return cnngongduOrderDao.deleteBatch(ids);
    }


    @Transactional
    public Map<String, Object> submit(JSONObject jsonParam, UserVo loginUser) {
        Map<String, Object> resultObj = new HashMap<String, Object>();

        String uid = jsonParam.getString("uid");
        if(!uid.equals(loginUser.getWeixin_openid())){
            resultObj.put("errno", 401);
            resultObj.put("errmsg", "用户不匹配");
            return resultObj;
        }

        Integer learnTypeId = jsonParam.getInteger("learnTypeId");
        CnnLearnTypeVo cnnLearnTypeVo = apiCnnLearnTypeMapper.queryObject(learnTypeId);
        GongDuOrderVo gongDuOrderVo = new GongDuOrderVo();
        gongDuOrderVo.setOrderSn(CommonUtil.generateOrderNumber());
        gongDuOrderVo.setUserId(loginUser.getUserId());
        gongDuOrderVo.setLearnTypeId(learnTypeId);
        gongDuOrderVo.setPayStatus(0);//付款状态 支付状态;0未付款;1付款中;2已付款;4退款
          /*
        订单状态
        1xx 表示订单取消和删除等状态 0订单创建成功等待付款，　101订单已取消，　102订单已删除
        2xx 表示订单支付状态　201订单已付款，等待发货
        3xx 表示订单物流相关状态　300订单已发货， 301用户确认收货
        4xx 表示订单退换货相关的状态　401 没有发货，退款　402 已收货，退款退货
        */
        gongDuOrderVo.setOrderStatus(0);
        gongDuOrderVo.setGoodsPrice(cnnLearnTypeVo.getProductPrice());
        cnngongduOrderDao.save(gongDuOrderVo); //开启事务，插入订单信息和订单商品
        if (null == gongDuOrderVo.getId()) {
            resultObj.put("errno", 1);
            resultObj.put("errmsg", "订单提交失败");
            return resultObj;
        }
        resultObj.put("errno", 0);
        resultObj.put("errmsg", "订单提交成功");
        resultObj.put("data", gongDuOrderVo);

        return resultObj;
    }

}
