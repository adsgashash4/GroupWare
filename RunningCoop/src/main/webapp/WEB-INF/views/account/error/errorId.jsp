<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function goBack(){
		opener.document.getElementById("id").value = "";
		self.close();
	}
</script>
</head>
<body>

	이미 존재하는 아이디입니다.<br>
	다시 입력해주세요.<br>
	<input type = "button" value = "재입력" onclick = "goBack()">

</body>
</html>