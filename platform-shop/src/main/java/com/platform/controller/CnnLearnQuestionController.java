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

import com.platform.entity.CnnLearnQuestionEntity;
import com.platform.service.CnnLearnQuestionService;
import com.platform.utils.PageUtils;
import com.platform.utils.Query;
import com.platform.utils.R;

/**
 * Controller
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-22 14:52:32
 */
@RestController
@RequestMapping("cnnlearnquestion")
public class CnnLearnQuestionController {
    @Autowired
    private CnnLearnQuestionService cnnLearnQuestionService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("cnnlearnquestion:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<CnnLearnQuestionEntity> cnnLearnQuestionList = cnnLearnQuestionService.queryList(query);
        int total = cnnLearnQuestionService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(cnnLearnQuestionList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cnnlearnquestion:info")
    public R info(@PathVariable("id") Integer id) {
        CnnLearnQuestionEntity cnnLearnQuestion = cnnLearnQuestionService.queryObject(id);

        return R.ok().put("cnnLearnQuestion", cnnLearnQuestion);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("cnnlearnquestion:save")
    public R save(@RequestBody CnnLearnQuestionEntity cnnLearnQuestion) {
        cnnLearnQuestionService.save(cnnLearnQuestion);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("cnnlearnquestion:update")
    public R update(@RequestBody CnnLearnQuestionEntity cnnLearnQuestion) {

        cnnLearnQuestionService.update(cnnLearnQuestion);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("cnnlearnquestion:delete")
    public R delete(@RequestBody Integer[]ids) {
        cnnLearnQuestionService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<CnnLearnQuestionEntity> list = cnnLearnQuestionService.queryList(params);

        return R.ok().put("list", list);
    }
}
