package com.platform.entity.wxAuth.req;

import com.platform.entity.base.AbstractParams;

import java.util.Map;
import java.util.TreeMap;

/**
 * 刷新token请求
 * 
 * @author phil
 * @date 2017年7月2日
 *
 */
public class RefreshAuthTokenParams extends AbstractParams {

	private static final long serialVersionUID = -1748872326563685295L;
	private String appid;
	private String grant_type = "refresh_token";
	private String refresh_token;

	public RefreshAuthTokenParams(String appid, String grant_type, String refresh_token) {
		super();
		this.appid = appid;
		this.grant_type = grant_type;
		this.refresh_token = refresh_token;
	}

	/**
	 * 参数组装
	 * 
	 * @return
	 */
	public Map<String, String> getParams() {
		Map<String, String> params = new TreeMap<String, String>();
		params.put("appid", this.appid);
		params.put("grant_type", this.grant_type);
		params.put("refresh_token", this.refresh_token);
		return params;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getGrant_type() {
		return grant_type;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
}
