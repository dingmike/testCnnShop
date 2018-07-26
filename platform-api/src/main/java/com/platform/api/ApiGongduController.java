package com.platform.api;

import com.alibaba.fastjson.JSONObject;
import com.platform.annotation.LoginUser;
import com.platform.entity.CnnLearnQuestionVo;
//import com.platform.entity.SmsConfig;
//import com.platform.entity.SmsLogVo;
import com.platform.entity.CnnUserCardVo;
import com.platform.entity.UserVo;
import com.platform.entity.UserLearnVo;

import com.platform.service.ApiUserLearnService;
import com.platform.service.ApiCnnLearnQuestionService;
import com.platform.service.ApiCnnUserCardService;
import com.platform.service.ApiGongduService;
//import com.platform.service.ApiUserService;
//import com.platform.service.SysConfigService;
import com.platform.util.ApiBaseAction;
import com.platform.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 作者: @mike
 * 时间: 2018-08-11 08:32
 * 描述: ApiGongduController
 */
@Api(tags = "API共读内容")
@RestController
@RequestMapping("/api/gongdu")
public class ApiGongduController extends ApiBaseAction {
//    @Autowired
//    private ApiUserService userService;
//    @Autowired
//    private SysConfigService sysConfigService;
    @Autowired
    private ApiUserLearnService userLearnService;
    @Autowired
    private ApiGongduService gongduService ;
    @Autowired
    private ApiCnnLearnQuestionService cnnLearnQuestionService ;
    @Autowired
    private ApiCnnUserCardService cnnUserCardService ;

    /**
     * 获取共读内容/api/gongdu/getContent
     */
    @RequestMapping("getContent")
    @ApiOperation(value = "获取共读内容接口", response = Map.class)
    public Object getContent(@LoginUser UserVo loginUser) {
        JSONObject jsonParams = getJsonRequest();
        Integer unlockdays = jsonParams.getInteger("days");
        Integer type = jsonParams.getInteger("type");
        String openid =  loginUser.getWeixin_openid();
        System.out.println(unlockdays);
        System.out.println(type);
        // 微信授权用户才能获取信息
        if (null != jsonParams&& openid.equals(jsonParams.getString("uid"))) {
            Object gongDucontent = gongduService.queryObjectBytypeAndDays(unlockdays, type);
            System.out.println(gongDucontent);
            return toResponsSuccess(gongDucontent);
        }
        return toResponsFail("执行失败");
    }


    /**
     * 获取共读内容/api/gongdu/getContent
     */
    @RequestMapping("getOraleDetail")
    @ApiOperation(value = "获取共读重点以及问答接口", response = Map.class)
    public Object getOraleDetail(@LoginUser UserVo loginUser) {
        JSONObject jsonParams = getJsonRequest();
        Integer unlockdays = jsonParams.getInteger("days"); // 第几天的学习内容
        Integer type = jsonParams.getInteger("type");
        String openid =  loginUser.getWeixin_openid();
        // 微信授权用户才能获取信息
        if (null != jsonParams&& openid.equals(jsonParams.getString("uid"))) {
            List<CnnLearnQuestionVo> learnQuestionList = cnnLearnQuestionService.queryQuestionByDaysAndType(unlockdays, type);
            return toResponsSuccess(learnQuestionList);
        }
        return toResponsFail("执行失败");
    }


    /**
     * 获取某天打卡信息/api/gongdu/getOneCard
     */
    @RequestMapping("getOneCard")
    @ApiOperation(value = "获取某天打卡接口", response = Map.class)
    public Object getOneCard(@LoginUser UserVo loginUser) {
        JSONObject jsonParams = getJsonRequest();
        Integer type = jsonParams.getInteger("type");
        Integer day = jsonParams.getInteger("day");
        Integer month = jsonParams.getInteger("month");
        Integer year = jsonParams.getInteger("year");

        String openid =  loginUser.getWeixin_openid();
        // 微信授权用户才能获取信息
//        Integer newUserId = loginUser.getUserId().intValue();
        if (null != jsonParams&& openid.equals(jsonParams.getString("uid"))) {
            CnnUserCardVo userCardList = cnnUserCardService.queryObjectByOther(loginUser.getUserId(), day, month, year, type);
            return toResponsSuccess(userCardList);
        }
        return toResponsFail("执行失败");
    }




