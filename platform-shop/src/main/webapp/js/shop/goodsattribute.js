$(function () {
    $("#jqGrid").Grid({
        url: '../goodsattribute/list',
        colModel: [
			{label: 'id', name: 'id', index: 'id', key: true, hidden: true},
			{label: '商品名称', name: 'goodsName', index: 'goodsName', width:120},
			// {label: '商品序列号', name: 'goodsId', index: 'goods_id', width: 80},
			{label: '属性名称', name: 'attributeName', index: 'attributeName', width: 100},
			{label: '属性值', name: 'value', index: 'value', width: 120}]
    });
});

let vm = new Vue({
	el: '#rrapp',
	data: {
        showList: true,
        title: null,
		goodsAttribute: {},
		ruleValidate: {
			name: [
				{required: true, message: '名称不能为空', trigger: 'blur'}
			]
		},
		q: {
            goodsId: ''
		},
        attributeCategories: [],
        attributes: [],
        goodss: []
	},
    mounted() {
        this.getGoodss();
    },
	methods: {
        getGoodss: function () {
            $.get("../goods/queryAll/", function (r) {
                vm.goodss = r.list;
            });
        },
        getAttributeCategory: function () {
            $.get("../attributecategory/queryAll/", function (r) {
                vm.attributeCategories = r.list;
            });
        },
        changeCateId: function (cateId) {
            debugger
            vm.getAttributes(cateId);
        },
        getAttributes: function (attributeCategoryId) {
            $.get("../attribute/attributeList/" + attributeCategoryId, function (r) {
                vm.attributes = r.list;
            });
        },
      /*  getAttributesByCateId: function (cateId) {
            $.get("../attribute/attributeList/" + cateId, function (r) {
                vm.attributes = r.list;
            });
        },*/
		query: function () {
			vm.reload();
		},
		add: function () {
			vm.showList = false;
			vm.title = "新增";
			vm.goodsAttribute = {};
			vm.getGoodss();
			vm.getAttributeCategory();
		},
		update: function (event) {
            let id = getSelectedRow("#jqGrid");
			if (id == null) {
				return;
			}
			vm.showList = false;
            vm.title = "修改";
            vm.getGoodss();
            vm.getAttributeCategory();
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
            let url = vm.goodsAttribute.id == null ? "../goodsattribute/save" : "../goodsattribute/update";
            Ajax.request({
			    url: url,
                params: JSON.stringify(vm.goodsAttribute),
                type: "POST",
			    contentType: "application/json",
                successCallback: function (r) {
                    alert('操作成功', function (index) {
                        vm.reload();
                    });
                }
			});
		},
		del: function (event) {
            let ids = getSelectedRows("#jqGrid");
			if (ids == null){
				return;
			}

			confirm('确定要删除选中的记录？', function () {
                Ajax.request({
				    url: "../goodsattribute/delete",
                    params: JSON.stringify(ids),
                    type: "POST",
				    contentType: "application/json",
                    successCallback: function () {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
					}
				});
			});
		},
		getInfo: function(id){
            Ajax.request({
                url: "../goodsattribute/info/"+id,
                async: true,
                successCallback: function (r) {
                    vm.goodsAttribute = r.goodsAttribute;
                }
            });
		},
        // 搜索刷新
		reload: function (event) {
			vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
			$("#jqGrid").jqGrid('setGridParam', {
                postData: {'goodsId': vm.q.goodsId},
                page: page
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
		},
        reloadSearch: function() {
            vm.q = {
                goodsId: ''
            }
            vm.reload();
        },
        handleSubmit: function (name) {
            handleSubmitValidate(this, name, function () {
                vm.saveOrUpdate()
            });
        },
        handleReset: function (name) {
            handleResetForm(this, name);
        }
	}
});