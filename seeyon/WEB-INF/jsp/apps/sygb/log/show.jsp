<%@ page contentType="text/html; charset=utf-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/jsp/common/common.jsp" %>
<html>
<head>
    <title>底部浏览编辑页面</title>
</head>
<script>
    $(document).ready(function () {
        var id = "${id}";
        var manager = new gbEdocLogManager();
        var vo = manager.getEdocLogVoById(id);
        $("#tableForm").fillform(vo);
        $("#tableForm").disable();
    });
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
                        <th nowrap="nowrap"><label class="margin_r_10" for="text">应用编码:</label></th>
                        <td width="100%">
                            <div class="common_txtbox_wrap">
                                <input type="text" id="systemCode" class="validate" readonly/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th nowrap="nowrap"><label class="margin_r_10" for="text">应用系统:</label></th>
                        <td width="100%">
                            <div class="common_txtbox_wrap">
                                <input type="text" id="systemName" class="validate" readonly/>
                            </div>
                        </td>
                    </tr>
                    <tr class="">
                        <th nowrap="nowrap"><label class="margin_r_10" for="text">rest接口地址:</label></th>
                        <td width="100%">
                            <div class="common_txtbox_wrap">
                                <input type="text" id="url" class="validate" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th nowrap="nowrap"><label class="margin_r_10" for="text">同步传递数据:</label></th>
                        <td width="100%">
                            <div class="common_txtbox_wrap">
                                <div>
                                    <textarea id="dataJson" name="dataJson" rows="10" cols="60"></textarea>
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
</body>
</html>