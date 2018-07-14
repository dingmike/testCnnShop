$(function () {
    let goodsId = getQueryString("goodsId");
    let url = '../product/list';
    if (goodsId) {
        url += '?goodsId=' + goodsId;
    }
    $("#jqGrid").Grid({
        url: url,
        colModel: [
            {label: 'id', name: 'id', index: 'id', key: true, hidden: true},
            {label: '商品', name: 'goodsName', index: 'goods_id', width: 120},
            {
                label: '商品规格',
                name: 'specificationValue',
                index: 'goods_specification_ids',
                width: 100,
                formatter: function (value, options, row) {
                    return value.replace(row.goodsName + " ", '');
                }
            },
            {label: '商品序列号', name: 'goodsSn', index: 'goods_sn', width: 80},
            {label: '商品库存', name: 'goodsNumber', index: 'goods_number', width: 80},
            {label: '零售价格(元)', name: 'retailPrice', index: 'retail_price', width: 80},
            {label: '市场价格(元)', name: 'marketPrice', index: 'market_price', width: 80}]
    });
});

const aComponent = Vue.extend({
    props: ['text'],
    template: '<li>A Component: {{ text }}</li>'
});
let vm = new Vue({
    el: '#rrapp',
    components: {
        aComponent
    },
    data: {
        testItems:[],
        showList: {show:true},
        title: null,
        product: {},
        ruleValidate: {
            name: [
                {required: true, message: '名称不能为空', trigger: 'blur'}
            ]
        },
        q: {
            goodsName: ''
        },
        goodss: [],
        attribute: [],
        color: [], guige: [], weight: [],
        colors: [],
        guiges: [],
        weights: [],
        specifications: [],
        goodsSpec: [],
        specsList: [{specId: 1,specName: '颜色',id: 5,value:"白", options:[{
            "id": 5,
            "goodsId": 1181000,
            "specificationId": 1,
            "value": "烟白灰",
            "picUrl": "http://yanxuan.nosdn.127.net/36f64a7161b67e7fb8ea45be32ecfa25.png?quality=90&amp;thumbnail=200x200&amp;imageView",
            "goodsName": "母亲节礼物-舒适安睡组合",
            "specificationName": "颜色"
        },{
            "id": 4,
            "goodsId": 1181000,
            "specificationId": 1,
            "value": "玛瑙红",
            "picUrl": "http://yanxuan.nosdn.127.net/29442127f431a1a1801c195905319427.png?quality=90&thumbnail=200x200&imageView",
            "goodsName": "母亲节礼物-舒适安睡组合",
            "specificationName": "颜色"
        }]},{specId: 2, specName: '规格', id: 5,value:"白",options:[
            {
                "id": 8,
                "goodsId": 1181000,
                "specificationId": 2,
                "value": "1.9米",
                "picUrl": null,
                "goodsName": "母亲节礼物-舒适安睡组合",
                "specificationName": "规格"
            },
            {
                "id": 2,
                "goodsId": 1181000,
                "specificationId": 2,
                "value": "1.8m床垫*1+枕头*2",
                "picUrl": "",
                "goodsName": "母亲节礼物-舒适安睡组合",
                "specificationName": "规格"
            },
            {
                "id": 1,
                "goodsId": 1181000,
                "specificationId": 2,
                "value": "1.5m床垫*1+枕头*2",
                "picUrl": "",
                "goodsName": "母亲节礼物-舒适安睡组合",
                "specificationName": "规格"
            }
        ]}],
        type: ''
    },
    methods: {
        testAdd (name, text) {
            debugger
            vm.showList.show = false;
            vm.testItems.push({
                component: name,
                text: text
            })
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList.show = false;
            vm.title = "新增";
            vm.product = {};
            vm.getGoodss();
            vm.getAllSpecification();
            vm.type = 'add';
        },
        update: function (event) {
            let id = getSelectedRow("#jqGrid");
            if (id == null) {
                return;
            }
            vm.showList.show = false;
            vm.title = "修改";
            vm.type = 'update';

            vm.getInfo(id)
        },
        changeGoods: function (opt) {
            let goodsId = opt.value;

            debugger
            vm.getSpecification(goodsId);

            $.get("../goods/info/" + goodsId, function (r) {
                if (vm.type == 'add') {
                    vm.product.goodsSn = r.goods.goodsSn;
                    vm.product.goodsNumber = r.goods.goodsNumber;
                    vm.product.retailPrice = r.goods.retailPrice;
                    vm.product.marketPrice = r.goods.marketPrice;
                    vm.product.goodsId = r.goods.goodsId;
                }
                $.get("../goodsspecification/queryAll?goodsId=" + goodsId + "&specificationId=1", function (r) {
                    vm.colors = r.list;
                });
                $.get("../goodsspecification/queryAll?goodsId=" + goodsId + "&specificationId=2", function (r) {
                    vm.guiges = r.list;
                });
                $.get("../goodsspecification/queryAll?goodsId=" + goodsId + "&specificationId=4", function (r) {
                    vm.weights = r.list;
                });
            });
        },
        changeRenderSpecOptions (chooseSpecId) {
          /*  this.items.push({
                'component': component,
                'text': text,
            })*/


            $.get("../goodsspecification/queryAll?goodsId=" + vm.product.goodsId + "&specificationId=" + chooseSpecId, function (r) {
                vm.colors = r.list;
            });
        },
        saveOrUpdate: function (event) {
            let url = vm.product.id == null ? "../product/save" : "../product/update";
            vm.product.goodsSpecificationIds = vm.color + '_' + vm.guige + '_' + vm.weight;

            Ajax.request({
                type: "POST",
                url: url,
                contentType: "application/json",
                params: JSON.stringify(vm.product),
                successCallback: function (r) {
                    alert('操作成功', function (index) {
                        vm.reload();
                    });
                }
            });


        },
        del: function (event) {
            let ids = getSelectedRows("#jqGrid");
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                Ajax.request({
                    type: "POST",
                    url: "../product/delete",
                    contentType: "application/json",
                    params: JSON.stringify(ids),
                    successCallback: function (r) {
                        alert('操作成功', function (index) {
                            $("#jqGrid").trigger("reloadGrid");
                        });
                    }
                });


            });
        },
        getInfo: function (id) {
            $.get("../product/info/" + id, function (r) {
                vm.product = r.product;
                vm.getGoodss();
            });
        },
        reload: function (event) {
            vm.showList.show = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'goodsName': vm.q.goodsName},
                page: page
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
        },
        handleSubmit: function (name) {
            handleSubmitValidate(this, name, function () {
                vm.saveOrUpdate()
            });
        },
        handleReset: function (name) {
            handleResetForm(this, name);
        },
        getGoodss: function () {
            $.get("../goods/queryAll/", function (r) {
                vm.goodss = r.list;
            });
        },
        getAllSpecification: function () {
            $.get("../specification/queryAll/", function (r) {
                debugger
                vm.specifications = r.list;
            });
        },
        getSpecification: function (goods_id) {
            $.get("../goodsspecification/queryGoodsSpec/" + goods_id, function (r) {
                debugger
                vm.goodsSpec = r.list;
            });
        },
    }
});