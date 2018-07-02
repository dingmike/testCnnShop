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

import com.platform.entity.CnnAdEntity;
import com.platform.service.CnnAdService;
import com.platform.utils.PageUtils;
import com.platform.utils.Query;
import com.platform.utils.R;

/**
 * Controller
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-02 11:42:33
 */
@RestController
@RequestMapping("cnnad")
public class CnnAdController {
    @Autowired
    private CnnAdService cnnAdService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cnnad:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<CnnAdEntity> cnnAdList = cnnAdService.queryList(query);
        int total = cnnAdService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(cnnAdList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cnnad:info")
    public R info(@PathVariable("id") Integer id) {
        CnnAdEntity cnnAd = cnnAdService.queryObject(id);

        return R.ok().put("cnnAd", cnnAd);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cnnad:save")
    public R save(@RequestBody CnnAdEntity cnnAd) {
        cnnAdService.save(cnnAd);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cnnad:update")
    public R update(@RequestBody CnnAdEntity cnnAd) {
        cnnAdService.update(cnnAd);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cnnad:delete")
    public R delete(@RequestBody Integer[]ids) {
        cnnAdService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<CnnAdEntity> list = cnnAdService.queryList(params);

        return R.ok().put("list", list);
    }
}
