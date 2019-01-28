/**
 * 계정정보 찾기, 개인정보 수정, 탈퇴
 * @author 김혜원
 */

	var checked = false;	//비밀번호 조회 여부
	
	//개인정보 수정에서 비밀번호 일치 확인
	function pw_chk(){
		if($("#upPw").val()!=$("#upPw2").val()){
			alert("비밀번호가 일치하지 않습니다.\n 재입력해주세요");
			$("#upPw").val("");
			$("#upPw").val("");
		}else{
			alert("비밀번호가 확인됐습니다.");
			checked = true;
		}
	}
	//개인정보 수정(본인확인, 수정)
	$(function(){
		$(".pwChkForm").submit(function(){	//본인확인
			if($("#ckPw").val()==""){
				alert("비밀번호를 입력해주세요.");
				return false;
			}
			$.ajax({
				type: "POST",
				url: "./ckPwInfo.do",
				data: "mem_pw="+$("#ckPw").val(),
				async:false,
				success: function(msg){
					if(!msg.result){
						alert("비밀번호가 일치하지 않습니다. 다시 입력해주세요"); 
						return false;
					}else{
						$(".pwChkForm").prop("action", "./writeModifyForm.do");
					}
				}
			});
		});
		
		$(".modifyBox").submit(function(event){	//수정
			if(!checked){
				alert("비밀번호를 확인해주세요.");
				return false;
			}
			var standardPhone = /\d{3,4}$/;
			var phoneNum = $("#upPhone").val();
			if(!standardPhone.test(phoneNum)||phoneNum.length>11){
				alert("숫자형식으로 3~4자씩 최대 11자리 입력해주세요");
				return false;
			}
			if(!$("#upPw").val()||!$("#upPw2").val()||!$("#upName").val()||!$("#upEmail").val()||!$("#upPhone").val()){
				alert("모두 입력해주세요");
				return false;
			}
		});
	});
	
	//탈퇴이동
	function goLeave(){
		if(confirm("정말 탈퇴하시겠습니까?")){
			showLeavBox();
		}
	}
	
	//탈퇴화면 보이기
	function showLeavBox(){
		$(".modifyBox").css("display", "none");	
		$(".leaveBox").css("display", "block");	
	}
	
	//수정화면 보이기
	function showModiBox(){
		$(".modifyBox").css("display", "block");	
		$(".leaveBox").css("display", "none");	
	}
	
	//탈퇴 전 pm 리스트 조회
	function goListPm(){
		window.open("./viewListPm.do");
	}
	
	//탈퇴 전 gm 리스트 조회
	function goListGm(){
		window.open("./viewListGm.do");
	}
	
	//리스트 팝업 닫기
	function closeWindow(){
		window.close();		
	}
	
	//탈퇴
	function doLeave(){
		$.ajax({
			type: "GET",
			url: "./doLeaveService.do",
			async: false,
			success: function(msg){
				if(!msg.result){
					alert("프로젝트 관리자와 그룹 관리자 권한을 모두 위임해야 탈퇴가 가능합니다");
				}else{
					leaveService();
				}
			}
			
		});
	}
	
	//탈퇴처리 후
	function leaveService(){
		$.ajax({
			type: "GET",
			url: "./deleteMem.do",
			async: false,
			success: function(msg){
				if(!msg.result){
					alert("탈퇴가 정상적으로 종료되지 못했습니다. 다시 시도해주세요.");
				}else{
					alert("탈퇴가 성공적으로 이뤄졌습니다. 감사합니다.");
					location.href = "./main.do";
				}
			}
		});
	}