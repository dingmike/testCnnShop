$(function () {
    $("#jqGrid").Grid({
        url: '../cnnlearncontent/list',
        colModel: [
			// {label: 'id', name: 'id', index: 'id', key: true, hidden: true},
			{label: '学习类型',align : "center", name: 'learnType', index: 'learn_type', width: 80},
			{label: '主内容标题',align : "center", name: 'title', index: 'title', width: 100},
			{label: '学习天数',align : "center", name: 'genusDays', index: 'genus_days', width: 40},
			{label: '内容关键词',align : "left", name: 'keyNums', index: 'key_nums', width: 100},
			// {label: '主内容详情', name: 'oraleContent', index: 'orale_content', width: 80},
			{label: '主内容语音',align : "center", name: 'oraleSound', index: 'orale_sound', width: 80},
			{label: '合成图片路径',align : "center", name: 'scenceImg', index: 'scence_img', width: 80},
			{label: '状态', align : "center",name: 'isUse', index: 'is_use', width: 30,
                formatter: function (value) {
                    return transStatus(value);
                }},
			// {label: '扩展内容标题', name: 'extendSen', index: 'extend_sen', width: 80},
			{label: '扩展语音',align : "center", name: 'extendSound', index: 'extend_sound', width: 80},
			// {label: '扩展内容', name: 'extendWord', index: 'extend_word', width: 80},
			{label: '添加时间', align : "center",name: 'addTime', index: 'add_time', width: 60, formatter: function (value) {
                return transDate(value, 'yyyy-MM-dd hh:mm:ss');
            }},
			{label: '修改时间', align : "center",name: 'updateTime', index: 'update_time', width: 60, formatter: function (value) {
                return transDate(value, 'yyyy-MM-dd hh:mm:ss');
            }}]
    });
});

let vm = new Vue({
	el: '#rrapp',
	data: {
        showList: true,
        title: null,
		cnnLearnContent: {
		},
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
			vm.cnnLearnContent = {
			};
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
            let url = vm.cnnLearnContent.id == null ? "../cnnlearncontent/save" : "../cnnlearncontent/update";
            Ajax.request({
			    url: url,
                params: JSON.stringify(vm.cnnLearnContent),
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
				    url: "../cnnlearncontent/delete",
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
                url: "../cnnlearncontent/info/"+id,
                async: true,
                successCallback: function (r) {
                    vm.cnnLearnContent = r.cnnLearnContent;
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
        },
        handleSuccessPicUrl: function (res, file) {
            vm.cnnLearnContent.oraleSound = file.response.url;
        },
        handleSuccessPicUrlExtend: function (res, file) {
            vm.cnnLearnContent.extendSound = file.response.url;
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
                desc: '文件 ' + file.name + ' 太大，不能超过 20M。'
            });
        },
        eyeImagePicUrl: function () {
            var url = vm.cnnLearnContent.oraleSound;
            eyeImage(url);
        },
        eyeImagePicUrlExtend: function () {
            var url = vm.cnnLearnContent.extendSound;
            eyeImage(url);
        }
	}
});