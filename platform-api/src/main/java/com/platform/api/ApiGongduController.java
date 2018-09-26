package com.platform.api;

import com.alibaba.fastjson.JSONObject;
import com.platform.annotation.LoginUser;
import com.platform.entity.*;
//import com.platform.entity.SmsConfig;
//import com.platform.entity.SmsLogVo;

import com.platform.service.*;
//import com.platform.service.ApiUserService;
//import com.platform.service.SysConfigService;
import com.platform.util.ApiBaseAction;
import com.platform.util.UserRemindTask;
import com.platform.util.wechat.WechatUtil;
import com.platform.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
    private UserRemindTask userRemindTask;

    @Autowired
    private ApiUserLearnService userLearnService;
    @Autowired
    private ApiGongduService gongduService ;
    @Autowired
    private ApiCnnLearnQuestionService cnnLearnQuestionService ;
    @Autowired
    private ApiCnnUserCardService cnnUserCardService ;

    @Autowired
    private ScheduleJobService scheduleJobService;
    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private ApiCnnLearnResultService cnnLearnResultService;

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
    @PostMapping("getOraleDetail")
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
//    @RequestMapping("getOneCard")
    @PostMapping("getOneCard")
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
        String formId = jsonParams.getString("formId");
        String openid =  loginUser.getWeixin_openid();
        Long userId=  loginUser.getUserId();
        String username = loginUser.getUsername();
        String nickname = loginUser.getNickname();
        UserLearnVo userLearnObj = userLearnService.queryObjectByUserId(userId.intValue());
        Integer setCardDay = userLearnObj.getUnlocks();
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

            System.out.println("------------------------------");

            // 如果已经打过卡了，就返回0
            if(null != userCardVo){
                if(16!=setCardDay){
                    return toResponsSuccess(0);
                }else {
                    return toResponsSuccess(21);
                }

            }else{
                // 还没打卡继续打卡操作
                CnnUserCardVo userCard = new CnnUserCardVo();
                // 打卡操作
                userCard.setDay(day);
                userCard.setMonth(month);
                userCard.setYear(year);
                userCard.setCardDay(setCardDay); // 从day 1开始不包括day1 从day2开始 连续打卡20天  2-21
                Integer newUserId = userId.intValue();
                userCard.setUserid(newUserId);
                userCard.setUsername(username);
                userCard.setNickname(nickname);
                userCard.setLearnTypeId(learnTypeId);
                // 规定时间5-10点
                if(hour>5&&hour<10){
                    userCard.setReasonable(1);
                }else{
                    userCard.setReasonable(0);
                }

                Integer saveSuccess = cnnUserCardService.save(userCard);
                // 打卡完后更新微信表单formID 多个formId是个字符串用“，”隔开
                UserLearnVo userLearnVo = new UserLearnVo();
                userLearnVo.setLearnTypeId(learnTypeId);

                UserLearnVo oldUserLearnVo = userLearnService.queryObjectByUserId(newUserId);
                String oldFormIds = oldUserLearnVo.getFormId();
                String newFormIds;
                if("".equals(oldFormIds)||oldFormIds==null){
                    newFormIds = formId;
                }else{
                    newFormIds = oldFormIds+","+ formId;
                }

                userLearnVo.setFormId(newFormIds);
                userLearnVo.setUserid(userId.intValue());
                Integer successResult = userLearnService.update(userLearnVo);
                System.out.println("更新formId成功-----------");
                System.out.println(successResult);

                // 每次打卡完成判断是否是第二十一天最后一次打卡，记录该用户是否按规定打完21天的卡完成学习任务。
                // 打的21天最后一天卡 就终止打卡行为
                if(setCardDay==16){
                  List<CnnUserCardVo> userCardList = cnnUserCardService.queryUserCardList(userId, learnTypeId);
                    // 打卡天数必须为20天
                    CnnLearnResultVo learnResultVo = new CnnLearnResultVo();
                    learnResultVo.setTotalCards(userCardList.size());
                    String reasonStr = "";
                  if(userCardList.size()<20){
                      learnResultVo.setResult(0);
                      reasonStr = "打卡天数不够,";
                    //  learnResultVo.setReason("打卡天数不够");
                  }
                  Integer successCardsNum=0;

                    List<Integer> cardsList = new ArrayList<>();
                    for(int j=0;j<userCardList.size(); j++){
                        cardsList.add(userCardList.get(j).getCardDay());
                    }
                    for(int i=2; i<=21;i++){
                        if(cardsList.contains(i)){
                            continue;
                        }else{
//                            cardStr = cardStr + "第"+i+"天";
                            reasonStr = reasonStr+"(第"+ i +"天)未打卡;";
                        }
                    }

                  for(int i=0; i<userCardList.size(); i++){

                      if(userCardList.get(i).getReasonable()==0){
                        // 添加用户最终打卡结果有一天是无效的则无效
//                          cnnLearnResultService
                          learnResultVo.setResult(0);
                          reasonStr = reasonStr+ "而且有未在规定时间打卡";
                          break;
                      }else{
                          // 记录成功打卡天数
                          ++successCardsNum;

                      }
                  }
                    learnResultVo.setLearnTypeId(learnTypeId);
                    learnResultVo.setUserid(userId.intValue());
                    learnResultVo.setUsername(username);
                    learnResultVo.setNickname(nickname);
                    learnResultVo.setSuccessTotalCards(successCardsNum);
                    learnResultVo.setReason(reasonStr);
                    cnnLearnResultService.save(learnResultVo);
                    // 终止打卡行为
                    return toResponsSuccess(21);
                }

                if(successResult !=0){
                    return toResponsSuccess(saveSuccess);
                }else{
                    return toResponsFail("内部错误没有formID，联系管理员！");
                }

            }

        }
        return toResponsFail("执行失败");
    }

    /**
     *
     * 设置打卡时间
     *
     * */

    @RequestMapping("setRemindTime")
    @ApiOperation(value = "设置提醒时间接口", response = Map.class)
    public Object setRemindTime(@LoginUser UserVo loginUser) {
        JSONObject jsonParams = getJsonRequest();
        Integer learnTypeId = jsonParams.getInteger("type");
        String setupTime = jsonParams.getString("setupTime");
        String openid =  loginUser.getWeixin_openid();
        Long userId =  loginUser.getUserId();
        // 微信授权用户才能操作
        if (null != jsonParams&& openid.equals(jsonParams.getString("uid"))) {
            UserLearnVo userLearnVo = new UserLearnVo();
            userLearnVo.setUserid(loginUser.getUserId().intValue());
            userLearnVo.setSetupTime(setupTime);
            userLearnVo.setLearnTypeId(learnTypeId);
            // 参加成功的用户才可以设置定时提醒时间
            Integer result = userLearnService.update(userLearnVo);
            // 启动定时器执行定时任务
            userRemindTask.test(userId, setupTime);

            // ----------------测试发送模板消息
           /* String templateId = "aR2vBrOkQipCeAB1tcQ2-jXHJket3CjhpGjYiYdGaOY";
            UserLearnVo newUserLearnObj = userLearnService.queryObjectByUserId(userId.intValue());
            String formId = newUserLearnObj.getFormId(); // 表单formId // 可存为数组
            String templateUrl = "pages/gongDu/gongDu";
            String page = "pages/gongDu/gongDu";
            String topcolor = "ff6600";
            String carrierName = "测试模板消息1";
            String waybillCode = "测试模板消息2";
            String waybillDesc = "测试模板消息3";
            String jsonObj = WechatUtil.makeRouteMessage(openid,templateId,page,formId,templateUrl,topcolor,carrierName,waybillCode,waybillDesc);

            // 发送消息
            AccessTokenEntity accessTokenEntity = accessTokenService.queryByFirst();
            Boolean sendSuccess = WechatUtil.sendTemplateMessage(accessTokenEntity.getAccessToken(),jsonObj);*/


            return toResponsSuccess(result);
            }
        return toResponsFail("执行失败");
    }
}