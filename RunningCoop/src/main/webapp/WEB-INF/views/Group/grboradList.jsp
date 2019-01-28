<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html >
<html>
<head>
<script type="text/javascript">
	function cheakAll(chk){
		var box = document.getElementsByName("cnkUuid");
		for(var i=0; i<box.length; i++){
			box[i].checked = chk;
		}
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/main.css" type="text/css">
</head>
<body>
<div class="notice_board_list">
<form action="./bordeMultDelet.do" method="get">
	<table class="noticeTable">
		<tr>
		<td><input type="submit" value="선택삭제"></td>
		</tr>
		<tr>
		<td><input type="checkbox" onclick="cheakAll(this.checked)"></td>
		<td>제 목</td>
		<td>작성자</td>
		<td>작성일자</td>
		</tr>
	<c:forEach var="bdlist" items="${gblist}" varStatus="status">
		<tr>
		<td><input type="checkbox" value="${bdlist.br_uuid}" name="cnkUuid"></td>
		<td>${bdlist.br_title}</td>
		<td>${bdlist.memberdto.mem_name}</td>
		<td>${bdlist.br_regDate }</td>
		<td style="display: none;">${bdlist.br_uuid}</td>
		<td style="display: none;">${bdlist.br_noticeYN}</td>
	</tr>
	</c:forEach>
	</table>
</form>
</div>
</body>
</html>