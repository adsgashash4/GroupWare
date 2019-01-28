<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 그룹 게시판</title>
<link rel="stylesheet" href="css/main.css" type="text/css"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
$(function(){
	$(".sbr_title").on("click", function(){
		var uuid = $(this).children("input[name=uuid]").val();
		
			location.href = "./grBoardView.do?br_uuid="+uuid;
	});
});

	function writer(){
		location.href = "./grBoradWriteForm.do";
	}
</script>
</head>
<body>
<div id = "header">
	<jsp:include page="../header.jsp" flush="false"/>
</div>
<div id = "container">
<h3>그룹 게시판</h3>
<div class="pr_search_area">
		<form action="./grBoradList.do" method="POST">
			<input type="text" name="br_title" /> 
			<input type='hidden' name='index' id='index' value='${paging.index}'>
			<input type='hidden' name='pageStartNum' id='pageStartNum' value='${paging.pageStartNum}'>
			<input type='hidden' name='listCnt' id='listCnt' value='${paging.listCnt}'>
			<input type="submit" value="검색" class="body_btn pr_search_btn"/>
		</form>
	</div>
	
	<div id="div_select_area">
		<select class='notice_list_select' id='listCount' name='listCount' onchange="listCnt()">
				<option value='15'>15</option>
				<option value='30'>30</option>
				<option value='45'>45</option>
				<option value='60'>60</option>
		</select>
			<form action="./grBoradList.do" method="post" id="frmPaging">
			<input type='hidden' name='index' id='index' value='${paging.index}'>
			<input type='hidden' name='pageStartNum' id='pageStartNum' value='${paging.pageStartNum}'>
			<input type='hidden' name='listCnt' id='listCnt' value='${paging.listCnt}'>
			<input type="hidden" name="br_title" value="${br_title}">
		</form>
	</div>
		
		<div class="user_control_div">
		<input type="button" id="write" class="body_btn qna_writeForm_btn" value="게시글작성" onClick="writer()" />
	</div>
<div class="notice_board_list">
	<table class="noticeTable">
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
			</tr>
			<c:choose>
			<c:when test="${ fn:length(grlists) == 0 }">
				<tr>
					<td colspan="4">작성된 글이 없습니다</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach var="list" items="${grlists}" varStatus="vs">
					<tr>
						<td>${vs.count}</td>
						<td><span class = "sbr_title">
							<input type="hidden" name = "uuid" value = "${ list.get('BR_UUID') }"/>
							<input type="hidden" name = "scryn" value="${ list.get('BR_TITLE') }"/>
							${ list.get("BR_TITLE") }</span></td>
						<td>${ list.get("MEM_NAME") }</td>
						<td>${ list.get("BR_REGDATE") }</td>
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
	<jsp:include page="../footer.jsp" flush="false" />
</div>
</body>
</html>