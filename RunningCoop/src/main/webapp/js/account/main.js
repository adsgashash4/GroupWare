/**
 * 로그인, 회원가입, 계정정보 찾기(이동)
 * @author 김혜원
 */

	var joinStatus = 0;	//회원가입 팝업 display 여부
/**
 * 로그인
 * */
	function doLogin(){
		if($("#loginId").val()==""||$("#loginPw").val()==""){
			alert("아이디와 비밀번호 모두 입력해주세요");
		}else{
			var loginForm = $(".loginBox").serialize();
			$.ajax({
				type: "POST",
				url: "./ckLogin.do",
				data: loginForm,
				async: false,
				success:function(result){
					if(!result.login){
						var failNotice = 
							"<p style = 'font-size:8pt; color:red;'>로그인에 실패했습니다. 아이디 또는 비밀번호를 확인해주세요</p>";
						$(".loginFail").children().remove();
						$(".loginFail").append(failNotice);
					}else if(result.login=="mgr"){
						alert("관리자 계정은 관리자 페이지에서 로그인 해주세요.");
					}else{
						location.href = "./myGrSelect.do";
					}
				}
			});
		}
	}
	
	/**
	 * 관리자 로그인
	 * */
	
	function doMgrLogin(){
		if($("#loginId").val()==""||$("#loginPw").val()==""){
			alert("아이디와 비밀번호 모두 입력해주세요");
		}else{
			var loginForm = $(".loginBox").serialize();
			$.ajax({
				type: "POST",
				url: "./ckLogin.do",
				data: loginForm,
				async: false,
				success:function(result){
					if(!result.login){
						$(".loginFail").children().remove();
						$(".loginFail").append("<p style = 'font-size:8pt; color:red;'>로그인에 실패했습니다. 아이디 또는 비밀번호를 확인해주세요</p>");
					}else if(result.login=="user"){
						alert("일반 회원 계정은 서비스 메인 페이지에서 로그인 해주세요.");
					}else{
						location.href = "./enterMgr.do";
					}
				}
			});
		}
	}
	
