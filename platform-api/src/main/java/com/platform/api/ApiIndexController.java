package com.platform.api;

import com.github.pagehelper.PageHelper;

import com.platform.annotation.IgnoreAuth;
import com.platform.entity.*;
import com.platform.service.*;
import com.platform.util.ApiBaseAction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: @author admin <br>
 * 时间: 2017-08-11 08:32<br>
 * 描述: ApiIndexController <br>
 */
@Api(tags = "首页接口文档")
@RestController
@RequestMapping("/api/index")
public class ApiIndexController extends ApiBaseAction {
    @Autowired
    private ApiAdService adService;
    @Autowired
    private ApiCnnAdService cnnAdService;
    @Autowired
    private ApiUserLearnService userLearnService;
    @Autowired
    private ApiCnnNewsService cnnNewsService;
    @Autowired
    private ApiChannelService channelService;
    @Autowired
    private ApiGoodsService goodsService;
    @Autowired
    private ApiBrandService brandService;
    @Autowired
    private ApiTopicService topicService;
    @Autowired
    private ApiCategoryService categoryService;
    @Autowired
    private ApiCartService cartService;
    /**
     * 测试
     */
    @IgnoreAuth
//    @RequestMapping(value = "test", method = RequestMethod.GET)
    @GetMapping(value = "test")
    public Object test() {
        return toResponsMsgSuccess("请求成功yyy");
    }

    /**
     *  cnn首页数据
     */
    @ApiOperation(value = "cnn首页")
    @IgnoreAuth
//    @RequestMapping(value = "cnnIndex", method = RequestMethod.GET)
    @GetMapping(value = "cnnIndex")
    public Object cnnIndex() {
        String learnTypeId = request.getParameter("learnTypeId"); // 只获取一种学习类型的用户学习情况
        Map<String, Object> resultObj = new HashMap();
        Map param = new HashMap();
//        List<CnnAdVo> banner = cnnAdService.queryList(param);
        List<CnnAdVo> banner = cnnAdService.queryListByMediaType(1);
        //获取计划详情说明图片
        List<CnnAdVo> learnFilePics = cnnAdService.queryListByMediaType(2);
        resultObj.put("banner", banner);
        resultObj.put("learnFilePics", learnFilePics);
        param = new HashMap();
        List<CnnNewsVo> newsList = cnnNewsService.queryList(param);
        resultObj.put("newsList", newsList);
        param = new HashMap();
        param.put("learnTypeId", learnTypeId);
        List<UserLearnVo> userLearnList = userLearnService.queryList(param);
        resultObj.put("userLearnList", userLearnList);
        param = new HashMap();
        param.put("learnTypeId", learnTypeId);
        int sum = userLearnService.queryTotalByLearnTypeId(param);
        resultObj.put("userListTotal", sum);
        return toResponsSuccess(resultObj);
    }

