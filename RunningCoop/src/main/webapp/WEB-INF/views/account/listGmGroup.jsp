<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 탈퇴-그룹관리자목록</title>
<link rel="stylesheet" href="css/main.css" type="text/css">
<style type="text/css">
	#groupGmList{
		clear: both;
		overflow: auto;
		padding-top: 20px;
	}
	
	.gmListTable{
		border-collapse: collapse;
		margin-top: 15px;
		width: 90%;
		margin: auto;		
	}
	
	.gmListTable th{
		color: white;
		background: darkgreen;
		font-weight: bold;
		text-align: center;
	}
	
	.gmListTable th, td, tr{
		border-top: 0.5px solid #D8D8D8;
		border-bottom: 0.5px solid #D8D8D8; 	
	}
</style>
<script type="text/javascript" src="js/account/modify.js"></script>
</head>
<body>

	<div id = "header">
   		<jsp:include page="../header.jsp" flush="false"/>
	</div>

	<div id = "container">
		<h3>GM으로 소속된 그룹 목록</h3>
		<div id = "groupGmList">
			<table class = "gmListTable">
				<tr>
					<th>그룹아이디</th>
					<th>그룹명</th>
					<th>권한</th>
					<th>위임하기</th>
				</tr>
		 		<c:choose>
					<c:when test="${fn:length(gmSearchList)==0}">
						<tr>
							<td colspan="4">해당 그룹이 없습니다</td>
						</tr>
					</c:when>
					<c:otherwise> 
						<c:forEach var="gmList" items="${gmSearchList}">
							<tr>
								<td>${ gmList.get("GR_ID")}</td>
								<td>${ gmList.get("GR_NAME")}</td>
								<td>${ gmList.get("GR_LEVEL")}</td>
								<td><input type = "button" value = "위임하기" style = "width: 80px; height: 30px; font-size:10pt; border-radius: 4px; background-color: #5cb85c; color: #fff; border: 0px;" onclick="location.href='./grmodify.do?gr_id=${gmList.get('GR_ID')}'"></td>
							</tr>
						</c:forEach>
		 			</c:otherwise>
				</c:choose> 
			</table>
			<input type = "button" value = "돌아가기" style = "width: 80px; height: 30px; margin-top: 20px; font-size:10pt; border-radius: 4px; border: 0px; background-color: #5cb85c; color: #fff;" onclick = "closeWindow();">
		</div>
	</div>
	
	<div id = "footer">
		<jsp:include page="../footer.jsp" flush="false"/>
	</div>

</body>
</html>
