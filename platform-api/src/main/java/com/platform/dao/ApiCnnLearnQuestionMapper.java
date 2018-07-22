package com.platform.dao;

import com.platform.entity.CnnLearnQuestionVo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Dao
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-07-22 14:52:32
 */
public interface ApiCnnLearnQuestionMapper extends BaseDao<CnnLearnQuestionVo> {
       List<CnnLearnQuestionVo>  queryQuestionByDaysAndType(@Param("genusdays") Integer genusdays, @Param("learnTypeId") Integer type);
}