    /**
     * 获取共读有效打卡信息/api/gongdu/getCardNums
     */
    @RequestMapping("getCardNums")
    @ApiOperation(value = "获取有效打卡记录数接口", response = Map.class)
    public Object getCardNums(@LoginUser UserVo loginUser) {
        JSONObject jsonParams = getJsonRequest();
        Integer type = jsonParams.getInteger("type");
        String openid =  loginUser.getWeixin_openid();
        // 微信授权用户才能获取信息
        if (null != jsonParams&& openid.equals(jsonParams.getString("uid"))) {
            List<CnnUserCardVo> userCardList = cnnUserCardService.queryUserCardByUseridAndType(loginUser.getUserId(), type);
            return toResponsSuccess(userCardList);
        }
        return toResponsFail("执行失败");
    }

    /**
     * 获取共读打卡信息/api/gongdu/getCardRecord
     */
    @RequestMapping("getCardRecord")
    @ApiOperation(value = "获取打卡记录数接口", response = Map.class)
    public Object getCardRecord(@LoginUser UserVo loginUser) {
        JSONObject jsonParams = getJsonRequest();
        Integer type = jsonParams.getInteger("type");
        String openid =  loginUser.getWeixin_openid();
        // 微信授权用户才能获取信息
        if (null != jsonParams&& openid.equals(jsonParams.getString("uid"))) {
            List<CnnUserCardVo> userCardList = cnnUserCardService.queryUserCardList(loginUser.getUserId(), type);
            return toResponsSuccess(userCardList);
        }
        return toResponsFail("执行失败");
    }

    /**
     *
     *打卡set
     *
    * */

    @RequestMapping("setCardById")
    @ApiOperation(value = "打卡接口", response = Map.class)
    public Object setCardById(@LoginUser UserVo loginUser) {

        JSONObject jsonParams = getJsonRequest();
        Integer learnTypeId = jsonParams.getInteger("type");
        String openid =  loginUser.getWeixin_openid();

        Long userId=  loginUser.getUserId();
        // 微信授权用户才能获取信息
        if (null != jsonParams&& openid.equals(jsonParams.getString("uid"))) {




//            SimpleDateFormat simdf = new SimpleDateFormat("MM月dd日");
            Calendar cal = Calendar.getInstance();
            //分别获取年、月、日
            System.out.println("年："+cal.get(cal.YEAR));
            System.out.println("月："+(cal.get(cal.MONTH)+1));
            System.out.println("日："+cal.get(cal.DATE));

            System.out.println("时: " + cal.get(cal.HOUR_OF_DAY));
            System.out.println("分: " + cal.get(cal.MINUTE));
            System.out.println("秒: " + cal.get(cal.SECOND));


            Integer year =cal.get(cal.YEAR);
            Integer month =cal.get(cal.MONTH)+1;
            Integer day=cal.get(cal.DATE);
            Integer hour=cal.get(cal.HOUR_OF_DAY);


            CnnUserCardVo userCardVo = cnnUserCardService.queryObjectByOther(userId, day, month, year, learnTypeId);
            // 如果已经打过卡了，就返回打卡数据
            System.out.println("------------------------------");
            System.out.println(userCardVo);
            if(null != userCardVo){

                return toResponsSuccess(userCardVo);
            }else{
                // 继续打卡操作
                CnnUserCardVo userCard = new CnnUserCardVo();
                // 打卡操作
                userCard.setDay(day);
                userCard.setMonth(month);
                userCard.setYear(year);
                userCard.setCardDay(day);
                Integer newUserId = userId.intValue();
                userCard.setUserid(newUserId);
                userCard.setLearnTypeId(learnTypeId);
                // 规定时间5-10点
                if(hour>5&&hour<10){
                    userCard.setReasonable(1);
                }else{
                    userCard.setReasonable(0);
                }

                Integer saveSuccess = cnnUserCardService.save(userCard);
                // 更新user_learn unlocks
                // 通过userId查出解锁天数然后才可更新
                UserLearnVo userlearnObj = userLearnService.queryObjectByUserId(userId.intValue());

                Integer unlocksNow = userlearnObj.getUnlocks()+1;
                System.out.println("--------------------------unlocksNow");
                System.out.println(unlocksNow);
                UserLearnVo newUserLearnVo = new UserLearnVo();
                newUserLearnVo.setUserid(userId.intValue());
                newUserLearnVo.setLearnTypeId(learnTypeId);
                newUserLearnVo.setUnlocks(unlocksNow);
                userLearnService.update(newUserLearnVo);


                return toResponsSuccess(saveSuccess);

            }

        }
        return toResponsFail("执行失败");
    }
}