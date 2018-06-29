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

import com.platform.entity.CnnNewsEntity;
import com.platform.service.CnnNewsService;
import com.platform.utils.PageUtils;
import com.platform.utils.Query;
import com.platform.utils.R;

/**
 * Controller
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-06-29 09:17:59
 */
@RestController
@RequestMapping("cnnnews")
public class CnnNewsController {
    @Autowired
    private CnnNewsService cnnNewsService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cnnnews:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<CnnNewsEntity> cnnNewsList = cnnNewsService.queryList(query);
        int total = cnnNewsService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(cnnNewsList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cnnnews:info")
    public R info(@PathVariable("id") Integer id) {
        CnnNewsEntity cnnNews = cnnNewsService.queryObject(id);

        return R.ok().put("cnnNews", cnnNews);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cnnnews:save")
    public R save(@RequestBody CnnNewsEntity cnnNews) {
        System.out.println("newsData: " + cnnNews);
        cnnNewsService.save(cnnNews);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cnnnews:update")
    public R update(@RequestBody CnnNewsEntity cnnNews) {
        cnnNewsService.update(cnnNews);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cnnnews:delete")
    public R delete(@RequestBody Integer[]ids) {
        cnnNewsService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<CnnNewsEntity> list = cnnNewsService.queryList(params);

        return R.ok().put("list", list);
    }
}
