package com.platform.api;

import com.alibaba.fastjson.JSONObject;
import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.entity.SmsConfig;
import com.platform.entity.SmsLogVo;
import com.platform.entity.UserLearnVo;
import com.platform.entity.UserVo;
import com.platform.service.ApiUserLearnService;
import com.platform.service.ApiUserService;
import com.platform.service.SysConfigService;
import com.platform.util.ApiBaseAction;
import com.platform.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者: @author Harmon <br>
 * 时间: 2017-08-11 08:32<br>
 * 描述: ApiIndexController <br>
 */
@Api(tags = "会员验证")
@RestController
@RequestMapping("/api/user")
public class ApiUserController extends ApiBaseAction {
    @Autowired
    private ApiUserService userService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private ApiUserLearnService userLearnService;

    /**
     * 发送短信
     */
    @ApiOperation(value = "发送短信")
    @PostMapping("smscode")
//    @RequestMapping("smscode")
    public Object smscode(@LoginUser UserVo loginUser) {
        JSONObject jsonParams = getJsonRequest();
        String phone = jsonParams.getString("phone");
        // 一分钟之内不能重复发送短信
        SmsLogVo smsLogVo = userService.querySmsCodeByUserId(loginUser.getUserId());
        if (null != smsLogVo && (System.currentTimeMillis() / 1000 - smsLogVo.getLog_date()) < 1 * 60) {
            return toResponsFail("短信已发送");
        }
        //生成验证码
        String sms_code = CharUtil.getRandomNum(4);
        String msgContent = "您的验证码是：" + sms_code + "，请在页面中提交验证码完成验证。";
        // 发送短信
        String result = "";
        //获取云存储配置信息
        SmsConfig config = sysConfigService.getConfigObject(ConfigConstant.SMS_CONFIG_KEY, SmsConfig.class);
        if (StringUtils.isNullOrEmpty(config)) {
            throw new RRException("请先配置短信平台信息");
        }
        if (StringUtils.isNullOrEmpty(config.getName())) {
            throw new RRException("请先配置短信平台用户名");
        }
        if (StringUtils.isNullOrEmpty(config.getPwd())) {
            throw new RRException("请先配置短信平台密钥");
        }
        if (StringUtils.isNullOrEmpty(config.getSign())) {
            throw new RRException("请先配置短信平台签名");
        }
        try {
            /**
             * 状态,发送编号,无效号码数,成功提交数,黑名单数和消息，无论发送的号码是多少，一个发送请求只返回一个sendid，如果响应的状态不是“0”，则只有状态和消息
             */
            result = SmsUtil.crSendSms(config.getName(), config.getPwd(), phone, msgContent, config.getSign(),
                    DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), "");
        } catch (Exception e) {

        }
        String arr[] = result.split(",");

        if ("0".equals(arr[0])) {
            smsLogVo = new SmsLogVo();
            smsLogVo.setLog_date(System.currentTimeMillis() / 1000);
            smsLogVo.setUser_id(loginUser.getUserId());
            smsLogVo.setPhone(phone);
            smsLogVo.setSms_code(sms_code);
            smsLogVo.setSms_text(msgContent);
            userService.saveSmsCodeLog(smsLogVo);
            return toResponsSuccess("短信发送成功");
        } else {
            return toResponsFail("短信发送失败");
        }
    }

    /**
     * 获取当前会员等级
     *
     * @param loginUser
     * @return
     */
    @ApiOperation(value = "获取当前会员等级")
    @GetMapping("getUserLevel")
//    @RequestMapping("getUserLevel")
    public Object getUserLevel(@LoginUser UserVo loginUser) {
        String userLevel = userService.getUserLevel(loginUser);
        return toResponsSuccess(userLevel);
    }

    /**
     * 绑定手机
     */
    @ApiOperation(value = "绑定手机")
    @PostMapping("bindMobile")
