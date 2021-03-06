package com.platform.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.platform.entity.*;
import com.platform.util.wechat.WechatUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.platform.cache.J2CacheUtils;
import com.platform.dao.ApiAddressMapper;
import com.platform.dao.ApiCartMapper;
import com.platform.dao.ApiCouponMapper;
import com.platform.dao.ApiOrderGoodsMapper;
import com.platform.dao.ApiOrderMapper;
import com.platform.util.CommonUtil;


@Service
public class ApiOrderService {
    @Autowired
    private ApiOrderMapper orderDao;
    @Autowired
    private ApiAddressMapper apiAddressMapper;
    @Autowired
    private ApiCartMapper apiCartMapper;
    @Autowired
    private ApiCouponMapper apiCouponMapper;
    @Autowired
    private ApiOrderMapper apiOrderMapper;
    @Autowired
    private ApiOrderGoodsMapper apiOrderGoodsMapper;
    @Autowired
    private ApiProductService productService;
    @Autowired
    private CnnSysParamsService cnnSysParamsService;
    @Autowired
    private CnnUserFormidService cnnUserFormidService;
    @Autowired
    private ApiUserService userService;
    @Autowired
    private AccessTokenService accessTokenService;
    @Autowired
    private ApiGoodsSpecificationService apiGoodsSpecificationService;

    public OrderVo queryObject(Integer id) {
        return orderDao.queryObject(id);
    }
    public OrderVo queryObjectByOrderSn(String orderSn) {
        return orderDao.queryObjectByOrderSn(orderSn);
    }


    public List<OrderVo> queryList(Map<String, Object> map) {
        return orderDao.queryList(map);
    }


    public int queryTotal(Map<String, Object> map) {
        return orderDao.queryTotal(map);
    }


    public void save(OrderVo order) {
        orderDao.save(order);
    }


    public int update(OrderVo order) {
        return orderDao.update(order);
    }


    public void delete(Integer id) {
        orderDao.delete(id);
    }


    public void deleteBatch(Integer[] ids) {
        orderDao.deleteBatch(ids);
    }


    @Transactional
    public Map<String, Object> submit(JSONObject jsonParam, UserVo loginUser) {
        Map<String, Object> resultObj = new HashMap<String, Object>();

        Integer couponId = jsonParam.getInteger("couponId");
        String type = jsonParam.getString("type");
        BigDecimal intergrals = jsonParam.getBigDecimal("intergrals");// 抵扣积分
        String postscript = jsonParam.getString("postscript");
//        AddressVo addressVo = jsonParam.getObject("checkedAddress",AddressVo.class);
        AddressVo addressVo = apiAddressMapper.queryObject(jsonParam.getInteger("addressId"));


        Integer freightPrice = 0;

        // * 获取要购买的商品
        List<CartVo> checkedGoodsList = new ArrayList<>();
        BigDecimal goodsTotalPrice;
        if (type.equals("cart")) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("user_id", loginUser.getUserId());
            param.put("session_id", 1);
            param.put("checked", 1);
            checkedGoodsList = apiCartMapper.queryList(param);
            if (null == checkedGoodsList) {
                resultObj.put("errno", 400);
                resultObj.put("errmsg", "请选择商品");
                return resultObj;
            }
            //统计商品总价
            goodsTotalPrice = new BigDecimal(0.00);
            for (CartVo cartItem : checkedGoodsList) {
                goodsTotalPrice = goodsTotalPrice.add(cartItem.getRetail_price().multiply(new BigDecimal(cartItem.getNumber())));
            }
        } else {
//            BuyGoodsVo goodsVo = (BuyGoodsVo) J2CacheUtils.get("goods" + loginUser.getUserId());
            BuyGoodsVo goodsVo = (BuyGoodsVo) J2CacheUtils.get(J2CacheUtils.SHOP_CACHE_NAME, "goods" + loginUser.getUserId());
            ProductVo productInfo = productService.queryObject(goodsVo.getProductId()); //带有规格的商品信息
            //计算订单的费用
            //商品总价
            goodsTotalPrice = productInfo.getRetail_price().multiply(new BigDecimal(goodsVo.getNumber()));

            CartVo cartVo = new CartVo();
            BeanUtils.copyProperties(productInfo, cartVo);//copy属性
            cartVo.setNumber(goodsVo.getNumber());
            cartVo.setProduct_id(goodsVo.getProductId());
            checkedGoodsList.add(cartVo);
        }


