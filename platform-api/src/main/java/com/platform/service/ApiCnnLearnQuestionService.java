package com.platform.service;

import com.platform.dao.ApiCnnLearnQuestionMapper;
import com.platform.entity.CnnLearnQuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-22 14:52:32
 */
@Service
public class ApiCnnLearnQuestionService {
    @Autowired
    private ApiCnnLearnQuestionMapper cnnLearnQuestionMapper;

    /**
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     */
   public List<CnnLearnQuestionVo> queryQuestionByDaysAndType(Integer days, Integer type){
       return cnnLearnQuestionMapper.queryQuestionByDaysAndType(days, type);
   };
    public CnnLearnQuestionVo queryObject(Integer id) {
        return cnnLearnQuestionMapper.queryObject(id);
    }


    public List<CnnLearnQuestionVo> queryList(Map<String, Object> map) {
        return cnnLearnQuestionMapper.queryList(map);
    }


    public int queryTotal(Map<String, Object> map) {
        return cnnLearnQuestionMapper.queryTotal(map);
    }


    public void save(CnnLearnQuestionVo brand) {
        cnnLearnQuestionMapper.save(brand);
    }


    public void update(CnnLearnQuestionVo brand) {
        cnnLearnQuestionMapper.update(brand);
    }


    public void delete(Integer id) {
        cnnLearnQuestionMapper.delete(id);
    }


    public void deleteBatch(Integer[] ids) {
        cnnLearnQuestionMapper.deleteBatch(ids);
    }
}
