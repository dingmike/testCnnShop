package com.platform.api;

import com.alibaba.fastjson.JSONObject;
import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.entity.OrderGoodsVo;
import com.platform.entity.OrderVo;
import com.platform.entity.UserVo;
import com.platform.service.ApiKdniaoService;
import com.platform.service.ApiOrderGoodsService;
import com.platform.service.ApiOrderService;
import com.platform.util.ApiBaseAction;
import com.platform.util.ApiPageUtils;
import com.platform.util.wechat.WechatRefundApiResult;
import com.platform.util.wechat.WechatUtil;
import com.platform.utils.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: @author admin <br>
 * 时间: 2017-08-11 08:32<br>
 * 描述: ApiIndexController <br>
 */
@Api(tags = "订单相关")
@RestController
@RequestMapping("/api/order")
public class ApiOrderController extends ApiBaseAction {
    @Autowired
    private ApiOrderService orderService;
    @Autowired
    private ApiOrderGoodsService orderGoodsService;
    @Autowired
    private ApiKdniaoService apiKdniaoService;

    /**
     */
    @ApiOperation(value = "订单首页")
    @IgnoreAuth
//    @RequestMapping("index")
    @GetMapping("index")
    public Object index(@LoginUser UserVo loginUser) {
        //
        return toResponsSuccess("");
    }

    /**
     * 获取订单列表
     */
    @ApiOperation(value = "获取订单列表")
//    @RequestMapping("list")
    @GetMapping("list")
    public Object list(@LoginUser UserVo loginUser,
                       @RequestParam(value = "orderStatus", defaultValue = "") Integer orderStatus,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        //
        Map params = new HashMap();
        params.put("user_id", loginUser.getUserId());
        params.put("order_status", orderStatus); // 订单状态查询
        params.put("page", page);
        params.put("limit", size);
        params.put("sidx", "add_time"); // 按添加时间倒序
        params.put("order", "desc");  // asc正序 desc倒序
        //查询列表数据
        Query query = new Query(params);
        List<OrderVo> orderEntityList = orderService.queryList(query);
        int total = orderService.queryTotal(query);
        ApiPageUtils pageUtil = new ApiPageUtils(orderEntityList, total, query.getLimit(), query.getPage());
        //
        for (OrderVo item : orderEntityList) {
            Map orderGoodsParam = new HashMap();
            orderGoodsParam.put("order_id", item.getId());
            //订单的商品
            List<OrderGoodsVo> goodsList = orderGoodsService.queryList(orderGoodsParam);
            Integer goodsCount = 0;
            for (OrderGoodsVo orderGoodsEntity : goodsList) {
                goodsCount += orderGoodsEntity.getNumber();
                item.setGoodsCount(goodsCount);
            }
            item.setGoodsVos(goodsList);
        }
        return toResponsSuccess(pageUtil);
    }

    /**
     * 获取订单详情
     */
    @ApiOperation(value = "获取订单详情")
//    @RequestMapping("detail")
    @GetMapping("detail")
    public Object detail(@LoginUser UserVo loginUser, Integer orderId) {
        Map resultObj = new HashMap();
        //
        OrderVo orderInfo = orderService.queryObject(orderId);
        if (null == orderInfo) {
            return toResponsObject(400, "订单不存在", "");
        }
        Map orderGoodsParam = new HashMap();
        orderGoodsParam.put("order_id", orderId);
        //订单的商品
        List<OrderGoodsVo> orderGoods = orderGoodsService.queryList(orderGoodsParam);
        //订单最后支付时间
        if (orderInfo.getOrder_status() == 0) {
            Date addTime = orderInfo.getAdd_time();
            if((new Date().getTime()-addTime.getTime())>30 * 60 * 1000){
                //超过时间不支付，更新订单状态为取消
                orderInfo.setOrder_status(101);
                orderService.update(orderInfo);
            }
        }

        //订单可操作的选择,删除，支付，收货，评论，退换货
        Map handleOption = orderInfo.getHandleOption();
        //
        resultObj.put("orderInfo", orderInfo);
        resultObj.put("orderGoods", orderGoods);
        resultObj.put("handleOption", handleOption);
        if (!StringUtils.isEmpty(orderInfo.getShipping_code()) && !StringUtils.isEmpty(orderInfo.getShipping_no())) {
            // 快递
            List Traces = apiKdniaoService.getOrderTracesByJson(orderInfo.getShipping_code(), orderInfo.getShipping_no());
            resultObj.put("shippingList", Traces);
        }
        return toResponsSuccess(resultObj);
    }

