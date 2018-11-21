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

        import com.platform.entity.CnnSysParamsEntity;
        import com.platform.service.CnnSysParamsService;
        import com.platform.utils.PageUtils;
        import com.platform.utils.Query;
        import com.platform.utils.R;

/**
 * Controller
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-11-21 17:07:03
 */
@RestController
@RequestMapping("cnnsysparams")
public class CnnSysParamsController {
    @Autowired
    private CnnSysParamsService cnnSysParamsService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cnnsysparams:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<CnnSysParamsEntity> cnnSysParamsList = cnnSysParamsService.queryList(query);
        int total = cnnSysParamsService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(cnnSysParamsList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cnnsysparams:info")
    public R info(@PathVariable("id") Integer id) {
            CnnSysParamsEntity cnnSysParams = cnnSysParamsService.queryObject(id);

        return R.ok().put("cnnSysParams", cnnSysParams);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cnnsysparams:save")
    public R save(@RequestBody CnnSysParamsEntity cnnSysParams) {
            cnnSysParamsService.save(cnnSysParams);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cnnsysparams:update")
    public R update(@RequestBody CnnSysParamsEntity cnnSysParams) {
            cnnSysParamsService.update(cnnSysParams);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cnnsysparams:delete")
    public R delete(@RequestBody Integer[] ids) {
            cnnSysParamsService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<CnnSysParamsEntity> list = cnnSysParamsService.queryList(params);

        return R.ok().put("list", list);
    }
}
