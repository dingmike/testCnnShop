$(function () {
    $("#jqGrid").Grid({
        url: '../cnnlearnresult/list',
        colModel: [
			{label: 'id', name: 'id', index: 'id', key: true, hidden: true},
			// {label: '', name: 'userid', index: 'userid', width: 80},
			{label: '微信ID', align : "center", name: 'username', index: 'username', width: 40},
			{label: '微信昵称',align : "center", name: 'nickname', index: 'nickname', width: 40},
            {label: '学习类型',align : "center", name: 'learnType', index: 'learn_type', width: 60},
			{label: '最终打卡结果',align : "center", name: 'result', index: 'result', width: 50,
                formatter: function (value) {
                    return toSuccessOrFailture(value);
                }
			},
            {label: '失败原因',align : "left", name: 'reason', index: 'reason', width: 220},
            {label: '生成时间',align : "center", name: 'addTime', index: 'add_time', width: 60,
                formatter: function (value) {
                    return transDate(value, 'yyyy-MM-dd hh:mm:ss');
                }},
		]
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
		},
        learnTypes: []
	},
	methods: {
        /**
         * 获取学习类型
         */
        getLearnTypes: function () {
            $.get("../cnnlearntype/queryAll", function (r) {
                vm.learnTypes = r.list;
            });
        },
		query: function () {
			vm.reload();
		},
		add: function () {
			vm.showList = false;
			vm.title = "新增";
			vm.cnnLearnResult = {};
            vm.getLearnTypes();
		},
		update: function (event) {
            let id = getSelectedRow("#jqGrid");
			if (id == null) {
				return;
			}
			vm.showList = false;
            vm.title = "修改";
            vm.getLearnTypes();
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