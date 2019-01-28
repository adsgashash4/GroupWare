<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/main.css" type="text/css"/>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	$(function(){
		var resutl = "${rst}";
		if(resutl=="ture"){
		alert("메일 전송 완료");
		close();
		}
	})
</script>
</head>
<body>
<div id="container">
	<form action="./goGroupMail.do" method="post">
	<table>
		<tr>
			<td>수신자명: <input type = "text" name = "toName"></td>
		</tr>
		<tr>
			<td>이메일: <input type="text" name="toSend"></td>
		</tr>
		<tr><td><input type="submit" value="메일보내기"></td></tr>
	</table>
	</form>
</div>
</body>
</html>