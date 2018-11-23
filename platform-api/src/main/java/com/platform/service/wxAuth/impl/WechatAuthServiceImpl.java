package com.platform.service.wxAuth.impl;

import com.google.gson.JsonSyntaxException;
import com.platform.config.WechatConfig;
import com.platform.constant.SystemConstant;
import com.platform.util.otherWxUtil.HttpReqUtil;
import com.platform.util.otherWxUtil.JsonUtil;
import com.platform.entity.wxAuth.resp.JsapiTicket;

//import com.platform.entity.wxAuth.resp.AccessToken;
//import com.platform.entity.wxAuth.resp.AuthAccessToken;
//import com.platform.entity.wxAuth.resp.AuthUserInfo;
import com.platform.entity.wxAuth.resp.*;
import com.platform.service.wxAuth.WechatAuthService;
import com.platform.entity.base.AbstractParams;
import com.platform.entity.base.ResultState;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

/**
 * Wechat Auth Service
 * 
 * @author admin
 * @date 2017年7月9日
 */
@Service
public class WechatAuthServiceImpl implements WechatAuthService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 获取授权凭证token
	 * 
	 * @param key
	 *            应用appid
	 * @param secret
	 *            应用密匙
	 * @return json格式的字符串
	 */
	public static String getAccessToken(String appid, String secret) {
		TreeMap<String, String> map = new TreeMap<>();
		map.put("grant_type", "client_credential");
		map.put("appid", appid);
		map.put("secret", secret);
		String json = HttpReqUtil.HttpDefaultExecute(SystemConstant.GET_METHOD, WechatConfig.GET_ACCESS_TOKEN_URL, map,
				"", null);
		String result = null;
		AccessToken accessToken = JsonUtil.fromJsonString(json, AccessToken.class);
		if (accessToken != null) {
			result = accessToken.getAccess_token();
		}
		return result;
	}

	/**
	 * 获取授权请求url
	 * 
	 * @param basic
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String getAuthPath(AbstractParams basic, String url) throws Exception {
		Map<String, String> params = basic.getParams();
		url = HttpReqUtil.setParmas(params, url, "") + "#wechat_redirect";
		return url;
	}

	/**
	 * 获取网页授权凭证
	 * 
	 * @param basic
	 * @param url
	 * @return
	 */
	public AuthAccessToken getAuthAccessToken(AbstractParams basic, String url) {
		AuthAccessToken authAccessToken = null;
		// 获取网页授权凭证
		try {
			if (StringUtils.isEmpty(url)) {
				url = WechatConfig.GET_OAUTH_TOKEN_URL;
			}
			String result = HttpReqUtil.HttpsDefaultExecute(SystemConstant.GET_METHOD, url, basic.getParams(), null, null);
			authAccessToken = JsonUtil.fromJsonString(result, AuthAccessToken.class);
		} catch (Exception e) {
			logger.debug("error" + e.getMessage());
		}
		return authAccessToken;
	}

	/**
	 * 刷新网页授权验证
	 * 
	 * @param basic
	 *            参数
	 * @param url
	 *            请求路径
	 * @return
	 */
	public AuthAccessToken refreshAuthAccessToken(AbstractParams basic, String url) {
		AuthAccessToken authAccessToken = null;
		// 刷新网页授权凭证
		try {
			if (StringUtils.isEmpty(url)) {
				url = WechatConfig.REFRESH_OAUTH_TOKEN_URL;
			}
			String result = HttpReqUtil.HttpsDefaultExecute(SystemConstant.GET_METHOD, url, basic.getParams(), null, null);
			authAccessToken = JsonUtil.fromJsonString(result, AuthAccessToken.class);
		} catch (Exception e) {
			logger.debug("error" + e.getMessage());
		}
		return authAccessToken;
	}

	/**
	 * 通过网页授权获取用户信息
	 * 
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	public AuthUserInfo getAuthUserInfo(String accessToken, String openid) {
		AuthUserInfo authUserInfo = null;
		// 通过网页授权获取用户信息
		Map<String, String> params = new TreeMap<>();
		params.put("openid", openid);
		params.put("access_token", accessToken);
		String result = HttpReqUtil.HttpsDefaultExecute(SystemConstant.GET_METHOD, WechatConfig.SNS_USERINFO_URL, params,
				null, null);
		try {
			authUserInfo = JsonUtil.fromJsonString(result, AuthUserInfo.class);
		} catch (JsonSyntaxException e) {
			logger.debug("transfer exception");
		}
		return authUserInfo;
	}

	/**
	 * 检验授权凭证（access_token）是否有效
	 * 
	 * @param accessToken
	 *            网页授权接口调用凭证
	 * @param openid
	 *            用户的唯一标识
	 * @return { "errcode":0,"errmsg":"ok"}表示成功 { "errcode":40003,"errmsg":"invalid
	 *         openid"}失败
	 */
	public ResultState authToken(String accessToken, String openid) {
		ResultState state = null;
		Map<String, String> params = new TreeMap<>();
		params.put("access_token", accessToken);
		params.put("openid", openid);
		String jsonResult = HttpReqUtil.HttpDefaultExecute(SystemConstant.GET_METHOD,
				WechatConfig.CHECK_SNS_AUTH_STATUS_URL, params, "", null);
		state = JsonUtil.fromJsonString(jsonResult, ResultState.class);
		return state;
	}

	/**
	 * 获取jsapi_ticket 调用微信JS接口的临时票据
	 * 
	 * @return
	 */
	public String getTicket(String accessToken) {
		JsapiTicket jsapiTicket = null;
		Map<String, String> params = new TreeMap<>();
		params.put("access_token", accessToken);
		params.put("type", "jsapi");
		String result = HttpReqUtil.HttpDefaultExecute(SystemConstant.GET_METHOD, WechatConfig.GET_TICKET_URL, params,
				"", null);
		jsapiTicket = JsonUtil.fromJsonString(result, JsapiTicket.class);
		if (jsapiTicket.getErrcode() == 0) {
			return jsapiTicket.getTicket();
		}
		return null;
	}
}