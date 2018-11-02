package com.platform.api;

import com.platform.annotation.LoginUser;

import com.platform.entity.UserVo;
import com.platform.service.*;
import com.platform.util.ApiBaseAction;

import org.apache.commons.collections.MapUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 作者: @author admin <br>
 * 时间: 2017-08-11 08:32<br>
 * 描述: ApiIndexController <br>
 */
@RestController
@RequestMapping("/api/gdOrder")
public class ApiCnnGongduOrder extends ApiBaseAction {
    @Autowired
    private ApiGongduOrderService apiGongduOrderService;
    @Autowired
    private ApiCnnLearnTypeService apiCnnLearnTypeService;

    /**
     * 提交共读支付订单生成订单号
     */
    @RequestMapping("submit")
    public Object submit(@LoginUser UserVo loginUser) {
        Map resultObj = null;
        // 传入学习类型ID
        try {
            resultObj = apiGongduOrderService.submit(getJsonRequest(), loginUser);
            if (null != resultObj) {
                return toResponsObject(MapUtils.getInteger(resultObj, "errno"), MapUtils.getString(resultObj, "errmsg"), resultObj.get("data"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toResponsFail("提交失败");
    }
}
