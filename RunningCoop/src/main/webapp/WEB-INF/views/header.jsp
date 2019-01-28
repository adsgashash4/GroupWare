<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>헤더</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script type="text/javascript">
 function goHome(){
	 location.href = "./goHome.do?";
 }
 
 function sidebar_toggle(){
	 $("#footer").toggle(function(){
		if($(this).css("display") == "none"){
			$("#container").css("width", "100%");
		} else {
			$("#container").css("width", "80%");
		}
	 });
 }
</script>
</head>
<body>
	<% 
	   String userName = (String)session.getAttribute("mem_name"); 
	   String userId = (String)session.getAttribute("mem_id");
	%>
	<%
	   Calendar cal = Calendar.getInstance();
	   String year = String.valueOf(cal.get(Calendar.YEAR));
	   String month = String.valueOf(cal.get(Calendar.MONTH)+1);
	%>
	
	<img alt="개인 일정" class="user_calendar" src="./images/header/user_calendar.png" onclick="location.href='./viewSchedule.do?year=<%=year%>&month=<%=month%>'">
	<img alt="" class="user_logo" src="./images/header/logo.png" onclick="goHome()">
	<div class = "user_menu">
		<span class="user_edit"><a href="#" onclick="location.href='./enterModify.do'"><%= userName%></a>님 접속중</span>
		<input type = "button" class="header_btn user_logout" value = "LOGOUT" onclick = "location.href='./ckLogout.do'">
		<img alt="사이드 메뉴 토글" class="side_menu_toggle" src="./images/header/menu_toggle.png" onclick="sidebar_toggle()"/>
	</div>
</body>
</html>