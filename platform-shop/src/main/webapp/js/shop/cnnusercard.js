$(function () {
    $("#jqGrid").Grid({
        url: '../cnnusercard/list',
        colModel: [
			//{label: 'id', name: 'id', index: 'id', key: true, hidden: true},
			//{label: '学习类型', name: 'learnTypeId', index: 'learn_type_id', width: 80},
			{label: '学习类型', name: 'learnType', index: 'learn_type', width: 80},
			//{label: '用户', name: 'userid', index: 'userid', width: 80},
			{label: '微信昵称', name: 'nickname', index: 'nickname', width: 80},
			{label: '用户名称', name: 'username', index: 'username', width: 100},
			{label: '打第几天卡', name: 'cardDay', index: 'card_day', width: 40},
			{label: '打卡日', name: 'day', index: 'day', width: 40},
			{label: '打卡月', name: 'month', index: 'month', width: 30},
			{label: '打卡年', name: 'year', index: 'year', width:30},
			{label: '是否有效', name: 'reasonable', index: 'reasonable', width: 40,
				formatter: function (value) {
					return transStatus(value);
				}},
			{label: '打卡时间', name: 'cardTime', index: 'card_time', width: 80,
				formatter: function (value) {
					return transDate(value, 'yyyy-MM-dd hh:mm:ss');
				}}
			//{label: '添加时间', name: 'addTime', index: 'add_time', width: 80},
			//{label: '更新时间', name: 'updateTime', index: 'update_time', width: 80}
		]
    });
});

let vm = new Vue({
	el: '#rrapp',
	data: {
        showList: true,
        title: null,
		cnnUserCard: {},
		ruleValidate: {
			name: [
				{required: true, message: '名称不能为空', trigger: 'blur'}
			]
		},
		q: {
            username: '',
            nickname: ''
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
			vm.cnnUserCard = {};
			vm.learnTypes = [];
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
            let url = vm.cnnUserCard.id == null ? "../cnnusercard/save" : "../cnnusercard/update";
            Ajax.request({
			    url: url,
                params: JSON.stringify(vm.cnnUserCard),
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
				    url: "../cnnusercard/delete",
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
                url: "../cnnusercard/info/"+id,
                async: true,
                successCallback: function (r) {
                    vm.cnnUserCard = r.cnnUserCard;
                }
            });
		},
		reload: function (event) {
			vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
			$("#jqGrid").jqGrid('setGridParam', {
                postData: {'username': vm.q.username, 'nickname': vm.q.nickname},
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