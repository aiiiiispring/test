<%@ page contentType="text/html; charset=utf-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<html>
<head>
    <title>第三方系统底部浏览编辑页面</title>
</head>
<script>
    $(document).ready(function () {
        var flag = "${flag}";
        if (flag == "new") {
            $("#bottomButton").show();
            $("#tableForm").clearform();
            $("#id").val("");
        } else {
            if (flag == "update") {
                $("#bottomButton").show();
            } else if (flag == "show") {
                $("#bottomButton").hide();
            }
            var id = "${id}";
            var manager = new authenticationManager();
            var vo = manager.getAuthenticationVoById(id);
            $("#tableForm").fillform(vo);
            if (flag == "show") {
                $("#tableForm").disable();
            }
        }
    });

    function OK() {
        var re = $("#tableForm").validate();
        if (re == false) {
            return;
        }
        debugger
        var o = $("#tableForm").formobj();
        var flag = "${flag}";
        o.id = $("#id").val();
        if (o.registerId == null || o.registerId == '') {
            $.alert("请选择绑定的应用系统！");
            return;
        }
        if (o.moduleType == null || o.moduleType == "") {
            $.alert("请选择模块类型！");
            return;
        }
        if (o.isEnable == null || o.isEnable == "") {
            $.alert("请选择是否启用！");
            return;
        }
        if (o.url == "") {
            $.alert("请输入系统接口地址！");
            return;
        }
        debugger
        var url = o.url.toUpperCase();
        if (!(url.length >= 10 && (url.indexOf("HTTP://") !=-1 || url.indexOf("HTTPS://") !=-1 || url.indexOf("FTP://")!=-1))) {
            $.alert("接口地址不合法，应以http://或https://开头");
            return;
        }
        var manager = new authenticationManager();
        var isRepeat = false;
        if (flag == "new") {
            isRepeat = manager.getIsExistByRegisterId(o.registerId,o.moduleType, '', true);
        } else if (flag == "update") {
            isRepeat = manager.getIsExistByRegisterId(o.registerId,o.moduleType, o.id, false);
        }
        if (isRepeat) {
            $.alert("系统中同一应用，同一模块类型只能存在一个，请检查！");
            return;
        }
        manager.updateAuthenticationPo(o);
        cancel();
    }
    function cancel() {
        parent.location.href = _ctxPath + "/sygb/authenticationController.do?method=listAuthentication"
    }
    function registerChange(){
        var registerId = $("#registerId").find("option:selected").val();
        var manager = new authenticationManager();
        var result = manager.getRegisterById(registerId);
        if (result != null){
            $("#appCode").val(result.appCode);
        }
    }
</script>
<body>
<div class="stadic_layout_body form_area" scrolling="no" style="top:25px;overflow-x:hidden">
    <form id="tableForm" name="tableForm">
        <div style="width: 100%;text-align: center;">
            <div style="width: 500px;text-align: center;margin: auto;">
                <table border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                        <input type="hidden" id="id"/>
                        <tr>
                            <th nowrap="nowrap"><label class="margin_r_10" for="text"><font color="red">*</font>绑定应用:</label></th>
                            <td width="100%">
                                <div class="common_selectbox_wrap">
                                    <div>
                                        <select id="registerId" name="registerId" class="form-control codecfg"
                                                codecfg="codeType:'java',codeId:'com.seeyon.apps.sygb.constants.RegisterCategoryEnum'" onchange='registerChange()'>
                                            <option value=""></option>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th nowrap="nowrap"><label class="margin_r_10" for="text">应用编码:</label></th>
                            <td width="100%">
                                <div class="common_txtbox_wrap">
                                    <input type="text" id="appCode" class="validate" readonly />
                                </div>
                            </td>
                        </tr>
                        <tr class="">
                            <th nowrap="nowrap"><label class="margin_r_10" for="text"><font color="red">*</font>rest接口地址:</label></th>
                            <td width="100%">
                                <div class="common_txtbox_wrap">
                                    <input type="text" id="url" class="validate" placeholder="以http://或https://开头的接口地址"
                                           validate="type:'string',name:'接口地址',maxLength:'300'"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th nowrap="nowrap"><label class="margin_r_10" for="text"><font color="red">*</font>所属模块:</label></th>
                            <td>
                                <div class="common_selectbox_wrap">
                                    <div>
                                        <select id="moduleType" name="moduleType" class="form-control codecfg"
                                                codecfg="codeType:'java',codeId:'com.seeyon.apps.sygb.constants.ModuleTypeCategoryEnum'">
                                            <option value=""></option>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th nowrap="nowrap"><label class="margin_r_10" for="text"><font color="red">*</font>是否启用:</label></th>
                            <td>
                                <div class="common_selectbox_wrap">
                                    <div>
                                        <select id="isEnable" name="isEnable" class="form-control codecfg"
                                                codecfg="codeType:'java',codeId:'com.seeyon.apps.sygb.constants.EnableCategoryEnum'">
                                            <option value=""></option>
                                        </select>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </form>
</div>
<div class="stadic_layout_footer stadic_footer_height"
     id="bottomButton">
    <div id="button" align="center" class="page_color button_container">
        <div class="common_checkbox_box clearfix  stadic_footer_height padding_t_5 border_t">
            <a href="javascript:OK()" class="common_button common_button_emphasize margin_r_10 hand"
               id="edit_confirm_button">确定</a>&nbsp;
            <a href="javascript:cancel()" class="common_button common_button_gray" id="edit_cancel_button">取消</a>
        </div>
    </div>
</div>
</body>
</html>