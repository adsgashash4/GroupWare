<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 관리자 변경</title>

<link rel="stylesheet" href="css/main.css" type="text/css"/>
<style type="text/css">
	.mgrChange_list{
		width: 332px;
		height: 550px;
		height: 100%;
		border: 0.5px solid lightgrey;
	}
	
	.mgrChange_table{
		width:95%; 
		border-collapse: collapse;
		margin: auto;
	}
	
	.mgrChange_table th{
		background: darkgreen;
		color: white;
	}
</style>

<%
	String gr_id = (String)session.getAttribute("gr_id");
	String mem_id2 = (String)session.getAttribute("mem_id");
%>
<link rel="stylesheet" href="css/main.css" type="text/css"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
	function changeGrMgr(){
		$.ajax({
			type: "GET",
			url: "./grMgChange.do",
			data: "mem_id="+$("#mem_id").val()+"&mem_id2="+$("#mem_id2").val(),
			async: false,
			success: function(msg){
				if(msg.result){
					if(confirm("관리자 위임이완료됐습니다.")){
						window.close();
						opener.location.href = "./myGrSelect.do";
					}
				}else{
					alert("관리자 위임이 실패됐습니다. 다시 확인해주세요.");
				}
			}
		});
	}
</script>

</head>
<body>
<div id="container" style = "top:0px;">
	<form class = "mgrChange_list">
		<h3>그룹 멤버 목록</h3>
		<input name="mem_id2" id = "mem_id2" type="hidden" value="<%=mem_id2%>">
		<table class = "mgrChange_table">
			<tr>
				<th style = "width:24%;">관리자변경</th>
				<th style = "width:38%;">이름</th>
				<th style = "width:38%;">아이디</th>
			</tr>
			<c:forEach var="memlists" items="${memlists}">
			<tr>
				<td style = "width:30%;"><input name="mem_id" id = "mem_id" type="radio" value="${memlists.mem_id}"></td>
				<td style = "width:35%;">${memlists.mem_name}</td>
				<td style="width:35%;">${memlists.mem_id}</td>
			</tr>	
			</c:forEach>
		</table>
		<div style = "height: 10%; margin-top:20px;">
			<input type="button" value="관리자변경" onclick = "changeGrMgr()">
		</div>
	</form>
</div>
</body>
</html>