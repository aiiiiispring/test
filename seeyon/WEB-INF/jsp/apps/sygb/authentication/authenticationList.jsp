<%@ page contentType="text/html; charset=utf-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<html>
<head>
    <title>第三方系统注册</title>
    <script text="text/javascript">
        var grid;
        $(function () {
            new MxtLayout({
                'id': 'layout',
                'northArea': {
                    'id': 'north',
                    'height': 40,
                    'sprit': false,
                    'border': false
                },
                'centerArea': {
                    'id': 'center',
                    'border': false,
                    'minHeight': 150
                }
            });

            var colModel = [{
                display: "id",
                name: 'id',
                width: 'smallest',
                sortable: true,
                type: 'checkbox'
            },
            {
                display: "${ctp:i18n('cip.service.register.appname')}",
                name: 'appName',
                width: 'medium',
                sortable: true
            },
            {
                display: "${ctp:i18n('cip.service.register.appcode')}",
                name: 'appCode',
                width: 'medium',
                sortable: true
            },
            {
                display: "${ctp:i18n('cip.base.register.product')}" + "${ctp:i18n('cip.third.semanic.code')}",
                sortable: true,
                name: 'productCode',
                width: 'medium'
            },
             {
                display: "rest接口地址",
                name: 'url',
                width: 'big',
                sortable: true,
                align: 'left'
            }, {
                display: "模块类型",
                name: 'moduleTypeName',
                width: 'medium',
                sortable: true,
                align: 'left'
            }, {
                display: "是否启用",
                name: 'isEnableName',
                width: 'medium',
                sortable: true,
                align: 'left'
            }, {
                display: "修改时间",
                name: 'updateTime',
                width: 'medium',
                sortable: true,
                align: 'left'
            }];
            grid = $('#dataList').ajaxgrid({
                click: showInfo,
                dblclick: dbclickRow,
                colModel: colModel,
                width: "auto",
                height: 400,
                showTableToggleBtn: true,
                parentId: $('.layout_center').eq(0).attr('id'),
                vChange: true,
                isHaveIframe: true,
                gridType: 'autoGrid',
                slideToggleBtn: true,
                managerName : "authenticationManager",
                managerMethod : "getAuthenticationList",
                showToggleBtn: false,
                customize: false
            });
            var o = new Object();
            $("#dataList").ajaxgridLoad(o);
            //搜索框
            var topSearchSize = 2;
            if ($.browser.msie && $.browser.version == '6.0') {
                topSearchSize = 5;
            }
            var searchobj = $.searchCondition({
                top: topSearchSize,
                right: 10,
                searchHandler: function () {
                    o = searchobj.g.getReturnValue();
                    $("#dataList").ajaxgridLoad(o);
                },
                conditions: [{
                    id: 'appNameCondition',
                    name: 'app_Name',
                    type: 'input',
                    text: "${ctp:i18n('cip.service.register.appname')}",
                    value: 'app_name'
                },
                {
                    id: 'appCodeCondition',
                    name: 'app_code',
                    type: 'input',
                    text: "${ctp:i18n('cip.service.register.appcode')}",
                    value: 'app_code'
                },
                {
                    id: 'productCode',
                    name: 'product_code',
                    type: 'input',
                    text: "${ctp:i18n('cip.base.register.product')}" + "${ctp:i18n('cip.third.semanic.code')}",
                    value: 'product_code'
                }]
            });

            var toolbar = new Array();
            //新建
            toolbar.push({id: "newCreate", name: "新建", className: "ico16", click: addRow});
            //修改
            toolbar.push({id: "update", name: "修改", className: "ico16 editor_16", click: updateRow});
            //删除
            toolbar.push({id: "delete", name: "删除", className: "ico16 del_16", click: deleteRows});

            $("#toolbars").toolbar({
                borderLeft: false,
                borderTop: false,
                borderRight: false,
                toolbar: toolbar
            });

        });

        //删除用户
        function deleteRows() {
            var ids = new Array();
            var manager = new authenticationManager();
            var v = getTableChecked();
            if (v.length === 0) {
                $.alert("请选择要删除的数据！");
                return;
            } else {
                for (var i = 0; i < v.length; i++) {
                    ids.push(v[i].id);
                }
                $.confirm({
                    'msg': "删除数据后，无法恢复，并将删除所有与当前系统有关的配置！确定要删除选择的数据？",
                    ok_fn: function () {
                        manager.deleteAuthenticationPos(ids, {
                            success: function () {
                                reloadTab();
                            }
                        });
                    },
                    cancel_fn: function () {
                    }
                });
            }
        };

        //加载列表
        function reloadTab() {
            var o = new Object();
            $("#dataList").ajaxgridLoad(o);
        };

        //获取选中
        function getTableChecked() {
            return $("#dataList").formobj({
                gridFilter: function (data, row) {
                    return $("input:checkbox", row)[0].checked;
                }
            });
        };

        //只读展示
        function showInfo(row, rowIndex, colIndex) {
            grid.grid.resizeGridUpDown('middle');
            $('#summary').attr("src", _ctxPath + "/sygb/authenticationController.do?method=edit&flag=show&id=" + row.id);
        };

        //双击修改
        function dbclickRow() {
            updateRow();
        };

        //点击修改
        function updateRow() {
            var rows = grid.grid.getSelectRows();
            if (rows.length === 0) {
                $.alert("请选择一条记录!");//请选择一条记录
                return;
            }
            if (rows.length > 1) {
                $.alert("只能选择一条记录进行修改!");//只能选择一条记录进行修改
                return;
            }
            grid.grid.resizeGridUpDown('middle');
            $('#summary').attr("src", _ctxPath + "/sygb/authenticationController.do?method=edit&flag=update&id=" + rows[0].id);
        };

        //添加
        function addRow() {
            grid.grid.resizeGridUpDown('middle');
            $('#summary').attr("src", _ctxPath + "/sygb/authenticationController.do?method=edit&flag=new");
        }
    </script>
</head>
<body>
<div id='layout'>
    <div class="layout_north bg_color f0f0f0" id="north">
        <div id="toolbars"></div>
    </div>
    <div class="layout_center over_hidden" id="center">
        <table class="flexme3" id="dataList"></table>
        <div id="grid_detail" class="h100b" style="overflow:hidden">
            <iframe id="summary" width="100%" height="100%" frameborder="20" scrolling="no"
                    style="overflow-y:hidden"></iframe>
        </div>
    </div>
</div>

</body>
</html>