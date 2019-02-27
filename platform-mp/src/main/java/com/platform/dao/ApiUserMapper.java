package com.platform.dao;

import com.platform.entity.SmsLogVo;
import com.platform.entity.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2017-03-23 15:22:06
 */
public interface ApiUserMapper extends BaseDao<UserVo> {

    UserVo queryByMobile(String mobile);

    List<UserVo> queryListAll(); //查询所有userid

    UserVo queryByOpenId(@Param("openId") String openId);

    /**
     * 获取用户最后一条短信
     *
     * @param user_id
     * @return
     */
    SmsLogVo querySmsCodeByUserId(@Param("user_id") Long user_id);

    /**
     * 保存短信
     *
     * @param smsLogVo
     * @return
     */
    int saveSmsCodeLog(SmsLogVo smsLogVo);



    int updateIntergral(Map<String, Object> map);
}
