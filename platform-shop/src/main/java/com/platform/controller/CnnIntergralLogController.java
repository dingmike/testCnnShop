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

        import com.platform.entity.CnnIntergralLogEntity;
        import com.platform.service.CnnIntergralLogService;
        import com.platform.utils.PageUtils;
        import com.platform.utils.Query;
        import com.platform.utils.R;

/**
 * Controller
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-09-28 16:54:10
 */
@RestController
@RequestMapping("cnnintergrallog")
public class CnnIntergralLogController {
    @Autowired
    private CnnIntergralLogService cnnIntergralLogService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cnnintergrallog:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<CnnIntergralLogEntity> cnnIntergralLogList = cnnIntergralLogService.queryList(query);
        int total = cnnIntergralLogService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(cnnIntergralLogList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cnnintergrallog:info")
    public R info(@PathVariable("id") Integer id) {
            CnnIntergralLogEntity cnnIntergralLog = cnnIntergralLogService.queryObject(id);

        return R.ok().put("cnnIntergralLog", cnnIntergralLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cnnintergrallog:save")
    public R save(@RequestBody CnnIntergralLogEntity cnnIntergralLog) {
            cnnIntergralLogService.save(cnnIntergralLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cnnintergrallog:update")
    public R update(@RequestBody CnnIntergralLogEntity cnnIntergralLog) {
            cnnIntergralLogService.update(cnnIntergralLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cnnintergrallog:delete")
    public R delete(@RequestBody Integer[] ids) {
            cnnIntergralLogService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<CnnIntergralLogEntity> list = cnnIntergralLogService.queryList(params);

        return R.ok().put("list", list);
    }
}
