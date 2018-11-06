package com.platform.service;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.Map;

        import com.platform.dao.CnnUserFormidMapper;
        import com.platform.entity.CnnUserFormidEntity;

/**
 * Service类
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-11-05 14:46:55
 */
@Service
public class CnnUserFormidService{
    @Autowired
    private CnnUserFormidMapper cnnUserFormidDao;

    public CnnUserFormidEntity queryObject(Integer id) {
        return cnnUserFormidDao.queryObject(id);
    }
    public CnnUserFormidEntity queryObjectByUserid(Integer userid) {
        return cnnUserFormidDao.queryObjectByUserid(userid);
    }

    public List<CnnUserFormidEntity> queryList(Map<String, Object> map) {
        return cnnUserFormidDao.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return cnnUserFormidDao.queryTotal(map);
    }

    public int save(CnnUserFormidEntity cnnUserFormid) {
        return cnnUserFormidDao.save(cnnUserFormid);
    }

    public int update(CnnUserFormidEntity cnnUserFormid) {
        return cnnUserFormidDao.update(cnnUserFormid);
    }

    public int delete(Integer id) {
        return cnnUserFormidDao.delete(id);
    }


    public int deleteBatch(Integer[] ids) {
        return cnnUserFormidDao.deleteBatch(ids);
    }
}