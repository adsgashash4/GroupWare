<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 전체 프로젝트 검색</title>

<link rel="stylesheet" href="css/main.css" type="text/css"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script src="./js/paging.js"></script>

<script type="text/javascript">
$(function(){
	var listCnt = $("#ListCnt").val();
	$("#listCount").val(listCnt).prop("selected", true);
});

function detailPro(val){
	$.ajax({
		type : "POST",
		url : "./detailPro.do",
		data : "pr_id="+val,
		async : false,
		success : function(msg){
			showProDetail(msg)
		}
	});
}

function showProDetail(nodes){
	var pr_name = nodes.PR_NAME;
	var mem_name = nodes.MEM_NAME;
	var pr_memcnt = nodes.PR_MEMCNT;
	var pr_goal = nodes.PR_GOAL;
	var pr_enddate = nodes.PR_ENDDATE;
	var pr_etc = nodes.PR_ETC;
	
	$("#pr_name_detail").text(pr_name);
	$("#mem_name").text(mem_name);
	$("#pr_memcnt").text(pr_memcnt);
	$("#pr_goal").text(pr_goal);
	$("#pr_enddate").text(pr_enddate);
	$("#pr_etc").text(pr_etc);
	
	$(".pr_detail_view").css("display", "block");
	$(".pr_detail_view").dialog({
		title : "프로젝트 정보 보기",
		height : 400,
		width : 500,
		position : {my : "center", at : "center"},
		resizable : false,
		modal : true
	});
}

function goSelectPro(){
	$(".pr_detail_view").css("display", "none");
	$(".pr_detail_view").eq(0).children("p").html("");
}

function doSearch(){
	var type = $("#search_select_type option:selected").val();
	var work = $("#search_word_02").val();

	if(type == "choice"){
		alert("검색 타입을 선택해주세요!");
	} else if (type == "group"){
		$("#gr_name").val(work);
		$("#groupSearch").submit();	
	} else {
		$("#pr_name").val(work);
		$("#projectSearch").submit();
	}
}
</script>
</head>
<body>
<div id = "header">
	<jsp:include page="../header.jsp" flush="false"/>
</div>

<div id = "container">
<h3>프로젝트 검색 목록</h3>
	<div class="search_project_div">
		<select class="search_select_type" id="search_select_type">
			<option value="choice">선택</option>
			<option value="group">그룹</option>
			<option value="project">프로젝트</option>
		</select>
		<input type="text" id="search_word_02" name="search_word"/>
		<button class="body_btn do_search_btn_02" onclick="doSearch()">검색</button>
	</div>
	
	<div id="div_select_area">
		<select class='project_list_select' id='listCount' name='listCount' onchange='listCnt();'>
			<option value='15'>15</option>
			<option value='30'>30</option>
			<option value='45'>45</option>
			<option value='60'>60</option>
		</select>
		<form action="./allPrSelect.do" method="post" id='frmPaging'>
			<input type='hidden' name='index' id='index' value='${paging.index}'>
			<input type='hidden' name='pageStartNum' id='pageStartNum' value='${paging.pageStartNum}'>
			<input type='hidden' name='listCnt' id='listCnt' value='${paging.listCnt}'>
			<input type="hidden" name="pr_name" id="pr_name" value="${pr_name}">
		</form>
	</div>
	
	<div class="div_result_area">
		<table class="projectTable">
			<tr>
				<th style="width: 10%;">번호</th>
				<th style="width: 20%;">소속</th>
				<th style="width: 40%;">프로젝트명</th>
				<th style="width: 30%;">PM명</th>
			</tr>
			<c:choose>
				<c:when test="${ fn:length(list) == 0 }">
					<tr>
						<td colspan="4">검색된 프로젝트가 없습니다</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="list" items="${ list }" varStatus="vs">
						<tr>
							<td>${ list.RNUM }</td>
							<td>${ list.GRYN }</td>
							<td><span class="pr_name" onclick="detailPro('${list.PR_ID}')">${ list.PR_NAME }</span></td>
							<td>${ list.MEM_NAME }</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</table>
		<div class="center">
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
			
	<div class="hide_form">
		<form id="groupSearch" action="./allGrSelect.do" method="post">
			<div class="ui-widget">
				<input type="text" name="gr_name" id="gr_name" class="searchbox"/>
			</div>
		</form>
		<form id="projectSearch" action="./allPrSelect.do" method="post">
			<div class="ui-widget">
				<input type="text" name="pr_name" id="pr_name" />
			</div>
		</form>
	</div>
	
	<div class="pr_detail_view">
		<table class="pr_detail_table">
			<tr>
				<th>프로젝트명</th>
				<td><span id="pr_name_detail"></span></td>
			</tr>
			<tr>
				<th>프로젝트관리자</th>
				<td><span id="mem_name"></span></td>
			</tr>
			<tr>
				<th>프로젝트 인원</th>
				<td><span id="pr_memcnt"></span></td>
			</tr>
			<tr>
				<th>프로젝트목적</th>
				<td><span id="pr_goal"></span></td>
			</tr>
			<tr>
				<th>프로젝트마감기한</th>
				<td><span id="pr_enddate"></span></td>
			</tr>
			<tr>
				<th style="height : 80px;">비고</th>
				<td style="height : 80px;"><span id="pr_etc"></span></td>
			</tr>
		</table>
	</div>
</div>

<div id = "footer">
	<jsp:include page="../footer.jsp" flush="false"/>
</div>
</body>
</html>