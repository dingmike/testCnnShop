$(function () {
    $("#jqGrid").Grid({
        url: '../cnnintergrallog/list',
        colModel: [
			{label: 'id', name: 'id', index: 'id', key: true, hidden: true},
			{label: '学习类型', align : "center", name: 'learnTypeId', index: 'learn_type_id', width: 60, hidden: true},
			{label: '用户ID', align : "center", name: 'userid', index: 'userid', width: 80, hidden: true},
			{label: '用户名', align : "center", name: 'username', index: 'username', width: 80},
			{label: '微信昵称', align : "center", name: 'nickname', index: 'nickname', width: 80},
			{label: '能力券变化', align : "center", name: 'points', index: 'points', width: 80},
			{label: '打卡记录Id', align : "center", name: 'cardId', index: 'card_id', width: 40, hidden: true},
			{label: '能力券（加减）', align : "center",name: 'plusMins', index: 'plus_mins', width: 40,
                formatter: function (value) {
                    return toPlusMins(value);
                }},
            {label: '总数', align : "center", name: 'nowPoints', index: 'nowPoints', width: 60},
			{label: '记录说明', align : "center", name: 'memo', index: 'memo', width: 100},
			{label: '生成时间', align : "center", name: 'addTime', index: 'add_time', width: 60, formatter: function (value) {
                return transDate(value, 'yyyy-MM-dd hh:mm:ss');
            }},
			{label: '更新时间', align : "center",name: 'updateTime', index: 'update_time', width: 60, hidden: true, formatter: function (value) {
                return transDate(value, 'yyyy-MM-dd hh:mm:ss');
            }}]
    });
});

let vm = new Vue({
	el: '#rrapp',
	data: {
        showList: true,
        title: null,
		cnnIntergralLog: {},
		ruleValidate: {
			name: [
				{required: true, message: '名称不能为空', trigger: 'blur'}
			]
		},
		q: {
            username: '',
            nickname: ''
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function () {
			vm.showList = false;
			vm.title = "新增";
			vm.cnnIntergralLog = {};
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
            let url = vm.cnnIntergralLog.id == null ? "../cnnintergrallog/save" : "../cnnintergrallog/update";
            Ajax.request({
			    url: url,
                params: JSON.stringify(vm.cnnIntergralLog),
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
				    url: "../cnnintergrallog/delete",
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
                url: "../cnnintergrallog/info/"+id,
                async: true,
                successCallback: function (r) {
                    vm.cnnIntergralLog = r.cnnIntergralLog;
                }
            });
		},
		reload: function (event) {
			vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
			$("#jqGrid").jqGrid('setGridParam', {
                postData: {'username': vm.q.username, 'nickname':vm.q.nickname},
                page: page
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
		},
        reloadSearch: function() {
            vm.q = {
                username: '',
                nickname: ''
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