        //获取订单使用的优惠券
        BigDecimal couponPrice = new BigDecimal(0.00);
        CouponVo couponVo = null;
        if (couponId != null && couponId != 0 ) {
            couponVo = apiCouponMapper.getUserCoupon(couponId);
            if (couponVo != null && couponVo.getCoupon_status() == 1) {
                couponPrice = couponVo.getType_money();
            }
        }

        //订单价格计算
        BigDecimal orderTotalPrice = goodsTotalPrice.add(new BigDecimal(freightPrice)); //订单的总价

//        BigDecimal percentNum = new BigDecimal(0.2); //打八折 使用积分不能超过总价格的20%
        // 获取抵扣率参数
        CnnSysParamsVo deduction = cnnSysParamsService.queryObject(2);
        BigDecimal percentNum = deduction.getIncreaseparams();
        if (intergrals.compareTo(orderTotalPrice.multiply(percentNum))==1) {
            resultObj.put("errno", 1);
            resultObj.put("errmsg", "使用的积分券不能超过限定额度");
            return resultObj;
        }

        BigDecimal orderTotalPriceReally = orderTotalPrice.subtract(intergrals); //积分抵扣

//        BigDecimal actualPrice = orderTotalPrice.subtract(couponPrice);  //减去其它支付的金额后，要实际支付的金额
        BigDecimal actualPrice = orderTotalPriceReally.subtract(couponPrice);  //减去其它支付的金额后，要实际支付的金额


        Long currentTime = System.currentTimeMillis() / 1000;

        //
        OrderVo orderInfo = new OrderVo();
        orderInfo.setOrder_sn(CommonUtil.generateOrderNumber());
        orderInfo.setUser_id(loginUser.getUserId());
        //收货地址和运费
        orderInfo.setConsignee(addressVo.getUserName());
        orderInfo.setMobile(addressVo.getTelNumber());
        orderInfo.setCountry(addressVo.getNationalCode());
        orderInfo.setProvince(addressVo.getProvinceName());
        orderInfo.setCity(addressVo.getCityName());
        orderInfo.setDistrict(addressVo.getCountyName());
        orderInfo.setAddress(addressVo.getDetailInfo());
        //
        orderInfo.setFreight_price(freightPrice);
        //留言
        orderInfo.setPostscript(postscript);
        //使用的优惠券
        orderInfo.setCoupon_id(couponId);
        orderInfo.setCoupon_price(couponPrice);
        orderInfo.setAdd_time(new Date());
        orderInfo.setGoods_price(goodsTotalPrice);
        orderInfo.setOrder_price(orderTotalPrice);
        orderInfo.setActual_price(actualPrice);
        // 待付款
        orderInfo.setOrder_status(0);
        orderInfo.setShipping_status(0);
        orderInfo.setPay_status(0);
        orderInfo.setShipping_id(0);
        orderInfo.setShipping_fee(new BigDecimal(0));
        orderInfo.setIntegral(0);
        orderInfo.setIntegral_money(intergrals); //使用的积分
        if (type.equals("cart")) {
            orderInfo.setOrder_type("1");
        } else {
            orderInfo.setOrder_type("4");
        }

        //开启事务，插入订单信息和订单商品
        apiOrderMapper.save(orderInfo);
        if (null == orderInfo.getId()) {
            resultObj.put("errno", 1);
            resultObj.put("errmsg", "订单提交失败");
            return resultObj;
        }
        //统计商品总价
        List<OrderGoodsVo> orderGoodsData = new ArrayList<OrderGoodsVo>();
        for (CartVo goodsItem : checkedGoodsList) {
            OrderGoodsVo orderGoodsVo = new OrderGoodsVo();
            orderGoodsVo.setOrder_id(orderInfo.getId());
            orderGoodsVo.setGoods_id(goodsItem.getGoods_id());
            orderGoodsVo.setGoods_sn(goodsItem.getGoods_sn());
            orderGoodsVo.setProduct_id(goodsItem.getProduct_id());
            orderGoodsVo.setGoods_name(goodsItem.getGoods_name());
            orderGoodsVo.setList_pic_url(goodsItem.getList_pic_url());
            orderGoodsVo.setMarket_price(goodsItem.getMarket_price());
            orderGoodsVo.setRetail_price(goodsItem.getRetail_price());
            orderGoodsVo.setNumber(goodsItem.getNumber());


            String specIds = goodsItem.getGoods_specification_ids();
            String[] specIdsArr = specIds.split("_");
            String lastSpecStr="";
            for (String idStr : specIdsArr) {
                GoodsSpecificationVo specObj = apiGoodsSpecificationService.queryObject(Integer.valueOf(idStr));
                lastSpecStr = lastSpecStr + specObj.getValue()+",";
            }
            orderGoodsVo.setGoods_specifition_name_value(lastSpecStr);

            orderGoodsVo.setGoods_specifition_ids(goodsItem.getGoods_specification_ids());
            orderGoodsData.add(orderGoodsVo);
            apiOrderGoodsMapper.save(orderGoodsVo);
        }