    /**
     * app商城首页
     */
    @ApiOperation(value = "商城首页")
    @IgnoreAuth
//    @RequestMapping(value = "index", method = RequestMethod.GET)
    @GetMapping(value = "index")
    public Object index() {
        Map<String, Object> resultObj = new HashMap();
        //
//        Map param = new HashMap();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ad_position_id", 1);
        List<AdVo> banner = adService.queryList(param);
        resultObj.put("banner", banner);
        //
//        param = new HashMap();
        param = new HashMap<String, Object>();
        param.put("sidx", "sort_order ");
        param.put("order", "asc ");
        List<ChannelVo> channel = channelService.queryList(param);
        resultObj.put("channel", channel);
        //
//        param = new HashMap();
        param = new HashMap<String, Object>();
        param.put("is_new", 1);
        param.put("is_delete", 0);
        param.put("fields", "id, name, list_pic_url, retail_price");
        PageHelper.startPage(0, 4, false);
        List<GoodsVo> newGoods = goodsService.queryList(param);
        resultObj.put("newGoodsList", newGoods);
        //
//        param = new HashMap();
        param = new HashMap<String, Object>();
        param.put("is_hot", "1");
        param.put("is_delete", 0);
        PageHelper.startPage(0, 3, false);
        List<GoodsVo> hotGoods = goodsService.queryHotGoodsList(param);
        resultObj.put("hotGoodsList", hotGoods);
        // 当前购物车中
//        List<CartVo> cartList = new ArrayList();
        List<CartVo> cartList = new ArrayList<CartVo>();
        if (null != getUserId()) {
            //查询列表数据
//            Map cartParam = new HashMap();
            Map<String, Object> cartParam = new HashMap<String, Object>();
            cartParam.put("user_id", getUserId());
            cartList = cartService.queryList(cartParam);
        }
        if (null != cartList && cartList.size() > 0 && null != hotGoods && hotGoods.size() > 0) {
            for (GoodsVo goodsVo : hotGoods) {
                for (CartVo cartVo : cartList) {
                    if (goodsVo.getId().equals(cartVo.getGoods_id())) {
                        goodsVo.setCart_num(cartVo.getNumber());
                    }
                }
            }
        }
        //
//        param = new HashMap();
        param = new HashMap<String, Object>();
        param.put("is_new", 1);
        param.put("sidx", "new_sort_order ");
        param.put("order", "asc ");
        param.put("offset", 0);
        param.put("limit", 4);
        List<BrandVo> brandList = brandService.queryList(param);
        resultObj.put("brandList", brandList);

//        param = new HashMap();
        param = new HashMap<String, Object>();
        param.put("offset", 0);
        param.put("limit", 3);
        List<TopicVo> topicList = topicService.queryList(param);
        resultObj.put("topicList", topicList);

//        param = new HashMap();
        param = new HashMap<String, Object>();
        param.put("parent_id", 0);
        param.put("notName", "推荐");//<>
        List<CategoryVo> categoryList = categoryService.queryList(param);
//        List<Map> newCategoryList = new ArrayList<>();
        List<Map<String, Object>> newCategoryList = new ArrayList<>();

        for (CategoryVo categoryItem : categoryList) {
            param.remove("fields");
            param.put("parent_id", categoryItem.getId());
            List<CategoryVo> categoryEntityList = categoryService.queryList(param);
            List<Integer> childCategoryIds = new ArrayList<>();
            for (CategoryVo categoryEntity : categoryEntityList) {
                childCategoryIds.add(categoryEntity.getId());
            }
            //
//            param = new HashMap();
            param = new HashMap<String, Object>();
            param.put("categoryIds", childCategoryIds);
            param.put("fields", "id as id, name as name, list_pic_url as list_pic_url, retail_price as retail_price");
            PageHelper.startPage(0, 7, false);
            List<GoodsVo> categoryGoods = goodsService.queryList(param);
//            Map newCategory = new HashMap();
            Map<String, Object> newCategory = new HashMap<String, Object>();
            newCategory.put("id", categoryItem.getId());
            newCategory.put("name", categoryItem.getName());
            newCategory.put("goodsList", categoryGoods);
            newCategoryList.add(newCategory);
        }
        resultObj.put("categoryList", newCategoryList);
        return toResponsSuccess(resultObj);
    }

