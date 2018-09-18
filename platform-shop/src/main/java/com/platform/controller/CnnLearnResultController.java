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

        import com.platform.entity.CnnLearnResultEntity;
        import com.platform.service.CnnLearnResultService;
        import com.platform.utils.PageUtils;
        import com.platform.utils.Query;
        import com.platform.utils.R;

/**
 * Controller
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-09-18 10:48:08
 */
@RestController
@RequestMapping("cnnlearnresult")
public class CnnLearnResultController {
    @Autowired
    private CnnLearnResultService cnnLearnResultService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cnnlearnresult:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<CnnLearnResultEntity> cnnLearnResultList = cnnLearnResultService.queryList(query);
        int total = cnnLearnResultService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(cnnLearnResultList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cnnlearnresult:info")
    public R info(@PathVariable("id") Integer id) {
            CnnLearnResultEntity cnnLearnResult = cnnLearnResultService.queryObject(id);

        return R.ok().put("cnnLearnResult", cnnLearnResult);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cnnlearnresult:save")
    public R save(@RequestBody CnnLearnResultEntity cnnLearnResult) {
            cnnLearnResultService.save(cnnLearnResult);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cnnlearnresult:update")
    public R update(@RequestBody CnnLearnResultEntity cnnLearnResult) {
            cnnLearnResultService.update(cnnLearnResult);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cnnlearnresult:delete")
    public R delete(@RequestBody Integer[] ids) {
            cnnLearnResultService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<CnnLearnResultEntity> list = cnnLearnResultService.queryList(params);

        return R.ok().put("list", list);
    }
}
