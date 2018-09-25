    * 1.1 springframework4.3.7.RELEASE
    * 1.2 mybatis3.4.1
    * 1.3 shiro1.3.2
    * 1.4 servlet3.1.0
    * 1.5 druid1.0.28
    * 1.6 slf4j1.7.19
    * 1.7 fastjson1.2.30
    * 1.8 poi3.15
    * 1.9 velocity1.7
    * 1.10 alisms1.0
    * 1.11 quartz2.2.3
    * 1.12 mysql5.1.39
    * 1.13 swagger2.4
    
    六：短信服务平台
    
    需要短信验证码、短信通知、短信营销的客户进群私聊我
    a 配置短信平台账户信息
    b 向外提供发送短信接口：
    http://域名:端口/api/sendSms?mobile=13000000000,15209831990&content=发送的短信内容  
    安全起见，需配置有效IP地址。platform.properties -> sms.validIp
    
    配置环境（推荐jdk1.8、maven3.3、tomcat8、mysql5.7）
    创建数据库
    初始化sql脚本 /doc/platform.sql
    导入项目到IDE中
    导入支付证书至/platform-shop/src/main/resources/cert/目录下（申请商户号、开通微信支付、下载支付证书）
    修改配置文件 /platform-admin/src/main/resources/dev/platform.properties
    jdbc.url
    jdbc.username
    jdbc.password
    wx.appId
    wx.secret
    wx.mchId
    wx.paySignKey
    wx.notifyUrl
    sms.validIp
    启动后台项目（参照启动手册）
    打开微信开发者工具
    导入 /wx-mall填写appId
    修改 /wx-mall/config/app.js里NewApiRootUrl的值
    使用eclipse启动项目后默认访问路径
    http://localhost:8080/platform-framework
    使用idea启动项目后默认访问路径
    http://localhost:8080
   
   
   
    ##提交 > git commit -a -m "zhushi"
    ##上传 >  git push
    ##生产环境打包 platform-wechat-mall>mvn package -P prod
    
    1. mvn clean 清理上次生成的
    
    ##跳过测试打包
    2. mvn package -Dmaven.test.skip=true
    
