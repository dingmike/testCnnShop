package com.platform.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.platform.util.AccessTokenTask;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.platform.annotation.IgnoreAuth;
import com.platform.entity.FullUserInfo;
import com.platform.entity.UserInfo;
import com.platform.entity.UserVo;
import com.platform.service.ApiUserService;
import com.platform.service.TokenService;
import com.platform.util.ApiBaseAction;
import com.platform.util.ApiUserUtils;
import com.platform.util.CommonUtil;
import com.platform.utils.CharUtil;
import com.platform.utils.R;
import com.platform.validator.Assert;
import com.qiniu.util.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * API登录授权
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2017年11月16日 下午10:43:36
 */
@Api(tags = "API登录授权接口")
@RestController
@RequestMapping("/api/auth")
public class ApiAuthController extends ApiBaseAction {
    private Logger logger = Logger.getLogger(getClass());
    @Autowired
    private ApiUserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AccessTokenTask accessTokenTask;
    /**
     * 登录
     */
    @IgnoreAuth
    @RequestMapping("login")
    @ApiOperation(value = "登录接口")
    public R login(String mobile, String password) {
        Assert.isBlank(mobile, "手机号不能为空");
        Assert.isBlank(password, "密码不能为空");

        //用户登录
        long userId = userService.login(mobile, password);

        //生成token
        Map<String, Object> map = tokenService.createToken(userId);

        return R.ok(map);
    }

    /**
     * 登录
     */
    @ApiOperation(value = "登录")
    @IgnoreAuth
    @RequestMapping("login_by_weixin")
    public Object loginByWeixin() {
        JSONObject jsonParam = this.getJsonRequest();
        FullUserInfo fullUserInfo = null;
        String code = "";
        if (!StringUtils.isNullOrEmpty(jsonParam.getString("code"))) {
            code = jsonParam.getString("code");
        }
        if (null != jsonParam.get("userInfo")) {
            fullUserInfo = jsonParam.getObject("userInfo", FullUserInfo.class);
        }

        Map<String, Object> resultObj = new HashMap<String, Object>();
        //
        UserInfo userInfo = fullUserInfo.getUserInfo();

        //获取openid
        String requestUrl = ApiUserUtils.getWebAccess(code);//通过自定义工具类组合出小程序需要的登录凭证 code
        logger.info("》》》组合token为：" + requestUrl);
        String res = restTemplate.getForObject(requestUrl, String.class);
        JSONObject sessionData = JSON.parseObject(res);

        if (null == sessionData || StringUtils.isNullOrEmpty(sessionData.getString("openid"))) {
            System.out.println("1-登录失败");
            return toResponsFail("登录失败");
        }
        //验证用户信息完整性
        String sha1 = CommonUtil.getSha1(fullUserInfo.getRawData() + sessionData.getString("session_key"));
        if (!fullUserInfo.getSignature().equals(sha1)) {
            System.out.println("2-登录失败");
            return toResponsFail("登录失败");
        }
        Date nowTime = new Date();
        UserVo userVo = userService.queryByOpenId(sessionData.getString("openid"));
        if (null == userVo) {
            userVo = new UserVo();
            userVo.setUsername("微信用户" + CharUtil.getRandomString(12));
            userVo.setPassword(sessionData.getString("openid"));
            userVo.setRegister_time(nowTime);
            userVo.setRegister_ip(this.getClientIp());
            userVo.setLast_login_ip(userVo.getRegister_ip());
            userVo.setLast_login_time(userVo.getRegister_time());
            userVo.setWeixin_openid(sessionData.getString("openid"));
            userVo.setAvatar(userInfo.getAvatarUrl());
            userVo.setGender(userInfo.getGender()); // //性别 0：未知、1：男、2：女
            userVo.setNickname(userInfo.getNickName());
            userService.save(userVo);
            //新用户第一次登陆时，虽然user表数据已经新增成功，但是此时userId还是null，从数据库再查一次就能取到了
           // userVo = userService.queryByOpenId(sessionData.getString("openid"));
        } else {
            userVo.setLast_login_ip(this.getClientIp());
            userVo.setLast_login_time(nowTime);
            userVo.setAvatar(userInfo.getAvatarUrl());
            userVo.setGender(userInfo.getGender()); // //性别 0：未知、1：男、2：女
            userVo.setNickname(userInfo.getNickName());
            userService.update(userVo);
        }

        Map<String, Object> tokenMap = tokenService.createToken(userVo.getUserId());
        String token = MapUtils.getString(tokenMap, "token");

        if (null == userInfo || StringUtils.isNullOrEmpty(token)) {
            return toResponsFail("登录失败");
        }

        resultObj.put("token", token);
//        resultObj.put("userInfo", userService.queryByOpenId(sessionData.getString("openid")));
//        resultObj.put("userInfo", userVo);
        resultObj.put("userInfo", userService.queryByOpenId(sessionData.getString("openid")));
        resultObj.put("userId", userVo.getUserId());

        // 调用获取为您access_token定时器 需要启动项目完成后立即执行
//        accessTokenTask.askAccessToken();

        return toResponsSuccess(resultObj);
    }
}