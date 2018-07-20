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

import com.platform.entity.CnnLearnContentEntity;
import com.platform.service.CnnLearnContentService;
import com.platform.utils.PageUtils;
import com.platform.utils.Query;
import com.platform.utils.R;

/**
 * Controller
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-20 16:31:32
 */
@RestController
@RequestMapping("cnnlearncontent")
public class CnnLearnContentController {
    @Autowired
    private CnnLearnContentService cnnLearnContentService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cnnlearncontent:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<CnnLearnContentEntity> cnnLearnContentList = cnnLearnContentService.queryList(query);
        int total = cnnLearnContentService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(cnnLearnContentList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cnnlearncontent:info")
    public R info(@PathVariable("id") Integer id) {
        CnnLearnContentEntity cnnLearnContent = cnnLearnContentService.queryObject(id);

        return R.ok().put("cnnLearnContent", cnnLearnContent);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cnnlearncontent:save")
    public R save(@RequestBody CnnLearnContentEntity cnnLearnContent) {
        cnnLearnContentService.save(cnnLearnContent);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cnnlearncontent:update")
    public R update(@RequestBody CnnLearnContentEntity cnnLearnContent) {
        cnnLearnContentService.update(cnnLearnContent);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cnnlearncontent:delete")
    public R delete(@RequestBody Integer[]ids) {
        cnnLearnContentService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<CnnLearnContentEntity> list = cnnLearnContentService.queryList(params);

        return R.ok().put("list", list);
    }
}
