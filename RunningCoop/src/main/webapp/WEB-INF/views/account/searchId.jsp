<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 아이디찾기</title>
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/body/user_account.css">
<style type="text/css">
	.joinModal_content{	/*옮기면 안됨*/
		top: 10%;
		height: 270px;
		width: 30%;
	}
	
	.joinModal_header{	/*옮기면 안됨*/
		overflow: hidden;
	}
	
	.idPopClose{
		float: right;
		margin-right: 10px;
	}
	
	.join_row .int{	/*옮기면 안됨*/
		z-index: 0;
	}
</style>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
	function doSearchId(){
		if($("#searchName").val()==""||$("#searchEmail").val()==""){
			alert("이름과 이메일 모두 입력해주세요.");
		} else {
			
			$(".idModal").css("display", "block");
				var searchForm = $(".searchIdBox").serialize();
			$.ajax({
				type : "POST",
				url : "./searchId.do",
				data: searchForm,
				async: false,
				success: function(result){
					$("#searchName").val("");
					$("#searchEmail").val("");
					$(".join_modal").eq(0).css("display","block");
						if(result.resultId == null){
						$("#resultPart").text("조회된 결과가 없습니다");
					}else{
						$("#result").text(result.resultId); 
					}
				} 
			});
		}
	}
	
	function closeIdPop(){
		$(".join_modal").eq(0).css("display","none");
	}
	
 	function closeIdForm(){
		window.close();
	} 
 	
 	function openPwForm(){
 		$(".join_modal").eq(0).css("display","none");
 		$(".join_modal").eq(1).css("display","block");
 	}
 	
 	function closePwPop(){
 		$(".join_modal").eq(1).css("display","none");
 	}
 	
 	function doSearchPw(){
 		if($("#searchId").val()==""||$("#searchEmail2").val()==""){
			alert("아이디와 이메일 모두 입력해주세요.");
		}else{
			$.ajax({
				type: "GET",
				url: "./pwMailSending.do",
				data: "title="+$("#searchId").val()+"&toMail="+$("#searchEmail2").val(),
				async: false,
				success: function(result){
					if(result.pwMail){
						alert("새로운 임시 비밀번호를 해당 메일로 전송했습니다. 확인 후 재로그인 해주세요.");
						window.close();
					}else{
						alert("임시 비밀번호 전송이 실패하였습니다. 다시 시도해주세요.");
					}
				}
			});
		}
 	}
</script>
</head>
<body>
	<!-- 로고 -->
	<div class = "mainHeader">
		<img alt = "로고" src = "images/header/logo.png">
	</div>
	
	<!-- 게정 관리 부분 -->
 	<div class = "mainContain">
		아이디 찾기
		<div class = "accountBox">
		<form class = "searchIdBox">
			<fieldset class = "joinField">
				<div class = "rowGroup">
					<div class = "join_row">
						<span class = "ps_box int_id">
							<input type = "text" class = "int" id = "searchName" name = "mem_name" placeholder = "이름" style = "width:100%;">
						</span>
					</div>
					<div class = "join_row">
						<span class = "ps_box int_id">
							<input type = "text" class = "int" id = "searchEmail" name = "mem_email" placeholder = "이메일" value = "" onKeypress="if(event.keyCode==13) $('#searchIdBtn').click();">
						</span>
					</div>
				</div>
				<span style = "font-size: 8pt;">회원정보에 등록한 이름과 이메일 계정이 입력한 이름, 이메일 계정과 같아야, 아이디를 조회할 수 있습니다.</span>
			</fieldset>
			<div class = "enter">
				<input type = "button" class = "accountBtn" id = "searchIdBtn" value = "아이디찾기" onclick="doSearchId()" style = "width: 300px; height: 40px; font-size:14pt; border-radius: 4px; background-color: #5cb85c; color: #fff;">
			</div>
		</form>
		<span style = "font-size: 10pt; color: black; text-decoration: underline;" onclick = "openPwForm()">비밀번호찾기</span>
		</div> 
		<!-- 아이디 찾기 끝 -->
		
		<!-- 아이디 결과 -->
  		<div class = "join_modal idModal">
			<div class = "joinModal_content">
				<div class = "joinModal_header">
					<h3 style="font-size:20px; display:inline;">아이디 조회 결과</h3>
					<img class = "idPopClose" alt="아이디조회팝업 닫기" src="images/cancel.png" onclick = "closeIdPop()">
				</div>
				<div class = "joinModal_body">
					<h3 id = "resultPart" style = "padding-top:30px;">회원님의 아이디는 <span id="result"></span>입니다.</h3>
					<input type = "button" class = "accountBtn" style = "padding-bottom: 20px;" value = "로그인하기" onclick = "closeIdForm()">
					<input type = "button" class = "accountBtn" style = "padding-bottom: 20px;" value = "비밀번호찾기" onclick = "openPwForm()">
				</div>
			</div>
		</div> 
		
		<!-- 비밀번호 찾기 -->
  		<div class = "join_modal pwModal">
			<div class = "joinModal_content">
				<div class = "joinModal_header">
					<h3 style="font-size:20px; display:inline;">비밀번호 찾기(재발급)</h3>
					<img class = "idPopClose" alt="아이디조회팝업 닫기" src="images/cancel.png" onclick = "closePwPop()">
				</div>
				<div class = "joinModal_body">
					<form class = "searchPwBox">
						<fieldset class = "joinField">
							<div class = "rowGroup">
								<div class = "join_row">
									<span class = "ps_box int_id">
										<input type = "text" class = "int" id = "searchId" name = "mem_id" placeholder = "아이디" style = "width:100%;">
									</span>
								</div>
								<div class = "join_row">
									<span class = "ps_box int_id">
										<input type = "text" class = "int" id = "searchEmail2" name = "mem_email" placeholder = "이메일" value = "" onKeypress="if(event.keyCode==13) $('#searchPwBtn').click();">
									</span>
								</div>
							</div>
							<span style = "font-size: 8pt;">회원정보에 등록한 이름과 이메일 계정이 입력한 이름, 이메일 계정과 같아야, 임시 비밀번호를 발급받을 수 있습니다.</span>
						</fieldset>
						<div class = "enter">
							<input type = "button" class = "accountBtn" id = "searchPwBtn" value = "비밀번호 재발급" style = "width:100px; padding-bottom: 20px;" onclick="doSearchPw()">
						</div>
				</form>
				</div>
			</div>
		</div> 
	</div>
</body>
</html>