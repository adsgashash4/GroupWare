<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 전체 그룹 검색</title>

<link rel="stylesheet" href="css/main.css" type="text/css" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="./js/paging.js"></script>

<%
	String mem_id = (String)session.getAttribute("mem_id"); 
	String gr_id = (String)session.getAttribute("gr_id");
%>
<script type="text/javascript">
$(function(){
	var listCnt = $("#listCnt").val();
	$("#listCount").val(listCnt).prop("selected", true);
});

var openwin;
var serarchWord = "";
	function openChild(val){
		var gr_id = $(val).siblings("input[name=gr_id]").val();
		var userid = "<%=mem_id%>";
			$.ajax({
			type : "POST",
			url	 : "./grCheck.do",
			data : "gr_id="+gr_id+"&mem_id="+userid,
			async: false,
			success : function(data){
				if(data==1){
					alert("이미 가입된 그룹 입니다.");
				} else {
					createPro(val);
				}
			}
		});
	}
	function doSearch(){
		var type = $("#search_select_type option:selected").val();
		var work = $("#search_word_02").val();
		serarchWord = $("#search_word_02").val();
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
	
	function createPro(val){
		var gr_id = ""+$(val).siblings("input[name=gr_id]").val();
		var gr_name = ""+$(val).siblings("input[name=gr_name]").val();
		
		$("#request_gr_id").val(gr_id);
		$("#request_gr_name").val(gr_name);
		$("#td_gr_name").text(gr_name);
		
		$("#request_Form").dialog({
			title : "그룹 가입 신청하기",
			height : 400,
			width : 400,
			position : {my : "center", at : "center"},
			resizable : false,
			modal : true,
		});
		
	}
</script>
</head>
<body>
<div id = "header">
	<jsp:include page="../header.jsp" flush="false"/>
</div>

<div id="container">
<h3>그룹 검색 목록</h3>
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
			<option value='15' >15</option>
			<option value='30'>30</option>
			<option value='45'>45</option>
			<option value='60'>60</option>
		</select>
		
		<form action="./allGrSelect.do" method="post" id='frmPaging'>
			<input type='hidden' name='index' id='index' value='${paging.index}'>
			<input type='hidden' name='pageStartNum' id='pageStartNum' value='${paging.pageStartNum}'>
			<input type='hidden' name='listCnt' id='listCnt' value='${paging.listCnt}'>
			<input type="hidden" name="gr_name" id="gr_name" value="${gr_name}">
		</form>
	</div>
	
	<div class="div_result_area">
		<table class="projectTable">
			<tr>
				<th style="width: 15%;">번호</th>
				<th style="width: 40%;">그룹명</th>
				<th style="width: 20%;">그룹담당자</th>
				<th style="width: 15%;">생성일</th>
				<th style="width: 10%;">가입신청</th>
			</tr>
			<c:forEach var="dtos" items="${lists}">
				<tr>
					<td>${ dtos.RNUM }</td>
					<td id="gr_name">${dtos.GR_NAME}</td>
					<td>${dtos.MEM_NAME}</td>
					<td>${dtos.GR_REGDATE }</td>
					<c:if test="${ fn:contains(dtos.GR_JOINYN, 'Y') == true }">
					<td>
						<input type="hidden" name="gr_id" value="${dtos.GR_ID }">
						<input type="hidden" name="gr_name" value="${dtos.GR_NAME}">
						<input type="button" value="가입신청" onclick="openChild(this)"/>
					</td>
					</c:if>
					<c:if test="${ fn:contains(dtos.GR_JOINYN, 'N') == true }">
						<td>가입불가</td>
					</c:if>
				</tr>						
			</c:forEach>
		</table>
		<div class="center">
			<ul class="pagination">
				<li><input type="hidden" value="${gr_name}" name="gr_name">
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
				<input type="text" name="gr_name" id="gr_name" class="searchbox" value="${gr_name}"/>
			</div>
		</form>
		<form id="projectSearch" action="./allPrSelect.do" method="post">
			<div class="ui-widget">
				<input type="text" name="pr_name" id="pr_name" />
			</div>
		</form>
	</div>	
	
	<div id="request_Form" style = "display:none;">
		<form action="./grWaitInsert.do" method="post">
		<table class="request_groupMem_table">
			<tr>
				<th>아이디 </th>
				<td id="request_mem_id"><%=mem_id%><input type="hidden" name="mem_id" value="<%=mem_id%>"/></td>
			</tr>
			<tr>
				<th>그룹명</th>
				<td id="td_gr_name"></td>
			</tr>
			<tr style="display:none;">
				<td colspan="2">
					<input type="hidden" id="request_gr_id" name="gr_id"/>
					<input type="hidden" id="request_gr_name" name="gr_name"/>
				</td>
			<tr>
				<th colspan="2">자기소개 </th>
			</tr>
			<tr>
				<td colspan="2"><textarea name="wait_content"></textarea></td>
			</tr>
		</table>
		<input type="submit" value="가입신청">
		</form>
	</div>
</div>
	
<div id = "footer">
	<jsp:include page="../footer.jsp" flush="false"/>
</div>

</body>
</html>