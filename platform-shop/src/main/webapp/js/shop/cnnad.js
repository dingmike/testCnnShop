$(function () {
    $("#jqGrid").Grid({
        url: '../cnnad/list',
        colModel: [
			// {label: 'id', name: 'id', index: 'id', key: true, hidden: true},
			// {label: 'cnn媒体广告类型', name: 'mediaType', index: 'media_type', width: 80},
			{label: '广告名称',align : "center", name: 'name', index: 'name', width: 80},
            {label: '形式',align : "center", name: 'mediaType', index: 'media_type', width: 80},
			{label: '链接',align : "center", name: 'link', index: 'link', width: 80},
            {
                label: '图片',align : "center", name: 'imageUrl', index: 'image_url', width: 80, formatter: function (value) {
                return transImg(value);
            }
            },
			{label: '文字类容',align : "center", name: 'content', index: 'content', width: 80},
			{label: '添加时间',align : "center", name: 'addTime', index: 'add_time', width: 80, formatter: function (value) {
                return transDate(value,'yyyy-MM-dd hh:mm:ss');
            }},
			{label: '更新时间',align : "center", name: 'updateTime', index: 'update_time', width: 80, formatter: function (value) {
                return transDate(value,'yyyy-MM-dd hh:mm:ss');
            }},
            {
                label: '状态',align : "center", name: 'enabled', index: 'enabled', width: 80, formatter: function (value) {
                return value === 0 ?
                    '<span class="label label-danger">禁用</span>' :
                    '<span class="label label-success">正常</span>';
            }}]
    });
});

let vm = new Vue({
	el: '#rrapp',
	data: {
        showList: true,
        title: null,
        cnnAd: {enabled: 1, imageUrl: '', mediaType: 0},
        ruleValidate: {
            name: [
                {required: true, message: '广告名称不能为空', trigger: 'blur'}
            ],
            imageUrl: [
                {required: true, message: '图片不能为空', trigger: 'blur'}
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
            vm.cnnAd = {enabled: 1, imageUrl: '', mediaType: 0};
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
            let url = vm.cnnAd.id == null ? "../cnnad/save" : "../cnnad/update";
            Ajax.request({
			    url: url,
                params: JSON.stringify(vm.cnnAd),
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
				    url: "../cnnad/delete",
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
                url: "../cnnad/info/"+id,
                async: true,
                successCallback: function (r) {
                    vm.cnnAd = r.cnnAd;
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
        handleSuccess: function (res, file) {
            vm.cnnAd.imageUrl = file.response.url;
        },
        handleFormatError: function (file) {
            this.$Notice.warning({
                title: '文件格式不正确',
                desc: '文件 ' + file.name + ' 格式不正确，请上传 jpg 或 png 格式的图片。'
            });
        },
        handleMaxSize: function (file) {
            this.$Notice.warning({
                title: '超出文件大小限制',
                desc: '文件 ' + file.name + ' 太大，不能超过 2M。'
            });
        },
        eyeImage: function () {
            var url = vm.cnnAd.imageUrl;
            eyeImage(url);
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