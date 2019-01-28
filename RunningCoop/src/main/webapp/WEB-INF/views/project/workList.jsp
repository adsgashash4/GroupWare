<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 업무 리스트</title>

<link rel="stylesheet" href="css/main.css" type="text/css"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="js/project/project.js"></script>
<script type="text/javascript" src="js/project/worklist.js"></script>

<script type="text/javascript">
	<%
		String pr_level = (String) session.getAttribute("pr_level");
		String mem_id = (String)session.getAttribute("mem_id");
	%>
	/* 관리자 여부에 따라 프로젝트 관리 버튼 출력 */
	$(function(){
		var pr_level = "<%=pr_level%>";
		if( pr_level === 'PM'){
			$(".pr_settings").css("display", "block");
		}
	})
	<%-- $(document).ready(function(){
		if( '<%=pr_level%>' == 'PM'){
			$(".project_manage").css("display", "block");
		}
	}) --%>
	
	/* 업무 상세 페이지 - 업무 코멘트 목록 출력 기능 */
	function showcommentList(nodes){
		$("#wk_Comment_List").children("table").remove().end().children("p").remove();
		$("#wk_Comment_List").append("<table class='comment_list_table'>")
		if( nodes.length == 0){
			$("#wk_Comment_List").children("table").append("<tr><td colspan='3'>코멘트가 없습니다</td></tr>");
		} else {
			$("#wk_Comment_List").children("table").append("<tr><td>작성자</td><td>내용</td><td>작성일</td></tr>");
			for(var i = 0; i < nodes.length; i++){
				var mem_id = nodes[i].MEM_ID;
				var mem_name = nodes[i].MEM_NAME;
				var wcom_content = nodes[i].WCOM_CONTENT;
				var wcom_id = nodes[i].WCOM_ID;
				var wcom_regdate = nodes[i].WCOM_REGDATE;
				if(mem_id == '<%=mem_id%>'){
					$("#wk_Comment_List").children(".comment_list_table")
					.append("<tr><td>"+mem_name+"</td><td>"+wcom_content+"<img alt='댓글수정버튼' src='images/project/wcom_edit_btn.png' onclick='wcom_Edit(this, \""+wcom_id+"\", \""+wcom_content+"\")'/>"+
					"<img alt='댓글삭제버튼' src='images/project/wcom_delete_btn.png' onclick='wcom_Delete(\""+wcom_id+"\")'/></td><td>"+wcom_regdate+"</td></tr>");
				} else {
					$("#wk_Comment_List").children(".comment_list_table")
					.append("<tr><td>"+nodes[i].MEM_NAME+"</td><td>"+nodes[i].WCOM_CONTENT+"</td><td>"+nodes[i].WCOM_REGDATE+"</td></tr>");
				}
			}
		}
		
	}
	
