<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 개인 프로젝트 관리</title>

<link rel="stylesheet" href="css/main.css" type="text/css"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>

<script type="text/javascript">
	$(function(){
		loadPage();
	})
	
	function loadPage(){
		var chkyn = $("#searchYN").val();
		if (chkyn == 'A'){
			$("input:radio[name='pr_searchYN']:radio[value='A']").attr("checked", true);
		} else {
			$("input:radio[name='pr_searchYN']:radio[value='N']").attr("checked", true);
		}
		var condition = $("#condition").val();
		if (condition == 'T'){
			$("select[name='pr_condition'] option:eq(0)").attr("selected", "selected");
		} else if (condition == 'I'){
			$("select[name='pr_condition'] option:eq(1)").attr("selected", "selected");
		} else {
			$("select[name='pr_condition'] option:eq(2)").attr("selected", "selected");
		}
	}
	
	function projectEdit(){
		var pr_id = $("#pr_id").val();
		var pr_name = $("input[name=pr_name]:eq(0)").val();
		var pr_endDate = $("input[name=pr_endDate]:eq(0)").val();
		var pr_goal = $("textarea[name=pr_goal]:eq(0)").val();
		var pr_searchYN = $("input[name=pr_searchYN]:checked").val();
		var pr_condition = $("select[name=pr_condition] option:selected").val();
		
		$.ajax({
			type : "POST",
			url : "./projectEdit.do",
			data : {"pr_id":pr_id, "pr_name":pr_name, "pr_endDate":pr_endDate, "pr_goal":pr_goal, "pr_searchYN":pr_searchYN, "pr_condition":pr_condition},
			async : false,
			success : function(msg){
				if(msg == "success"){
					location.reload();
				} else {
					alert("수정에 실패했습니다");
				}
			}
		});
	}
	
	// 프로젝트 해체 기능
	function project_delete(){
		var isc = confirm("프로젝트를 삭제하시겠습니까?");
		
		if(isc){
			var pr_id = $("#pr_id").val();
			
			$.ajax({
				type : "POST",
				url : "./project_Delete.do",
				data : "pr_id="+pr_id,
				async : false,
				success : function(msg){
					if(msg == "success"){
						alert("프로젝트 삭제 성공");
						location.href= "./iProSelect.do";
					} else {
						alert("프로젝트 삭제 실패");
						location.reload();
					}
				}
			});
		}
	}
	
	function backToproject(){
		var pr_id = $("#pr_id").val();
		location.href="./goProject.do?pr_id="+pr_id;
	}
	
</script>
</head>
<body>
<div id = "header">
	<jsp:include page="../header.jsp" flush="false"/>
</div>
<div id = "container">
	<input type="hidden" id = "pr_id" value="${ pr_id }"/>
	<h3>개인 프로젝트 관리 페이지</h3>
	<div id = "backProject">
		<input type="button" class="body_btn backToProject_btn" value="프로젝트로 돌아가기" onclick="backToproject()"/>
	</div>
	<div id = "con_body">
		<div class = "info_manage">
			<h4>프로젝트 정보 수정</h4>
			<c:set var="detail" value="${ detail }"></c:set>
			<table class="project_manage_table">
				<tr>
					<th>프로젝트 명</th>
					<td><input type="text" name="pr_name" value="${ detail.PR_NAME }"/></td>
				</tr>
				<tr>
					<th>프로젝트 담당자</th>
					<td>${ detail.MEM_NAME }</td>
				</tr>
				<tr>
					<th>프로젝트 시작일</th>
					<td>${ detail.PR_STARTDATE }</td>
				</tr>
				<tr>
					<th>프로젝트 마감기한</th>
					<td><input type="date" name="pr_endDate" value="${ detail.PR_ENDDATE }"/></td>
				</tr>
				<tr>
					<th>공개 여부</th>
					<td><input type="hidden" id="searchYN" value="${ detail.PR_SEARCHYN }"/>
						<input type="radio" name="pr_searchYN" value="A"/>공개 <input type="radio" name="pr_searchYN" value="N"/>비공개
					</td>
				</tr>
				<tr>
					<th>진행상태</th>
					<td>
						<input type="hidden" id="condition" value="${ detail.PR_CONDITION }"/>
						<select name = "pr_condition">
							<option value="T">진행 예정</option>
							<option value="I">진행 중</option>
							<option value="C">진행 완료</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>프로젝트 목적</th>
					<td><textarea name="pr_goal">${ detail.PR_GOAL }</textarea>
				</tr>
			</table>
			<input type="button" value="수정" class="body_btn pr_info_edit_btn" onclick="projectEdit()"/>
			<input type="button" value="프로젝트 삭제" class="body_btn pr_delete_btn" onclick="project_delete()"/>
		</div>
	</div>
</div>
<div id = "footer">
	<jsp:include page="../footer.jsp" flush="false"/>
</div>
</body>
</html>