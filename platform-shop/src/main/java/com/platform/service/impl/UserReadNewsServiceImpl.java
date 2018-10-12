package com.platform.service.impl;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.Map;

        import com.platform.dao.UserReadNewsDao;
        import com.platform.entity.UserReadNewsEntity;
        import com.platform.service.UserReadNewsService;

/**
 * Service实现类
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2018-10-12 12:46:27
 */
@Service("userReadNewsService")
public class UserReadNewsServiceImpl implements UserReadNewsService {
    @Autowired
    private UserReadNewsDao userReadNewsDao;

    @Override
    public UserReadNewsEntity queryObject(Integer id) {
        return userReadNewsDao.queryObject(id);
    }

    @Override
    public List<UserReadNewsEntity> queryList(Map<String, Object> map) {
        return userReadNewsDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return userReadNewsDao.queryTotal(map);
    }

    @Override
    public int save(UserReadNewsEntity userReadNews) {
        return userReadNewsDao.save(userReadNews);
    }

    @Override
    public int update(UserReadNewsEntity userReadNews) {
        return userReadNewsDao.update(userReadNews);
    }

    @Override
    public int delete(Integer id) {
        return userReadNewsDao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[] ids) {
        return userReadNewsDao.deleteBatch(ids);
    }
}