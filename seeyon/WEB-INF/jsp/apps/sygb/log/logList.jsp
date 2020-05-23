<%@ page contentType="text/html; charset=utf-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<html>
<head>
    <title>国博日志显示</title>
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
                    name: 'systemName',
                    width: 'medium',
                    sortable: true
                }, {
                    display: "${ctp:i18n('cip.service.register.appcode')}",
                    name: 'systemCode',
                    width: 'medium',
                    sortable: true
                }, {
                    display: "模块",
                    sortable: true,
                    name: 'moduleTypeName',
                    width: 'medium'
                }, {
                    display: "动作方向",
                    sortable: true,
                    name: 'actionName',
                    width: 'medium'
                }, {
                    display: "rest接口地址",
                    name: 'url',
                    width: 'big',
                    sortable: true,
                    align: 'left'
                },{
                    display: "操作人员",
                    name: 'memberName',
                    width: 'medium',
                    sortable: true,
                    align: 'left'
                }, {
                    display: "是否成功",
                    name: 'resultTypeName',
                    width: 'medium',
                    sortable: true,
                    align: 'left'
                }, {
                    display: "详情",
                    name: 'resultInfo',
                    width: 'medium',
                    sortable: true,
                    align: 'left'
                }, {
                    display: "时间",
                    name: 'createTime',
                    width: 'medium',
                    sortable: true,
                    align: 'left'
                }];
            grid = $('#dataList').ajaxgrid({
                click: showInfo,
                // dblclick: dbclickRow,
                colModel: colModel,
                width: "auto",
                height: 400,
                showTableToggleBtn: true,
                parentId: $('.layout_center').eq(0).attr('id'),
                vChange: true,
                isHaveIframe: true,
                gridType: 'autoGrid',
                slideToggleBtn: true,
                managerName: "gbEdocLogManager",
                managerMethod: "getGbLogList",
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
                    var choose = $('#' + searchobj.p.id).find("option:selected").val();
                    if (choose === 'createDate') {
                        startDate = $('#from_createDate').val();
                        endDate = $('#to_createDate').val();
                        if (startDate != "") {
                            o.from_createDate = startDate;
                        }
                        if (endDate != "") {
                            o.to_createDate = endDate;
                        }
                    }
                    if ("memberName" == o.condition && o.value != "") {
                        var memStr = o.value[1];
                        if (memStr != "") {
                            o.memberId = memStr.split("|")[1];
                        } else {
                            o.memberId = null;
                        }
                    }
                    $("#dataList").ajaxgridLoad(o);
                },
                conditions: [{
                    id: 'systemCode',
                    name: 'systemCode',
                    type: 'input',
                    text: "应用编码",
                    value: 'systemCode',
                    maxLength: 100
                }, {
                    id: 'systemName',
                    name: 'systemName',
                    type: 'input',
                    text: "应用名称",
                    value: 'systemName',
                    maxLength: 100
                }, {
                    id: 'url',
                    name: 'url',
                    type: 'input',
                    text: "rest接口",
                    value: 'url',
                    maxLength: 500
                }, {
                    id: 'actionType',
                    name: 'actionType',
                    type: 'select',
                    text: '动作方向',
                    value: 'actionType',
                    items: [{
                        text: '外部推入',
                        value: '0'
                    }, {
                        text: '内部推送',
                        value: '1'
                    }]
                }, {
                    id: 'moduleType',
                    name: 'moduleType',
                    type: 'select',
                    text: '模块类型',
                    value: 'moduleType',
                    items: [{
                        text: '报批件公文',
                        value: '1'
                    }]
                }, {
                    id: 'resultType',
                    name: 'resultType',
                    type: 'select',
                    text: '是否成功',
                    value: 'resultType',
                    items: [{
                        text: '失败',
                        value: '0'
                    }, {
                        text: '成功',
                        value: '1'
                    }]
                }, {
                    id: 'memberName',
                    name: 'memberName',
                    type: 'selectPeople',
                    text: "操作用户",
                    value: 'memberName',
                    comp: "type:'selectPeople',mode:'open',panels:'Department,Team,Post,Level',selectType:'Member',maxSize:1"
                }, {
                    id: 'createTime',
                    name: 'createTime',
                    type: 'datemulti',
                    text: '创建时间',
                    value: 'createTime',
                    dateTime: true,
                    ifFormat: '%Y-%m-%d'
                }]
            });

            var toolbar = new Array();
            //新建
            // toolbar.push({id: "newCreate", name: "新建", className: "ico16", click: addRow});
            //修改
            // toolbar.push({id: "update", name: "修改", className: "ico16 editor_16", click: updateRow});
            //删除
            // toolbar.push({id: "delete", name: "删除", className: "ico16 del_16", click: deleteRows});

            $("#toolbars").toolbar({
                borderLeft: false,
                borderTop: false,
                borderRight: false,
                toolbar: toolbar
            });

            // var skinPathKey = getCtpTop().skinPathKey == null ? "harmony" : getCtpTop().skinPathKey;
            // var html = '<span class="nowLocation_ico"><img src="' + _ctxPath + '/main/skin/frame/' + skinPathKey + '/menuIcon/' + getCtpTop().currentSpaceType + '.png"></span>';
            // html += '<span class="nowLocation_content">第三方系统注册 ';
            // html += '</span>';
            // getCtpTop().showLocation(html);
        });

        //删除用户
        function deleteRows() {
            // var ids = new Array();
            // var manager = new authenticationManager();
            // var v = getTableChecked();
            // if (v.length === 0) {
            //     $.alert("请选择要删除的数据！");
            //     return;
            // } else {
            //     for (var i = 0; i < v.length; i++) {
            //         ids.push(v[i].id);
            //     }
            //     $.confirm({
            //         'msg': "删除数据后，无法恢复，并将删除所有与当前系统有关的配置！确定要删除选择的数据？",
            //         ok_fn: function () {
            //             manager.deleteAuthenticationPos(ids, {
            //                 success: function () {
            //                     reloadTab();
            //                 }
            //             });
            //         },
            //         cancel_fn: function () {
            //         }
            //     });
            // }
        };

        //加载列表
        function reloadTab() {
            var o = new Object();
            $("#dataList").ajaxgridLoad(o);
            // location.href = _ctxPath + "/sygb/authenticationController.do?method=listAuthentication"
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
            $('#viewShow').attr("src", _ctxPath + "/sygb/gbEdocLog.do?method=show&flag=show&id=" + row.id);
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
            $('#viewShow').attr("src", _ctxPath + "/sygb/gbEdocLog.do?method=show&flag=show&id=" + + rows[0].id);
        };

        //添加
        function addRow() {
            grid.grid.resizeGridUpDown('middle');
            $('#viewShow').attr("src", _ctxPath + "/sygb/gbEdocLog.do?method=show&flag=show&flag=new");
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
            <iframe id="viewShow" width="100%" height="100%" frameborder="20" scrolling="no"
                    style="overflow-y:hidden"></iframe>
        </div>
    </div>
</div>

</body>
</html>