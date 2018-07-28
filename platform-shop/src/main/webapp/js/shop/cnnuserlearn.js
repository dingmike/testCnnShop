$(function () {
    $("#jqGrid").Grid({
        url: '../cnnuserlearn/list',
        colModel: [
			{label: 'id', name: 'id', index: 'id', key: true, hidden: true},
			{label: '微信名', name: 'nickname', index: 'nickname', width: 80},
			{label: '头像', name: 'avatar', index: 'avatar', width: 80, formatter: function (value) {
                return transImg(value);
            }},
			{label: '学习类型', name: 'learnType', index: 'learn_type', width: 80},
			// {label: '用户ID', name: 'userid', index: 'userid', width: 80},
			{label: '已打卡阅读天数', name: 'unlocks', index: 'unlocks', width: 80},
			{label: '微信表单ID', name: 'formId', index: 'formId', width: 80},
			/*{label: '提醒打卡时间', name: 'setupTime', index: 'setup_time', width: 80, formatter:function (value) {
				return formatDateTime(value, 'hh:mm');
            }},*/

            {label: '提醒打卡时间', name: 'setupTime', index: 'setup_time', width: 80},
            {label: '是否开始', name: 'startStatus', index: 'start_status', width: 80,  formatter: function (value) {
                return transIsNot(value);
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
		cnnUserLearn: {},
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
		query: function () {
			vm.reload();
		},
		add: function () {
			vm.showList = false;
			vm.title = "新增";
			vm.cnnUserLearn = {};
            vm.learnTypes = [];
            vm.getLearnTypes();
		},
		update: function (event) {
			debugger
            let id = getSelectedRow("#jqGrid");
			if (id == null) {
				return;
			}
			vm.showList = false;
            vm.title = "修改";
            vm.getLearnTypes();
            vm.getInfo(id)
		},
        /**
         * 获取学习类型
         */
        getLearnTypes: function () {
        	debugger
            $.get("../cnnlearntype/queryAll", function (r) {
                vm.learnTypes = r.list;
            });
        },
		saveOrUpdate: function (event) {
            let url = vm.cnnUserLearn.id == null ? "../cnnuserlearn/save" : "../cnnuserlearn/update";
            Ajax.request({
			    url: url,
                params: JSON.stringify(vm.cnnUserLearn),
                type: "POST",
			    contentType: "application/json;charset=utf-8",
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
				    url: "../cnnuserlearn/delete",
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
                url: "../cnnuserlearn/info/"+id,
                async: true,
                successCallback: function (r) {
                    // r.cnnUserLearn.setupTime = new Date(r.cnnUserLearn.setupTime); // 时间转换为UTC时间
                    // r.cnnUserLearn.setupTime = formatDateTime(r.cnnUserLearn.setupTime, 'hh:mm');
                    vm.cnnUserLearn = r.cnnUserLearn;
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