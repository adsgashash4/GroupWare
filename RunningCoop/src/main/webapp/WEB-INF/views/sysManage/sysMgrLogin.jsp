<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 시스템 관리자 로그인</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="js/account/main.js"></script>
<link rel="stylesheet" href="css/main.css" type="text/css">
<script type="text/javascript">
</script>
</head>
<body>

	<!-- 로고 -->
	<div class= "mainHeader">
		<img alt = "로고" src = "images/header/logo.png">
	</div>

	<!-- 사용자 로그인 화면으로 돌아가기 -->
	<div class = "go_user_Login" onclick="location.href='./main.do'">
		<span>일반 회원 로그인 하러 가기</span>
	</div>
	
	<div class = "mainContain">
		<div class = "accountBox">
		<h3>관리자 로그인</h3>
		<form class = "loginBox">
			<div class = "login">
				<div class = "idPart">
					<input type = "text" class="id" id = "loginId" name = "mem_id" placeholder = "아이디">
				</div>
				<div class = "pwPart">
					<input type = "password" class="pw" id = "loginPw" name = "mem_pw" placeholder = "비밀번호" onKeypress="if(event.keyCode==13) $('#loginBtn').click();">
				</div>
				<div class = "loginFail"></div>
			</div>
			<div class = "enter">
				<input type = "button" class="accountBtn" id = "loginBtn" value="로그인" onclick="doMgrLogin()" style="width: 300px; height: 40px; font-size: 14pt; border-radius: 4px; background-color: #5cb85c; color: #fff;">
			</div>
		</form>
		</div>
	</div>

</body>
</html>