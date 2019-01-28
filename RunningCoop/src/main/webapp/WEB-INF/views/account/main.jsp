<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>프로젝트관리툴 :: Running Coop!</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="js/account/main.js"></script>
<link rel="stylesheet" href="css/main.css" type="text/css">
<style type="text/css">
@import url(//fonts.googleapis.com/earlyaccess/nanumgothic.css);

	body{
		font-family: 'Nanum Gothic', sans-serif;
	}

</style>

</head>
<body>
	
	<!-- 관리자 로그인 -->
	<div>
		<input type="button" class = "accountBtn" value="관리자 로그인" style = "position: absolute; right: 10px; top: 10px;" onclick="location.href='./adminLogin.do'"/>
	</div>	
	
	<!-- 로고 -->
	<div class = "mainHeader">
		<img alt = "로고" src = "images/header/logo.png">
	</div>
	
	<!-- 게정 관리 부분 -->
	<div class = "mainContain">
		<!-- 로그인 -->
		<div class = "accountBox">
		<form class = "loginBox">
			<div class = "login">
				<div class = "idPart">
					<input type = "text" class = "id" id = "loginId" name = "mem_id" placeholder = "아이디" value = "">
				</div>
				<div class = "pwPart">
					<input type = "password" class = "pw" id = "loginPw" name = "mem_pw" placeholder = "비밀번호" value = "" onKeypress="if(event.keyCode==13) $('#loginBtn').click();">
				</div>
				<div class = "loginFail"></div>
			</div>
			<div class = "enter">
				<input type = "button" class = "accountBtn" id = "loginBtn" value = "로그인" style = "width: 300px; height: 40px; font-size:14pt; border-radius: 4px; background-color: #5cb85c; color: #fff;" onclick = "doLogin()">
			</div>
		</form>
		<span class="search_userInfo" style = "font-size: 8pt; color: black; text-decoration: underline;" onclick = "searchAccount()">아이디/비밀번호찾기</span>
		<span class="search_userInfo" style = "font-size: 8pt; color: black; text-decoration: underline;" onclick = "joinAccount()">회원가입</span>
		</div>
		
		<!-- 회원가입 -->
		<div class = "join_modal">
			<div class = "joinModal_content">
				<div class = "joinModal_header">
					<p style="font-size:20px; display:inline;">회원가입</p>
					<img alt="닫기" src="images/project/wd_close_btn.png" onclick="closeJoin()"/>
				</div>
				<div class = "joinModal_body">
					<div class = "joinAccount">
						<form class = "joinBox" action="./chkJoin.do" method="post">
							<fieldset class = "joinField">
								<legend></legend>
								<div class = "rowGroup">
					 				<div class = "join_row">
					 					<span class = "ps_box int_id">
					 						<input type = "text" class = "int" id = "id" name = "mem_id" placeholder = "아이디(영소문자, 숫자 포함 8~12자)" style = "width: 50%; float:left;"> 
					 						<input type = "button" class = "accountBtn" id = "idChk" value = "중복확인" onclick = "id_chk()" style = "float:right; margin-right:5px;">
											<span id = "resultIdChk"></span>
					 					</span>
					 				</div>
					 				<div class = "join_row">
					 					<span class = "ps_box int_id">
					 						<input type = "password" class = "int" id = "pw" name = "mem_pw" placeholder = "비밀번호(영소문자, 숫자 포함 8~16자)">
					 					</span>
					 				</div>
					 				<div class = "join_row">
					 					<span class = "ps_box int_id">
					 						<input type = "password" class = "int" id = "pw2" placeholder = "비밀번호 재확인"  style = "width: 70%; float: left;">
					 						<input type = "button" class = "accountBtn" value = "확인" onclick = "pw_chk()" style = "float:right; margin-right:5px;">
					 					</span>
					 				</div>
					 				<div class = "join_row">
					 					<span class = "ps_box int_id">
					 						<input type = "text" class = "int" id = "name" name = "mem_name" placeholder = "이름(대소문자를 구분한 영문, 한글, 숫자포함 10자 이내)" style = "width: 70%; float: left;">
					 						<input type = "button" class = "accountBtn" onclick = "name_chk()" value = "확인" style = "float:right; margin-right:5px;">
					 					</span>
					 				</div>
								</div>
								<div class = "rowGroup">
									<div class = "join_row">
										<span class = "ps_box int_id">
											 <input type = "text" class = "int" id = "email" name = "mem_email" placeholder = "이메일" style = "width: 60%; float: left;">
											 <input type = "button" class = "accountBtn" onclick = "chkMail()" value = "중복확인" style = "margin-right:2px;">
											 <input type = "button" class = "accountBtn" onclick = "sendMail()" value = "인증번호 전송" style = "float:right; margin-right:5px;">
										</span>
									</div>
									<div class = "join_row">
										<span class = "ps_box int_id">
											<div id = "chkInfo" style = "overflow: auto;">
												<%String num = (String)session.getAttribute("identifyNum"); %>
												<input type = "text" class= "int" id = "num" placeholder = "인증번호입력" style = "width: 60%; float: left;">
												<div class = "timeCut" style = "float: left; overflow: auto;">
													<span id = "minute" style = "font-size:9pt;"></span>
													<span id = "second" style = "font-size:9pt;"></span>
												</div>
												<input type = "button" class = "accountBtn" id = "btnIdentify" value = "확인" onclick = "identifyNum()" style = "float:right; margin-right:5px;">
											</div> 
										</span>
									</div>
									<div class = "join_row">
										<span class = "ps_box int_id" style = "overflow : auto;">
											<input type = "text" class = "int" id = "phone1" placeholder = "연락처(ex.010)" style = "width: 30%; float: left; text-align: center;"><span style = "font-size:14pt; float: left; padding: 0px 5px 0px 5px;">-</span>
											<input type = "text" class = "int" id = "phone2" placeholder = "연락처(ex.1234)" style = "width: 30%; float: left; text-align: center;"><span style = "font-size:14pt; float: left; padding: 0px 5px 0px 5px;">-</span>
											<input type = "text" class = "int" id = "phone3" placeholder = "연락처(ex.1234)" style = "width: 30%; float: left; text-align: center;">
											<input type = "hidden" id = "phone" name = "mem_phone"><br>
										</span>
									</div>
								</div>
							</fieldset>
							<div class = "enter">
								<input type = "submit" class = "accountBtn" id = "join" value = "가입" style = "width: 440px; height: 40px; padding-top: 5px; padding-bottom: 5px;">
							</div>
						</form>
					</div>						
				</div>
			</div>
		</div>
		<!-- 회원가입 끝 -->
	</div>
	
</body>
</html>
