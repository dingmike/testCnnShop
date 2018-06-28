package com.platform.service.impl;

import com.platform.annotation.DataFilter;
import com.platform.dao.GoodsAttributeDao;
import com.platform.dao.GoodsDao;
import com.platform.dao.GoodsGalleryDao;
import com.platform.dao.NewsDao;
import com.platform.entity.NewsEntity;
import com.platform.entity.*;
import com.platform.service.NewsService;
import com.platform.utils.RRException;
import com.platform.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-21 21:19:49
 */
@Service("NewsService")
public class NewsServiceImpl implements NewsService {
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private GoodsAttributeDao goodsAttributeDao;
    @Autowired
    private NewsDao newsDao;
    @Autowired
    private GoodsGalleryDao goodsGalleryDao;

    @Override
    public NewsEntity queryObject(Integer id) {
        return newsDao.queryObject(id);
    }

    @Override
    @DataFilter(userAlias = "nideshop_goods.create_user_id", deptAlias = "nideshop_goods.create_user_dept_id")
    public List<NewsEntity> queryList(Map<String, Object> map) {
        return newsDao.queryList(map);
    }

    @Override
    @DataFilter(userAlias = "nideshop_goods.create_user_id", deptAlias = "nideshop_goods.create_user_dept_id")
    public int queryTotal(Map<String, Object> map) {
        return newsDao.queryTotal(map);
    }

    @Override
    @Transactional
    public int save(NewsEntity goods) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        Map<String, Object> map = new HashMap<>();
        map.put("name", goods.getName());
        List<NewsEntity> list = queryList(map);
        if (null != list && list.size() != 0) {
            throw new RRException("商品名称已存在！");
        }
        Integer id = newsDao.queryMaxId() + 1;
        goods.setId(id);

        //保存产品信息
        NewsEntity newsEntity = new NewEntity();
        newsEntity.setGoodsId(id);
        newsEntity.setGoodsSn(goods.getGoodsSn());
        newsEntity.setGoodsNumber(goods.getGoodsNumber());
        newsEntity.setRetailPrice(goods.getRetailPrice());
        newsEntity.setMarketPrice(goods.getMarketPrice());
        newsEntity.setGoodsSpecificationIds("");
        NewsDao.save(NewsEntity);

        goods.setAddTime(new Date());
        goods.setPrimaryProductId(productEntity.getId());

        //保存商品详情页面显示的属性
        List<GoodsAttributeEntity> attributeEntityList = goods.getAttributeEntityList();
        if (null != attributeEntityList && attributeEntityList.size() > 0) {
            for (GoodsAttributeEntity item : attributeEntityList) {
                item.setGoodsId(id);
                goodsAttributeDao.save(item);
            }
        }

        //商品轮播图
        List<GoodsGalleryEntity> galleryEntityList = goods.getGoodsImgList();
        if (null != galleryEntityList && galleryEntityList.size() > 0) {
            for (GoodsGalleryEntity galleryEntity : galleryEntityList) {
                galleryEntity.setGoodsId(id);
                goodsGalleryDao.save(galleryEntity);
            }
        }

        goods.setIsDelete(0);
        goods.setCreateUserDeptId(user.getDeptId());
        goods.setCreateUserId(user.getUserId());
        goods.setUpdateUserId(user.getUserId());
        goods.setUpdateTime(new Date());
        return newsDao.save(goods);
    }

    @Override
    @Transactional
    public int update(NewsEntity goods) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        List<GoodsAttributeEntity> attributeEntityList = goods.getAttributeEntityList();
        if (null != attributeEntityList && attributeEntityList.size() > 0) {
            for (GoodsAttributeEntity goodsAttributeEntity : attributeEntityList) {
                int result = goodsAttributeDao.updateByGoodsIdAttributeId(goodsAttributeEntity);
                if (result == 0) {
                    goodsAttributeDao.save(goodsAttributeEntity);
                }
            }
        }
        goods.setUpdateUserId(user.getUserId());
        goods.setUpdateTime(new Date());
        //商品轮播图
        //修改时全删全插
        List<GoodsGalleryEntity> galleryEntityList = goods.getGoodsImgList();
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("goodsId", goods.getId());
        goodsGalleryDao.deleteByGoodsId(map);

        if (null != galleryEntityList && galleryEntityList.size() > 0) {
            for (GoodsGalleryEntity galleryEntity : galleryEntityList) {
                galleryEntity.setGoodsId(goods.getId());
                goodsGalleryDao.save(galleryEntity);
            }
        }
        return newsDao.update(goods);
    }

    @Override
    public int delete(Integer id) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        NewsEntity goodsEntity = queryObject(id);
        goodsEntity.setIsDelete(1);
        goodsEntity.setIsOnSale(0);
        goodsEntity.setUpdateUserId(user.getUserId());
        goodsEntity.setUpdateTime(new Date());
        return newsDao.update(NewsEntity);
    }

    @Override
    @Transactional
    public int deleteBatch(Integer[] ids) {
        int result = 0;
        for (Integer id : ids) {
            result += delete(id);
        }
        return result;
    }

    @Override
    @Transactional
    public int back(Integer[] ids) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        int result = 0;
        for (Integer id : ids) {
            NewsEntity newsEntity = queryObject(id);
            newsEntity.setIsDelete(0);
            newsEntity.setIsOnSale(1);
            newsEntity.setUpdateUserId(user.getUserId());
            newsEntity.setUpdateTime(new Date());
            result += newsDao.update(newsEntity);
        }
        return result;
    }

    @Override
    public int enSale(Integer id) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        NewsEntity newsEntity = queryObject(id);
        if (1 == newsEntity.getIsOnSale()) {
            throw new RRException("此商品已处于上架状态！");
        }
        newsEntity.setIsOnSale(1);
        newsEntity.setUpdateUserId(user.getUserId());
        newsEntity.setUpdateTime(new Date());
        return newsDao.update(newsEntity);
    }

    @Override
    public int unSale(Integer id) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        GoodsEntity goodsEntity = queryObject(id);
        if (0 == goodsEntity.getIsOnSale()) {
            throw new RRException("此商品已处于下架状态！");
        }
        goodsEntity.setIsOnSale(0);
        goodsEntity.setUpdateUserId(user.getUserId());
        goodsEntity.setUpdateTime(new Date());
        return goodsDao.update(goodsEntity);
    }
}
