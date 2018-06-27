package com.platform.dao;

import com.platform.entity.NewsEntity;

/**
 * Dao
 *
 * @author zdj
 * @email mikeding@qq.com
 * @date 20178-06-27 21:19:49
 */
public interface NewsDao extends BaseDao<NewsEntity> {
    Integer queryMaxId();
}