    @ApiOperation(value = "修改订单")
//    @RequestMapping("updateSuccess")
    @PostMapping("updateSuccess")
    public Object updateSuccess(@LoginUser UserVo loginUser, Integer orderId) {
        OrderVo orderInfo = orderService.queryObject(orderId);
        if (orderInfo==null) {
            return toResponsFail("订单不存在");
        } else if (orderInfo.getOrder_status()!=0) {
            return toResponsFail("订单状态不正确orderStatus" + orderInfo.getOrder_status() + "payStatus" + orderInfo.getPay_status());
        }

        orderInfo.setId(orderId);
        orderInfo.setPay_status(2);
        orderInfo.setOrder_status(201);
        orderInfo.setShipping_status(0);
        orderInfo.setPay_time(new Date());
        int num = orderService.update(orderInfo);
        if (num>0) {
            return toResponsMsgSuccess("修改订单成功");
        } else {
            return toResponsFail("修改订单失败");
        }
    }

    /**
     * 提交
     */
    @ApiOperation(value = "订单提交")
//    @RequestMapping("submit")
    @PostMapping("submit")
    public Object submit(@LoginUser UserVo loginUser) {
        Map resultObj;
        JSONObject jsonParams = getJsonRequest();
        try {

            // 获取用户输入的积分1积分抵扣1元，且不能超过用户拥有的总积分
            BigDecimal totalIntergrals = loginUser.getIntergral();
            BigDecimal intergrals = jsonParams.getBigDecimal("intergrals");
            if(intergrals.compareTo(totalIntergrals)==1){ // intergrals>totalInterfrals
                return toResponsFail("积分不够了");
            }

            resultObj = orderService.submit(jsonParams, loginUser);
            if (null != resultObj) {
                return toResponsObject(MapUtils.getInteger(resultObj, "errno"), MapUtils.getString(resultObj, "errmsg"), resultObj.get("data"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toResponsFail("提交失败");
    }

    /**
     * 取消订单
     */
    @ApiOperation(value = "取消订单")
    @GetMapping("cancelOrder")
//    @RequestMapping("cancelOrder")
    public Object cancelOrder(@LoginUser UserVo loginUser, Integer orderId) {
        try {
            OrderVo orderVo = orderService.queryObject(orderId);
            if (orderVo.getOrder_status() == 300) {
                return toResponsFail("已发货，不能取消");
            } else if (orderVo.getOrder_status() == 301) {
                return toResponsFail("已收货，不能取消");
            }
            // 需要退款
            if (orderVo.getPay_status() == 2) {
                WechatRefundApiResult result = WechatUtil.wxRefund(orderVo.getId().toString(),
                        0.01, 0.01);
                if (result.getResult_code().equals("SUCCESS")) {
                    if (orderVo.getOrder_status() == 201) {
                        orderVo.setOrder_status(401);
                    } else if (orderVo.getOrder_status() == 300) {
                        orderVo.setOrder_status(402);
                    }
                    orderVo.setPay_status(4);
                    orderService.update(orderVo);
                    return toResponsMsgSuccess("取消成功");
                } else {
                    return toResponsObject(400, "取消成失败", "");
                }
            } else {
                orderVo.setOrder_status(101);
                orderService.update(orderVo);
                return toResponsSuccess("取消成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toResponsFail("提交失败");
    }

    /**
     * 确认收货
     */
    @ApiOperation(value = "确认收货")
    @GetMapping("confirmOrder")
//    @RequestMapping("confirmOrder")
    public Object confirmOrder(Integer orderId) {
        try {
            OrderVo orderVo = orderService.queryObject(orderId);
            orderVo.setOrder_status(301);
            orderVo.setShipping_status(2);
            orderVo.setConfirm_time(new Date());
            orderService.update(orderVo);
            return toResponsSuccess("收货成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toResponsFail("提交失败");
    }
}