package com.platform.api;

import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.cache.J2CacheUtils;
import com.platform.entity.*;
import com.platform.entity.wxPayReq.UnifiedOrderParams;
import com.platform.entity.wxPayResp.UnifiedOrderResult;
import com.platform.service.*;
import com.platform.util.ApiBaseAction;
import com.platform.util.CommonUtil;
import com.platform.util.wechat.WechatRefundApiResult;
import com.platform.util.wechat.WechatUtil;
import com.platform.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

//new

import com.platform.util.otherWxUtil.DateTimeUtil;
import com.platform.util.otherWxUtil.HttpReqUtil;
import com.platform.util.otherWxUtil.MsgUtil;
import com.platform.util.otherWxUtil.PayUtil;
import com.platform.util.otherWxUtil.SignatureUtil;
//import com.platform.util.otherWxUtil.XmlUtil;
//import com.platform.utils.XmlUtil;
import com.platform.config.WechatConfig;
import com.platform.constant.SystemConstant;
import com.platform.constant.PayConstant;
import com.platform.entity.wxPayResp.JsPayResult;
import com.platform.entity.wxPayResp.PayShortUrlResult;
import com.platform.entity.wxPayReq.PayShortUrlParams;
/**
 * 作者: @author admin <br>
 * 时间: 2018-06-11 08:32<br>
 * 描述: ApiIndexController <br>
 */
@Api(tags = "商户支付")
@RestController
@RequestMapping("/api/pay")
public class ApiPayController extends ApiBaseAction {
    private Logger logger = Logger.getLogger(getClass());
    @Autowired
    private ApiOrderService orderService;
    @Autowired
    private ApiOrderGoodsService orderGoodsService;
    @Autowired
    private ApiCnnLearnTypeService cnnLearnTypeService;
    @Autowired
    private ApiGongduOrderService apiGongduOrderService;
    @Autowired
    private ApiUserLearnService apiUserLearnService;

    @Autowired
    private ApiUserService apiUserService;
    @Autowired
    private ApiUserIntergralLogService userIntergralLogService;

    /**
     * 跳转
     */
    @ApiOperation(value = "跳转")
    @GetMapping("index")
    public Object index() {
        //
        return toResponsSuccess("");
    }

