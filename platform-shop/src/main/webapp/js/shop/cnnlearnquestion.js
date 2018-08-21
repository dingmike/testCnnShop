$(function () {
    $("#jqGrid").Grid({
        url: '../cnnlearnquestion/list',
        colModel: [
			//{label: 'id', name: 'id', index: 'id', key: true, hidden: true},
			//{label: '学习类型ID', name: 'learnTypeId', index: 'learn_type_id', width: 80},
			{label: '学习类型', name: 'learnType', index: 'learn_type', width: 80},
			{label: '学习天数', name: 'genusDays', index: 'genus_days', width: 60},
			{label: '重点内容', name: 'oraleitem', index: 'oraleitem', width: 80},
			{label: '关键词', name: 'keyword', index: 'keyword', width: 80},
			{label: '中文问题', name: 'oralech', index: 'oralech', width: 80},
			{label: '其它问题', name: 'oralejp', index: 'oralejp', width: 80},
			{label: '解题分析', name: 'analysis', index: 'analysis', width: 80},
			{label: '选项1', name: 'opt1', index: 'opt1', width: 80},
			{label: '选项2', name: 'opt2', index: 'opt2', width: 80},
			{label: '选项3', name: 'opt3', index: 'opt3', width: 80},
			{label: '选项4', name: 'opt4', index: 'opt4', width: 80},
			{label: '内容语音链接', name: 'oralesound', index: 'oralesound', width: 80},
			{label: '答案选项', name: 'copt', index: 'copt', width: 60},
			{label: '问答题吗？', name: 'typeof', index: 'typeof', width: 60,
				formatter: function (value) {
					return transIsNot(value);
				}},
			{label: '添加时间', name: 'addTime', index: 'add_time', width: 80,
				formatter: function (value) {
					return transDate(value, 'yyyy-MM-dd hh:mm:ss');
				}},
			{label: '修改时间', name: 'updateTime', index: 'update_time', width: 80,
				formatter: function (value) {
					return transDate(value, 'yyyy-MM-dd hh:mm:ss');
				}}]
    });
});

let vm = new Vue({
	el: '#rrapp',
	data: {
        showList: true,
        title: null,
		cnnLearnQuestion: {},
		ruleValidate: {
			name: [
				{required: true, message: '名称不能为空', trigger: 'blur'}
			]
		},
		q: {
			genusDays: '',
			learnTypeId: ''
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
			vm.getLearnTypes();
			vm.reload();
		},
		add: function () {
			vm.showList = false;
			vm.title = "新增";
			vm.cnnLearnQuestion = {};
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
            vm.getInfo(id);
		},
		saveOrUpdate: function (event) {
            let url = vm.cnnLearnQuestion.id == null ? "../cnnlearnquestion/save" : "../cnnlearnquestion/update";
            Ajax.request({
			    url: url,
                params: JSON.stringify(vm.cnnLearnQuestion),
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
				    url: "../cnnlearnquestion/delete",
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
                url: "../cnnlearnquestion/info/"+id,
                async: true,
                successCallback: function (r) {
                    vm.cnnLearnQuestion = r.cnnLearnQuestion;
                }
            });
		},
		reload: function (event) {
			vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
			$("#jqGrid").jqGrid('setGridParam', {
                postData: {'genusDays': vm.q.genusDays, 'learnTypeId': vm.q.learnTypeId},
                page: page
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
		},
        reloadSearch: function() {
            vm.q = {
				genusDays: '',
				learnTypeId: ''
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
        },
		handleSuccessPicUrl: function (res, file) {
			vm.cnnLearnQuestion.oralesound = file.response.url;
		},
		handleFormatError: function (file) {
			this.$Notice.warning({
				title: '文件格式不正确',
				desc: '文件 ' + file.name + ' 格式不正确，请上传 mp3 格式的文件。'
			});
		},
		handleMaxSize: function (file) {
			this.$Notice.warning({
				title: '超出文件大小限制',
				desc: '文件 ' + file.name + ' 太大，不能超过 10M。'
			});
		}
	}
});