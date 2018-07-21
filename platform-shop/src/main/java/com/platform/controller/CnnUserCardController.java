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

import com.platform.entity.CnnUserCardEntity;
import com.platform.service.CnnUserCardService;
import com.platform.utils.PageUtils;
import com.platform.utils.Query;
import com.platform.utils.R;

/**
 * Controller
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-21 17:16:22
 */
@RestController
@RequestMapping("cnnusercard")
public class CnnUserCardController {
    @Autowired
    private CnnUserCardService cnnUserCardService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cnnusercard:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<CnnUserCardEntity> cnnUserCardList = cnnUserCardService.queryList(query);
        int total = cnnUserCardService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(cnnUserCardList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cnnusercard:info")
    public R info(@PathVariable("id") Integer id) {
        CnnUserCardEntity cnnUserCard = cnnUserCardService.queryObject(id);

        return R.ok().put("cnnUserCard", cnnUserCard);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cnnusercard:save")
    public R save(@RequestBody CnnUserCardEntity cnnUserCard) {
        cnnUserCardService.save(cnnUserCard);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cnnusercard:update")
    public R update(@RequestBody CnnUserCardEntity cnnUserCard) {
        cnnUserCardService.update(cnnUserCard);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cnnusercard:delete")
    public R delete(@RequestBody Integer[]ids) {
        cnnUserCardService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<CnnUserCardEntity> list = cnnUserCardService.queryList(params);

        return R.ok().put("list", list);
    }
}
