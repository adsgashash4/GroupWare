<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 게시글 보기</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<link rel="stylesheet" href="css/main.css" type="text/css"/>

<script type="text/javascript">
	function back() {
		location.href="./qnaList.do";
	}
	
	function Edit(){
		var sbr_uuid = $("#sbr_uuid").val();
		location.href = "./boardEditMove.do?sbr_uuid="+sbr_uuid;
	}
	
	function Delete(){
		var sbr_uuid = $("#sbr_uuid").val();
		location.href = "./boardDelete.do?sbr_uuid="+sbr_uuid;
	}
	
	$(function(){
		var sessionID = $("#session").val();
		var mem_id = $("#mem_id").val();
		
		if(mem_id == sessionID){
			$("#editBtn").css("display","inline-block");
			$("#deleteBtn").css("display", "inline-block");
		}
	});
</script>

</head>
<body>
<div id = "header">
	<jsp:include page="../header.jsp" flush="false"/>
</div>

<div id = "container">
	<div id = "view_div">
		<c:set var="view"  value="${ view }"/>
			<input type="hidden" id="session" value="${ mem_id }"/>
			<input type="hidden" id="mem_id" value="${ view.get('MEM_ID') }"/>
			<table class="board_view_table">
				<tr>
					<th colspan="4">
						<input type="hidden" value="${ view.get('SBR_UUID')}" id = "sbr_uuid"/>
						${ view.get("SBR_TITLE") }
					</th>
				<tr>
					<th>작성자</th>
					<td>${ view.get("MEM_NAME") }</td>
					<th>작성일</th>
					<td>${ view.get("SBR_REGDATE") }</td>
				</tr>
				<tr>
					<th colspan="2">첨부파일</th>
					<td colspan="2">
						<c:set var="attach" value="${ attach }"/>
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
			<input type="button" value="게시글 수정" class="body_btn board_edit" id="editBtn" onclick="Edit()" style="display: none"/>
			<input type="button" value="게시글 삭제" class="body_btn board_delete" id="deleteBtn" onclick="Delete()" style="display: none"/>
			<input type="button" value="목록으로" class="body_btn board_return" onclick="back()"/>
	</div>

</div>

<div id = "footer">
	<jsp:include page="../footer.jsp" flush="false"/>
</div>
</body>
</html>