</script>
</head>
<body>
	<div id="header">
		<jsp:include page="../header.jsp" flush="false" />
	</div>
	<div id="container">
		<div id="work_list_area">
			<div>
				<input type="hidden" id="pr_id" value="${ pr_id }" /> 
				<c:set var="pr_name" value="${ pr_detail }"></c:set>
				<p>${ pr_name.get('PR_NAME') }</p>
				<img alt="프로젝트 관리" class="pr_settings" src="images/project/pr_settings.png" style="display:none;" onclick="location.href='./goProManage.do?pr_id=${ pr_id }'"/>
			</div>
			<div class="work_list">
				<div id="todoList" class="list_field">
					<div class="title_area">
						<span>Todo</span>
					</div>
					<div class="list_area">
						<table>
							<tr class="work" id="add_work" onclick="workInsert()">
								<td colspan="5"><img alt="업무 추가" src="images/project/pr_workAdd.png" /></td>
							</tr>
						<c:choose>
							<c:when test="${ fn:length(todo) == 0 }">
								<tr class="work">
									<td>
										업무가 없습니다.
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="todo" items="${ todo }">
									<tr class="work" onclick="viewWork('${ todo.get('WK_ID') }', 0, '${ todo.get('WK_TITLE') }')">
										<td>${ todo.get('WK_TITLE') }</td>
										<td>${ todo.get('MEM_NAME') }</td>
										<td>${ todo.get('WK_PRORATE') }%</td>
										<td><input type="button" value="수정" class="body_btn workedit_btn" onclick="workEdit('${ todo.get('WK_ID') }', '${ todo.get('WK_TITLE') }', '${ todo.get('WK_ENDDATE') }', '${ todo.get('MEM_ID') }', event)" /></td>
										<td><input type="button" value="삭제" class="body_btn workdelete_btn" onclick="workDelete('${ todo.get('WK_ID') }', event)" /></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						</table>
					</div>
				</div>
			</div>
			<div class="work_list">
				<div id="doingList" class="list_field">
					<div class="title_area">
						<span>Doing</span>
					</div>
					<div class="list_area">
						<table>
						<c:choose>
							<c:when test="${ fn:length(doing) == 0 }">
								<tr class="work">
									<td>업무가 없습니다.</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="doing" items="${ doing }">
									<tr class="work" onclick = "viewWork('${ doing.get('WK_ID')}', '${ doing.get('WK_PRORATE') }', '${ doing.get('WK_TITLE') }')">
										<td>${ doing.get("WK_TITLE") }</td>
										<td>${ doing.get("MEM_NAME") }</td>
										<td>${ doing.get("WK_PRORATE") }%</td>
										<td><input type="button" value="수정" class="body_btn workedit_btn" onclick="workEdit('${ doing.get('WK_ID') }', '${ doing.get('WK_TITLE') }', '${ doing.get('WK_ENDDATE') }', '${ doing.get('MEM_ID') }')" /></td>
										<td><input id=${doing.get('WK_ID')} type="button" class="body_btn workdelete_btn" value="삭제" onclick="workDelete('${ doing.get('WK_ID') }')" /></td>
										<td><img alt="" src="./images/bell.png" style="display:none;"></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						</table>
					</div>
				</div>
			</div>
			<div class="work_list" >
				<div id="doneList" class="list_field">
					<div class="title_area">
						<span>Done</span>
					</div>
					<div class="list_area">
						<table>
						<c:choose>
							<c:when test="${ fn:length(done) == 0 }">
								<tr class="work">
									<td>업무가 없습니다.</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="done" items="${ done }">
									<tr class="work">
										<td onclick="viewWork('${ done.get('WK_ID') }', '${ done.get('WK_PRORATE') }', '${ done.get('WK_TITLE') }')">${ done.get("WK_TITLE") }</td>
										<td>${ done.get("MEM_NAME") }</td>
										<td>${ done.get("WK_PRORATE") }%</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div id="workInsert_Form" class="workInsert_Form">
			<div id="wk_insert_div">
				<form id="wk_insert_form" action="./workInsert.do?pr_id=${ pr_id }" method="POST">
					<table>
						<tr>
							<th>업무 명</th>
							<td><input type="text" name="wk_title" required="required" onkeyup="wk_title_length(this)"/></td>
						</tr>
						<tr>
							<th>업무 담당자</th>
							<td><input type="text" id="insert_mem_id" name="mem_id" value="${ mem_id }" /></td>
						</tr>
						<tr>
							<th>업무 마감기한</th>
							<td><input type="date" name="wk_endDate" id="new_workEnddate" required="required" /></td>
						</tr>
					</table>
					<input type="reset" class="body_btn workInsert_btn" id="work_reset" value="초기화"/>
					<input type="submit" class="body_btn workInsert_btn" value="등록" />
				</form>
			</div>
			<div id="promem_list_insert" class="promem_list">
				<table id="promem_insert_table">
					<thead>
					<tr>
						<th colspan="2">프로젝트 멤버</th>
					</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
		
		<div id="work_Edit_Form" class="work_Edit_Form">
			<div id="wk_edit_div">
				<form id="work_edit" action="./workEdit.do?pr_id=${ pr_id }" method="POST">
				<input type="hidden" id="edit_wk_id" name="wk_id" value="" />
				<table>
					<tr>
						<th>업무내용</th>
						<td><input type="text" id="edit_wk_title" name="wk_title" required="required" onkeyup="wk_title_length(this)"/></td>
					</tr>
					<tr>
						<th>마감기한</th>
						<td><input type="date" id="edit_wk_endDate" name="wk_endDate" required="required" /></td>
					</tr>
					<tr>
						<th>담당자</th>
						<td><input type="text" id="edit_mem_id" name="mem_id" required="required" readonly="readonly"/></td>
					</tr>
				</table>
				<input type="submit" class="body_btn work_edit_btn" value="수정" />
				</form>
			</div>
			<div id="promem_list_edit" class="promem_list">
				<table id="promem_edit_table">
					<thead>
					<tr>
						<th colspan="2">프로젝트 멤버</th>
					</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
		
		<div class="wd_modal">
			<div class="modal_content">
				<div class="modal_header">
					<h2 id="header_title" style="display:inline;"></h2>
					<img alt="닫기" src="images/project/wd_close_btn.png" onclick="backToProject()"/>
				</div>
				<div class="modal_body">
					<div id = "allProgress">
						<div id = "partProgress"></div>
					</div>
					<input type="hidden" id="wk_id" value="" />
					<div class="wd_Field">
						<div class="wd_table_area">
							<table id="wd_field_table">
							</table>
						</div>
						<div class="work_Detail_Insert">
							<span>업무명</span>
							<input type="text" id="insert_wd_title" name="wd_title" onkeyup="new_wd_title_length(this)"/>
							<span>마감기한</span>
							<input type="date" id="insert_wd_endDate" name="wd_endDate"/>
							<input type="button" class="body_btn wd_insert_btn" value="추가" onclick="wd_Insert()" />
						</div>
					</div>
					<div class="work_Attach_View">
						<span>첨부파일</span>
						<div id="wk_Attach_List">
						</div>
						<div id="file_area">
							<form id="file_attach_form" action="./attachInsert.do"
								method="POST" enctype="multipart/form-data">
								<input type="hidden" name="wk_id" value="" />
								<label class="file_input_label" for="new_attach_file"><img alt="파일첨부" src="images/project/wk_file_attach_btn.png"/>&nbsp;<span>파일추가</span></label>
								<input type="file" class="body_btn" id="new_attach_file" name="gatt_name" value="" onchange="file_insert(this)"/>
							</form>
						</div>
					</div>
					<div class="work_Comment_View">
						<div id="wk_Comment_List">
						</div>
						<div id="new_comment_insert">
							<input type="text" id="new_wcom_content" onkeyup="new_commentLength(this)"/>
							<input type="button" class="body_btn comment_insert_btn" value="등록" onclick="wcom_Insert()" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">
		<jsp:include page="../footer.jsp" flush="false" />
	</div>
</body>
</html>