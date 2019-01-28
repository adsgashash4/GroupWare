<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>시스템게시판 게시글 보기</title>
<link rel="stylesheet" href="css/main.css" type="text/css"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
	function back() {
		location.href="./sysNoticeMgr.do";
	}

	function Edit() {
		var sbr_uuid = $("#sbr_uuid").val();
		location.href = "./sysBoardEditMove.do?sbr_uuid=" + sbr_uuid;
	}

	function Delete() {
		var sbr_uuid = $("#sbr_uuid").val();
		location.href = "./sysboardDelete.do?sbr_uuid=" + sbr_uuid +"&noticeyn=Y";
	}

	$(function() {
		var sessionID = $("#session").val();
		var mem_id = $("#mem_id").val();

		if (mem_id == sessionID) {
			$("#editBtn").css("display", "inline-block");
			$("#deleteBtn").css("display", "inline-block");
		}
	});
</script>
</head>
<body>
<% 
	String mem_id = (String)session.getAttribute("mem_id");
%>
	<div id="sys_header">
		<jsp:include page="../sysHeader.jsp" flush="false" />
	</div>
	<div id="sys_container">
			<h3>공지 게시글 보기</h3>
		<div class="adm_notice_view">
			<c:set var="view" value="${ view }" />
			<input type="hidden" id="session" value="<%=mem_id %>" /> 
			<input type="hidden" id="mem_id" value="${ view.get('MEM_ID') }" />
			<table class="adm_notice_view_table">
				<tr>
					<th colspan="4">
						<input type="hidden" value="${ view.get('SBR_UUID')}" id="sbr_uuid" />
						${ view.get("SBR_TITLE") }
					</th>
				</tr>
				<tr>
					<th>작성자</th>
					<td>${ view.get("MEM_NAME") }</td>
					<th>작성일</th>
					<td>${ view.get("SBR_REGDATE") }</td>
				</tr>
				<tr>
					<th colspan="2">첨부파일</th>
					<td colspan="2">
						<c:set var="attach" value="${ attach }" />
							<c:if test="${ fn:length( attach.get('SATT_NAME') ) != 0 }">
								<span><a href="./fileDown.do?sbr_uuid=${ view.get('SBR_UUID') }">${ attach.get("SATT_NAME") }</a>
								(${ attach.get('SATT_SIZE')/1000 }KB)</span>
							</c:if>
					</td>
				</tr>
				<tr>
					<td colspan="4">${ view.get("SBR_CONTENT") }</td>
				</tr>
			</table> 
			<input type="button" class="body_btn adm_notice_edit" value="게시글 수정" id="editBtn" onclick="Edit()" style="display: none" /> 
			<input type="button" class="body_btn adm_notice_delete" value="게시글 삭제" id="deleteBtn" onclick="Delete()" style="display: none" /> 
			<input type="button" class="body_btn adm_notice_back" value="목록으로" onclick="back()" />
		</div>
	</div>
	
	<div id="sys_footer">
		<jsp:include page="../sysFooter.jsp" flush="false"/>
	</div>
</body>
</html>