<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	<script type="text/javascript" src="${path}/common/SelectPeople/js/orgDataCenter.js"></script>
	<script type="text/javascript" src="${path}/main/common/js/frame-ajax.js"></script>
	<script type="text/javascript">
		<%--关闭窗口,操作成功后回调--%>
		function closeWindow() {
			if (window.dialogArguments) {
				window.returnValue = "true";
				window.close();
			} else {
				window.close();
			}
		}
		
		$(document).ready(function() {
			window.location.href="${path}/${sapbusinessUrl}";
		});
	</script>
</head>
</html>