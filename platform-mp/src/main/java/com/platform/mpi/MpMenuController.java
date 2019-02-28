package com.platform.mpi;

import com.platform.annotation.IgnoreAuth;
import com.platform.service.MpMenuService;
import com.platform.utils.R;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.mp.bean.menu.WxMpGetSelfMenuInfoResult;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 公众号菜单Controller
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-10-30 13:01:47
 */
@Controller
@RequestMapping("/mpi/menu")
public class MpMenuController {
    @Autowired
    private MpMenuService mpMenuService;

    @IgnoreAuth
    @GetMapping("/create")
    @ResponseBody
    public String menuCreate(@RequestBody WxMenu wxMenu) throws Exception {
        return mpMenuService.menuCreate(wxMenu);
    }

    @IgnoreAuth
    @GetMapping("/menuCreateSample")
    @ResponseBody
    public String menuCreateSample() throws Exception {
        return mpMenuService.menuCreate(initMenu());
    }

    /**
     * 初始化菜单，
     * TODO 后面可以考虑把菜单存入数据库来维护
     *
     * @return
     */
    public WxMenu initMenu() {
        WxMenuButton mainBtn1 = new WxMenuButton();
        mainBtn1.setName("官网1");
        mainBtn1.setType(WxConsts.MenuButtonType.VIEW);
        mainBtn1.setUrl("http://www.sina.cn");

        WxMenuButton btn21 = new WxMenuButton();
        btn21.setName("便利主义超市1");
        btn21.setType(WxConsts.MenuButtonType.MINIPROGRAM);
        btn21.setUrl("http://mp.weixin.qq.com");
        btn21.setAppId("wx2a601dffd92609df");
        btn21.setPagePath("/pages/index/index");

        WxMenuButton btn22 = new WxMenuButton();
        btn22.setName("海数据在线");
        btn22.setType(WxConsts.MenuButtonType.MINIPROGRAM);
        btn22.setUrl("http://mp.weixin.qq.com");
        btn22.setAppId("wx69de7e7034266a70");
        btn22.setPagePath("/pages/index/index");

        WxMenuButton mainBtn2 = new WxMenuButton();
        mainBtn2.setName("客户案例");
        mainBtn2.getSubButtons().add(btn21);
        mainBtn2.getSubButtons().add(btn22);

        WxMenuButton btn31 = new WxMenuButton();
        btn31.setName("历史消息");
        btn31.setType(WxConsts.MenuButtonType.VIEW);
        btn31.setUrl("https://www.baidu.com");

        WxMenuButton btn32 = new WxMenuButton();
        btn32.setName("商务合作");
        btn32.setType(WxConsts.MenuButtonType.VIEW);
        btn32.setUrl("https://www.baidu.com");

        WxMenuButton btn33 = new WxMenuButton();
        btn33.setName("更多资讯");
        btn33.setType(WxConsts.MenuButtonType.VIEW);
        btn33.setUrl("https://www.baidu.com");

        WxMenuButton btn34 = new WxMenuButton();
        btn34.setName("曾获荣誉");
        btn34.setType(WxConsts.MenuButtonType.VIEW);
        btn34.setUrl("https://www.baidu.com");

        WxMenuButton mainBtn3 = new WxMenuButton();
        mainBtn3.setName("更多");
        mainBtn3.getSubButtons().add(btn31);
        mainBtn3.getSubButtons().add(btn32);
        mainBtn3.getSubButtons().add(btn33);
        mainBtn3.getSubButtons().add(btn34);

        WxMenu menu = new WxMenu();
        menu.getButtons().add(mainBtn1);
        menu.getButtons().add(mainBtn2);
        menu.getButtons().add(mainBtn3);
        return menu;
    }

    @IgnoreAuth
    @GetMapping("/create/{json}")
    @ResponseBody
    public String menuCreate(@PathVariable String json) throws Exception {
        return mpMenuService.menuCreate(json);
    }

    @IgnoreAuth
    @GetMapping("/delete")
    public void menuDelete() throws Exception {
        mpMenuService.menuDelete();
    }

    @IgnoreAuth
    @GetMapping("/delete/{menuId}")
    public void menuDelete(@PathVariable String menuId) throws Exception {
        mpMenuService.menuDelete(menuId);
    }

    @IgnoreAuth
    @GetMapping("/get")
    @ResponseBody
    public WxMpMenu menuGet() throws Exception {
        return mpMenuService.menuGet();
    }

    @IgnoreAuth
    @GetMapping("/menuTryMatch/{userid}")
    @ResponseBody
    public WxMenu menuTryMatch(@PathVariable String userid) throws Exception {
        return mpMenuService.menuTryMatch(userid);
    }

    @IgnoreAuth
    @GetMapping("/getSelfMenuInfo")
    @ResponseBody
    public WxMpGetSelfMenuInfoResult getSelfMenuInfo() throws Exception {
        return mpMenuService.getSelfMenuInfo();
    }

    /**
     * 获取本地设置的菜单
     *
     * @return
     */
    @IgnoreAuth
    @RequestMapping("/getMenu")
    @ResponseBody
    public R getMenu() {
        return R.ok().put("menu", initMenu());
    }
}