        //清空已购买的商品
        apiCartMapper.deleteByCart(loginUser.getUserId(), 1, 1);
        resultObj.put("errno", 0);
        resultObj.put("errmsg", "订单提交成功");
        //
        Map<String, OrderVo> orderInfoMap = new HashMap<String, OrderVo>();
        orderInfoMap.put("orderInfo", orderInfo);
        //
        resultObj.put("data", orderInfoMap);
        // 优惠券标记已用
        if (couponVo != null && couponVo.getCoupon_status() == 1) {
            couponVo.setCoupon_status(2);
            apiCouponMapper.updateUserCoupon(couponVo);
        }

        // 订单提交成功给管理员发送模板消息

        // ----------------发送模板消息
        String templateId = "6BzUy8qLwuWJrX90R3telLkIzkxII9XbQUS7IZmipYs"; // 新订单提醒

        // 获取 formID
        CnnUserFormidEntity cnnUserFormidEntity = cnnUserFormidService.queryObjectByUserid(34); //管理员34
        Integer formIDId = cnnUserFormidEntity.getId();
        UserVo adminUser = userService.queryObject(new Long(34));
        Map<String, Object> orderParams = new HashMap<String, Object>();
        String templateUrl = "pages/index/index";
        String page = "pages/index/index";
        String topcolor = "#ff6600";

        String openid = adminUser.getWeixin_openid();


        orderParams.put("page",page);

        String formId = cnnUserFormidEntity.getFormid();

        String productName = "";
        Integer goodNumbers =0;
        for (OrderGoodsVo goodsItem : orderGoodsData) {
             productName = productName + goodsItem.getGoods_name()+",";
             goodNumbers = goodNumbers + goodsItem.getNumber();
        }

        String payTime = "无";
        if(null!=orderInfo.getPay_time()){
             payTime = orderInfo.getPay_time().toString();
        }
        Integer orderStatusNum = orderInfo.getOrder_status();
        String orderStatus="";
        switch(orderStatusNum){
            case 0:
                orderStatus="未发货";
                break;
            case 1:
                orderStatus="已发货";
                break;
            case 2:
                orderStatus="已收货";
                break;
            case 4:
                orderStatus="退货";
                break;
           default:
                break;
        }

        Integer payStatusNum = orderInfo.getPay_status();
        String payStatus="";
        switch(payStatusNum){
            case 0:
                payStatus="未付款";
                break;
            case 1:
                payStatus="付款中";
                break;
            case 2:
                payStatus="已付款";
                break;
            case 4:
                payStatus="退款";
                break;
            default:
                break;
        }
        String receiver = orderInfo.getConsignee();//收件人
        String username = adminUser.getUsername();//用户名
        String orderTime = orderInfo.getAdd_time().toString();//下单时间
        String address = orderInfo.getProvince()+orderInfo.getCity()+orderInfo.getDistrict()+orderInfo.getAddress()+"";
        String orderMoney = orderInfo.getOrder_price().toString();
        String productNumber = goodNumbers.toString();
      String jsonObj = WechatUtil.makeNewOrderTemplateMessage(openid,templateId,page,formId,templateUrl,topcolor,productName,payTime,orderStatus,payStatus,receiver,username,orderTime,address,orderMoney,productNumber);
//        String jsonObj = WechatUtil.makeNewOrderTemplateMessage(orderParams);
        // 发送消息
        AccessTokenEntity accessTokenEntity = accessTokenService.queryByFirst();
        Boolean sendSuccess = WechatUtil.sendTemplateMessage(accessTokenEntity.getAccessToken(),jsonObj);
        // 如果发送成功就删除用过的formId
        if(sendSuccess) {
            if(sendSuccess) {
                cnnUserFormidService.delete(formIDId);
            }
        }
        // 订单提交成功给管理员发送模板消息-end

        return resultObj;
    }

}
