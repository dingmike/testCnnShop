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

let vm = new Vue({
    el: '#rrapp',
    data() {
        return {
            loading: false,
            columns7 : [
                {
                    title: '规格名称',
                    key: 'specificationName'
                },
                {
                    title: '规格值',
                    key: 'goodsSpecificationList',
                    render: (h, params) => {
                        debugger
                        return h('Select', {
                                props:{
                                    value: this.goodsSpecs[params.index].goodsName,
                                },
                                on: {
                                    'on-change':(event) => {
                                        this.goodsSpecs[params.index].goodsName = event;
                                        console.log(vm)
                                    }
                                },
                            },
                            this.goodsSpecs[params.index].goodsSpecificationList.map(function(obj){
                                return h('Option', {
                                    props: {value: obj.id}
                                }, obj.value);
                            })
                        );

                    }
                }
            ],

            showList: true,
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
            goodsSpecs: [],
            productId: '',
            type: ''
        }

    },
    methods: {
        show: function () {
            this.visible = true;
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
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
            vm.showList = false;
            vm.title = "修改";
            vm.type = 'update';

            vm.getInfo(id)
        },
        changeGoods: function (opt) {
            let goodsId = opt.value;
            this.productId = goodsId;
            vm.queryGoodsSpec(goodsId);
            $.get("../goods/info/" + goodsId, function (r) {
                if (vm.type == 'add') {
                    vm.product.goodsSn = r.goods.goodsSn;
                    vm.product.goodsNumber = r.goods.goodsNumber;
                    vm.product.retailPrice = r.goods.retailPrice;
                    vm.product.marketPrice = r.goods.marketPrice;
                }
                /*$.get("../goodsspecification/queryAll?goodsId=" + goodsId + "&specificationId=1", function (r) {
                    vm.colors = r.list;
                });
                $.get("../goodsspecification/queryAll?goodsId=" + goodsId + "&specificationId=2", function (r) {
                    vm.guiges = r.list;
                });
                $.get("../goodsspecification/queryAll?goodsId=" + goodsId + "&specificationId=4", function (r) {
                    vm.weights = r.list;
                });*/
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
            vm.showList = true;
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
        queryGoodsSpec: function (goods_id) {
            $.get("../goodsspecification/queryGoodsSpec/" + goods_id, function (r) {
                debugger
                vm.goodsSpecs = r.list;
                // vm.loading = false;
            });
        },
        renderChooseSec(){
            debugger
            for(let i=0; i<this.attribute.length; i++){
                $.get("../goodsspecification/queryAll?goodsId=" + this.productId + "&specificationId=" +this.attribute[i] , (r)=>{
                    // vm['colors'+ this.attribute[i]] = r.list;
                    vm['colors'+ i] = r.list;
                });
            }
            console.log(vm)

        }
    }
});