$(function () {
    $("#jqGrid").Grid({
        url: '../cnnnews/list',
        colModel: [
			{label: 'id', name: 'id', index: 'id', key: true, hidden: true},
			{label: '文章标题', name: 'title', index: 'title', width: 80},
			{label: '图片', align : "center",name: 'imageUrl', index: 'imageUrl', width: 30, formatter: function (value) {
                return transImg(value);
            }},
			//{label: '文章详情', name: 'newsDetail', index: 'news_detail', width: 80},
			//{label: '用户数据表ID', name: 'userId', index: 'user_id', width: 80},
            {label: '文章音频', name: 'voiceUrl', index: 'voice_url', width: 100},
            {
                label: '添加时间', align : "center", name: 'addTime', index: 'add_time', width: 60, formatter: function (value) {
                return transDate(value, 'yyyy-MM-dd hh:mm:ss');
            }},
			{
                label: '修改时间', align : "center", name: 'updateTime', index: 'update_time', width: 60, formatter: function (value) {
                return transDate(value, 'yyyy-MM-dd hh:mm:ss');
            }},
            {
                label: '发布状态', name: 'isUse', index: 'is_use', width: 40,
                formatter: function (value) {
                    return transIsNot(value);
                }
            }]
    });
    $('#newsDetail').editable({
        inlineMode: false,
        alwaysBlank: true,
        height: '400px', //高度
        minHeight: '200px',
        language: "zh_cn",
        spellcheck: false,
        plainPaste: true,
        enableScript: false,
        imageButtons: ["floatImageLeft", "floatImageNone", "floatImageRight", "linkImage", "replaceImage", "removeImage"],
        allowedImageTypes: ["jpeg", "jpg", "png", "gif"],
        imageUploadURL: '../sys/oss/upload',
        imageUploadParams: {id: "edit"},
        imagesLoadURL: '../sys/oss/queryAll'
    })
    $('#chinese').editable({
        inlineMode: false,
        alwaysBlank: true,
        height: '300px', //高度
        minHeight: '200px',
        language: "zh_cn",
        spellcheck: false,
        plainPaste: true,
        enableScript: false,
        imageButtons: ["floatImageLeft", "floatImageNone", "floatImageRight", "linkImage", "replaceImage", "removeImage"],
        allowedImageTypes: ["jpeg", "jpg", "png", "gif"],
        imageUploadURL: '../sys/oss/upload',
        imageUploadParams: {id: "edit"},
        imagesLoadURL: '../sys/oss/queryAll'
    })
});

let vm = new Vue({
	el: '#rrapp',
	data: {
        showList: true,
        title: null,
		cnnNews: {
        	title: '',
            isUse: ''
		},
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
            $('#newsDesc').editable('setHTML', '');
            $('#chinese').editable('setHTML', '');
			vm.cnnNews = {
                title: '',
                isUse: 0
			};
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
            let url = vm.cnnNews.id == null ? "../cnnnews/save" : "../cnnnews/update";
            vm.cnnNews.newsDetail = $('#newsDetail').editable('getHTML');
            vm.cnnNews.chinese= $('#chinese').editable('getHTML');
            Ajax.request({
			    url: url,
                params: JSON.stringify(vm.cnnNews),
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
				    url: "../cnnnews/delete",
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
                url: "../cnnnews/info/"+id,
                async: true,
                successCallback: function (r) {
                    vm.cnnNews = r.cnnNews;
                    $('#newsDetail').editable('setHTML', vm.cnnNews.newsDetail);
                    $('#chinese').editable('setHTML', vm.cnnNews.chinese);
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
            vm.cnnNews.imageUrl = file.response.url;
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
            var url = vm.cnnNews.imageUrl;
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
        },
        handleSuccessPicUrl: function (res, file) {
            vm.cnnNews.voiceUrl = file.response.url;
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
        }
	}
});