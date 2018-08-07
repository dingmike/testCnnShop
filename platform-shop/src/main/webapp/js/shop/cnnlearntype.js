$(function () {
    $("#jqGrid").Grid({
        url: '../cnnlearntype/list',
        colModel: [
			{label: 'id', name: 'id', index: 'id', key: true, hidden: true},
			{label: '学习类型名称', name: 'learnType', index: 'learn_type', width: 80},
			{label: '价格', name: 'productPrice', index: 'product_price', width: 80},
            {label: '学习天数', name: 'genusdays', index: 'genusdays', width: 80},
            {
                label: '状态', name: 'status', index: 'status', width: 80, formatter: function (value) {
                return value == 0 ?
                    '<span class="label label-danger">禁用</span>' :
                    '<span class="label label-success">启用</span>';
            }},
			{label: '添加时间', name: 'addTime', index: 'add_time', width: 80, formatter: function (value) {
                return transDate(value, 'yyyy-MM-dd hh:mm:ss');
            }},
			{label: '更新时间', name: 'updateTime', index: 'update_time', width: 80, formatter: function (value) {
                return transDate(value, 'yyyy-MM-dd hh:mm:ss');
            }}]
    });
});

let vm = new Vue({
	el: '#rrapp',
	data: {
        showList: true,
        title: null,
		cnnLearnType: {},
		ruleValidate: {
			name: [
				{required: true, message: '名称不能为空', trigger: 'blur'}
			]
		},
		q: {
		    name: ''
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function () {
			vm.showList = false;
			vm.title = "新增";
			vm.cnnLearnType = {};
		},
		update: function (event) {
            let id = getSelectedRow("#jqGrid");
			if (id == null) {
				return;
			}
			vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
            let url = vm.cnnLearnType.id == null ? "../cnnlearntype/save" : "../cnnlearntype/update";
            Ajax.request({
			    url: url,
                params: JSON.stringify(vm.cnnLearnType),
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
				    url: "../cnnlearntype/delete",
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
                url: "../cnnlearntype/info/"+id,
                async: true,
                successCallback: function (r) {
                    vm.cnnLearnType = r.cnnLearnType;
                }
            });
		},
		reload: function (event) {
			vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
			$("#jqGrid").jqGrid('setGridParam', {
                postData: {'name': vm.q.name},
                page: page
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
		},
        reloadSearch: function() {
            vm.q = {
                name: ''
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