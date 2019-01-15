package com.platform.service.impl;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.Map;

        import com.platform.dao.WxMenuDao;
        import com.platform.entity.WxMenuEntity;
        import com.platform.service.WxMenuService;

/**
 * Service实现类
 *
 * @author admin
 * @email 2252277509@qq.com
 * @date 2019-01-15 15:06:35
 */
@Service("wxMenuService")
public class WxMenuServiceImpl implements WxMenuService {
    @Autowired
    private WxMenuDao wxMenuDao;

    @Override
    public WxMenuEntity queryObject(String id) {
        return wxMenuDao.queryObject(id);
    }

    @Override
    public List<WxMenuEntity> queryList(Map<String, Object> map) {
        return wxMenuDao.queryList(map);
    }

    @Override
    public List<WxMenuEntity> queryListByParentId(Map<String, Object> map) {
        return wxMenuDao.queryListByParentId(map);
    }

    @Override
    public Integer queryCountByPid(String pid) {
        return wxMenuDao.queryCountByPid(pid);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return wxMenuDao.queryTotal(map);
    }

    @Override
    public int save(WxMenuEntity wxMenu) {
        return wxMenuDao.save(wxMenu);
    }

    @Override
    public int update(WxMenuEntity wxMenu) {
        return wxMenuDao.update(wxMenu);
    }

    @Override
    public int delete(String id) {
        return wxMenuDao.delete(id);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return wxMenuDao.deleteBatch(ids);
    }
}