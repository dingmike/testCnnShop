$(function () {
    $("#jqGrid").Grid({
        url: '../userreadnews/list',
        colModel: [
			{label: 'id', name: 'id', index: 'id', key: true, hidden: true},
			{label: '用户ID', name: 'userid', index: 'userid', width:40},
			{label: '用户名称', name: 'username', index: 'username', width: 80},
			{label: '微信昵称', name: 'nickname', index: 'nickname', width: 80},
			{label: '阅读文章标题', name: 'title', index: 'title', width: 80},
			{label: '阅读用时(s)',  align : "center",name: 'useTime', index: 'useTime', width: 80},
            {
                label: '状态（>30s）',align : "center", name: 'isToday', index: 'isToday', width: 40,
                formatter: function (value) {
                    return transIsNot(value);
                }
            },
			{label: '打卡时间', align : "center", name: 'addTime', index: 'add_time', width: 80, formatter: function (value) {
        return transDate(value, 'yyyy-MM-dd hh:mm:ss');
    }}]
    });
});

let vm = new Vue({
	el: '#rrapp',
	data: {
        showList: true,
        title: null,
		userReadNews: {},
		ruleValidate: {
			name: [
				{required: true, message: '名称不能为空', trigger: 'blur'}
			]
		},
        q: {
            username: '',
            nickname: ''
        },
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function () {
			vm.showList = false;
			vm.title = "新增";
			vm.userReadNews = {};
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
            let url = vm.userReadNews.id == null ? "../userreadnews/save" : "../userreadnews/update";
            Ajax.request({
			    url: url,
                params: JSON.stringify(vm.userReadNews),
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
				    url: "../userreadnews/delete",
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
                url: "../userreadnews/info/"+id,
                async: true,
                successCallback: function (r) {
                    vm.userReadNews = r.userReadNews;
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