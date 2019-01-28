<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 시스템 관리자 - 회원 관리</title>
<!-- <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script> -->
<link rel="stylesheet" href="css/main.css" type="text/css"/>
<script src="./js/paging.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script type="text/javascript">
	$(function(){
		var listCnt = $("#listCnt").val();
		$("#listCount").val(listCnt).prop("select", true);
	});
	function selectMem(id) {
		$.ajax({
			type : "GET",
			url: "./sysMemView.do",
			data: "mem_id="+id,
			async: false,
			success: function(msg){
				var mem_id = msg.info.mem_id;
				var mem_pw = msg.info.mem_pw;
				var mem_name = msg.info.mem_name;
				var mem_email = msg.info.mem_email;
				var mem_phone = msg.info.mem_phone;
				
				$("#upMem_id").val(mem_id);
				$("#upMem_pw").val(mem_pw);
				$("#upMem_name").val(mem_name);
				$("#upMem_email").val(mem_email);
				$("#upMem_phone").val(mem_phone);
			//	$("#insert_Container").css("display", "block");
			viewModiForm();
			}
		});
	}
	
	function viewModiForm(){
		$("#insert_Container").dialog({
			title : "회원 정보 수정",
			height : 400,
			width : 500,
			position : {my : "center", at : "center"},
			resizable : false,
			modal : true
		});
	}
	
	function hideInsertForm() {
		$("#insert_Container").css("display", "none");
	}
	
	function checkAll(bool){
		var obj = document.getElementsByName("number");
		for (var i = 0; i < obj.length; i++){
			obj[i].checked = bool;
		}
	}
	
	function checkDelete(){
		var inputbox = $("input:checkbox[name=number]:checked");
		var numList = new Array(inputbox.length);
		
		for (var i = 0; i < inputbox.length; i++){
			if(inputbox.eq(i).val() != ""){
				numList[i] = inputbox.eq(i).val();
			}
		}
		
		if(numList.length > 0){
			jQuery.ajaxSettings.traditional = true;
			
			$.ajax({
				type : "POST",
				url : "./select_PostDelete.do",
				data : {"numList":numList},
				async : false,
				success : function(){
						location.reload();
				}
			})
		}
		
	}
</script>
</head>
<body>
	<div id="sys_header">
		<jsp:include page="../sysHeader.jsp" flush="false" />
	</div>
	
	<div id="sys_container">
		<h3>회원 관리</h3>
		
		<div id="insert_Container">
			<form id= "memModify" action = "./sysMemModify.do" method = "POST">
				<table class = "adm_memEdit_table">
					<tr>
						<th>아이디</th>
						<td><input type = "text" id = "upMem_id" name = "mem_id"></td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td><input type = "text" id = "upMem_pw" name = "mem_pw"></td>
					</tr>
					<tr>
						<th>이름</th>
						<td><input type = "text" id = "upMem_name" name = "mem_name"></td>
					</tr>
					<tr>
						<th>이메일</th>
						<td><input type = "text" id = "upMem_email" name = "mem_email"></td>
					</tr>
					<tr>
						<th>전화번호</th>
						<td><input type = "text" id = "upMem_phone" name = "mem_phone"></td>
					</tr>
				</table>
					<input type="submit" value="수정"/> 
					<input type="button" value="취소" onclick="hideInsertForm()" />
			</form>
		</div>

		<div class="adm_search_area">
			<form action="./sysMemMgr.do" method="post">
				<input type="text" name="mem_name" />
				<input type="submit" class="body_btn mem_search" value="검색" />
			</form>
		</div>
		
		<div id="div_select_area">
			<select class="adm_listSelect" id='listCount' name='listCount' onchange='listCnt();'>
					<option value='15'>15</option>
					<option value='30'>30</option>
					<option value='45'>45</option>
					<option value='60'>60</option>
			</select>
		
			<form action="./sysMemMgr.do" method="post" id='frmPaging'>
				<input type='hidden' name='index' id='index' value='${paging.index}'>
				<input type='hidden' name='pageStartNum' id='pageStartNum' value='${paging.pageStartNum}'>
				<input type='hidden' name='listCnt' id='listCnt' value='${paging.listCnt}'>
				<input type="hidden" name="mem_name" value="${ search_value }"/>
			</form>		 
		</div>
		
		<div id="mgr_Container">
			<div id="mem_mgr_div">
				<table class="adm_memList_table">
					<tr>
						<th style="width: 5%;">번호</th>
						<th style="width: 15%;">아이디</th>
						<th style="width: 15%;">이름</th>
						<th style="width: 20%;">이메일</th>
						<th style="width: 20%;">휴대폰 번호</th>
						<th style="width: 20%;">가입일자</th>
						<th style="width: 5%;">수정</th>
					</tr>
					<c:choose>
						<c:when test="${ fn:length(list) == 0 }">
							<tr>
								<td colspan="7">조회 가능한 게시글이 없습니다</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="list" items="${ list }" >
								<tr>
									<td>${ list.RNUM }</td>
									<td>${ list.MEM_ID }</td>
									<td>${ list.MEM_NAME }</td>
									<td>${ list.MEM_EMAIL }</td>
									<td>${ list.MEM_PHONE }</td>
									<td>${ list.MEM_REGDATE }</td>
									<td><input type = "button" value = "수정" onclick="selectMem('${list.MEM_ID}')"/></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</table>
			</div>
			
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
	
	<div id="sys_footer">
		<jsp:include page="../sysFooter.jsp" flush="false"/>
	</div>
</body>
</html>
