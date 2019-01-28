<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 그룹 관리</title>

<link rel="stylesheet" href="css/main.css" type="text/css"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>

<% 
	String gr_id = (String)session.getAttribute("gr_id");
	String mem_id = (String)session.getAttribute("mem_id"); 
	String Newgr_id = (String)session.getAttribute("gr_id");
%>

<script type="text/javascript">
// 그룹 정보 관리
function view_info_manage(){
	$(".info_manage").css("display", "block");
	$(".mem_manage").css("display", "none");
	$(".grBoard_manage").css("display", "none");
}

// 그룹 멤버 관리
function view_mem_manage(){
	$(".info_manage").css("display", "none");
	$(".grBoard_manage").css("display", "none");
	$(".mem_manage").css("display", "block");
}

// 그룹 게시판 관리
function view_board_manage(){
	$(".info_manage").css("display", "none");
	$(".grBoard_manage").css("display", "block");
	$(".mem_manage").css("display", "none");
}

// 그룹 삭제
function group_delete(){
	var rst = confirm("그룹을 해체하시겠습니까?");
	var gr_id = "<%=Newgr_id%>";
	
	if(rst){
		location.href = "./groupDelete.do?gr_id="+gr_id;
		alert("그룹이 삭제 되었습니다.");
	}
}

// 그룹 멤버 관리 클릭시 맴버 출력 gr_id 필요

// function loadMember(){
// 	$("#memList").next().remove();
// 	var grId = $("#memgrid").val();
// 		$.ajax({
// 			type : "POST",
// 			url  : "./memModi.do",
// 			data : "gr_id="+grId,
// 			async : false,
// 			success : function(data){
// 				for(var i =0; i<data.length; i++){
// 						$("#memList").next().remove();
// 					for(var i =0; i<data.length; i++){
// 					$("#memList").after("<tr><td>"+data[i].mem_id+"</td><td>"+data[i].mem_name+"</td><td>"
// 							+"<img onclick=memDelete(this) alt=cancle src="+'./images/cancel.png'+">");
// 					}
// 				}
// 			}
// 		});
// }

// // 그룹 가입신청 멤버 출력
// function WaitloadMember(){
// 	$("#memList").next().remove();
// 	var grId = $("#memgrid").val();
// 		$.ajax({
// 			type : "POST",
// 			url  : "./waitMemModi.do",
// 			data : "gr_id="+grId,
// 			async : false,
// 			success : function(data){
// 				for(var i =0; i<data.length; i++){
// 						$("#memList").next().remove();
// 					for(var i =0; i<data.length; i++){
// 					$("#memList").after("<tr><td>"+data[i].mem_id+"</td><td>"+data[i].mem_name+"</td><td>"
// 							+"<img onclick=memDelete(this) alt=cancle src="+'./images/cancel.png'+">");
// 					}
// 				}
// 			}
// 		});
// }
// //그룹 멤버 삭제
// function memDelete(grmemid){
// 	var memID = $(grmemid).parent().siblings().eq(0).text();
// 	alert(memID);
<%-- 	var grID = "<%=gr_id%>"; --%>
// 	alert(grID);
	
// 	var rst = confirm("멤버를 추방하시겠습니까?");
// 	if(rst){
// 	location.href = "./groupMemDelete.do?gr_id="+grID+"&memID="+memID;
// 	}
// }

// 여기는 기존 스크립트
$(function(){
	var searchYN = $("#searchYN").val();
	var joinyn = $("#joinYN").val();
	
	if(searchYN=="Y"){
		$("input:radio[id='grsearchy']").attr("checked",true);
	}else{
		$("input:radio[id='grsearchy2']").attr("checked",true);
	}
	if(joinyn=="Y"){
		$("input:radio[id='grjoinyn']").attr("checked",true);
	}else{
		$("input:radio[id='grjoinyn2']").attr("checked",true);
	}
	
	$(".grModify").submit(function(){
		if($("#grGoal").val()==""){
			alert("내용을 입력해주세요")
			return false;
		}else{
			alert("정보수정 완료");
		}
	});
	
	for(var i = 1; i<30; i++){
		if(i<10){
			var imgNum = "이미지00"+i;
			$('#selectGrImg').append($('<option>').attr('value',imgNum).text(imgNum));
		} else {
			var imgNum = "이미지0"+i;
			$('#selectGrImg').append($('<option>').attr('value',imgNum).text(imgNum));
		}
	}
	
	var grimgnum = "이미지"+$("#grImgNum").val();
	$("#selectGrImg").val(grimgnum).prop("selected", true);
	
	$('#selectGrImg').on('change', function(){
		var imgln = $(this).val().length;
		var GroupImgs = $(this).val().substring(3, imgln );
		$("#grImgNum").val(GroupImgs);
		
		$("#imgbox").attr("src","./grImgs/img"+GroupImgs+".png");
	})	
	
})
function groupManagerCh(){
	var mem = "<%=mem_id%>";
	window.open("./createGrManagerCh.do?mem_id=<%=mem_id%>", "GroupManagerCh", "width=338, height=552, resizable = no, scrollbars = no");
}
//여기 까지 기존스크립트

