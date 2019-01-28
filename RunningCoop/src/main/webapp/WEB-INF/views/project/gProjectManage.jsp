<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 그룹 프로젝트 관리</title>

<link rel="stylesheet" href="css/main.css" type="text/css"/>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
<script type="text/javascript">
<%
	String mem_id = (String)session.getAttribute("mem_id");
%>
	$(function(){
		loadPage();
	})
	
	// 프로젝트 정보 관리
	function view_info_manage(){
		$(".info_manage").css("display", "block");
		$(".mem_manage").css("display", "none");
		$(".calendar_manage").css("display", "none");
		loadPage();
	}
	
	// 프로젝트 멤버 관리
	function view_mem_manage(){
		$(".info_manage").css("display", "none");
		$(".calendar_manage").css("display", "none");
		$(".mem_manage").css("display", "block");
		loadMember();
		invite_memList();
	}
	
	// 프로젝트 팀 일정 관리(컨트롤러 실행)
	function view_calendar_manage(){
		$(".info_manage").css("display", "none");
		$(".calendar_manage").css("display", "block");
		$(".mem_manage").css("display", "none");
	}
	
	//프로젝트 팀 일정 관리
	function showTeamCalendar(msg){
		
		$(".mem_manage").css("display", "none");
		$(".info_manage").css("display", "none");
		$(".calendar_manage").css("display", "block");
	}
	
	// 페이지 로드 시 실행 함수
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
	
	// 프로젝트 정보 수정 기능
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
		})
	}
	
	// 프로젝트 멤버 조회 기능
	function loadMember(){
		var pr_id = $("#pr_id").val();
		
		$(".mem_list").children("p").remove();
		
		$.ajax({
			type : "POST",
			url : "./loadMember.do",
			data : "pr_id="+pr_id,
			async : false,
			success : function(msg){
				if(msg.length == 0){
					$(".mem_list").append("<p>멤버가 없습니다</p>");
				} else {
					for(var i = 0; i < msg.length; i++){
						if(msg[i].MEM_ID == '<%=mem_id%>'){
							$(".mem_list").append("<p style='line-height: 20px; margin: 3px;'>(PM)"+msg[i].MEM_NAME+"</p>");
						} else {
							$(".mem_list").append("<p style='line-height: 20px; margin: 3px;'><input type='checkbox' name='mem_id' value=\""+msg[i].MEM_ID+"\">"+msg[i].MEM_NAME+"</p>");
						}
					}
				}
			}
		})
	}
	
	// 초대가능한 그룹 멤버 조회 기능
	function invite_memList(){
		var pr_id = $("#pr_id").val();
		
		$("#invitable_Memlist").children("p").remove();
		
		$.ajax({
			type : "POST",
			url : "./invite_memList.do",
			data : "pr_id="+pr_id,
			async : false,
			success : function(msg){
				if(msg.length == 0){
					$("#invitable_Memlist").append("<p>초대 가능한 멤버가 없습니다.</p>");
				} else {
					for(var i = 0; i < msg.length; i++){
						$("#invitable_Memlist").append("<p><input type='checkbox' name='mem_id' value='"+msg[i].mem_id+"'/>"+msg[i].mem_name+"</p>");
					}
				}
			}
		})
	}
	
	// 멤버 초대 기능
	function inviteMem(){
		var pr_id = $("#pr_id").val();
		var inputbox = $("input:checkbox[name=mem_id]:checked");
		var mem_id = new Array(inputbox.length);
		for (var i = 0; i < inputbox.length; i++){
			mem_id[i] = inputbox.eq(i).val();
		}
		
		jQuery.ajaxSettings.traditional = true;
		
		$.ajax({
			type : "POST",
			url : "./invite_Member.do",
			data : {"mem_id": mem_id, "pr_id": pr_id},
			async : false,
			success : function(msg){
				if(msg == "success"){
					alert("멤버초대성공!");
					loadMember();
					invite_memList()
				} else {
					alert("멤버초대실패!");
					loadMember();
					invite_memList()
				}
			}
		})
	}
	
	// 멤버 강제 탈퇴 기능
	function delete_mem(){
		var pr_id = $("#pr_id").val();
		var inputbox = $("input:checkbox[name=mem_id]:checked");
		var mem_list = new Array(inputbox.length);
		
		for (var i = 0; i < inputbox.length; i++){
			mem_list[i] = inputbox.eq(i).val();
		}
		
		jQuery.ajaxSettings.traditional = true;
		
		$.ajax({
			type : "POST",
			url : "./delete_Member.do",
			data : {"mem_list": mem_list, "pr_id": pr_id},
			async : false,
			success : function(msg){
				if(msg == "success"){
					alert("멤버탈퇴성공!");
					loadMember();
					invite_memList()
				} else {
					alert("멤버탈퇴실패!");
					loadMember();
					invite_memList()
				}
			}
		})
	}
	
	// 담당자 위임 기능
	function commission_mem(){
		var pr_id = $("#pr_id").val();
		var inputbox = $("input:checkbox[name=mem_id]:checked");
		
		if(inputbox.length != 1){
			alert("1명만 선택하세요!");
			inputbox.attr("checked", false);
		} else {
			var mem_id = inputbox.eq(0).val();
			 $.ajax({
				type : "POST",
				url : "./commissionPM.do",
				data : {"pr_id": pr_id, "mem_id": mem_id},
				async : false,
				success : function(msg){
					if(msg == "fail"){
						inputbox.attr("checked", false);
						alert("담당자 위임에 실패했습니다. 다시 시도해주세요!");
					} else {
						location.href="./goProject.do?pr_id="+pr_id;
					}
				}
			})
		}
	}
	
	// 멤버 정보 보기 기능
	function view_meminfo(){
		var inputbox = $("input:checkbox[name=mem_id]:checked");
		
		if(inputbox.length != 1){
			alert("1명만 선택하세요!");
			inputbox.attr("checked", false);
		} else {
			var mem_id = inputbox.eq(0).val();
			
			/* 회원의 기본정보 불러오기 */
			$.ajax({
				type : "POST",
				url : "./view_MemberInfo_1.do",
				data : "mem_id="+mem_id,
				async : false,
				success : function(msg){
					$(".mem_info_table").children("td").text("");
					$("#memInfo_mem_id").text(msg.MEM_ID);
					$("#memInfo_mem_name").text(msg.MEM_NAME);
					$("#memInfo_mem_email").text(msg.MEM_EMAIL);
					$("#memInfo_mem_phone").text(msg.MEM_PHONE);
					inputbox.attr("checked", false);
				}
			});
			
			/* 회원의 참여 프로젝트리스트 불러오기 */
			$.ajax({
				type : "POST",
				url : "./view_MemberInfo_2.do",
				data : "mem_id="+mem_id,
				async : false,
				success : function(msg){
					if (msg.length == 0){
						$("#memInfo_mem_project").text("참여한 프로젝트가 없습니다.")
					} else {
						var text = "";
						for(var i = 0; i < msg.length; i++){
							
							var pr_name = msg[i].PR_NAME;
							var pr_id = msg[i].PR_ID;
							var pr_condition = msg[i].PR_CONDITION;
							
							if(pr_condition == 'T'){
								pr_condition = '진행예정';
							} else if (pr_condition == 'I'){
								pr_condition = '진행중';
							} else if (pr_condition == 'C'){
								pr_condition = '진행완료';
							}
							
							text += "<span><a href='#'>"+pr_name+"</a>("+pr_condition+")</span></br>";
						}
							$("#memInfo_mem_project").html(text);
							
					}
				}
			});
			$("#member_Information").dialog({
				title : "회원 정보 보기",
				height : 400,
				width : 500,
				position : {my : "center", at : "center"},
				resizable : false,
				modal : true
			});
			
		}
	}
	
	<% String gr_id = (String)session.getAttribute("gr_id"); %>
	// 프로젝트 해체 기능
	function project_delete(){
		
		var deleteYn = confirm("프로젝트를 삭제하시겠습니까?");
		
		if(deleteYn){
			var pr_id = $("#pr_id").val();
			
			$.ajax({
				type : "POST",
				url : "./project_Delete.do",
				data : "pr_id="+pr_id,
				async : false,
				success : function(msg){
					if(msg == "success"){
						alert("프로젝트 삭제 성공");
						location.href= "./gProSelect.do?gr_id="+"<%=gr_id%>";
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
	<h3>그룹 프로젝트 관리 페이지</h3>
	<div id = "backProject">
		<input type="button" class="body_btn backToProject_btn" value="프로젝트로 돌아가기" onclick="backToproject()"/>
	</div>
	<div id = "con_side">
		<input type="button" value="프로젝트 정보관리" class="gPr_manage_btn" onclick="view_info_manage()"/>
		<input type="button" value="멤버 관리" class="gPr_manage_btn" onclick="view_mem_manage()"/>
		<input type="button" value="팀 일정 관리" class="gPr_manage_btn" onclick="view_calendar_manage()"/>
		<input type="button" value="프로젝트 삭제" class="gPr_manage_btn" onclick="project_delete()"/>
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
		</div>
		<div class = "mem_manage">
			<div class = "member_manage_area">
				<h4>프로젝트 멤버 관리</h4>
				<div class = "mem_manage_list">
					<div>프로젝트 멤버 리스트</div>
					<div class="mem_list"></div>
					<div class="pro_mem_con">
						<input type="button" value="멤버 삭제" onclick="delete_mem()"/>
						<input type="button" value="담당자 위임" onclick="commission_mem()"/>
					</div>
				</div>
				<div class = "mem_invite_list">
					<div>초대가능한 멤버 리스트</div>
					<div id = "invitable_Memlist">
					</div>
				</div>
				<div class="invite_mem_con">
					<input type="button" value="멤버 초대" onclick="inviteMem()"/><br/>
					<input type="button" value="멤버 정보 보기" onclick="view_meminfo()"/>
				</div>
				<div id = "member_Information" style="display: none;">
					<table class="mem_info_table">
						<tr>
							<th>회원 아이디</th>
							<td id="memInfo_mem_id"></td>
						</tr>
						<tr>
							<th>회원 이름</th>
							<td id="memInfo_mem_name"></td>
						</tr>
						<tr>
							<th>회원 이메일</th>
							<td id="memInfo_mem_email"></td>
						</tr>
						<tr>
							<th>회원 전화번호</th>
							<td id="memInfo_mem_phone"></td>
						</tr>
						<tr>
							<th>참여 프로젝트</th>
							<td id="memInfo_mem_project"></td>
					</table>
				</div>
			</div>
		</div>
		<div class = "calendar_manage">
			<div>
				<iframe width = "100%" height = "650px" frameborder = "0" framespacing = "0"  src = "./viewTeamSchedule.do?pr_id=${ pr_id }">
				</iframe>
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
</div>
<div id = "footer">
	<jsp:include page="../footer.jsp" flush="false"/>
</div>
</body>
</html>
