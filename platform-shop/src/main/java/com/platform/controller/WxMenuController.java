package com.platform.controller;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Map;

        import com.platform.utils.MapUtils;
        import org.apache.shiro.authz.annotation.RequiresPermissions;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.RequestBody;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.RestController;

        import com.platform.entity.WxMenuEntity;
        import com.platform.service.WxMenuService;
        import com.platform.utils.PageUtils;
        import com.platform.utils.Query;
        import com.platform.utils.R;
        import org.apache.commons.lang.StringUtils;
/**
 * Controller
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2019-01-15 15:06:35
 */
@RestController
@RequestMapping("wxmenu")
public class WxMenuController {
    @Autowired
    private WxMenuService wxMenuService;



    @RequestMapping("/child")
    @RequiresPermissions("wxmenu:list")
    public R child(@RequestParam Map<String, Object> params) {
        //查询列表数据
//        Query query = new Query(params);
//
//        List<WxMenuEntity> wxMenuList = wxMenuService.queryList(query);
//        int total = wxMenuService.queryTotal(query);
//
//        PageUtils pageUtil = new PageUtils(wxMenuList, total, query.getLimit(), query.getPage());
//
//        return R.ok().put("page", pageUtil);
        List<WxMenuEntity> list = new ArrayList<>();
        List<Map> treeList = new ArrayList<>();

//        if(params.get("pid")!=null)

//        String pid = params.get("pid");
        System.out.println(params);
//        if(StringUtils.isBlank(params.get("pid"))){
//            wxMenuService
//            return
//        }
        List<WxMenuEntity> menulist = wxMenuService.queryListByParentId(params);


        for (WxMenuEntity menu : menulist) {
            if (wxMenuService.queryCountByPid(menu.getParentid()) > 0) {
                menu.setHaschildren(1);
            }

            Map map = MapUtils.beanToMap(menu);
//            NutMap map = Lang.obj2nutmap(menu);
            map.put("expanded", false);

            map.put("children", new ArrayList<>());
            treeList.add(map);
        }
        return R.ok().put("data", treeList);
        /*List<WxMenuEntity> list = new ArrayList<>();
        List<Map> treeList = new ArrayList<>();
        Cnd cnd = Cnd.NEW();
        if (Strings.isBlank(pid)) {
            cnd.and(Cnd.exps("parentId", "=", "").or("parentId", "is", null));
        } else {
            cnd.and("parentId", "=", pid);
        }
        cnd.asc("location").asc("path");
        list = wxMenuService.query(cnd);
        for (Wx_menu menu : list) {
            if (wxMenuService.count(Cnd.where("parentId", "=", menu.getId())) > 0) {
                menu.setHasChildren(true);
            }
            NutMap map = Lang.obj2nutmap(menu);
            map.addv("expanded", false);
            map.addv("children", new ArrayList<>());
            treeList.add(map);
        }
        return Result.success().addData(treeList);*/

    }


    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wxmenu:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<WxMenuEntity> wxMenuList = wxMenuService.queryList(query);
        int total = wxMenuService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(wxMenuList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("wxmenu:info")
    public R info(@PathVariable("id") String id) {
            WxMenuEntity wxMenu = wxMenuService.queryObject(id);

        return R.ok().put("wxMenu", wxMenu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wxmenu:save")
    public R save(@RequestBody WxMenuEntity wxMenu) {
            wxMenuService.save(wxMenu);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wxmenu:update")
    public R update(@RequestBody WxMenuEntity wxMenu) {
            wxMenuService.update(wxMenu);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wxmenu:delete")
    public R delete(@RequestBody String[] ids) {
            wxMenuService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<WxMenuEntity> list = wxMenuService.queryList(params);

        return R.ok().put("list", list);
    }
}