    /**
     * 获取支付的请求参数
     */
    @ApiOperation(value = "获取支付的请求参数")
    @GetMapping("prepay")
//    @RequestMapping("prepay")
    public Object payPrepay(@LoginUser UserVo loginUser, Integer orderId, Integer repay) {
        //
        OrderVo orderInfo = orderService.queryObject(orderId);

        if (null == orderInfo) {
            return toResponsObject(400, "订单已取消", "");
        }


        if (orderInfo.getPay_status() ==2) {
            return toResponsObject(400, "订单已支付，请不要重复操作", "");
        }


        // 没有完成支付的订单继续支付，刷新订单编号
        if(repay==1&&orderInfo.getOrder_status()==0){
            orderInfo.setOrder_sn(CommonUtil.generateOrderNumber());
        }else{
            if (orderInfo.getPay_status() ==1) {
                return toResponsObject(400, "订单支付中，请不要重复操作", "");
            }
        }

        String  order_sn = orderInfo.getOrder_sn();

        String nonceStr = CharUtil.getRandomString(32);

        //https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=3
        Map<Object, Object> resultObj = new TreeMap();

        try {
            Map<Object, Object> parame = new TreeMap<Object, Object>();
            parame.put("appid", ResourceUtil.getConfigByName("wx.appId"));
            // 商家账号。
            parame.put("mch_id", ResourceUtil.getConfigByName("wx.mchId"));
            String randomStr = CharUtil.getRandomNum(18).toUpperCase();
            // 随机字符串
            parame.put("nonce_str", randomStr);
            // 商户订单编号
//            parame.put("out_trade_no", orderId);
            parame.put("out_trade_no", order_sn);
            Map orderGoodsParam = new HashMap();
            orderGoodsParam.put("order_id", orderId);
            // 商品描述
            parame.put("body", "商品-支付");
            //订单的商品
            List<OrderGoodsVo> orderGoods = orderGoodsService.queryList(orderGoodsParam);
            if (null != orderGoods) {
                String body = "商品-";
                for (OrderGoodsVo goodsVo : orderGoods) {
                    body = body + goodsVo.getGoods_name() + "、";
                }
                if (body.length() > 0) {
                    body = body.substring(0, body.length() - 1);
                }
                // 商品描述
                parame.put("body", body);
            }
            //支付金额
            parame.put("total_fee", orderInfo.getActual_price().multiply(new BigDecimal(100)).intValue());
            // 回调地址
            parame.put("notify_url", ResourceUtil.getConfigByName("wx.notifyUrl"));
            // 交易类型APP
            parame.put("trade_type", ResourceUtil.getConfigByName("wx.tradeType"));
            parame.put("spbill_create_ip", getClientIp());
            parame.put("openid", loginUser.getWeixin_openid());
            String sign = WechatUtil.arraySign(parame, ResourceUtil.getConfigByName("wx.paySignKey"));
            // 数字签证
            parame.put("sign", sign);

            String xml = MapUtils.convertMap2Xml(parame);
            logger.info("xml:" + xml);
            Map<String, Object> resultUn = XmlUtil.xmlStrToMap(WechatUtil.requestOnce(ResourceUtil.getConfigByName("wx.uniformorder"), xml));
            // 响应报文
            String return_code = MapUtils.getString("return_code", resultUn);
            String return_msg = MapUtils.getString("return_msg", resultUn);
            //
            if (return_code.equalsIgnoreCase("FAIL")) {
                return toResponsFail("支付失败," + return_msg);
            } else if (return_code.equalsIgnoreCase("SUCCESS")) {
                // 返回数据
                String result_code = MapUtils.getString("result_code", resultUn);
                String err_code_des = MapUtils.getString("err_code_des", resultUn);
                if (result_code.equalsIgnoreCase("FAIL")) {
                    return toResponsFail("支付失败," + err_code_des);
                } else if (result_code.equalsIgnoreCase("SUCCESS")) {
                    String prepay_id = MapUtils.getString("prepay_id", resultUn);
                    // 先生成paySign 参考https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=5
                    resultObj.put("appId", ResourceUtil.getConfigByName("wx.appId"));
                    resultObj.put("timeStamp", DateUtils.timeToStr(System.currentTimeMillis() / 1000, DateUtils.DATE_TIME_PATTERN));
                    resultObj.put("nonceStr", nonceStr);
                    resultObj.put("package", "prepay_id=" + prepay_id);
                    resultObj.put("signType", "MD5");
                    String paySign = WechatUtil.arraySign(resultObj, ResourceUtil.getConfigByName("wx.paySignKey"));
                    resultObj.put("paySign", paySign);
                    // 业务处理
                    orderInfo.setPay_id(prepay_id);
                    // 付款中
                    orderInfo.setPay_status(1);
                    orderService.update(orderInfo);
                    return toResponsObject(0, "微信统一订单下单成功", resultObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return toResponsFail("下单失败,error=" + e.getMessage());
        }
        return toResponsFail("下单失败");
    }

    /**
     * 微信查询订单状态
     */
    @ApiOperation(value = "查询订单状态")
    @GetMapping("query")
//    @RequestMapping("query")
    public Object orderQuery(@LoginUser UserVo loginUser, Integer orderId) {

        OrderVo orderInfo = orderService.queryObject(orderId);
        String  order_sn = orderInfo.getOrder_sn();
        if (orderId == null) {
            return toResponsFail("订单不存在");
        }

        Map<Object, Object> parame = new TreeMap<Object, Object>();
        parame.put("appid", ResourceUtil.getConfigByName("wx.appId"));
        // 商家账号。
        parame.put("mch_id", ResourceUtil.getConfigByName("wx.mchId"));
        String randomStr = CharUtil.getRandomNum(18).toUpperCase();
        // 随机字符串
        parame.put("nonce_str", randomStr);
        // 商户订单编号
//        parame.put("out_trade_no", orderId);
        parame.put("out_trade_no", order_sn);

        String sign = WechatUtil.arraySign(parame, ResourceUtil.getConfigByName("wx.paySignKey"));
        // 数字签证
        parame.put("sign", sign);

        String xml = MapUtils.convertMap2Xml(parame);
        logger.info("xml:" + xml);
        Map<String, Object> resultUn;
        try {
            resultUn = XmlUtil.xmlStrToMap(WechatUtil.requestOnce(ResourceUtil.getConfigByName("wx.orderquery"), xml));
        } catch (Exception e) {
            e.printStackTrace();
            return toResponsFail("查询失败,error=" + e.getMessage());
        }
        // 响应报文
        String return_code = MapUtils.getString("return_code", resultUn);
        String return_msg = MapUtils.getString("return_msg", resultUn);

        if (return_code.equals("SUCCESS")) {
            String trade_state = MapUtils.getString("trade_state", resultUn);
            if (trade_state.equals("SUCCESS")) {
                // 更改订单状态 线下测试使用
                // 业务处理



               /* OrderVo orderInfo2 = new OrderVo();
                orderInfo2.setId(orderId);
                orderInfo2.setPay_status(2);
                orderInfo2.setOrder_status(201);
                orderInfo2.setShipping_status(0);
                orderInfo2.setPay_time(new Date());
                orderService.update(orderInfo2);
                // 支付成功扣除用户积分
                //  支付成功减去使用的能力券
                UserIntergralLogVo userIntergralLogVo = new UserIntergralLogVo();
                Long userId=  loginUser.getUserId();
                String username = loginUser.getUsername();
                String nickname = loginUser.getNickname();
                userIntergralLogVo.setUserid(userId.intValue());
//                userIntergralLogVo.setLearnTypeId(2);
                userIntergralLogVo.setNickname(nickname);
                userIntergralLogVo.setUsername(username);
//                userIntergralLogVo.setPlusMins(1); // 1加 0减
                userIntergralLogVo.setMemo("兑换商品使用");
                BigDecimal usedIntegral = orderInfo.getIntegral_money();
                userIntergralLogService.save(userIntergralLogVo, loginUser, 0, usedIntegral); //0：减*/




//                BigDecimal latestIntergral = (loginUser.getIntergral()).subtract(orderInfo.getIntegral_money());
//                UserVo userNew= new UserVo();
//                userNew.setUserId(loginUser.getUserId());
//                userNew.setIntergral(latestIntergral);
//                apiUserService.update(userNew);

                return toResponsMsgSuccess("支付成功");
            } else if (trade_state.equals("USERPAYING")) {
                // 重新查询 正在支付中
//                Integer num = (Integer) J2CacheUtils.get("queryRepeatNum" + orderId + "");
                Integer num = (Integer) J2CacheUtils.get(J2CacheUtils.SHOP_CACHE_NAME, "queryRepeatNum" + orderId + "");
                if (num == null) {
//                    J2CacheUtils.put("queryRepeatNum" + orderId + "", 1);
                    J2CacheUtils.put(J2CacheUtils.SHOP_CACHE_NAME, "queryRepeatNum" + orderId + "", 1);
                    this.orderQuery(loginUser, orderId);
                } else if (num <= 3) {
//                    J2CacheUtils.remove("queryRepeatNum" + orderId);
                    J2CacheUtils.remove(J2CacheUtils.SHOP_CACHE_NAME, "queryRepeatNum" + orderId);
                    this.orderQuery(loginUser, orderId);
                } else {
                    return toResponsFail("查询失败,error=" + trade_state);
                }

            } else {
                // 失败
                return toResponsFail("查询失败,error=" + trade_state);
            }
        } else {
            return toResponsFail("查询失败,error=" + return_msg);
        }
        return toResponsFail("查询失败，未知错误");
    }

    /**
     * 微信订单回调接口
     *
     * @return
     */
    @ApiOperation(value = "微信订单回调接口")
    @IgnoreAuth
    @RequestMapping(value = "/notify", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void notify(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            InputStream in = request.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.close();
            in.close();
            //xml数据
            String reponseXml = new String(out.toByteArray(), "utf-8");

            WechatRefundApiResult result = (WechatRefundApiResult) XmlUtil.xmlStrToBean(reponseXml, WechatRefundApiResult.class);
            String result_code = result.getResult_code();
            if (result_code.equalsIgnoreCase("FAIL")) {
                //订单编号
                String out_trade_no = result.getOut_trade_no();
                logger.error("订单" + out_trade_no + "支付失败");
                response.getWriter().write(setXml("SUCCESS", "OK"));
            } else if (result_code.equalsIgnoreCase("SUCCESS")) {
                //订单编号
                String out_trade_no = result.getOut_trade_no();
                logger.info("订单号：" + out_trade_no + "支付成功");
                System.out.println("订单sss:" + out_trade_no + "支付成功");
                // 业务处理
//                OrderVo orderInfo = orderService.queryObject(Integer.valueOf(out_trade_no));
                OrderVo orderInfo = orderService.queryObjectByOrderSn(out_trade_no);


                //如果订单已支付重复回调
                if(orderInfo.getPay_status()==2){// 已支付且回调成功了的
//                XMLUtil.setXml
                  response.getWriter().write(setXml("SUCCESS", "OK"));
                }else{//第一次回调来了
                    // 修改能力券
                    Long userId = orderInfo.getUser_id();
                    UserVo loginUser = apiUserService.queryObject(userId);
                    UserIntergralLogVo userIntergralLogVo = new UserIntergralLogVo();
                    String username = loginUser.getUsername();
                    String nickname = loginUser.getNickname();
                    userIntergralLogVo.setUserid(userId.intValue());
//                userIntergralLogVo.setLearnTypeId(2);
                    userIntergralLogVo.setNickname(nickname);
                    userIntergralLogVo.setUsername(username);
                    userIntergralLogVo.setOrder_sn(out_trade_no);//
//                userIntergralLogVo.setPlusMins(1); // 1加 0减
                    userIntergralLogVo.setMemo("兑换商品使用");

                    //  支付成功减去使用的能力券
                    BigDecimal usedIntegral = orderInfo.getIntegral_money();
                    userIntergralLogService.save(userIntergralLogVo, loginUser, 0, usedIntegral); //0：减

                    orderInfo.setPay_status(2); //已付款
                    orderInfo.setOrder_status(201); //订单已付款待发货
                    orderInfo.setShipping_status(0); //未发货
                    orderInfo.setPay_time(new Date());
                    orderService.update(orderInfo);
//                XMLUtil.setXml
                    System.out.println(setXml("SUCCESS", "OK"));
                    response.getWriter().write(setXml("SUCCESS", "OK"));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * 订单退款请求
     */
    @ApiOperation(value = "订单退款请求")
    @PostMapping("refund")
//    @RequestMapping("refund")
    public Object refund(Integer orderId) {
        //
        OrderVo orderInfo = orderService.queryObject(orderId);

        if (null == orderInfo) {
            return toResponsObject(400, "订单已取消", "");
        }

        if (orderInfo.getOrder_status() == 401 || orderInfo.getOrder_status() == 402) {
            return toResponsObject(400, "订单已退款", "");
        }

//        if (orderInfo.getPay_status() != 2) {
//            return toResponsObject(400, "订单未付款，不能退款", "");
//        }

//        WechatRefundApiResult result = WechatUtil.wxRefund(orderInfo.getId().toString(),
//                orderInfo.getActual_price().doubleValue(), orderInfo.getActual_price().doubleValue());
        WechatRefundApiResult result = WechatUtil.wxRefund(orderInfo.getId().toString(),
                0.00, 0.00);
        if (result.getResult_code().equals("SUCCESS")) {
            if (orderInfo.getOrder_status() == 201) {
                orderInfo.setOrder_status(401);
            } else if (orderInfo.getOrder_status() == 300) {
                orderInfo.setOrder_status(402);
            }
            orderService.update(orderInfo);
            return toResponsObject(400, "成功退款", "");
        } else {
            return toResponsObject(400, "退款失败", "");
        }
    }


    //返回微信服务
    public static String setXml(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
    }

    //模拟微信回调接口
    public static String callbakcXml(String orderNum) {
        return "<xml><appid><![CDATA[wx2421b1c4370ec43b]]></appid><attach><![CDATA[支付测试]]></attach><bank_type><![CDATA[CFT]]></bank_type><fee_type><![CDATA[CNY]]></fee_type> <is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[10000100]]></mch_id><nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str><openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid> <out_trade_no><![CDATA[" + orderNum + "]]></out_trade_no>  <result_code><![CDATA[SUCCESS]]></result_code> <return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign><sub_mch_id><![CDATA[10000100]]></sub_mch_id> <time_end><![CDATA[20140903131540]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id></xml>";
    }

    /**
     * 其他种类的微信支付---------------------------------------------------------------------------------------------------------
     *
     */
    @GetMapping("/payhtml")
    public String oauthBind(Map<String, Object> data) throws Exception {
        UnifiedOrderParams params = new UnifiedOrderParams();
        data.put("params", params);
        return "wechat/pay/h5pay";
    }

    /**
     * 微信内H5调起支付
     *
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping("jspay")
    public Map<String, Object> jsPay(@LoginUser UserVo loginUser, @ModelAttribute(value = "params") UnifiedOrderParams params) {
        Map<String, Object> data = new HashMap<>();
        JsPayResult result = null;
        if (org.springframework.util.StringUtils.isEmpty(params) || org.springframework.util.StringUtils.isEmpty(params.getOpenid())) {
            data.put("code", -1);
            data.put("msg", "支付数据错误");
            return data;
        }
        String openId = loginUser.getWeixin_openid();
        logger.debug("****正在支付的openId****: "+ openId);
        // 统一下单
        String out_trade_no = PayUtil.createOutTradeNo();
        // int total_fee = 1; // 产品价格1分钱,用于测试
//        String spbill_create_ip = HttpReqUtil.getRemortIP(this.getRequest());
        String spbill_create_ip = getClientIp();
        logger.debug("支付客户端IP:"+ spbill_create_ip);

        String nonce_str = PayUtil.createNonceStr(); // 随机数据
        // 参数组装
        UnifiedOrderParams unifiedOrderParams = new UnifiedOrderParams();
        unifiedOrderParams.setAppid(WechatConfig.APP_ID);// 必须
        unifiedOrderParams.setMch_id(PayConstant.MCH_ID);// 必须
        unifiedOrderParams.setOut_trade_no(out_trade_no);// 必须
        unifiedOrderParams.setBody(params.getBody());// 必须 微信支付-支付测试
        unifiedOrderParams.setTotal_fee(params.getTotal_fee()); // 必须
        unifiedOrderParams.setNonce_str(nonce_str); // 必须
        unifiedOrderParams.setSpbill_create_ip(spbill_create_ip); // 必须
        unifiedOrderParams.setTrade_type("JSAPI"); // 必须
        unifiedOrderParams.setOpenid(params.getOpenid());
        unifiedOrderParams.setNotify_url(WechatConfig.NOTIFY_URL);// 异步通知url
        // 统一下单 请求的Xml(正常的xml格式)
        String unifiedXmL = MsgUtil.abstractPayToXml(unifiedOrderParams);// 签名并入util
        // 返回<![CDATA[SUCCESS]]>格式的XML
        String unifiedOrderResultXmL = HttpReqUtil.HttpsDefaultExecute(SystemConstant.POST_METHOD,
                WechatConfig.UNIFIED_ORDER_URL, null, unifiedXmL, null);
        // 进行签名校验
        try {
            if (SignatureUtil.checkIsSignValidFromWeiXin(unifiedOrderResultXmL, null, null)) {
                String timeStamp = PayUtil.createTimeStamp();
                // 统一下单响应
                UnifiedOrderResult unifiedOrderResult = XmlUtil.getObjectFromXML(unifiedOrderResultXmL,
                        UnifiedOrderResult.class);
                result = new JsPayResult();
                result.setAppId(WechatConfig.APP_ID);
                result.setTimeStamp(timeStamp);
                result.setNonceStr(unifiedOrderResult.getNonce_str());// 直接用返回的
                /**** prepay_id 2小时内都有效，再次支付方法自己重写 ****/
                result.setPackageStr("prepay_id=" + unifiedOrderResult.getPrepay_id());
                /**** 用对象进行签名 ****/
                String paySign = SignatureUtil.createSign(result, PayConstant.API_KEY,
                        SystemConstant.DEFAULT_CHARACTER_ENCODING);
                result.setPaySign(paySign);
                result.setResultCode(unifiedOrderResult.getResult_code());
                data.put("code", 0);
                data.put("msg", "支付成功");
                data.put("data", result);
            } else {
                data.put("code", -1);
                data.put("msg", "支付签名验证错误");
                logger.debug("签名验证错误");
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            data.put("code", -1);
            data.put("msg", "支付失败");
            logger.debug(e.getMessage());
        }
        return data;
    }

    /**
     * 扫码支付模式一生成二维码
     *
     * @param productId 商品ID
     * @throws IOException
     */
    @GetMapping("payone")
    public Map<String, Object> payone(String productId) {
        Map<String, Object> data = new HashMap<>();
        String nonce_str = PayUtil.createNonceStr();
        // String product_id = "product_001"; // 推荐根据商品ID生成
        TreeMap<String, Object> packageParams = new TreeMap<>();
        packageParams.put("appid", WechatConfig.APP_ID);
        packageParams.put("mch_id", PayConstant.MCH_ID);
        packageParams.put("product_id", productId);
        packageParams.put("time_stamp", PayUtil.createTimeStamp());
        packageParams.put("nonce_str", nonce_str);
        String str_url = PayUtil.createPayImageUrl(packageParams);
        String sign = SignatureUtil.createSign(packageParams, PayConstant.API_KEY,
                SystemConstant.DEFAULT_CHARACTER_ENCODING);
        packageParams.put("sign", sign);
        String payurl = "weixin://wxpay/bizpayurl?sign=" + sign + str_url;
        logger.debug("payurl is" + payurl);
        /**** 转成短链接 ****/
        PayShortUrlParams payShortUrlParams = new PayShortUrlParams();
        payShortUrlParams.setAppid(WechatConfig.APP_ID);
        payShortUrlParams.setMch_id(PayConstant.MCH_ID);
        payShortUrlParams.setLong_url(payurl);
        payShortUrlParams.setNonce_str(nonce_str);
        String urlSign = SignatureUtil.createSign(payShortUrlParams, PayConstant.API_KEY,
                SystemConstant.DEFAULT_CHARACTER_ENCODING);
        payShortUrlParams.setSign(urlSign);
        String longXml = XmlUtil.toSplitXml(payShortUrlParams);
        String shortResult = HttpReqUtil.HttpsDefaultExecute(SystemConstant.POST_METHOD, WechatConfig.PAY_SHORT_URL, null,
                longXml, null);
        PayShortUrlResult payShortUrlResult = XmlUtil.getObjectFromXML(shortResult, PayShortUrlResult.class);
        if (Objects.equals("SUCCESS", payShortUrlResult.getReturn_code())) {
            payurl = payShortUrlResult.getShort_url();
        } else {
            logger.debug("错误信息" + payShortUrlResult.getReturn_msg());
        }
        /**** 生成 二维码图片自行实现 ****/

        return data;
    }

    /**
     * 扫码模式二
     *
     * @param params
     * @throws Exception
     */
    @RequestMapping("paytwo")
    public Map<String, Object> paytwo(UnifiedOrderParams params) throws Exception {
        Map<String, Object> data = new HashMap<>();
        // 商户后台系统根据用户选购的商品生成订单。
        String out_trade_no = PayUtil.createNonceStr();
        String product_id = DateTimeUtil.getCurrTime();// 根据自己的逻辑生成
        // int total_fee = 1; // 1分作测试
        // String attach = "支付测试";
        // String body = "微信支付-支付测试";
        String nonce_str = PayUtil.createNonceStr();
//        String spbill_create_ip = HttpReqUtil.getRemortIP(this.getRequest()); // 获取IP
        String spbill_create_ip = getClientIp(); // 获取IP

        UnifiedOrderParams unifiedOrderParams = new UnifiedOrderParams();
        unifiedOrderParams.setAppid(WechatConfig.APP_ID);// 必须
        unifiedOrderParams.setMch_id(PayConstant.MCH_ID);// 必须
        unifiedOrderParams.setOut_trade_no(out_trade_no);
        unifiedOrderParams.setBody(params.getBody());
        unifiedOrderParams.setAttach(params.getAttach());
        unifiedOrderParams.setProduct_id(product_id);// 必须
        unifiedOrderParams.setTotal_fee(params.getTotal_fee());// 必须
        unifiedOrderParams.setNonce_str(nonce_str); // 必须
        unifiedOrderParams.setSpbill_create_ip(spbill_create_ip); // 必须
        unifiedOrderParams.setTrade_type("NATIVE");// 必须
        unifiedOrderParams.setNotify_url(WechatConfig.NOTIFY_URL); // 异步通知URL
        // 统一下单 请求的Xml(除detail外不需要<![CDATA[product_001]]>格式)
        String unifiedXmL = MsgUtil.abstractPayToXml(unifiedOrderParams); // 签名并入util
        // logger.debug("统一下单 请求的Xml"+unifiedXmL);
        // 统一下单 返回的xml(<![CDATA[product_001]]>格式)
        String unifiedResultXmL = HttpReqUtil.HttpsDefaultExecute(SystemConstant.POST_METHOD,
                WechatConfig.UNIFIED_ORDER_URL, null, unifiedXmL, null);
        // logger.debug("统一下单 返回的xml("+unifiedResultXmL);
        // 统一下单返回 验证签名
        if (SignatureUtil.checkIsSignValidFromWeiXin(unifiedResultXmL, null, null)) {
            UnifiedOrderResult unifiedOrderResult = XmlUtil.getObjectFromXML(unifiedResultXmL,
                    UnifiedOrderResult.class);
            if (Objects.equals("SUCCESS", unifiedOrderResult.getReturn_code())
                    && Objects.equals("SUCCESS", unifiedOrderResult.getResult_code())) {
                /**** 根据unifiedOrderResult.getCode_url()生成有效期为2小时的二维码 ****/

                /**** 根据product_id再次支付方法自己写 ****/
            }
        } else {
            logger.debug("签名验证错误");
        }
        return data;
    }


    /**
     * 共读支付获取支付的请求参数
     */
    @RequestMapping("gongDuPrepay")
    public Object gongDuPrepay(@LoginUser UserVo loginUser, String orderId) {
        //查询学习类型价格
//        CnnLearnTypeVo cnnLearnTypeVo = cnnLearnTypeService.queryObject(learnTypeId);

        GongDuOrderVo gongDuOrderVo = apiGongduOrderService.queryObject(orderId);
        //用来支付的价格格式
        Integer productPrice  = gongDuOrderVo.getGoodsPrice().multiply(new BigDecimal(100)).intValue();

      //  OrderVo orderInfo = orderService.queryObject(orderId);

        String nonceStr = CharUtil.getRandomString(32);

        //https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=3
        Map<Object, Object> resultObj = new TreeMap();

        try {
            Map<Object, Object> parame = new TreeMap<Object, Object>();
            parame.put("appid", ResourceUtil.getConfigByName("wx.appId"));
            // 商家账号。
            parame.put("mch_id", ResourceUtil.getConfigByName("wx.mchId"));
            String randomStr = CharUtil.getRandomNum(18).toUpperCase();
            // 随机字符串
            parame.put("nonce_str", randomStr);
            // 商户订单编号
            parame.put("out_trade_no", orderId);

            // 商品描述
            parame.put("body", "服务-支付");

            //支付金额
            parame.put("total_fee", productPrice);
            // 回调地址
            parame.put("notify_url", ResourceUtil.getConfigByName("wx.gongDuNotifyUrl"));
            // 交易类型APP
            parame.put("trade_type", ResourceUtil.getConfigByName("wx.tradeType"));
            parame.put("spbill_create_ip", getClientIp());
            parame.put("openid", loginUser.getWeixin_openid());
            String sign = WechatUtil.arraySign(parame, ResourceUtil.getConfigByName("wx.paySignKey"));
            // 数字签证
            parame.put("sign", sign);

            String xml = MapUtils.convertMap2Xml(parame);
            logger.info("xml:" + xml);
            //  发起支付请求 只请求一次
            Map<String, Object> resultUn = XmlUtil.xmlStrToMap(WechatUtil.requestOnce(ResourceUtil.getConfigByName("wx.uniformorder"), xml));
            // 响应报文
            String return_code = MapUtils.getString("return_code", resultUn);
            String return_msg = MapUtils.getString("return_msg", resultUn);
            //
            if (return_code.equalsIgnoreCase("FAIL")) {
                return toResponsFail("支付失败," + return_msg);
            } else if (return_code.equalsIgnoreCase("SUCCESS")) {
                // 返回数据
                String result_code = MapUtils.getString("result_code", resultUn);
                String err_code_des = MapUtils.getString("err_code_des", resultUn);
                if (result_code.equalsIgnoreCase("FAIL")) {
                    return toResponsFail("支付失败," + err_code_des);
                } else if (result_code.equalsIgnoreCase("SUCCESS")) {
                    String prepay_id = MapUtils.getString("prepay_id", resultUn);
                    // 先生成paySign 参考https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=5
                    resultObj.put("appId", ResourceUtil.getConfigByName("wx.appId"));
                    resultObj.put("timeStamp", DateUtils.timeToStr(System.currentTimeMillis() / 1000, DateUtils.DATE_TIME_PATTERN));
                    resultObj.put("nonceStr", nonceStr);
                    resultObj.put("package", "prepay_id=" + prepay_id);
                    resultObj.put("signType", "MD5");
                    String paySign = WechatUtil.arraySign(resultObj, ResourceUtil.getConfigByName("wx.paySignKey"));
                    resultObj.put("paySign", paySign);
                    // 业务处理
                    gongDuOrderVo.setPayId(prepay_id);
                    // 付款中
                    gongDuOrderVo.setPayStatus(1);
                    apiGongduOrderService.update(gongDuOrderVo);
                    return toResponsObject(0, "微信统一订单下单成功", resultObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return toResponsFail("下单失败,error=" + e.getMessage());
        }
        return toResponsFail("下单失败");
    }



    /**
     * 共读微信订单回调接口
     *
     * @return
     */
    @RequestMapping(value = "/gongDuNotify", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @IgnoreAuth
    @ResponseBody
    public void gongDuNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            InputStream in = request.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.close();
            in.close();
            //xml数据
            String reponseXml = new String(out.toByteArray(), "utf-8");

            WechatRefundApiResult result = (WechatRefundApiResult) XmlUtil.xmlStrToBean(reponseXml, WechatRefundApiResult.class);
            String result_code = result.getResult_code();
            if (result_code.equalsIgnoreCase("FAIL")) {
                //订单编号
                String out_trade_no = result.getOut_trade_no();
                logger.error("订单" + out_trade_no + "支付失败");
                response.getWriter().write(setXml("SUCCESS", "OK"));
            } else if (result_code.equalsIgnoreCase("SUCCESS")) {
                //订单编号
                String out_trade_no = result.getOut_trade_no();
                logger.info("订单" + out_trade_no + "支付成功");
                System.out.println("共读订单: " + out_trade_no + "支付成功");
                // 业务处理
//                OrderVo orderInfo = orderService.queryObject(Integer.valueOf(out_trade_no));
             /*   GongDuOrderVo gongDuOrderVo = apiGongduOrderService.queryObject(String.valueOf(out_trade_no));
                gongDuOrderVo.setPayStatus(2);
                gongDuOrderVo.setOrderStatus(201);
                gongDuOrderVo.setPayTime(new Date());
                apiGongduOrderService.update(gongDuOrderVo);*/




                //----------------------------------------


                // 确认成功后更改订单状态
                // 业务处理
//                OrderVo orderInfo = orderService.queryObject(Integer.valueOf(out_trade_no));
                GongDuOrderVo gongDuOrderVo = apiGongduOrderService.queryObject(String.valueOf(out_trade_no));
                //如果订单已支付重复回调
                if(gongDuOrderVo.getPayStatus()==2) {// 已支付且回调成功了的不在修改业务数据
                    response.getWriter().write(setXml("SUCCESS", "OK"));
                }else{
                    // 修改能力券
                    Long userId = gongDuOrderVo.getUserId();
                    UserVo loginUser = apiUserService.queryObject(userId);
                    gongDuOrderVo.setPayStatus(2);
                    gongDuOrderVo.setOrderStatus(201);
                    gongDuOrderVo.setPayTime(new Date());
                    apiGongduOrderService.update(gongDuOrderVo);
                    // 支付成功可以进行共读
                    UserLearnVo oldUserLearnVo = apiUserLearnService.queryObjectByUserIdAndLearnTypeId(gongDuOrderVo.getUserId().intValue(),gongDuOrderVo.getLearnTypeId());
                    // 后台修改了阅读状态为0 用户又必须重新支付开启阅读
                    if(null!=oldUserLearnVo){//存在该 用户学习 信息
                        if(oldUserLearnVo.getStartStatus()==0){ // 学习状态=0
                            oldUserLearnVo.setStartStatus(1);// 开启共读
                            oldUserLearnVo.setUnlocks(1); // 默认开启第一天课程
                            oldUserLearnVo.setMiss(0);
                            apiUserLearnService.updateByUserIdAndLearnTypeId(oldUserLearnVo);
                            System.out.println("老用户重新加入21天计划");
                        }
                    }else{
                        UserLearnVo userLearnVo = new UserLearnVo();
                        userLearnVo.setLearnTypeId(gongDuOrderVo.getLearnTypeId());
                        userLearnVo.setUserid(gongDuOrderVo.getUserId().intValue());
                        userLearnVo.setStartStatus(1);// 开启共读
                        userLearnVo.setMiss(0);
                        userLearnVo.setSetupTime(" "); // 默认7点提醒
                        userLearnVo.setUnlocks(1); // 支付成功后默认开启第一天课程
                        // 新加入微信名称等
                        userLearnVo.setUserName(loginUser.getUsername());
                        userLearnVo.setNickname(loginUser.getNickname());
                        apiUserLearnService.save(userLearnVo);
                        System.out.println("新用户加入21天计划");
                    }
                    response.getWriter().write(setXml("SUCCESS", "OK"));
                }


                //--------------------------------------------










                // 支付成功可以进行共读
//                UserLearnVo userLearnVo = apiUserLearnService.queryObject(gongDuOrderVo.getUserId().intValue());


               /* UserLearnVo userLearnVo = new UserLearnVo();
                userLearnVo.setLearnTypeId(gongDuOrderVo.getLearnTypeId());
                userLearnVo.setUserid(gongDuOrderVo.getUserId().intValue());

                userLearnVo.setUserName(loginUser.getUsername());
                userLearnVo.setNickname(loginUser.getNickname());

                userLearnVo.setStartStatus(1);// 开启共读
                userLearnVo.setMiss(0);
                userLearnVo.setUnlocks(0);
                apiUserLearnService.save(userLearnVo);*/


//                XMLUtil.setXml
//                response.getWriter().write(setXml("SUCCESS", "OK"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }



    /**
     * 共读微信查询订单状态
     */
//    @RequestMapping("gongDuQuery")
    @GetMapping("gongDuQuery")
    public Object gongDuQuery(@LoginUser UserVo loginUser, String orderId) {
        if (orderId == null) {
            return toResponsFail("订单不存在");
        }

        Map<Object, Object> parame = new TreeMap<Object, Object>();
        parame.put("appid", ResourceUtil.getConfigByName("wx.appId"));
        // 商家账号。
        parame.put("mch_id", ResourceUtil.getConfigByName("wx.mchId"));
        String randomStr = CharUtil.getRandomNum(18).toUpperCase();
        // 随机字符串
        parame.put("nonce_str", randomStr);
        // 商户订单编号
        parame.put("out_trade_no", orderId);

        String sign = WechatUtil.arraySign(parame, ResourceUtil.getConfigByName("wx.paySignKey"));
        // 数字签证
        parame.put("sign", sign);

        String xml = MapUtils.convertMap2Xml(parame);
        logger.info("xml:" + xml);
        Map<String, Object> resultUn;
        try {
            resultUn = XmlUtil.xmlStrToMap(WechatUtil.requestOnce(ResourceUtil.getConfigByName("wx.orderquery"), xml));
        } catch (Exception e) {
            e.printStackTrace();
            return toResponsFail("查询失败,error=" + e.getMessage());
        }
        // 响应报文
        String return_code = MapUtils.getString("return_code", resultUn);
        String return_msg = MapUtils.getString("return_msg", resultUn);

        if (return_code.equals("SUCCESS")) {
            String trade_state = MapUtils.getString("trade_state", resultUn);
            if (trade_state.equals("SUCCESS")) {
                // 确认成功后更改订单状态
                // 业务处理
//                OrderVo orderInfo = orderService.queryObject(Integer.valueOf(out_trade_no));
             //   GongDuOrderVo gongDuOrderVo = apiGongduOrderService.queryObject(String.valueOf(orderId));
             //   gongDuOrderVo.setPayStatus(2);
             //   gongDuOrderVo.setOrderStatus(201);
              //  gongDuOrderVo.setPayTime(new Date());
              //  apiGongduOrderService.update(gongDuOrderVo);
                // 支付成功可以进行共读
              //  UserLearnVo oldUserLearnVo = apiUserLearnService.queryObjectByUserIdAndLearnTypeId(gongDuOrderVo.getUserId().intValue(),gongDuOrderVo.getLearnTypeId());
                // 后台修改了阅读状态为0 用户又必须重新支付开启阅读


              /*  if(null!=oldUserLearnVo){//存在该 用户学习 信息
                    if(oldUserLearnVo.getStartStatus()==0){ // 学习状态=0
                        oldUserLearnVo.setStartStatus(1);// 开启共读
                        oldUserLearnVo.setUnlocks(1); // 默认开启第一天课程
                        // 新加入微信名称等
                        oldUserLearnVo.setUserName(loginUser.getUsername());
                        oldUserLearnVo.setNickname(loginUser.getNickname());
                        apiUserLearnService.updateByUserIdAndLearnTypeId(oldUserLearnVo);
                    }
                }else{
                    UserLearnVo userLearnVo = new UserLearnVo();
                    userLearnVo.setLearnTypeId(gongDuOrderVo.getLearnTypeId());
                    userLearnVo.setUserid(gongDuOrderVo.getUserId().intValue());
                    userLearnVo.setStartStatus(1);// 开启共读
                    userLearnVo.setMiss(0);
                    userLearnVo.setSetupTime(" "); // 默认7点提醒
                    userLearnVo.setUnlocks(1); // 支付成功后默认开启第一天课程
                    // 新加入微信名称等
                    userLearnVo.setUserName(loginUser.getUsername());
                    userLearnVo.setNickname(loginUser.getNickname());
                    apiUserLearnService.save(userLearnVo);
                }
*/

                return toResponsMsgSuccess("支付成功");
            } else if (trade_state.equals("USERPAYING")) {
                // 重新查询 正在支付中
                Integer num = (Integer) J2CacheUtils.get(J2CacheUtils.SHOP_CACHE_NAME, "queryRepeatNum" + orderId + "");
                if (num == null) {
                    J2CacheUtils.put(J2CacheUtils.SHOP_CACHE_NAME,"queryRepeatNum" + orderId + "", 1);
                    this.gongDuQuery(loginUser, orderId);
                } else if (num <= 3) {
                    J2CacheUtils.remove(J2CacheUtils.SHOP_CACHE_NAME,"queryRepeatNum" + orderId);
                    this.gongDuQuery(loginUser, orderId);
                } else {
                    return toResponsFail("查询失败,error=" + trade_state);
                }

            } else {
                // 失败
                return toResponsFail("查询失败,error=" + trade_state);
            }
        } else {
            return toResponsFail("查询失败,error=" + return_msg);
        }
        return toResponsFail("查询失败，未知错误");
    }

}