</script>

</head>
<body>
<div id = "header">
	<jsp:include page="../header.jsp" flush="false"/>
</div>

<div id="container">
	<div id = "con_side">
		<input type="button" value="그룹정보 수정" class="group_manage_btn" onclick="view_info_manage()"/>
		<input type="button" value="그룹 인원 관리" class="group_manage_btn" onclick="view_mem_manage()"/>
		<!-- <input type="button" value="그룹 게시판 관리" class="group_manage_btn" onclick="view_board_manage()"/> -->
		<input type="button" value="그룹 해체" class="group_manage_btn" onclick="group_delete()"/>
	</div>
	
	<div id="con_body">
	
		<div class="info_manage">
			<h3>그룹정보 수정</h3>
			
			<form class="grModify" action="./grInfoEdit.do" method="post">
			<c:set var="grSelect" value="${grSelect}"/>
				<input type="hidden" name="gr_id" value="${ grSelect.GR_ID }"/>
				<table class="group_manage_table">
					<tr>
						<th>그룹이름</th>
						<td>${grSelect.GR_NAME }</td>
					</tr>
					<tr>
						<th>그룹담당자</th>
						<td>${grSelect.MEM_NAME }&nbsp;&nbsp;<input type="button" class="body_btn grMgr_Edit_btn" value="관리자위임" onclick="groupManagerCh()"></td>
					</tr>
					<tr>
						<th>그룹인원수</th>
						<td>${grSelect.GR_MEMCNT }</td>
					</tr>
					<tr>
						<th>그룹활동목적</th>
						<td><textarea id="grGoal" name="gr_goal">${grSelect.GR_GOAL}</textarea></td>
					</tr>
					<tr>
						<th>그룹검색여부</th>
						<td>
							<input type="hidden" id="searchYN" value="${grSelect.GR_SEARCHYN }"/>
							<input type="radio" name="gr_searchyn" id="grsearchy" value="Y">예&nbsp;&nbsp;
							<input type="radio" name="gr_searchyn" id="grsearchy2" value="N">아니오
						</td>
					</tr>
					<tr>
						<th>그룹가입신청여부</th>
						<td>
							<input type="hidden" id="joinYN" value="${ grSelect.GR_JOINYN }"/>
							<input type="radio" name="gr_joinyn" id="grjoinyn" value="Y">예&nbsp;&nbsp;
							<input type="radio" name="gr_joinyn" id="grjoinyn2" value="N">아니오
						</td>
					</tr>
					<tr>
						<th>그룹생성일자</th>
						<td>${grSelect.GR_REGDATE }</td>
					</tr>
					<tr>
						<th>그룹이미지</th>
						<td>
							<input type="hidden" id="grImgNum" name="gr_img" value="${grSelect.GR_IMG}"/>
							<img id="imgbox" alt="그룹이미지" src="./grImgs/img${grSelect.GR_IMG}.png">&nbsp;&nbsp;
							<select id="selectGrImg"></select>
						</td>
					</tr>
				</table>
				<input type="submit" class="body_btn grInfo_Edit_btn" value="정보수정">
			</form>
		</div>
			
			<div class="mem_manage">
				<iframe width = "100%" height = "595px"  frameborder = 0; src = "./memModi.do?gr_id=<%=Newgr_id%>" >여기
				</iframe>
			</div>
			<div class = "grBoard_manage">
				<iframe width = "100%" height = "595px"  src = "./gbListSelect.do?gr_id=<%=Newgr_id%>" >여기
				</iframe>
			</div>
		</div>
</div>
<div id = "footer">
	<jsp:include page="../footer.jsp" flush="false"/>
</div>
</body>
</html>
