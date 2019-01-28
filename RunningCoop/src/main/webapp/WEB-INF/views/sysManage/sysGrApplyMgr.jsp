<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html >
<html>
<head>
<link rel="stylesheet" href="css/main.css" type="text/css"/>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script type="text/javascript">
   function cheakAll(all) {
      var box = document.getElementsByName("gr_id");
      alert(all);
      for (var i = 0; i < box.length; i++) {
         box[i].checked = all;
      }
   }
   
   function doApply() {
      $("#groupApply").submit(function() {
         
         var box = document.getElementsByName("gr_id");
         var chk = 0;
         for (var i = 0; i < box.length; i++) {
            if(box[i].checked==true){
               chk = chk + 1 ;
            }
         }
         if(chk==0){
            alert("1개 이상 선택하시오");            
            return false;   
         }else{
         $(this).attr("action", "grApplyYse.do");
         }
      });
   }
   
   function doReject() {
      $("#groupApply").submit(function() {
         
         var box = document.getElementsByName("gr_id");
         var chk = 0;
         for (var i = 0; i < box.length; i++) {
            if(box[i].checked==true){
               chk = chk + 1 ;
            }
         }
         if(chk==0){
            alert("1개 이상 선택하시오");            
            return false;   
         }else{
         $(this).attr("action", "grApplyNo.do");
         }
      });
   }
   
   function groupDetaildOpen(grid) {
      var gr_id = $(grid).parent().siblings().eq(0).find("input[name=gr_id]").val();
         $.ajax({
            type : "POST",
            url : "./groupInfo.do",
            data : "gr_id="+gr_id,
            async : false,
            success : function(data){
               $("#gr_name").text(data[0].GR_NAME);
               $("#mem_name").text(data[0].MEM_NAME);
               $("#gr_goal").text(data[0].GR_GOAL);
               $("#gr_regdate").text(data[0].GR_REGDATE);
               
               $(".pr_detail_view").css("display", "block");
               $(".pr_detail_view").dialog({
                  title : "그룹 정보 보기",
                  height : 300,
                  width : 700,
                  position : {my : "center", at : "center"},
                  resizable : false,
                  modal : true,
               });
            }
         });

//       window.open("./groupInfoChild.do?gr_id=" + gr_id, "InfoChild",
//             "width=570, height=350, resizable = no, scrollbars = no");
   }
</script>
<style type="text/css">
#groupChild {
   cursor: pointer;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>시스템 관리자 - 그룹 생성 승인 관리</title>
</head>
<body>
   <div id="sys_header">
      <jsp:include page="../sysHeader.jsp" flush="false" />
   </div>
   <div id="sys_container">
      <h3>그룹생성 관리</h3>
      <div class="adm_search_area">
         <form action="./grApplySch.do" method="post">
               <input type="text"  name="gr_name" />
               <input type="submit"  class="body_btn mem_search" value="그룹검색" />
         </form>
      </div>
      <div id="mgr_Container">
         <div class="mem_mgr_div">
         <form id="groupApply" action="" method="post">
               <input type="submit"  class="body_btn mem_search" value="승인" onclick="doApply()">
               <input type="submit" class="body_btn mem_search" value="거절" onclick="doReject()">
            <table class="adm_memList_table">
               <tr>
                  <th><input type="checkbox" onclick="cheakAll(this.checked)"></th>
                  <th>그룹명</th>
                  <th>그룹관리자</th>
                  <th>신청일자</th>
               </tr>
               <c:forEach var="apply" items="${Apply}">
                  <tr>
                     <td><input type="checkbox" name="gr_id" value="${apply.GR_ID}"></td>
                     <td><a onclick="groupDetaildOpen(this)" id="groupChild">${apply.GR_NAME}</a></td>
                     <td>${apply.MEM_NAME}</td>
                     <td>${apply.GR_REGDATE}</td>
                  </tr>
               </c:forEach>
            </table>
         </form>
      </div>
      </div>
   <div class="pr_detail_view">
         <table class="pr_detail_table">
            <tr>
               <td>그룹명</td>
               <td id="gr_name"></td>
            </tr>
            <tr>
               <td>그룹관리자</td>
               <td id="mem_name"></td>
            </tr>
            <tr>
               <td>그룹생성목적</td>
               <td id="gr_goal"></td>
            </tr>
            <tr>
               <td>신청일자</td>
               <td id="gr_regdate"></td>
            </tr>         
         </table>
      </div>
   </div>
   
   <div id="sys_footer">
      <jsp:include page="../sysFooter.jsp" flush="false"/>
   </div>
</body>
</html>