/**
 * 아이디, 비밀번호 찾기
 * */	
	function searchAccount(){
		window.open("./searchAccount.do");
	}
	
	/**
	회원가입
	*/
	
	var interval;	//인증번호 등록 제한 시간 설정을 위해 설정
	var checked = [false, false, false, false, false, false];	//각 확인 버튼 성공 여부(모두 true여야 회원가입 가능)
	
	//회원가입 팝업 띄우기	
	function joinAccount(){
		$(".join_modal").css("display", "block");
		joinStatus = 1;
	}
	
	function closeJoin(){
		$(".join_modal").css("display", "none");
		$("#id").val("");
		$("#resultIdChk").html("");
		$("#pw").val("");
		$("#pw2").val("");
		$("#name").val("");
		$("#email").val("");
		$("#num").val("");
		$("#phone1").val("");
		$("#phone2").val("");
		$("#phone3").val("");
		joinStatus = 0;
		
		for(var i = 0; i< checked.length; i++){
			checked[i] = false;
		}
	}

	//아이디 제약조건, 중복 확인
	function id_chk(){
		if(joinStatus == 1){
			checked[0] = true;
			var mem_id = $("#id").val();
			var standardId = /^[a-z0-9]{8,12}$/g;
			if(!standardId.test(mem_id)){
				alert("아이디는 영소문자, 숫자를 포함하여 8~12자로 작성해야 합니다.");
				$("#id").val("");
				checked[0] = false;
			}else{
				$.ajax({
					type: "GET",
					url: "./memIdSelect.do",
					data: "mem_id="+mem_id,
					async: false,
					success: function(msg){
						printIdCk(msg)
					},
					error: function(){
						checked[0] = false;
						alert("아이디 중복 조회 전송 실패, 다시 해주세요");
					}
				});
			}
		}
	}
	
	//아이디 중복확인 결과
	function printIdCk(map){
		if(joinStatus == 1){
			if(map.result){
				$("#resultIdChk").html("사용 가능한 아이디입니다").css("font-size","7pt");
				checked[0] = true;
			}else{
				$("#resultIdChk").html("중복된 아이디입니다.<br>다시 입력해주세요").css("font-size","7pt");
				checked[0] = false;
			}
		}
	}
	
	//비밀번호 제약조건, 재확인
	function pw_chk(){
		if(joinStatus == 1){
			checked[1] = true;
			var standardPw = /^((?=.*\d)(?=.*[a-z]).{8,15})$/gm;
	
			if(!standardPw.test($("#pw").val())){
				checked[1] = false;
				alert("비밀번호는 영소문자, 숫자를 포함하여 8~16자로 작성해야 합니다.");
				$("#pw").val("");
				$("#pw2").val("");
			}else{
				if($("#pw").val()!=$("#pw2").val()){
					checked[1] = false;
					$("#modifyInfo").prop("disabled", true);
					alert("비밀번호가 일치하지 않습니다.\n 재입력해주세요.");
					$("#pw").val("");
					$("#pw").val("");
				}else{
					alert("비밀번호가 확인됐습니다.");
				}
			}
		}
	}
	
	//이름 제약조건 확인
	function name_chk(){
		if(joinStatus == 1){
			checked[2] = true;
			var standardName = /^[a-z0-9ㄱ-ㅎㅏ-ㅣ가-힣]{1,10}$/g;
			if(!standardName.test($("#name").val())){
				checked[2] = false;
				alert("이름은 대소문자를 구분한 영문, 한글, 숫자를 포함한 10자 이내로 작성해야 합니다.");
				$("#name").val("");
			}else{
				alert("사용가능합니다");
			}
		}
	}
	
	//이메일 중복확인 결과
	function chkMail(){
		if(joinStatus == 1){
			var mem_email = $("#email").val();
			if(mem_email == ""){
				alert("이메일을 입력 후 확인해주세요.");
			}else{
				checked[3] = true;
				$.ajax({
					type: "POST",
					url: "./memEmailSelect.do" ,
					data: "mem_email="+mem_email,
					async: false,
					success: function(msg){
						if(msg.result){
							alert("사용 가능합니다.");
						}else{
							alert("이미 등록된 계정입니다. 다시 입력해주세요.");
							checked[3] = false;
						}
					}
				});
			}
		}
	}
	
	//인증번호 메일 전송
	function sendMail(){
		if(joinStatus == 1){
			var mem_name = $("#name").val();
			var toMail = $("#email").val();
			if(toMail == "" || mem_name == ""){
				alert("이름과 이메일을 모두 입력해주세요.");
			}else{
				checked[4] = true;
				$.ajax({
					type: "POST",
					url: "./joinMailSending.do",
					data: "mem_name="+mem_name+"&toMail="+toMail,
					async: false,
					success: function(){
						alert("해당 메일계정으로 인증번호를 전송했습니다.");
					},
					error: function(){
						checked[4] = false;
						alert("인증번호 전송 실패, 다시 시도해주세요.");
					}
				});
				$("#chkInfo").css("display","block");
				$(".timeWord").css("display","block");
				timeCount(240, sEvent, eEvent);
			}
		}
	}
	
	//인증번호 입력 시간 카운트
	function timeCount(time, sEvent, eEvent){
		if(joinStatus == 1){
			//카운트 시간 설정
			var setTime = time;
					
			//1초마다 실행
			interval = setInterval(function(){
				timer();
				}, 1000);
		}
		function timer(){
			setTime = setTime-1;
			var minute = Math.floor(setTime/60);
			var second = Math.round(setTime%60);
				
			sEvent(minute, second);
				
			if(setTime==0){	//시간 내에 입력 안했을 때
				clearInterval(interval);
				eEvent();
				$.ajax({
					type: "POST",
					url: "./removeIdentify.do",
					async: false
				});
			}
		}
	}
	
	//카운트 동작 이벤트
	function sEvent(minute, second){
		$("#minute").text(minute+"분");
		$("#second").text(second+"초");
	}
	
	//카운트 종료 시 이벤트
	function eEvent(){
		alert("시간이 종료됐습니다. 다시 시도해주세요.");
	}
	
	//입력 후 시간 카운트 정지+세션(인증번호)삭제
	function endIdentify(){
		if(joinStatus == 1){
			clearInterval(interval);
			$.ajax({
				type: "POST",
				url: "./removeIdentify.do",
				async: false
			});
		}
	}
	
	//인증번호 입력확인
	function identifyNum(){
		var val = $("#num").val();
		if(joinStatus == 1){
			$.ajax({
				type: "POST",
				url: "./ckIdentifyNum.do",
				data: "input="+val,
				async: false,
				success: function(msg){
					endIdentify();
					resultIdentify(msg)
				}
			});
		}
	}
	
	//인증번호 입력 결과
	function resultIdentify(map){
		if(joinStatus == 1){
			if(map.result){
				checked[5] = true;
				alert("인증이 완료됐습니다.");
			}else{
				checked[5] = false;
				alert("인증이 실패했습니다. 다시 해주세요.");
				$("#num").val("");
			}
		}
	}
	
	//최종입력 제약조건
	$(function(){
			$(".joinBox").submit(function(event){
				//연락처 유효성 검사
				var phone1 = $("#phone1").val();
				var phone2 = $("#phone2").val();
				var phone3 = $("#phone3").val();
				var standardPhone = /\d{3,4}$/;
				if(!standardPhone.test(phone1)||!standardPhone.test(phone2)||!standardPhone.test(phone3)){
					alert("숫자형식으로 3~4자씩 입력해주세요");
					return false;
				}
				else{
					$("#phone").val(phone1+phone2+phone3);
				
 					//공백 확인
					if($("#id").val()==""||$("#pw").val()==""||$("#pw2").val()==""||$("#mem_name")==""||$("mem_email")==""||$("#num")==""||$("#mem_phone")==""){
						alert("모두 입력해주세요.");
						return false;
					}
					//버튼 조회 확인
					for(var i = 0; i <checked.length; i++){
						if(!checked[i]){
							alert("모두 확인 조회를 해주세요.");
							return false;
							break;
						}
					}
				}
				
			});
	});
