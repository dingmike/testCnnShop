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

import com.platform.entity.CnnLearnTypeEntity;
import com.platform.service.CnnLearnTypeService;
import com.platform.utils.PageUtils;
import com.platform.utils.Query;
import com.platform.utils.R;

/**
 * Controller
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-04 20:11:47
 */
@RestController
@RequestMapping("cnnlearntype")
public class CnnLearnTypeController {
    @Autowired
    private CnnLearnTypeService cnnLearnTypeService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cnnlearntype:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<CnnLearnTypeEntity> cnnLearnTypeList = cnnLearnTypeService.queryList(query);
        int total = cnnLearnTypeService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(cnnLearnTypeList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cnnlearntype:info")
    public R info(@PathVariable("id") Integer id) {
        CnnLearnTypeEntity cnnLearnType = cnnLearnTypeService.queryObject(id);

        return R.ok().put("cnnLearnType", cnnLearnType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cnnlearntype:save")
    public R save(@RequestBody CnnLearnTypeEntity cnnLearnType) {
        cnnLearnTypeService.save(cnnLearnType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cnnlearntype:update")
    public R update(@RequestBody CnnLearnTypeEntity cnnLearnType) {
        cnnLearnTypeService.update(cnnLearnType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cnnlearntype:delete")
    public R delete(@RequestBody Integer[]ids) {
        cnnLearnTypeService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<CnnLearnTypeEntity> list = cnnLearnTypeService.queryList(params);

        return R.ok().put("list", list);
    }
}
