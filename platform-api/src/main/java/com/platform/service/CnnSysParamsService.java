package com.platform.service;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import com.platform.dao.CnnSysParamsMapper;
        import com.platform.entity.CnnSysParamsVo;

/**
 * Service实现类
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-11-21 17:07:03
 */
@Service
public class CnnSysParamsService {
    @Autowired
    private CnnSysParamsMapper cnnSysParamsDao;

    public CnnSysParamsVo queryObject(Integer id) {
        return cnnSysParamsDao.queryObject(id);
    }


}