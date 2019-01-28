<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 진행완료인 프로젝트 목록</title>

<link rel="stylesheet" href="css/main.css" type="text/css"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="js/paging.js"></script>

<script type="text/javascript">
	function view_Detail(val){
		$.ajax({
			type : "POST",
			url : "./detailPro.do",
			data : "pr_id="+val,
			async : false,
			success : function(nodes){
				var pr_name = nodes.PR_NAME;
				var mem_name = nodes.MEM_NAME;
				var pr_memcnt = nodes.PR_MEMCNT;
				var pr_goal = nodes.PR_GOAL;
				var pr_enddate = nodes.PR_ENDDATE;
				var pr_etc = nodes.PR_ETC;
				
				$("#pr_name").text(pr_name);
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
		});
	}
	
	function goSelectPro(){
		$(".pr_detail_view").css("display", "none");
		$(".pr_detail_view").eq(0).children("p").html("");
	}
	
	$(function(){
		$("#group_project_list").css("display", "none");
		$("input[name=project_type]").on("change", function(){
			var value = $(this).val();
			if(value == "group"){
				$("#individual_project_list").css("display", "none");
				$("#group_project_list").css("display", "block");
			} else {
				$("#group_project_list").css("display", "none");
				$("#individual_project_list").css("display", "block");
			}
		});
		
		var listCnt = $("#iListCnt").val();
		$("#listCount").val(listCnt).prop("selected", true);
	});
</script>
</head>
<body>
	<div id="header">
		<jsp:include page="../header.jsp" flush="false" />
	</div>
	<div id="container">
		<h3>진행 완료인 프로젝트 목록</h3>
		
		<div class="pr_search_area">
			<form action="./goDoneSelect.do" method="post">
				<input type="hidden" name="pr_condition" value="C" />
				<input type="text" name="pr_name" />
				<input type="submit" class="body_btn pr_search_btn" value="검색" />
			</form>
		</div>
		
		<div id="div_select_area">
			<input type="radio" name="project_type" value="individual" checked/>개인 프로젝트
			<input type="radio" name="project_type" value="group"/>그룹 프로젝트&nbsp;&nbsp;
			<select class='project_list_select' id='listCount' name='listCount' onchange="projectListCnt()">
					<option value='15'>15</option>
					<option value='30'>30</option>
					<option value='45'>45</option>
					<option value='60'>60</option>
			</select>
			<form action="./goDoneSelect.do" method="post" id="frmPaging">
				<input type='hidden' name='gIndex' id='gIndex' value='${gPaging.index}'>
				<input type='hidden' name='gPageStartNum' id='gPageStartNum' value='${gPaging.pageStartNum}'>
				<input type='hidden' name='gListCnt' id='gListCnt' value='${gPaging.listCnt}'>
				<input type='hidden' name='iIndex' id='iIndex' value='${iPaging.index}'>
				<input type='hidden' name='iPageStartNum' id='iPageStartNum' value='${iPaging.pageStartNum}'>
				<input type='hidden' name='iListCnt' id='iListCnt' value='${iPaging.listCnt}'>
				<input type="hidden" name="pr_name" value="${ pr_name }"/>
			</form>
		</div>
		
		<div id="individual_project_list">
			<table class="projectTable">
				<tr>
					<th style="width: 10%;">번호</th>
					<th style="width: 15%;">소속</th>
					<th style="width: 50%;">프로젝트명</th>
					<th style="width: 15%;">PM명</th>
					<th style="width: 10%;">진행률</th>
				</tr>
				<tr>
					<td colspan="5" style="background-color : #E8E8E8;">개인프로젝트</td>
				</tr>
				<c:choose>
					<c:when test="${ fn:length(iPrList) == 0 }">
						<tr>
							<td colspan="5">조회가능한 개인 프로젝트가 없습니다</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="ilist" items="${ iPrList }">
							<tr onclick="view_Detail('${ ilist.PR_ID}')">
								<td>${ ilist.RNUM }</td>
								<td>개인</td>
								<td><span class="pr_name">${ ilist.PR_NAME }</span></td>
								<td>${ ilist.MEM_NAME }</td>
								<td>${ ilist.PR_PRORATE }%</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
			
			<div class="pagenum_div">
				<ul class="pagination">
					<!--맨 첫페이지 이동 -->
					<li><a href='#'
						onclick='pagePre(${iPaging.pageCnt+1},${iPaging.pageCnt});'>&laquo;</a></li>
					<!--이전 페이지 이동 -->
					<li><a href='#'
						onclick='pagePre(${iPaging.pageStartNum},${iPaging.pageCnt});'>&lsaquo;</a></li>
					<!--페이지번호 -->
					<c:forEach var='i' begin="${iPaging.pageStartNum}" end="${iPaging.pageLastNum}" step="1">
						<li><a href='#' onclick='projectPageIndex(${i});'>${i}</a></li>
					</c:forEach>
					<!--다음 페이지 이동 -->
					<li><a href='#'
						onclick='pageNext(${iPaging.pageStartNum},${iPaging.total},${iPaging.listCnt},${iPaging.pageCnt});'>&rsaquo;</a></li>
					<!--마지막 페이지 이동 -->
					<li><a href='#'
						onclick='pageLast(${iPaging.pageStartNum},${iPaging.total},${iPaging.listCnt},${iPaging.pageCnt});'>&raquo;</a></li>
				</ul>
			</div>
		</div>
		
		<div id="group_project_list" style="display:none;">
				<table class="projectTable">
					<tr>
						<th style="width: 10%;">번호</th>
						<th style="width: 15%;">소속</th>
						<th style="width: 50%;">프로젝트명</th>
						<th style="width: 15%;">PM명</th>
						<th style="width: 10%;">진행률</th>
					</tr>
					<tr>
						<td colspan="5" style="background : #E8E8E8;">그룹 프로젝트</td>
					</tr>
					<c:choose>
						<c:when test="${ fn:length(gPrList) == 0 }">
							<tr>
								<td colspan="5">조회가능한 그룹 프로젝트가 없습니다.</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="glist" items="${ gPrList }">
								<tr onclick="view_Detail('${ glist.PR_ID }')">
									<td>${ glist.RNUM }</td>
									<td>${ glist.GR_NAME }</td>
									<td><span class="pr_name">${ glist.PR_NAME }</span></td>
									<td>${ glist.MEM_NAME }</td>
									<td>${ glist.PR_PRORATE }%</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</table>
				
				<div class="pagenum_div">
					<ul class="pagination">
						<!--맨 첫페이지 이동 -->
						<li><a href='#' onclick='pagePre(${gPaging.pageCnt+1},${gPaging.pageCnt});'>&laquo;</a></li>
						<!--이전 페이지 이동 -->
						<li><a href='#' onclick='pagePre(${gPaging.pageStartNum},${gPaging.pageCnt});'>&lsaquo;</a></li>
						<!--페이지번호 -->
						<c:forEach var='i' begin="${gPaging.pageStartNum}" end="${gPaging.pageLastNum}" step="1">
							<li><a href='#' onclick='projectPageIndex(${i});'>${i}</a></li>
						</c:forEach>
						<!--다음 페이지 이동 -->
						<li><a href='#'
							onclick='pageNext(${gPaging.pageStartNum},${gPaging.total},${gPaging.listCnt},${gPaging.pageCnt});'>&rsaquo;</a></li>
						<!--마지막 페이지 이동 -->
						<li><a href='#'
							onclick='pageLast(${gPaging.pageStartNum},${gPaging.total},${gPaging.listCnt},${gPaging.pageCnt});'>&raquo;</a></li>
					</ul>
				</div>
		</div>
		
		<div class="pr_detail_view">
			<table class="pr_detail_table">
				<tr>
					<th>프로젝트명</th>
					<td><span id="pr_name"></span></td>
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
	
	<div id="footer">
		<jsp:include page="../footer.jsp" flush="false" />
	</div>
</body>
</html>