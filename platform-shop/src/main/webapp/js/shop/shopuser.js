$(function () {
    $("#jqGrid").Grid({
        url: '../user/list',
        colModel: [{
            label: 'id', name: 'id', index: 'id', key: true, hidden: true
        },
            {
                label: '头像', name: 'avatar',align : "center", index: 'avatar', width: 30, formatter: function (value) {
                return transImg(value);
            }
            }, {
                label: '会员名称',align : "center", name: 'username', index: 'username', width: 60
            },

            {
                label: '微信昵称',align : "center", name: 'nickname', index: 'nickname', width: 60
            },
            {label: '积分',align : "center", name: 'intergral', index: 'intergral', width: 30},
            {label: '余额',align : "center", name: 'balance', index: 'balance', width: 30},
            {
                label: '状态',align : "center", name: 'freeze', index: 'freeze', width: 30, formatter: function (value) {
                return toFreezed(value);
            }
            },

            {
                label: '手机号码',align : "center", name: 'mobile', index: 'mobile', width: 50
            },
            {
                label: '会员密码',align : "center", name: 'password', index: 'password', hidden: true
            }, {
                label: '性别',align : "center", name: 'gender', index: 'gender', width: 40, formatter: function (value) {
                    return transGender(value);
                }
            }, {
                label: '出生日期',align : "center", name: 'birthday', index: 'birthday', width: 60, formatter: function (value) {
                    return transDate(value);
                }
            }, {
                label: '注册时间',align : "center", name: 'registerTime', index: 'register_time', width: 60, formatter: function (value) {
                    return transDate(value);
                }
            }, {
                label: '最后登录时间',
                align : "center",
                name: 'lastLoginTime',
                index: 'last_login_time',
                width: 60,
                formatter: function (value) {
                    return transDate(value);
                }
            }, {
                label: '最后登录IP',align : "center", name: 'lastLoginIp', index: 'last_login_ip', hidden: true
            }, {
                label: '会员等级', align : "center",name: 'levelName', width: 40, hidden: true
            }, {
                label: '注册IP', align : "center",name: 'registerIp', index: 'register_ip', hidden: true
            }, {
                label: '微信Id', align : "center",name: 'weixinOpenid', index: 'weixin_openid', width: 80, hidden: true
            }]
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        user: {
            gender: 1
        },
        ruleValidate: {
            username: [
                {required: true, message: '会员名称不能为空', trigger: 'blur'}
            ]
        },
        q: {
            username: '',
            nickname: ''
        },
        userLevels: []
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.user = {gender: '1'};
            vm.userLevels = [];

            this.getUserLevels();
        },
        update: function (event) {
            var id = getSelectedRow("#jqGrid");
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
            this.getUserLevels();
        },
        saveOrUpdate: function (event) {
            var url = vm.user.id == null ? "../user/save" : "../user/update";

            Ajax.request({
                type: "POST",
                url: url,
                contentType: "application/json",
                params: JSON.stringify(vm.user),
                successCallback: function (r) {
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
                    url: "../user/delete",
                    contentType: "application/json",
                    params: JSON.stringify(ids),
                    successCallback: function (r) {
                        alert('操作成功', function (index) {
                            $("#jqGrid").trigger("reloadGrid");
                        });
                    }
                });

            });
        },
        exportUser: function () {
            exportFile('#rrapp', '../user/export', {'username': vm.q.username});
        },
        coupon: function () {
            var id = getSelectedRow("#jqGrid");
            if (id == null) {
                return;
            }
            openWindow({
                title: '优惠券',
                type: 2,
                content: '../shop/usercoupon.html?userId=' + id
            })
        },
        address: function () {
            var id = getSelectedRow("#jqGrid");
            if (id == null) {
                return;
            }
            openWindow({
                title: '收获地址',
                type: 2,
                content: '../shop/address.html?userId=' + id
            })
        },
        shopCart: function () {
            var id = getSelectedRow("#jqGrid");
            if (id == null) {
                return;
            }
            openWindow({
                title: '购物车',
                type: 2,
                content: '../shop/cart.html?userId=' + id
            })
        },
        getInfo: function (id) {
            $.get("../user/info/" + id, function (r) {
                vm.user = r.user;
            });
        },
        /**
         * 获取会员级别
         */
        getUserLevels: function () {
            $.get("../userlevel/queryAll", function (r) {
                vm.userLevels = r.list;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'username': vm.q.username, 'nickname': vm.q.nickname},
                page: page,
            }).trigger("reloadGrid");
            vm.handleReset('formValidate');
        },
        reloadSearch: function () {
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