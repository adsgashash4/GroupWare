<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 문의게시판</title>
<link rel="stylesheet" href="css/main.css" type="text/css"/>
<script type="text/javascript">
	function back(){
		location.href="./qnaList.do";
	}
</script>
</head>
<body>
<div id = "header">
	<jsp:include page="../header.jsp" flush="false"/>
</div>
<div id = "container">
	<div class="error_text_area">
		<h3>비밀번호가 틀렸습니다.</h3>
	</div>
	<div class="error_btn_area">
		<input type="button" class="body_btn error_collback_btn" value="목록으로" onclick="back()"/>
	</div>
</div>
<div id = "footer">
	<jsp:include page="../footer.jsp" flush="false"/>
</div>
</body>
</html>