    /**
     * app首页new
     */
    @ApiOperation(value = "新商品信息")
    @IgnoreAuth
//    @RequestMapping(value = "newGoods", method = RequestMethod.GET)
    @GetMapping(value = "newGoods")
    public Object newGoods() {
        Map<String, Object> resultObj = new HashMap<String, Object>();
        //
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("is_new", 1);
        param.put("is_delete", 0);
        param.put("fields", "id, name, list_pic_url, retail_price");
        PageHelper.startPage(0, 4,false);
        List<GoodsVo> newGoods = goodsService.queryList(param);
        resultObj.put("newGoodsList", newGoods);
        //

        return toResponsSuccess(resultObj);
    }
    @ApiOperation(value = "新热门商品信息")
    @IgnoreAuth
//    @RequestMapping(value = "hotGoods", method = RequestMethod.GET)
    @GetMapping(value = "hotGoods")
    public Object hotGoods() {
        Map<String, Object> resultObj = new HashMap<String, Object>();
        //
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("is_hot", "1");
        param.put("is_delete", 0);
        PageHelper.startPage(0, 3,false);
        List<GoodsVo> hotGoods = goodsService.queryHotGoodsList(param);
        resultObj.put("hotGoodsList", hotGoods);
        //

        return toResponsSuccess(resultObj);
    }
    @ApiOperation(value = "topic")
    @IgnoreAuth
//    @RequestMapping(value = "topic", method = RequestMethod.GET)
    @GetMapping(value = "topic")
    public Object topic() {
        Map<String, Object> resultObj = new HashMap<String, Object>();
        //
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("offset", 0);
        param.put("limit", 3);
        List<TopicVo> topicList = topicService.queryList(param);
        resultObj.put("topicList", topicList);
        //

        return toResponsSuccess(resultObj);
    }
    @ApiOperation(value = "brand")
    @IgnoreAuth
//    @RequestMapping(value = "brand", method = RequestMethod.GET)
    @GetMapping(value = "brand")
    public Object brand() {
        Map<String, Object> resultObj = new HashMap<String, Object>();
        //
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("is_new", 1);
        param.put("sidx", "new_sort_order ");
        param.put("order", "asc ");
        param.put("offset", 0);
        param.put("limit", 4);
        List<BrandVo> brandList = brandService.queryList(param);
        resultObj.put("brandList", brandList);
        //

        return toResponsSuccess(resultObj);
    }
    @ApiOperation(value = "category")
    @IgnoreAuth
//    @RequestMapping(value = "category", method = RequestMethod.GET)
    @GetMapping(value = "category")
    public Object category() {
        Map<String, Object> resultObj = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        param = new HashMap<String, Object>();
        param.put("parent_id", 0);
        param.put("notName", "推荐");
        param.put("is_show", 1);
        List<CategoryVo> categoryList = categoryService.queryList(param);
        List< Map<String, Object>> newCategoryList = new ArrayList<>();

        for (CategoryVo categoryItem : categoryList) {
            param.remove("fields");
            param.put("parent_id", categoryItem.getId());
            param.put("is_show", 1);
            List<CategoryVo> categoryEntityList = categoryService.queryList(param);
            List<Integer> childCategoryIds = new ArrayList<>();
            for (CategoryVo categoryEntity : categoryEntityList) {
                childCategoryIds.add(categoryEntity.getId());
            }
            //
            param = new HashMap<String, Object>();
            param.put("categoryIds", childCategoryIds);
            param.put("fields", "id as id, name as name, list_pic_url as list_pic_url, retail_price as retail_price");
            param.put("is_delete", 0);
            param.put("is_on_sale", 1);
            param.put("is_hot", 1);
            param.put("is_new", 1);
            PageHelper.startPage(0, 7,false);
            List<GoodsVo> categoryGoods = goodsService.queryList(param);
            Map<String, Object> newCategory = new HashMap<String, Object>();
            newCategory.put("id", categoryItem.getId());
            newCategory.put("name", categoryItem.getName());
            newCategory.put("goodsList", categoryGoods);
            newCategoryList.add(newCategory);
        }
        resultObj.put("categoryList", newCategoryList);

        return toResponsSuccess(resultObj);
    }
    @ApiOperation(value = "banner")
    @IgnoreAuth
//    @RequestMapping(value = "banner", method = RequestMethod.GET)
    @GetMapping(value = "banner")
    public Object banner() {
        Map<String, Object> resultObj = new HashMap<String, Object>();
        //
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ad_position_id", 1);
        List<AdVo> banner = adService.queryList(param);
        resultObj.put("banner", banner);
        //

        return toResponsSuccess(resultObj);
    }
    @ApiOperation(value = "channel")
    @IgnoreAuth
//    @RequestMapping(value = "channel", method = RequestMethod.GET)
    @GetMapping(value = "channel")
    public Object channel() {
        Map<String, Object> resultObj = new HashMap<String, Object>();
        //
        Map<String, Object> param = new HashMap<String, Object>();
        param = new HashMap<String, Object>();
        param.put("sidx", "sort_order ");
        param.put("order", "asc ");
        List<ChannelVo> channel = channelService.queryList(param);
        resultObj.put("channel", channel);
        //

        return toResponsSuccess(resultObj);
    }

    /*IndexUrlNewGoods: NewApiRootUrl + 'index/newGoods', //
    IndexUrlHotGoods: NewApiRootUrl + 'index/hotGoods', //首页数据接口
    IndexUrlTopic: NewApiRootUrl + 'index/topic', //首页数据接口
    IndexUrlBrand: NewApiRootUrl + 'index/brand', //首页数据接口IndexUrlChannel
    IndexUrlCategory: NewApiRootUrl + 'index/category', //首页数据接口IndexUrlChannel
    IndexUrlBanner: NewApiRootUrl + 'index/banner', //首页数据接口IndexUrlChannel
    IndexUrlChannel: NewApiRootUrl + 'index/channel', //首页数据接口IndexUrlChannel
    */
}