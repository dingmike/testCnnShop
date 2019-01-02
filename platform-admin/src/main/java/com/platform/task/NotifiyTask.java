package com.platform.task;



import com.platform.dao.ScheduleJobDao;
import com.platform.entity.*;
import com.platform.service.*;
import com.platform.util.UserRemindTask;
import com.platform.util.wechat.WechatUtil;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.ArrayUtils;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时微信提醒
 * <p>
 * NotifiyTask  bean
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018年01月30日 下午1:34:24
 */
@Component("NotifiyTask")
public class NotifiyTask {
    private  static Logger logger = LoggerFactory.getLogger(UserRemindTask.class);

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ScheduleJobDao schedulerJobDao;
    @Autowired
    private ApiUserService userService;

    @Autowired
    private UserService cnnUserService;
    @Autowired
    private ScheduleJobService scheduleJobService;

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private CnnUserFormidService cnnUserFormidService;

    @Autowired
    private ApiUserReadNewsService apiUserReadNewsService;
    @Autowired
    private OrderGoodsService orderGoodsService;

    // 更新user_learn unlocks
    public void notifiyUser(String params) {

        // ----------------发送模板消息
        String templateId = "tCX0aTuVpNIGdvbos8FkkRybqx2NvfAr_HSb6eGbGoU"; // 学习计划提醒
//      UserLearnVo newUserLearnObj = userLearnService.queryObjectByUserId(userid);
        List<UserVo> userListIds = userService.queryListAll();  //所有ID
        for (UserVo userObj : userListIds) {

            Integer userid = userObj.getUserId().intValue();
            String openid = userObj.getWeixin_openid();
            // 获取 formID
            CnnUserFormidEntity cnnUserFormidEntity = cnnUserFormidService.queryObjectByUserid(userid);

            if(null==cnnUserFormidEntity){
                continue;
            }

            Integer formID = cnnUserFormidEntity.getId();
            Map searchparams = new HashMap();
            searchparams.put("userid",userid);
            Integer counts = apiUserReadNewsService.queryTotalByUserId(searchparams);
            //转换为String
            String countTotal = counts+"篇文章";
            String formId = cnnUserFormidEntity.getFormid();
            String templateUrl = "pages/index/index";
            String page = "pages/index/index";
            String topcolor = "#ff6600";
            String carrierName = "每日阅读英语"; // 学习计划名称
            String waybillCode = "7:00-10:00"; // 学习时间段
            String waybillDesc = "EveryDay English 10 minutes"; // 学习内容
//            String wayContent = params; // 今日学习计划
//            Integer wayCount = 2; // 已完成多少篇文章
            String jsonObj = WechatUtil.makeTemplateMessage(openid,templateId,page,formId,templateUrl,topcolor,carrierName,waybillCode,waybillDesc,params,countTotal);
            // 发送消息
            AccessTokenEntity accessTokenEntity = accessTokenService.queryByFirst();
            Boolean sendSuccess = WechatUtil.sendTemplateMessage(accessTokenEntity.getAccessToken(),jsonObj);
            // 如果发送成功就删除用过的formId
            if(sendSuccess) {
                if(sendSuccess) {
                    cnnUserFormidService.delete(formID);
                }

            }

        }

    }


    //给用户发发货信息提醒
/*    public void sendShipInfo(OrderEntity order) {
        // ----------------发送模板消息
        String templateId = "0TthIbLogLLxtGKE2wjJppTxPLzQMvU5QmvOFMhSzV4"; // 发货提醒
//      UserLearnVo newUserLearnObj = userLearnService.queryObjectByUserId(userid);
        Integer userid = order.getUserId();
        UserEntity userObj = cnnUserService.queryObject(order.getUserId());
        String openid = userObj.getWeixinOpenid();
        // 获取 formID
        CnnUserFormidEntity cnnUserFormidEntity = cnnUserFormidService.queryObjectByUserid(userid);
        Integer formID = cnnUserFormidEntity.getId();
        //转换为String
        OrderGoodsEntity orderGoodsEntity = orderGoodsService.queryObjectByOrderId(order.getId());


        String formId = cnnUserFormidEntity.getFormid();
        String templateUrl = "pages/ucenter/order/order";
        String page = "pages/ucenter/order/order";
        String topcolor = "#ff6600";
        String keyword1 = order.getShippingName(); // 快递公司
        String keyword2 = order.getShippingNo(); // 快递单号
        String keyword3 = order.getOrderSn(); // 订单号
        String keyword4 = orderGoodsEntity.getGoodsName(); // 物品名称
        String keyword5 = order.getOrderPrice().toString(); // 订单总价
        String keyword6 = order.getConsignee(); // 收货人
        String keyword7 = order.getMobile(); // 收货人电话
        String keyword8 = order.getCountry()+order.getCity()+order.getDistrict()+order.getAddress(); // 收货地址


        String waybillCode = "7:00-10:00"; // 学习时间段
        String waybillDesc = "EveryDay English 10 minutes"; // 学习内容
//            String wayContent = params; // 今日学习计划
//            Integer wayCount = 2; // 已完成多少篇文章
        String jsonObj = WechatUtil.sendShipMessage(openid,templateId,page,formId,templateUrl,topcolor,keyword1,keyword2,keyword3,keyword4,keyword5,keyword6,keyword7,keyword8);
        // 发送消息
        AccessTokenEntity accessTokenEntity = accessTokenService.queryByFirst();
        Boolean sendSuccess = WechatUtil.sendTemplateMessage(accessTokenEntity.getAccessToken(),jsonObj);
        // 如果发送成功就删除用过的formId
        if(sendSuccess) {
            if(sendSuccess) {
                cnnUserFormidService.delete(formID);
            }

        }


    }*/

}