//    @RequestMapping("bindMobile")
    public Object bindMobile(@LoginUser UserVo loginUser) {
        JSONObject jsonParams = getJsonRequest();
        SmsLogVo smsLogVo = userService.querySmsCodeByUserId(loginUser.getUserId());

        String mobile_code = jsonParams.getString("mobile_code");
        String mobile = jsonParams.getString("mobile");

        if (!mobile_code.equals(smsLogVo.getSms_code())) {
            return toResponsFail("验证码错误");
        }
        UserVo userVo = userService.queryObject(loginUser.getUserId());
        userVo.setMobile(mobile);
        userService.update(userVo);
        return toResponsSuccess("手机绑定成功");
    }

    /**
     * 微信授权后更新微信用户的信息
     */
    @ApiOperation(value = "微信授权后更新微信用户的信息")
    @PostMapping("updateUserInfo")
//    @RequestMapping("updateUserInfo")
    public Object updateUserInfo(@LoginUser UserVo loginUser) {
        JSONObject jsonParams = getJsonRequest();
        UserVo entity = new UserVo();
        String openid =  loginUser.getWeixin_openid();
        if (null != jsonParams&& openid.equals(jsonParams.getString("uid"))) {
//            entity.setUsername(jsonParams.getString("username"));
            entity.setAvatar(jsonParams.getString("avatar"));
            entity.setWeixin_openid(jsonParams.getString("uid"));
            entity.setNickname(jsonParams.getString("nickname"));
            entity.setGender(jsonParams.getInteger("gender"));
        }

        return toResponsSuccess("更新成功");
    }

    /**
     * 微信授权后获取用户学习信息
     */
    @ApiOperation(value = "微信授权后获取用户学习信息")
//    @RequestMapping("getLearnInfo")
    @PostMapping("getLearnInfo")
    public Object getLearnInfo(@LoginUser UserVo loginUser) {
        JSONObject jsonParams = getJsonRequest();
        String openid =  loginUser.getWeixin_openid();
        if (null != jsonParams&& openid.equals(jsonParams.getString("uid"))) {
            Long userId =  loginUser.getUserId();
            Object userLearn = userLearnService.queryObjectByUserIdAndLearnTypeId(userId.intValue(), jsonParams.getInteger("learnTypeId"));
            return toResponsSuccess(userLearn);
        }
        return toResponsFail("执行失败");
    }


    /**
     * 获取用户积分余额等信息
     */
    @ApiOperation(value = "微信授权后获取用户积分余额信息")
//    @RequestMapping("getLearnInfo")
    @PostMapping("getUserIntergralInfo")
    public Object getUserIntergralInfo(@LoginUser UserVo loginUser) {
        JSONObject jsonParams = getJsonRequest();
        String openid =  loginUser.getWeixin_openid();
        if (null != jsonParams&& openid.equals(jsonParams.getString("uid"))) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("intergrals", loginUser.getIntergral());
            param.put("balance", loginUser.getBalance());
            return toResponsSuccess(param);
        }
        return toResponsFail("执行失败");
    }



    /**
     * 支付往后绑定微信号和手机号
     *
     */
    @ApiOperation(value = "支付完后绑定微信号和手机号")
    @PostMapping("submitPhone")
    public Object submitPhone(@LoginUser UserVo loginUser){
        JSONObject jsonParams = getJsonRequest();
        String openid =  loginUser.getWeixin_openid();
        if (null != jsonParams&& openid.equals(jsonParams.getString("uid"))) {
            UserVo userVo = userService.queryObject(loginUser.getUserId());
            userVo.setUsername(jsonParams.getString("wechatId"));
            userVo.setMobile(jsonParams.getString("mobile"));
            userService.update(userVo);
            // 保存多个formIds
            String formId=jsonParams.getString("formIds");
            UserLearnVo userLearnVo = userLearnService.queryObjectByUserId(loginUser.getUserId().intValue());
            String oldFormIds = userLearnVo.getFormId();
            String newFormIds;
            if(oldFormIds==null||oldFormIds==""){
                newFormIds = formId;
            }else {
                newFormIds = oldFormIds+","+formId;
            }
            String regex = "^,*|,*$";
            String rightFormIds = newFormIds.replaceAll(regex, "");

//            String newFormIds = oldFormIds+","+formId;
            userLearnVo.setFormId(rightFormIds);
            Integer successResult = userLearnService.update(userLearnVo);
//            return toResponsMsgSuccess("绑定成功");
            logger.info(" 保存多个formIds-----");
            return toResponsSuccess(successResult);
        }

       return toResponsFail("绑定失败");
    }
}