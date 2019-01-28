<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 공지 게시판</title>
<link rel="stylesheet" href="css/main.css" type="text/css"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="js/paging.js"></script>

<script type="text/javascript">
	$(function(){
		var listCnt = $("#listCnt").val();
		$("#listCount").val(listCnt).prop("select", true);
	});
</script>
</head>
<body>
<div id = "header">
	<jsp:include page="../header.jsp" flush="false"/>
</div>
<div id = "container">
	<h3>공지 게시판</h3>
	
	<div class="pr_search_area">
				<form action="./noticeSList.do" method="post">
					<input type="text" name="sbr_title" />
					<input type="submit" class="body_btn pr_search_btn" value="검색" />
				</form>
	</div>
	
	<div id="div_select_area">
		<select class='notice_list_select' id='listCount' name='listCount' onchange="listCnt()">
				<option value='15'>15</option>
				<option value='30'>30</option>
				<option value='45'>45</option>
				<option value='60'>60</option>
		</select>
		<form action="./noticeSList.do" method="post" id="frmPaging">
			<input type='hidden' name='index' id='index' value='${paging.index}'>
			<input type='hidden' name='pageStartNum' id='pageStartNum' value='${paging.pageStartNum}'>
			<input type='hidden' name='listCnt' id='listCnt' value='${paging.listCnt}'>
			<input type='hidden' name='sbr_title' value="${ sbr_title }">
		</form>
	</div>
	
	<div class="notice_board_list">
		<table class="noticeTable">
			<tr>
				<th style="width: 10%;">번호</th>
				<th style="width: 50%;">제목</th>
				<th style="width: 20%;">작성자</th>
				<th style="width: 20%;">작성일</th>
			</tr>
			<c:choose>
				<c:when test="${ fn:length(slist) == 0 }">
					<tr>
						<td colspan="4">작성된 글이 없습니다</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="slist" items="${ slist }" varStatus="vs">
						<tr>
							<td>${ slist.get("RNUM") }</td>
							<td><a href="./boardView.do?sbr_uuid=${ slist.get('SBR_UUID') }">${ slist.get("SBR_TITLE") }</a></td>
							<td>${ slist.get("MEM_NAME") }</td>
							<td>${ slist.get("SBR_REGDATE") }</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</table>
		
		<div class="pagenum_div">
			<ul class="pagination">
				<!--맨 첫페이지 이동 -->
				<li><a href='#' onclick='pagePre(${paging.pageCnt+1},${paging.pageCnt});'>&laquo;</a></li>
				<!--이전 페이지 이동 -->
				<li><a href='#' onclick='pagePre(${paging.pageStartNum},${paging.pageCnt});'>&lsaquo;</a></li>
				<!--페이지번호 -->
				<c:forEach var='i' begin="${paging.pageStartNum}" end="${paging.pageLastNum}" step="1">
					<li><a href='#' onclick='pageIndex(${i});'>${i}</a></li>
				</c:forEach>
				<!--다음 페이지 이동 -->
				<li><a href='#' onclick='pageNext(${paging.pageStartNum},${paging.total},${paging.listCnt},${paging.pageCnt});'>&rsaquo;</a></li>
				<!--마지막 페이지 이동 -->
				<li><a href='#' onclick='pageLast(${paging.pageStartNum},${paging.total},${paging.listCnt},${paging.pageCnt});'>&raquo;</a></li>
			</ul>
		</div>
	</div>
	
</div>
<div id = "footer">
	<jsp:include page="../footer.jsp" flush="false"/>
</div>
</body>
</html>