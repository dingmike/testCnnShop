package com.platform.controller;

        import java.util.List;
        import java.util.Map;

        import org.apache.shiro.authz.annotation.RequiresPermissions;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.RequestBody;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.RestController;

        import com.platform.entity.UserReadNewsEntity;
        import com.platform.service.UserReadNewsService;
        import com.platform.utils.PageUtils;
        import com.platform.utils.Query;
        import com.platform.utils.R;

/**
 * Controller
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-10-12 12:46:27
 */
@RestController
@RequestMapping("userreadnews")
public class UserReadNewsController {
    @Autowired
    private UserReadNewsService userReadNewsService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("userreadnews:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<UserReadNewsEntity> userReadNewsList = userReadNewsService.queryList(query);
        int total = userReadNewsService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(userReadNewsList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userreadnews:info")
    public R info(@PathVariable("id") Integer id) {
            UserReadNewsEntity userReadNews = userReadNewsService.queryObject(id);

        return R.ok().put("userReadNews", userReadNews);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("userreadnews:save")
    public R save(@RequestBody UserReadNewsEntity userReadNews) {
            userReadNewsService.save(userReadNews);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("userreadnews:update")
    public R update(@RequestBody UserReadNewsEntity userReadNews) {
            userReadNewsService.update(userReadNews);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("userreadnews:delete")
    public R delete(@RequestBody Integer[] ids) {
            userReadNewsService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<UserReadNewsEntity> list = userReadNewsService.queryList(params);

        return R.ok().put("list", list);
    }
}
