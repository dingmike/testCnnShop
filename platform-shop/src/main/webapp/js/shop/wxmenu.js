$(function () {
    $("#jqGrid").Grid({
        url: '../ad/list',
        colModel: [
            {label: 'id', name: 'id', index: 'id', key: true, hidden: true},
            {label: '广告位置', name: 'adPositionName', index: 'ad_Position_id', width: 80},
            {label: '形式', name: 'mediaType', index: 'media_type', width: 80},
            {label: '广告名称', name: 'name', index: 'name', width: 80},
            {label: '链接', name: 'link', index: 'link', width: 80},
            {
                label: '图片', name: 'imageUrl', index: 'image_url', width: 80, formatter: function (value) {
                return transImg(value);
            }
            },
            {label: '内容', name: 'content', index: 'content', width: 80},
            {
                label: '结束时间', name: 'endTime', index: 'end_time', width: 80, formatter: function (value) {
                return transDate(value);
            }
            },
            {
                label: '状态', name: 'enabled', index: 'enabled', width: 80, formatter: function (value) {
                return value === 0 ?
                    '<span class="label label-danger">禁用</span>' :
                    '<span class="label label-success">正常</span>';
            }
            }]
    });
});



var vm = new Vue({
    el: "#rrapp",
    data: function () {
        var wxConfigs = [];
        <!--#for(o in wxList){#-->
        wxConfigs.push({value: "${o.id!}", label: "${o.appname!}"});
        <!--#}#-->
        var self = this;
        var validateUrl = function (rule, value, callback) {
            if ((self.formData.menuType == 'view' || self.formData.menuType == 'miniprogram') && self.formData.url == "") {
                callback(new Error('请输入正确的URL路径'));
            } else {
                callback();
            }
        };
        var validateK = function (rule, value, callback) {
            if (self.formData.menuType == 'click' && (typeof(self.formData.menuKey) == "undefined" || self.formData.menuKey == "")) {
                callback(new Error('请选择关键词'));
            } else {
                callback();
            }
        };
        var validateA = function (rule, value, callback) {
            if (self.formData.menuType == 'miniprogram' && (typeof(self.formData.appid) == "undefined" || self.formData.appid == "")) {
                callback(new Error('请输入正确的 appid'));
            } else {
                callback();
            }
        };
        var validateP = function (rule, value, callback) {
            if (self.formData.menuType == 'miniprogram' && (typeof(self.formData.pagepath) == "undefined" || self.formData.pagepath == "")) {
                callback(new Error('请输入正确的 pagepath'));
            } else {
                callback();
            }
        };
        return {
            wxConfigs: wxConfigs,
            addDialogVisible: false,
            editDialogVisible: false,
            sortDialogVisible:false,
            isAddFromSub: false,
            checked1: false,
            checked2: false,
            selectData: [],
            pageForm: {
                wxid: "<!--#if(!isEmpty(wxConfig)){#-->${wxConfig.id!}<!--#}#-->",
                searchName: "",
                searchKeyword: "",
                pageNumber: 1,
                pageSize: 10,
                totalCount: 0,
                pageOrderName: "opAt",
                pageOrderBy: "descending"
            },
            tableData: [],//后台取出的数据
            treeData: [],//格式化tableData用于显示的数据
            formData: {},
            formRules: {
                menuName: [
                    {required: true, message: '菜单名称', trigger: 'blur'}
                ],
                url: [
                    {required: false, message: '链接', trigger: 'blur'},
                    {validator: validateUrl, trigger: ['blur', 'change']}
                ],
                menuKey: [
                    {required: false, message: '绑定关键词', trigger: 'blur'},
                    {validator: validateK, trigger: ['blur', 'change']}
                ],
                appid: [
                    {required: false, message: '小程序的appid', trigger: 'blur'},
                    {validator: validateA, trigger: ['blur', 'change']}
                ],
                pagepath: [
                    {required: false, message: '小程序的pagepath', trigger: 'blur'},
                    {validator: validateP, trigger: ['blur', 'change']}
                ]
            },
            menuOptions: [],
            parentMenu: [],
            keyList: [],
            sortMenuData: [],
            defaultProps: {
                children: 'children',
                label: 'label'
            },
        }
    },
    methods: {
        // 显示层级
        treeTablePath: function (path) {
            return path.length / 4 - 1;
        },
        // 显示行
        treeTableShowTr: function (row, index) {
            var parentIndex = this.treeData.findIndex(function (value) {
                return value.id === row.row.parentId;
            });
            var show = (row.row.parentId ? (this.treeData[parentIndex].expanded && this.treeData[parentIndex]._show) : true);
            row.row._show = show;
            return show ? '' : 'display:none;'
        },
        treeDataChildren: function (table, data, pid) {//树形数据的级联查找很是头疼
            var self = this;
            table.findIndex(function (obj) {
                if (pid == obj.id) {//找到这条数据追加下级数据
                    obj.children = data;
                    obj.expanded = !obj.expanded;
                } else if (obj.children && obj.children.length > 0) {
                    self.treeDataChildren(obj.children, data, pid);
                }
            });
        },
        // 展开下级
        treeTableChild: function (pid) {
            var self = this;
            var url = "../wxmenu/child";
            $.post(url, {pid: pid}, function (data) {
                if (data.code == 0) {
                    debugger
                    if (pid != undefined && pid != "") {
                        self.treeDataChildren(self.tableData, data.data, pid);
                    } else {
                        self.tableData = data.data;
                    }
                    self.treeData = self.treeToArray(self.tableData);
                }
            }, "json");

        },
        treeToArray: function (data, parent, level, expandedAll) {
            var tmp = [];
            var self = this;
            Array.from(data).forEach(function (record) {
                if (record._expanded === undefined) {
                    Vue.set(record, '_expanded', expandedAll);
                }
                if (parent) {
                    Vue.set(record, '_parent', parent);
                }
                var _level = 0;
                if (level !== undefined && level !== null) {
                    _level = level + 1;
                }
                Vue.set(record, '_level', _level);
                tmp.push(record);
                if (record.children && record.children.length > 0) {
                    var children = self.treeToArray(record.children, record, _level, expandedAll);
                    tmp = tmp.concat(children);
                }
            });
            return tmp;
        },
        // 点击展开和关闭的时候，图标的切换
        treeTableIconShow: function (record) {
            return record.haschildren;
        },
        formatAt: function (val) {
            if (val > 0)
                return moment(val * 1000).format("YYYY-MM-DD HH:mm");
            return "";
        },
        menuQuery: function (queryString, cb) {
            cb(this.menuOptions);
        },
        menuSelect: function (val) {
        },
        menuTree: function (table, data, pid) {//树形数据的级联查找很是头疼
            var self = this;
            table.findIndex(function (obj) {
                if (pid == obj.value) {
                    obj.children = data;
                } else if (obj.children && obj.children.length > 0) {
                    self.menuTree(obj.children, data, pid);
                }
            });
        },
        menuChange2: function (val) {
            var self = this;
            if (val[0] == "root") {
                self.parentMenu = [];
            }
        },
        menuChange: function (val) {
            var self = this;
            var pid = val[val.length - 1];
            self.parentMenu = val;
            self.menuLoadTree(pid, function (tree) {
                self.menuTree(self.menuOptions, tree, pid);
            });
        },
        menuLoadTree: function (val, cb) {
            var self = this;
            var url = "../platform/wx/conf/menu/tree";
            $.post(url, {pid: val}, function (data) {
                if (data.code == 0) {
                    cb(data.data);
                }
            }, "json");
        },
        handleSelectionChange: function (val) {
            this.selectData = val;
        },
        openAdd: function () {
            var self = this;
            self.addDialogVisible = true;
            self.isAddFromSub = false;
            self.formData = {
                id: "",
                parentId: "",
                parentName: "",
                wxid: "<!--#if(!isEmpty(wxConfig)){#-->${wxConfig.id!}<!--#}#-->",
                menuType: "",
                url: ""
            };//打开新增窗口,表单先清空
            if (self.$refs["addForm"])
                self.$refs["addForm"].resetFields();
            self.menuLoadTree("", function (val) {
                self.menuOptions = val;
            });
            self.queryKeyword();
        },
        doAdd: function () {
            var self = this;
            var url = "../platform/wx/conf/menu/addDo";
            self.$refs["addForm"].validate(function (valid) {
                if (valid) {//表单验证通过
                    if (!self.isAddFromSub) {//如果不是添加子菜单，则从级联选择框取父节点ID
                        self.formData.parentId = self.parentMenu[self.parentMenu.length - 1] || "";
                    }
                    $.post(url, self.formData, function (data) {
                        if (data.code == 0) {
                            self.$message({
                                message: data.msg,
                                type: 'success'
                            });
                            self.addDialogVisible = false;
                            self.treeTableChild("");
                        } else {
                            self.$message({
                                message: data.msg,
                                type: 'error'
                            });
                        }
                    }, "json");
                }
            });
        },
        doEdit: function () {
            var self = this;
            var url = "../platform/wx/conf/menu/editDo";
            self.$refs["editForm"].validate(function (valid) {
                if (valid) {//表单验证通过
                    $.post(url, self.formData, function (data) {
                        if (data.code == 0) {
                            self.$message({
                                message: data.msg,
                                type: 'success'
                            });
                            self.editDialogVisible = false;
                            self.treeTableChild("");
                        } else {
                            self.$message({
                                message: data.msg,
                                type: 'error'
                            });
                        }
                    }, "json");
                }
            });
        },
        delMore: function () {
            var self = this;
            if (self.selectData.length == 0) {
                self.$message({
                    message: "请选择消息",
                    type: 'warning'
                });
                return false;
            }
            var ids = [];
            self.selectData.forEach(function (val) {
                ids.push(val.id);
            });
            self.$confirm('您确定要删除选中的 ' + ids.length + ' 条数据？ ', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                callback: function (a, b) {
                    if ("confirm" == a) {//确认后再执行
                        $.post("../platform/wx/conf/menu/delete", {ids: ids.toString()}, function (data) {
                            if (data.code == 0) {
                                self.$message({
                                    message: data.msg,
                                    type: 'success'
                                });
                                self.treeTableChild("");
                            } else {
                                self.$message({
                                    message: data.msg,
                                    type: 'error'
                                });
                            }
                        }, "json");
                    }
                }
            });
        },
        dropdownCommand: function (command) {//监听下拉框事件
            var self = this;
            if ("add" == command.type) {
                self.openAdd();
                self.formData.parentId = command.id;
                self.formData.parentName = command.name;
                self.isAddFromSub = true;
            }
            if ("edit" == command.type) {
                $.post("../platform/wx/conf/menu/edit/" + command.id, {}, function (data) {
                    if (data.code == 0) {
                        self.formData = data.data;//加载后台表单数据
                        self.editDialogVisible = true;//打开编辑窗口
                        self.queryKeyword();
                    } else {
                        self.$message({
                            message: data.msg,
                            type: 'error'
                        });
                    }
                }, "json");
            }
            if ("delete" == command.type) {
                self.$confirm('此操作将删除： ' + command.name, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                    callback: function (a, b) {
                        if ("confirm" == a) {//确认后再执行
                            $.post("../platform/wx/conf/menu/delete/" + command.id, {}, function (data) {
                                if (data.code == 0) {
                                    self.$message({
                                        message: data.msg,
                                        type: 'success'
                                    });
                                    self.treeTableChild("");
                                } else {
                                    self.$message({
                                        message: data.msg,
                                        type: 'error'
                                    });
                                }
                            }, "json");
                        }
                    }
                });
            }
        },
        queryChannel: function (queryString, cb) {
            $.post("../platform/wx/reply/news/channel_data", {
                searchName: "name",
                searchKeyword: queryString,
                pageNumber: 1,
                pageSize: 10,
                pageOrderName: "opAt",
                pageOrderBy: "descending"
            }, function (data) {
                if (data.code == 0) {
                    var res = [];
                    data.data.list.forEach(function (o) {
                        res.push({value: o.name, id: o.id})
                    });
                    cb(res);
                }
            }, "json");
        },
        handleChannelSelect: function (val) {
            var domain = "${AppDomain!}";
            this.formData.url = domain  + "/public/wx/cms/channel/" + val.id;
        },
        queryArticle: function (queryString, cb) {
            $.post("../platform/wx/reply/news/article_data", {
                searchName: "title",
                searchKeyword: queryString,
                pageNumber: 1,
                pageSize: 10,
                pageOrderName: "opAt",
                pageOrderBy: "descending"
            }, function (data) {
                if (data.code == 0) {
                    var res = [];
                    data.data.list.forEach(function (o) {
                        res.push({value: o.title, id: o.id})
                    });
                    cb(res);
                }
            }, "json");
        },
        handleArticleSelect: function (val) {
            var domain = "${AppDomain!}";
            this.formData.url = domain  + "/public/wx/cms/article/" + val.id;
        },
        checkedChange1: function (val) {
            if (this.checked1) {
                var str = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=<!--#if(!isEmpty(wxConfig)){#-->${wxConfig.appid!}<!--#}#-->&redirect_uri=$s&response_type=code&scope=snsapi_base&state=11624317#wechat_redirect";
                this.formData.url = str.replace("$s", encodeURIComponent(this.formData.url));
            } else {
                var url = this.formData.url;
                var str = url.substring(url.indexOf("redirect_uri=") + 13, url.indexOf("&response_type="));
                this.formData.url = decodeURIComponent(str);
            }
        },
        checkedChange2: function (val) {
            if (this.checked2) {
                var str = "${AppDomain!}${base!}/public/wx/wechat/<!--#if(!isEmpty(wxConfig)){#-->${wxConfig.id!}<!--#}#-->/oauth?goto_url=$s";
                this.formData.url = str.replace("$s", this.formData.url);
            } else {
                var url = this.formData.url;
                this.formData.url = url.substring(url.indexOf("goto_url=") + 9);
            }
        },
        queryKeyword: function () {
            var self = this;
            $.post("../platform/wx/conf/menu/keywordData", {wxid: "<!--#if(!isEmpty(wxConfig)){#-->${wxConfig.id!}<!--#}#-->"}, function (data) {
                if (data.code == 0) {
                    var res = [];
                    data.data.forEach(function (o) {
                        res.push({value: o.keyword, id: o.keyword})
                    });
                    self.keyList = res;
                }
            }, "json");
        },
        pushMenu: function () {
            var self=this;
            self.$confirm('确定要将菜单推送到微信平台？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                callback: function (a, b) {
                    if ("confirm" == a) {//确认后再执行
                        $.post("../platform/wx/conf/menu/pushMenu", { wxid: "<!--#if(!isEmpty(wxConfig)){#-->${wxConfig.id!}<!--#}#-->"}, function (data) {
                            if (data.code == 0) {
                                self.$message({
                                    message: data.msg,
                                    type: 'success'
                                });
                                self.treeTableChild("");
                            } else {
                                self.$message({
                                    message: data.msg,
                                    type: 'error'
                                });
                            }
                        }, "json");
                    }
                }
            });
        },
        //排序树加载
        sortMenuLoad: function () {
            var self = this;
            $.post("../platform/wx/conf/menu/sort", {wxid: "<!--#if(!isEmpty(wxConfig)){#-->${wxConfig.id!}<!--#}#-->"}, function (data) {
                if (data.code == 0) {
                    self.sortMenuData = data.data;
                }
            }, "json");
        },
        //排序树控制不可跨级拖拽
        sortAllowDrop: function (moveNode, inNode, type) {
            if (moveNode.data.parentId == inNode.data.parentId) {
                return type == 'prev';
            }
        },
        //打开排序功能
        openSort: function () {
            this.sortDialogVisible = true;
            this.sortMenuLoad();
        },
        //递归获取所有的ID值
        getTreeIds: function (ids, data) {
            var self = this;
            data.forEach(function (obj) {
                ids.push(obj.id);
                if (obj.children && obj.children.length > 0) {
                    self.getTreeIds(ids, obj.children);
                }
            });
        },
        //提交排序后的数据
        doSort: function () {
            var self = this;
            var ids = [];
            self.getTreeIds(ids, self.sortMenuData);
            $.post("../platform/wx/conf/menu/sortDo", {wxid: "<!--#if(!isEmpty(wxConfig)){#-->${wxConfig.id!}<!--#}#-->",ids: ids.toString()}, function (data) {
                if (data.code == 0) {
                    self.$message({
                        message: data.msg,
                        type: 'success'
                    });
                    self.sortDialogVisible = false;
                    self.treeTableChild("");
                } else {
                    self.$message({
                        message: data.msg,
                        type: 'error'
                    });
                }
            }, "json");
        },
        change: function (val) {
            window.location.href = "${base}/platform/wx/conf/menu/index/" + val;
        }
    },
    created: function () {
        debugger
        this.treeTableChild("");
    }
});

