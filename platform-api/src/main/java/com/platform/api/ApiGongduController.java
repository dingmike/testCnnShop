package com.platform.api;

import com.alibaba.fastjson.JSONObject;
import com.platform.annotation.LoginUser;
import com.platform.entity.CnnLearnQuestionVo;
//import com.platform.entity.SmsConfig;
//import com.platform.entity.SmsLogVo;
import com.platform.entity.CnnUserCardVo;
import com.platform.entity.UserVo;
//import com.platform.service.ApiUserLearnService;
import com.platform.service.ApiCnnLearnQuestionService;
import com.platform.service.ApiCnnUserCardService;
import com.platform.service.ApiGongduService;
//import com.platform.service.ApiUserService;
//import com.platform.service.SysConfigService;
import com.platform.util.ApiBaseAction;
import com.platform.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//    @Autowired
//    private ApiUserLearnService userLearnService;
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
}