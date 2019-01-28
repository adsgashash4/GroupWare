<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Running Co-op :: welcome!</title>
<link rel="stylesheet" href="css/main.css" type="text/css"/>

<style type="text/css">
@import url(//fonts.googleapis.com/earlyaccess/nanumgothic.css);

	body{
		font-family: 'Nanum Gothic', sans-serif;
	}
	.btn{
		width: 220px;
		height: 40px;
	  	padding: 2px;
		margin: auto;
		color: white;
	    font-size: 18px;
	    background-color: #5cb85c;
	    border: 0.5px solid transparent;
	    border-radius: 4px;
	    text-align: center;
	  	cursor: pointer;
	}
</style>
<script type="text/javascript">
	function grJoinChild (val){
		window.open("./groupGoGo.do?mem_id="+val, "GroupCreate", "width=640, height=450, resizable = no, scrollbars = no");
	}
	
</script>
<title>Insert title here</title>
</head>
<body>
	<%
		String mem_id = (String)session.getAttribute("mem_id");
	%>
	<div id = "header">
		<jsp:include page="../header.jsp" flush="false"/>
	</div>
	<div id = "container" style = "padding:30px;">
		<!-- 가입완료메세지 -->
		<div style = "height:20%; margin-bottom: 40px;">
			<img src = "images/welcome.png" style = "margin-top:100px; width:650px; height:35px;">
		</div>
		<!-- 메인본문 -->
		<div style = "overflow: hidden; padding: 40px;">
			<div style = "float: left; width: 50%;">
				<div style = "width:100%; text-align: center;">
					<img alt="프로젝트시작" src = "images/project.png" style = "width:160px; height: 160px; margin-bottom:30px;">
				</div>
				<div style = "margin-bottom: 20px;"><span style = "font-size: 14px;">개인 프로젝트도 이제 효율적으로 관리해보세요!</span></div>
				<input type = "button" class = "btn" value = "프로젝트 시작하기" onclick = "location.href='./iProSelect.do'">
			</div>
			<div>
				<div style = "width:100%; text-align: center;">
					<img alt="그룹가입" src = "images/group.png" style = "width:160px; height: 160px; margin-bottom:30px;">
				</div>
				<div style = "margin-bottom: 20px;"><span style = "font-size: 14px;">그룹멤버들과 효율적으로 프로젝트를 관리해보세요!</span></div>
				<input type = "button" class = "btn"  value = "그룹 가입 신청하기" onclick = "location.href='./go_SearchForm.do'">
			</div>
		</div>
		<!-- 메인 본문 끝 -->
	</div>
	
	<div id = "footer">
		<jsp:include page="../footer.jsp" flush="false"/>
	</div>
</body>
</html>