/*
var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        ad: {enabled: 1, imageUrl: '', mediaType: 0},
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
        },
        adPositions: []
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.ad = {enabled: 1, imageUrl: '', mediaType: 0};
            vm.adPosition = [];
            this.getAdPositions();
        },
        update: function (event) {
            var id = getSelectedRow("#jqGrid");
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id);
            this.getAdPositions();
        },
        saveOrUpdate: function (event) {
            var url = vm.ad.id == null ? "../ad/save" : "../ad/update";

            Ajax.request({
                type: "POST",
                url: url,
                contentType: "application/json",
                params: JSON.stringify(vm.ad),
                successCallback: function () {
                    alert('操作成功', function (index) {
                        vm.reload();
                    });
                }
            });

        },
        del: function (event) {
            var ids = getSelectedRows("#jqGrid");
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {

                Ajax.request({
                    type: "POST",
                    url: "../ad/delete",
                    contentType: "application/json",
                    params: JSON.stringify(ids),
                    successCallback: function () {
                        alert('操作成功', function (index) {
                            $("#jqGrid").trigger("reloadGrid");
                        });
                    }
                });

            });
        },
        getInfo: function (id) {
            $.get("../ad/info/" + id, function (r) {
                vm.ad = r.ad;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'name': vm.q.name},
                page: page
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
        },
        handleSuccess: function (res, file) {
            vm.ad.imageUrl = file.response.url;
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
        handleSubmit: function (name) {
            handleSubmitValidate(this, name, function () {
                vm.saveOrUpdate()
            });
        },
        handleReset: function (name) {
            handleResetForm(this, name);
        },
        eyeImage: function () {
            var url = vm.ad.imageUrl;
            eyeImage(url);
        },
        /!**
         * 获取会员级别
         *!/
        getAdPositions: function () {
            $.get("../adposition/queryAll", function (r) {
                vm.adPositions = r.list;
            });
        }
    }
});*/
