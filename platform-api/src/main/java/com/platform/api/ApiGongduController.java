package com.platform.api;

import com.alibaba.fastjson.JSONObject;
import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.entity.*;
//import com.platform.entity.SmsConfig;
//import com.platform.entity.SmsLogVo;

import com.platform.service.*;
//import com.platform.service.ApiUserService;
//import com.platform.service.SysConfigService;
import com.platform.util.ApiBaseAction;
import com.platform.util.ApiPageUtils;
import com.platform.util.UserRemindTask;
import com.platform.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * 作者: @admin
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

    @Autowired
    private ApiUserService userService;

    @Autowired
    private ApiUserIntergralLogService userIntergralLogService;

    @Autowired
    private ApiUserReadNewsService apiUserReadNewsService;
    @Autowired
    private ApiCnnNewsService apiCnnNewsService;
    @Autowired
    private CnnUserFormidService cnnUserFormidService;
    @Autowired
    private CnnSysParamsService cnnSysParamsService;

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
     *类型21天打卡
     *
    * */
    @RequestMapping("setCardById")
    @ApiOperation(value = "类型21天打卡", response = Map.class)
    public Object setCardById(@LoginUser UserVo loginUser) {

        JSONObject jsonParams = getJsonRequest();
        Integer learnTypeId = jsonParams.getInteger("type");
        String formId = jsonParams.getString("formId");
        String openid =  loginUser.getWeixin_openid();
        Long userId=  loginUser.getUserId();
        String username = loginUser.getUsername();
        String nickname = loginUser.getNickname();
        UserLearnVo userLearnObj = userLearnService.queryObjectByUserIdAndLearnTypeId(userId.intValue(),learnTypeId);
        Integer setCardDay = userLearnObj.getUnlocks();
        // 微信授权用户才能获取信息
        if (null != jsonParams&& openid.equals(jsonParams.getString("uid"))) {
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
                // 如果打卡日大于等于21则返回21 计划已完成
                if(21<=setCardDay){
                    return toResponsSuccess(21);
                }else{
                    return toResponsSuccess(0);
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

                //2：00-23：59小时有效打卡 or 5:00-10:00
                if(hour>=5&&hour<=9){
                    userCard.setReasonable(1);
                }else{
                    userCard.setReasonable(0);
                }
                // 打卡信息保存
                Integer saveSuccess = cnnUserCardService.save(userCard);
                // 打卡信息保存后都可以获得积分1
//                BigDecimal increased  =  new BigDecimal(1);
//                loginUser.setIntergral(increased);
//                userService.update(loginUser);

                UserIntergralLogVo userIntergralLogVo = new UserIntergralLogVo();

                userIntergralLogVo.setUserid(userId.intValue());
                userIntergralLogVo.setLearnTypeId(learnTypeId);
                userIntergralLogVo.setNickname(nickname);
                userIntergralLogVo.setUsername(username);
//                userIntergralLogVo.setPlusMins(1); // 1加 0减
                userIntergralLogVo.setMemo("21天计划打卡获得");
//                userIntergralLogVo.setPoints(increased);
                // 获取阅读券额度参数
                CnnSysParamsVo couponParams = cnnSysParamsService.queryObject(1);

                BigDecimal intergrals = couponParams.getIncreaseparams();//改变的 打卡成功获得2积分  symbolMethod =1 加
                userIntergralLogService.save(userIntergralLogVo, loginUser, 1, intergrals);


                // 打卡完后更新微信表单formID 多个formId是个字符串用“，”隔开
                UserLearnVo userLearnVo = new UserLearnVo();
                userLearnVo.setLearnTypeId(learnTypeId);

//                UserLearnVo oldUserLearnVo = userLearnService.queryObjectByUserId(newUserId);
                UserLearnVo oldUserLearnVo = userLearnService.queryObjectByUserIdAndLearnTypeId(newUserId, learnTypeId);
                String oldFormIds = oldUserLearnVo.getFormId();
                String newFormIds;
                if("".equals(oldFormIds)||oldFormIds==null){
                    newFormIds = formId;
                }else{
                    newFormIds = oldFormIds+","+ formId;
                }

                userLearnVo.setFormId(newFormIds);
                userLearnVo.setUserid(userId.intValue());
//              Integer successResult = userLearnService.update(userLearnVo);
                Integer successResult = userLearnService.updateByUserIdAndLearnTypeId(userLearnVo);
                System.out.println("更新formId成功-----------");

                // 每次打卡完成判断是否是第二十一天最后一次打卡，记录该用户是否按规定打完21天的卡完成学习任务。
                // 打的21天最后一天卡 就终止打卡行为
                if(setCardDay==21){
                  List<CnnUserCardVo> userCardList = cnnUserCardService.queryUserCardList(userId, learnTypeId);
                    // 打卡天数必须为20天
                    CnnLearnResultVo learnResultVo = new CnnLearnResultVo();
                    learnResultVo.setTotalCards(userCardList.size());
                    String reasonStr = "";
                  if(userCardList.size()<20){
                      // 最终结果
                      learnResultVo.setResult(0);
                      reasonStr = "打卡天数不够,";
                  }else{
                      learnResultVo.setResult(1);
                    }
                  Integer successCardsNum=0;
                    List<Integer> cardsList = new ArrayList<>();
                    for(int j=0;j<userCardList.size(); j++){
                        cardsList.add(userCardList.get(j).getCardDay());
                    }
                    for(int i=2; i<=21;i++){
                        if(cardsList.contains(i)){
                            learnResultVo.setResult(1);
                            continue;
                        }else{
                            // 最终结果
                            learnResultVo.setResult(0);
                            reasonStr = reasonStr+"(第"+ i +"天)未打卡、";
                        }
                    }

                  for(int i=0; i<userCardList.size(); i++){
                      if(userCardList.get(i).getReasonable()==0){
                        // 添加用户最终打卡结果有一天是无效的则无效
                          learnResultVo.setResult(0);
                          reasonStr = reasonStr+ "而且有未在规定时间打卡";
                          break;
                      }else{
                          learnResultVo.setResult(1);
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
//            Integer result = userLearnService.update(userLearnVo);
            Integer result = userLearnService.updateByUserIdAndLearnTypeId(userLearnVo);
            // 启动定时器执行定时任务
            userRemindTask.setupRemindTask(userId, setupTime);

            return toResponsSuccess(result);
            }
        return toResponsFail("执行失败");
    }

    /**
     *
     * 获取用户已阅读文章
     * @params userId
     * */
    @ApiOperation(value = "获取用户已阅读文章", response = Map.class)
    @GetMapping(value = "getReadNewsByUserId")
    public Object getReadNewsByUserId(@LoginUser UserVo loginUser,
                                      @RequestParam(value ="page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "size", defaultValue = "10") Integer size) {

        Map params = new HashMap();
        params.put("userid", loginUser.getUserId());
        params.put("page", page);
        params.put("limit", size);
        params.put("sidx", "add_time"); // 按添加时间倒序
        params.put("order", "desc");  // asc正序 desc倒序
            Query query = new Query(params);
            List<UserReadNewsVo> userReadNewsVos = apiUserReadNewsService.queryListByUserId(query);
            int total = apiUserReadNewsService.queryTotalByUserId(query);
            //查询列表数据
            ApiPageUtils pageUtil = new ApiPageUtils(userReadNewsVos, total, query.getLimit(), query.getPage());
            return toResponsSuccess(pageUtil);
    }


    /**
     *
     * 获取用户已阅读文章的ID
     * @params userId
     * */
    @ApiOperation(value = "获取用户已阅读文章ID", response = Map.class)
    @GetMapping(value = "getAllReadNewsId")
    public Object getAllReadNewsId(@LoginUser UserVo loginUser) {

        Map params = new HashMap();
        params.put("userid", loginUser.getUserId());
        List<Integer> userReadNewsVos = apiUserReadNewsService.queryTotalNewsIdByUserId(params);
        return toResponsSuccess(userReadNewsVos);
    }



    /**
     *
     * 获取用户已阅读文章Id
     * @params ID
     * */
    @ApiOperation(value = "根据文章ID获取文章详情", response = Map.class)
    @RequestMapping(value = "getNewsById")
    public Object getNewsById(@LoginUser UserVo loginUser) {
        JSONObject jsonParams = getJsonRequest();
        Integer pageId = jsonParams.getInteger("pageId");
        CnnNewsVo cnnNewsVo = apiCnnNewsService.queryObject(pageId);
        return toResponsSuccess(cnnNewsVo);
    }



    /**
     *
     * 获取全部文章
     * @params ID
     * */
    @ApiOperation(value = "获取全部文章", response = Map.class)
    @GetMapping(value = "getAllNews")
    public Object getAllNews(@LoginUser UserVo loginUser,
                             @RequestParam(value ="page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {

        Map params = new HashMap();
        params.put("userid", loginUser.getUserId());
        params.put("page", page);
        params.put("limit", size);
        params.put("sidx", "add_time"); // 按添加时间倒序
        params.put("order", "desc");  // asc正序 desc倒序
        Query query = new Query(params);
        List<CnnNewsVo> allCnnNewsVos = apiCnnNewsService.queryList(query);
        int total = apiCnnNewsService.queryTotal(query);
        //查询列表数据
        ApiPageUtils pageUtil = new ApiPageUtils(allCnnNewsVos, total, query.getLimit(), query.getPage());
        return toResponsSuccess(pageUtil);
    }


    /**
     *
     * 每日阅读文章打卡
     * @params userId
     * */
    @ApiOperation(value = "每日阅读文章打卡", response = Map.class)
    @RequestMapping(value = "setNewsCard")
    public Object setNewsCard(@LoginUser UserVo loginUser) {
        JSONObject jsonParams = getJsonRequest();
        Integer newsId = jsonParams.getInteger("newsId");
        Integer learnTypeId = jsonParams.getInteger("learnTypeId");
        Integer useTime = jsonParams.getInteger("useTime");
        Long userId = loginUser.getUserId();
        String username = loginUser.getUsername();
        String nickname = loginUser.getNickname();

        UserReadNewsVo userReadNewsVo = new UserReadNewsVo();
        userReadNewsVo.setUserid(loginUser.getUserId().intValue());
        userReadNewsVo.setUsername(loginUser.getUsername());
        userReadNewsVo.setNickname(loginUser.getNickname());
        userReadNewsVo.setUseTime(useTime);
        if(useTime>30){// 大于30s才合格
            userReadNewsVo.setIsValid(1);
        }else{
            userReadNewsVo.setIsValid(0);
        }
        userReadNewsVo.setNewsid(newsId);

        Integer successInt = apiUserReadNewsService.save(userReadNewsVo);
        UserIntergralLogVo userIntergralLogVo = new UserIntergralLogVo();
        userIntergralLogVo.setUserid(userId.intValue());
        userIntergralLogVo.setLearnTypeId(learnTypeId);
        userIntergralLogVo.setNickname(nickname);
        userIntergralLogVo.setUsername(username);
        userIntergralLogVo.setMemo("每日阅读打卡获得");

        // 获取阅读券额度参数
        CnnSysParamsVo couponParams = cnnSysParamsService.queryObject(1);
//        userIntergralLogVo.setPlusMins(1); // 1加 0减
        Map result = new HashMap<>();
        BigDecimal intergrals = couponParams.getIncreaseparams();  //改变的 成功打卡获得2积分
        if(useTime>30){ // 认真学习的人
            userIntergralLogService.save(userIntergralLogVo, loginUser, 1, intergrals);
            result.put("status", successInt); //打卡是否成功状态
            result.put("intergrals", intergrals); //获得能力券多少
//            return toResponsSuccess(result);
        }else{// 不认真的不合格的
            result.put("status", successInt); //打卡是否成功状态
            result.put("intergrals", 0); //获得能力券多少
        }
        return toResponsSuccess(result);

    }

    /**
     *
     * 当天打卡阅读的文章GET
     * @params userId
     * */
    @ApiOperation(value = "当天打卡阅读的文章", response = Map.class)
    @GetMapping(value = "getTodayNews")
    public Object getTodayNews(@LoginUser UserVo loginUser) {
        Map params = new HashMap();
        params.put("isToday",1);
        CnnNewsVo cnnNewsVo = apiCnnNewsService.queryObjectByToday(params);
        //判断用户是否已打卡今日文章
        if(null!=cnnNewsVo){
            Integer newsId = cnnNewsVo.getId();
            params = new HashMap();
            params.put("userid",loginUser.getUserId());
            params.put("newsid",newsId);
            Integer haveReaded = apiUserReadNewsService.queryTotalByUserIdAndNewsId(params);

            Map result = new HashMap<>();
            result.put("haveReaded",haveReaded);
            result.put("todayNews",cnnNewsVo);
            return toResponsSuccess(result);
        }
        Map result = new HashMap<>();
        result.put("haveReaded",-1);
        return toResponsSuccess(result);

    }

    /**
     *
     * 是否已打卡当天文章
     * @params userId id
     * */
    @ApiOperation(value = "是否已打卡当天文章", response = Map.class)
    @PostMapping(value = "haveReaded")
    public Object haveReaded(@LoginUser UserVo loginUser) {
        JSONObject jsonParams = getJsonRequest();
        String openid = loginUser.getWeixin_openid();
        if(null != jsonParams&& openid.equals(jsonParams.getString("uid"))){
            Map params = new HashMap();
            params.put("userid",loginUser.getUserId());
            params.put("id",jsonParams.getInteger("id"));
            Integer haveReaded = apiUserReadNewsService.queryTotalByUserIdAndNewsId(params);
            return toResponsSuccess(haveReaded);
        }else{
            return toResponsFail("用户未授权");
        }
    }

    /**
     *
     * 给用户添加formId
     * @params uid, formId
     * */
    @ApiOperation(value = "添加formId", response = Map.class)
    @PostMapping(value = "addFormId")
    public Object addFormId(@LoginUser UserVo loginUser) {
        JSONObject jsonParams = getJsonRequest();
        String openid = loginUser.getWeixin_openid();
        if(null != jsonParams&& openid.equals(jsonParams.getString("uid"))){
            CnnUserFormidEntity userFormidEntity =  new CnnUserFormidEntity();
            userFormidEntity.setFormid(jsonParams.getString("formId"));
            userFormidEntity.setUserid(loginUser.getUserId().intValue());
            Integer haveSaved = cnnUserFormidService.save(userFormidEntity);
            return toResponsSuccess(haveSaved);
        }else{
            return toResponsFail("用户未授权");
        }
    }

    /**
     *
     * 能力券抵扣率
     * @params null
     * */
    @ApiOperation(value = "能力券抵扣率", response = Map.class)
    @GetMapping(value = "deduction")
    public Object deduction() {
        // 获取阅读能力券抵扣率参数id=2
            CnnSysParamsVo couponParams = cnnSysParamsService.queryObject(2);
            return toResponsSuccess(couponParams.getIncreaseparams());
    }

    /**
     *
     * 每日打卡排行
     * @params order
     * @params limit
     * */
   /* @IgnoreAuth
    @ApiOperation(value = "每日打卡排行", response = Map.class)
    @GetMapping(value = "rankList")
    public Object rankList(@RequestParam(value ="order", defaultValue = "1") Integer order,
                           @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Map params = new HashMap();
        if(order == 1){
            params.put("order", "desc"); // null和1都是倒序 ，0是顺序
        }else{
            params.put("order", "asc"); // null和1都是倒序 ，0是顺序
        }
            params.put("limit", limit); // 数量
            List<UserReadNewsVo> userReadNewsVoList = apiUserReadNewsService.queryRankList(params);
            return toResponsSuccess(userReadNewsVoList);

    }*/
    @IgnoreAuth
    @ApiOperation(value = "每日打卡排行", response = Map.class)
    @PostMapping("rankList")
    public Object rankList() {
        JSONObject jsonParams = getJsonRequest();
        Map params = new HashMap();
        if(null!=jsonParams){
            if(jsonParams.getInteger("order")==1){
                params.put("order", "desc"); // null和1都是倒序 ，0是顺序
            }else{
                params.put("order", "asc"); // null和1都是倒序 ，0是顺序
            }
            params.put("limit", jsonParams.getInteger("limit")); // 数量
            List<UserReadNewsVo> userReadNewsVoList = apiUserReadNewsService.queryRankList(params);
            return toResponsSuccess(userReadNewsVoList);
        }
        return toResponsFail("参数错误");

    }
}