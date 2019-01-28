<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 그룹관리 - 멤버 관리</title>

<link rel="stylesheet" href="css/main.css" type="text/css"/>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>

<script type="text/javascript">
	function accept1(memid){
		var gr_id ="${ grid }";
		var mem_id = $("#mem_id").text();
		
		location.href="./groupAccept.do?mem_id="+mem_id+"&gr_id="+gr_id;
	}
	function refusal1(memid){
		var gr_id ="${ grid }";
		var mem_id = $("#mem_id").text();
		
		location.href="./grouprefusal.do?mem_id="+mem_id+"&gr_id="+gr_id;
	}
	
	function groupMemAdd(){
		//window.open("./groupSend.do", "sendForm", "width=570, height=350, resizable = no, scrollbars = no");
		$("#memAdd_box").dialog({
			title : "그룹 초대",
			height : 250,
			width : 300,
			position : {my : "center", at : "center"},
			resizable : false,
			modal : true
		});
	}
	
	function sendInvite(){
		$.ajax({
			url: "./goGroupMail.do",
			type: "GET",
			data: "toSend="+$("#toSend").val()+"&toName="+$("#toName").val(),
			async: false,
			success: function(msg){
				if(msg.result){
					alert("메일이 성공적으로 발송됐습니다.");
					close();
				}else{
					alert("메일 전송이 실패했습니다. 다시 확인해주세요.");
				}
			}
		});
	}
	
	function memDelete(grmemid){
		var memID = $(grmemid).parent().siblings().eq(0).text();
		var grID = $(grmemid).parent().prev().text();
		
		var rst = confirm("멤버를 추방하시겠습니까?");
		if(rst){
		location.href = "./groupMemDelete.do?gr_id="+grID+"&memID="+memID;
		}
	}
</script>

</head>
<body>
<div class="member_manage_area">
	
	<div id = "memAdd_box" style = "display: none;">
		<form action="./goGroupMail.do" method="post">
			<table style = "border-collapse: collapse;">
				<tr>
					<td>수신자명: <input type = "text" id = "toName" name = "toName"></td>
				</tr>
				<tr>
					<td>이메일: <input type="text" id = "toSend" name="toSend"></td>
				</tr>
				<tr><td><input type="button" style = "margin-top:10px;" value="메일보내기" onclick = "sendInvite()"></td></tr>
			</table>
		</form>
	</div>
	
	<div class="div_member_area">
		<h3>그룹 멤버 목록</h3>
		<input type="hidden" name="gr_id" value="${grid }"/>
		<div class="memList_table_area">
			<table class="group_member_list">
				<tr>
					<th>아이디</th>
					<th>이  름</th>
				</tr>
			<c:forEach var="memls" items="${memList}">
				<tr> 
					<td>${memls.mem_id}</td>
					<td>${memls.mem_name}&nbsp;&nbsp;<img onclick="memDelete(this)" alt="멤버삭제" src="./images/cancel.png"></td>
				</tr>
			</c:forEach>
			</table>
		</div>
		<div class="gr_inviteMem_area">
			<input type="button" class="body_btn memInvite_btn" value="멤버초대" onclick="groupMemAdd()"/>
		</div>
	</div>
	
	<div class="div_requestMem_area">
		<h3>그룹 가입신청자</h3>
		<div class="memList_table_area">
			<table class="requestMem_table">
				<tr>
					<th style="width: 30%;">아이디</th>
					<th style="width: 30%;">이름</th>
					<th style="width: 20%;">신청일자</th>
					<th style="width: 10%;">수락</th>
					<th style="width: 10%;">거절</th>
				</tr>
			<c:forEach var="memWait" items="${grWait}">
				<tr>
					<td id="mem_id">${memWait.mem_id}</td>
					<td>${memWait.mem_name}</td> 
					<td>${memWait.groupwaitdto.wait_regDate}</td>
					<td><input type="button" class="body_btn mem_applyyn_btn" value="수락" onclick="accept1(this)"></td>
					<td><input type="button" class="body_btn mem_applyyn_btn" value="거절" onclick="refusal1(this)"></td>
				</tr>					
			</c:forEach>
			</table>	
		</div>
	</div>
	
</div>
</body>
</html>