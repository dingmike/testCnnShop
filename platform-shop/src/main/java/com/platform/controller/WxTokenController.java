package com.platform.controller;

import com.platform.utils.AesException;
import com.platform.utils.WXPublicUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("wxpublic")
public class WxTokenController {
  /**
   * 微信消息接收和token验证
   */
  @RequestMapping("/verify_wx_token")
  public String verifyWXToken(HttpServletRequest request) throws AesException {
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