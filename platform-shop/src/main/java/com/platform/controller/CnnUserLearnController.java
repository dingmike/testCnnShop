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

import com.platform.entity.CnnUserLearnEntity;
import com.platform.service.CnnUserLearnService;
import com.platform.utils.PageUtils;
import com.platform.utils.Query;
import com.platform.utils.R;

/**
 * Controller
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-04 21:13:26
 */
@RestController
@RequestMapping("cnnuserlearn")
public class CnnUserLearnController {
    @Autowired
    private CnnUserLearnService cnnUserLearnService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cnnuserlearn:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<CnnUserLearnEntity> cnnUserLearnList = cnnUserLearnService.queryList(query);
        int total = cnnUserLearnService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(cnnUserLearnList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cnnuserlearn:info")
    public R info(@PathVariable("id") Integer id) {
        CnnUserLearnEntity cnnUserLearn = cnnUserLearnService.queryObject(id);

        return R.ok().put("cnnUserLearn", cnnUserLearn);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cnnuserlearn:save")
    public R save(@RequestBody CnnUserLearnEntity cnnUserLearn) {
        cnnUserLearnService.save(cnnUserLearn);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cnnuserlearn:update")
    public R update(@RequestBody CnnUserLearnEntity cnnUserLearn) {
        cnnUserLearnService.update(cnnUserLearn);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cnnuserlearn:delete")
    public R delete(@RequestBody Integer[]ids) {
        cnnUserLearnService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<CnnUserLearnEntity> list = cnnUserLearnService.queryList(params);

        return R.ok().put("list", list);
    }
}
