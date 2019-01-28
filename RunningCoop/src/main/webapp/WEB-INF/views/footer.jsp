<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>사이드바메뉴</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<%
	String userName = (String) session.getAttribute("mem_name");
	String userId = (String) session.getAttribute("mem_id");
	String gr_id = (String)session.getAttribute("gr_id");
	String gr_level = (String)session.getAttribute("gr_level");
%>
<script type="text/javascript">
   $(function(){
	   var gr_id = "<%=gr_id%>";
	   var gr_level = "<%=gr_level%>";
	   
	   if(gr_level != "GM"){
		   $(".manage_Group").css("display", "none");
	   }else if(gr_level = "GM"){
		   $(".solo_Group").css("display","none");
	   }
       
       if(gr_id == "null"){
      	 $(".groupMenu").css("display", "none");
       }
	   
         $("#gr_name").autocomplete({
            source: function(request , response){
               $.ajax({
                  type:"POST",
                  url: "./autoauto.do",
                  dataType : "json",
                  async : false,
                  data:{ value : request.term},
                  success: function(data) {
                      response(
                                     $.map(data, function(item) {
                                         return {
                                             label: item.gr_name,
                                             value: item.gr_name
                                         }
                                     })
                                 );
            	      }
               });
            },
            minLength : 1,
            select : function ( event , ui){
            $("#gr_name").val(ui.item.label);
            }
         });
         
         $("#pr_name").autocomplete({
             source: function(request , response){
                $.ajax({
                   type:"POST",
                   url: "./autoprcomplete.do",
                   dataType : "json",
                   async : false,
                   data:{ value : request.term},
                   success: function(data) {
                       response(
                                      $.map(data, function(item) {
                                          return {
                                              label: item.pr_name,
                                              value: item.PR_NAME
                                          }
                                      })
                                  );
             	      }
                });
             },
             minLength : 1,
             select : function ( event , ui){
             $("#pr_name").val(ui.item.label);
             }
          });
         
         selectDoingPro();
         selectTodoPro();
         selectDonePro();
         
         

   });
   
	function selectDoingPro(){
	  var mem_id = "<%=userId%>";
	  		   
	  $.ajax({
	  	type : "POST",
	  	url : "./selectDoingPro.do",
	  	data : "mem_id="+mem_id,
	  	async : false,
	  	success : function(msg){
	  		$("#doingPro").html("진행중인 프로젝트 <span>"+msg.DOINGPRO+"</span>건");
	  		},
	  	})
	}
  	   
  	   function selectTodoPro(){
  		   var mem_id = "<%=userId%>";
  		   
  		   $.ajax({
  			   type : "POST",
  			   url : "./selectTodoPro.do",
  			   data : "mem_id="+mem_id,
  			   async : false,
  			   success : function(msg){
  				   $("#todoPro").html("진행예정인 프로젝트 <span>"+msg.TODOPRO+"</span>건");
  			   }
  		   })
  	   }
  	   
  	   function selectDonePro(){
  		   var mem_id = "<%=userId%>";

		$.ajax({
			type : "POST",
			url : "./selectDonePro.do",
			data : "mem_id=" + mem_id,
			async : false,
			success : function(msg) {
				$("#donePro").html("진행완료인 프로젝트 <span>" + msg.DONEPRO + "</span>건");
			}
		})
	}
  	
  	
  	function goProSelect(){
  		var gr_id = "<%=gr_id%>";
  		if(gr_id != "null"){
  			location.href = "./gProSelect.do?gr_id="+gr_id;
  		} else if (gr_id == "null"){
  			location.href = "./iProSelect.do";
  		}
  	}
  	
  	function goSocket(session){
  		var gr_id = session;
  		if(gr_id == "null"){
  			alert("그룹 선택 후 이용바랍니다");
  		}else{
  			var option = "";
  			window.open("./socketOpen.do", "그룹채팅", "width = 460, height = 480, resizable = no, toolbar = no, menubar = no, location = no, fullscreen = no, left = 300, top = 50");
  		}
  	}
  	
  	function goGrBoard(session){
  		var gr_id = session;
  		if(gr_id == "null"){
  			alert("그룹 선택 후 이용 바랍니다!");
  		} else {
	  		location.href="./grBoradList.do?gr_id="+gr_id;
  		}
  	}
  	
  	function groupOut(){
  		var rst = confirm("그룹을 탈퇴 하시겠습니까?");
  		var mem_id = "<%=userId%>";
  		if(rst == true){
			$.ajax({
				type : "POST",
				url : "./groupOut.do",
				async : false,
				success : function(rst){
					if(rst){
						
					}else{
						leaveService();
					}
					
				}
			})  		
  		}
  	}
  	
	function leaveService(){
		$.ajax({
			type: "GET",
			url: "./deleteMem.do",
			async: false,
			success: function(msg){
				if(msg.result==false){
					alert("탈퇴가 정상적으로 종료되지 못했습니다. 다시 시도해주세요.");
				}else{
					alert("탈퇴가 성공적으로 이뤄졌습니다. 감사합니다.");
					location.href = "./main.do";
				}
			}
		});
	}
  	
</script>
</head>
<body>
	
	<ul class="sidebar_list">
		<li class="footer-menu"><a href="#" class="top_menu" onclick="goProSelect()">프로젝트 선택</a></li>
		<li class="bottom-menu">
			<ul class="iProject">
				<li><a href='./goDoingSelect.do' id="doingPro"></a></li>
				<li><a href='./goTodoSelect.do' id="todoPro"></a></li>
				<li><a href='./goDoneSelect.do' id="donePro"></a></li>
			</ul>
		</li>
		<li class="footer-menu"><a href="#" class="top_menu" onclick="location.href='./myGrSelect.do?mem_id=<%=userId%>'">그룹 선택</a></li>
		<li class="menu_logo"><span class="top_menu">그룹</span></li>
		<li class="bottom-menu">
			<ul class="groupMenu">
<%-- 				<li><a href="#" onclick="goGrBoard('<%=gr_id%>')">그룹 게시판</a></li> --%>
				<li><a href="#" onclick="goSocket('<%=gr_id%>')">그룹 채팅</a></li>
				<li><a href="#" class="solo_Group" onclick="groupOut()">그룹 탈퇴</a></li>
				<li><a href="#" class="manage_Group" onclick="location.href='./grmodify.do?gr_id=<%=gr_id%>'">그룹 관리</a></li>
			</ul>
		</li>
		<li class="footer-menu"><a href="#" class="top_menu" onclick="location.href = './go_SearchForm.do'">검색하기</a></li>
		<li class="footer-menu"><a href="#" class="top_menu" onclick="location.href = './noticeList.do'">공지게시판</a></li>
		<li class="footer-menu"><a href="#" class="top_menu" onclick="location.href = './qnaList.do'">문의게시판</a></li>
		<li class="footer-menu"><a href="#" class="top_menu" onclick="location.href = './developerInfo.do'">개발자정보</a></li>
	</ul>

</body>
</html>
