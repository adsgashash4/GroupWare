<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 탈퇴-프로젝트관리자목록</title>
<link rel="stylesheet" href="css/main.css" type="text/css">
<style type="text/css">
	#projectPmList{
		clear: both;
		overflow: auto;
		padding-top: 20px;
	}
	
	.pmListTable{
		border-collapse: collapse;
		margin-top: 15px;
		width: 90%;
		margin: auto;		
	}
	
	.pmListTable th{
		color: white;
		background: darkgreen;
		font-weight: bold;
		text-align: center;
	}
	
	.pmListTable th, td, tr{
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
		<h3>PM으로 소속된 프로젝트 목록</h3>
		<div id = "projectPmList">
			<table class = "pmListTable">
				<tr>
					<th style = "width: 25%;">프로젝트아이디</th>
					<th style = "width: 30%;">프로젝트명</th>
					<th style = "width: 10%;">권한</th>
					<th style = "width: 25%;">그룹아이디</th>
					<th style = "width: 10%;">위임하기</th>
				</tr>
		 		<c:choose>
					<c:when test="${fn:length(pmSearchList)==0}">
						<tr>
							<td colspan="5">해당 프로젝트가 없습니다</td>
						</tr>
					</c:when>
					<c:otherwise> 
						<c:forEach var="pmList" items="${pmSearchList}">
							<tr>
								<td>${ pmList.get("PR_ID")}</td>
								<td>${ pmList.get("PR_NAME")}</td>
								<td>${ pmList.get("PR_LEVEL")}</td>
								<td>${ pmList.get("GR_ID")}</td>
								<td><input type = "button" value = "위임하기" style = "width: 80px; height: 30px; font-size:10pt; border-radius: 4px; background-color: #5cb85c; color: #fff; border: 0px;" onclick="location.href='./goProManage.do?pr_id=${pmList.get('PR_ID')}&gr_id=${pmList.get('GR_ID')}'"></td>
							</tr>
						</c:forEach>
		 			</c:otherwise>
				</c:choose>
			</table>
			<input type = "button" value = "돌아가기" style = "width: 80px; height: 30px; margin-top:20px; font-size:10pt; border-radius: 4px; border: 0px; background-color: #5cb85c; color: #fff;" onclick = "closeWindow();">
		</div>
	</div>
	
	<div id = "footer">
		<jsp:include page="../footer.jsp" flush="false"/>
	</div>

</body>
</html>