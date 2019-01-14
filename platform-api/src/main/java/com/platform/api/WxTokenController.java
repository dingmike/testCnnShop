package com.platform.api;

import com.platform.annotation.IgnoreAuth;
import com.platform.util.AesException;
import com.platform.util.ApiBaseAction;
import com.platform.util.WXPublicUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/wxpublic")
public class WxTokenController{
  /**
   * 微信消息接收和token验证
   */
  @IgnoreAuth
  @GetMapping("verifyWXToken")
  public String verifyWXToken(HttpServletRequest request) throws AesException {
    System.out.println(request.getParameter("signature"));
    String msgSignature = request.getParameter("signature");
    String msgTimestamp = request.getParameter("timestamp");
    String msgNonce = request.getParameter("nonce");
    String echostr = request.getParameter("echostr");
    if (WXPublicUtils.verifyUrl(msgSignature, msgTimestamp, msgNonce)) {
      return echostr;
    }
    return null;
  }
}