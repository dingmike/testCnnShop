$(function () {
    $("#jqGrid").Grid({
        url: '../cnnlearnresult/list',
        colModel: [
			{label: 'id', name: 'id', index: 'id', key: true, hidden: true},
			{label: '', name: 'userid', index: 'userid', width: 80},
			{label: '', name: 'learnTypeId', index: 'learn_type_id', width: 80},
			{label: '', name: 'username', index: 'username', width: 80},
			{label: '', name: 'nickname', index: 'nickname', width: 80},
			{label: '21天后打卡结果1：成功，0失败', name: 'result', index: 'result', width: 80},
			{label: '添加时间', name: 'addTime', index: 'add_time', width: 80},
			{label: '更新时间', name: 'updateTime', index: 'update_time', width: 80}]
    });
});

let vm = new Vue({
	el: '#rrapp',
	data: {
        showList: true,
        title: null,
		cnnLearnResult: {},
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
			vm.cnnLearnResult = {};
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
            let url = vm.cnnLearnResult.id == null ? "../cnnlearnresult/save" : "../cnnlearnresult/update";
            Ajax.request({
			    url: url,
                params: JSON.stringify(vm.cnnLearnResult),
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
				    url: "../cnnlearnresult/delete",
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
                url: "../cnnlearnresult/info/"+id,
                async: true,
                successCallback: function (r) {
                    vm.cnnLearnResult = r.cnnLearnResult;
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