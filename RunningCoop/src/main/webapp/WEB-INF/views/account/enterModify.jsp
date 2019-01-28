<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 개인정보수정</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="js/account/modify.js"></script>
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/body/user_account.css">
<style type="text/css">
	.subContainer1, .subContainer2{
		margin-top: 160px;
		text-align: center;
	}

	.pwChkBox{
		width: 350px;
		height: 100px;
		border: 0.5px solid #B4B4B4; 
		padding: 20px;
		margin: auto;
		margin-top: 20px;
	}	
	
	.subContainer2{
		display: none;
	}
</style>
</head>
<body>

	<div id = "header">
  		<jsp:include page="../header.jsp" flush="false"/>
	</div>
	<div id = "container">
		<div class = "subContainer1">
			<div style = "height: 20%;">
				<span style = "font-size:30pt;">CHECK</span>
				<div style="overflow: hidden;">
					<img alt="본인인증안내" src="images/exclamation.png" style = "width: 30px; height: 30px;">
					<p style = "font-size: 10pt; display: inline-block;">개인정보 수정 전, 등록된 비밀번호로 본인인증을 해야합니다.</p>
				</div>
			</div>
			<div class = "pwChkBox">
				<form class = "pwChkForm" method="post">
					<div style = "margin-bottom: 30px;">
						<input type = "password" class = "int" id = "ckPw" name = "mem_pw" placeholder = "비밀번호" style = "width: 300px; height:30px; font-size:12pt; text-align: center;">
					</div>
					<div class = "enter">
						<input type = "submit" class = "accountBtn" id = "ckPwBtn" value = "비밀번호 확인" style = "width: 300px; height: 40px; font-size:14pt; border-radius: 4px; background-color: #5cb85c; color: #fff;">
					</div>
				</form>
			</div>
		</div>

	</div>
	<div id = "footer">
   		<jsp:include page="../footer.jsp" flush="false"/>
	</div>

</body>
</html>