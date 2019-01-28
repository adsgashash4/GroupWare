<%@page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>프로젝트관리툴:Running Coop!</title>
</head>
<body>

<!-- 10. 첫페이지 이동화면 설정 -->
<!--  index Test 입니다 commit 되나 안되나 체크중 1 -->
<!-- <a href="./noticeList.do">공지게시판보기</a>
<a href="./qnaList.do">문의게시판보기</a>
<a href="./main.do">메인화면으로가기</a> -->

<%--    <!-- 10. 첫페이지 이동화면 설정 -->
   <!--  index Test 입니다 commit 되나 안되나 체크중 1 -->
   <jsp:forward page="./main.do">
   </jsp:forward>  --%>
   
   <jsp:forward page="./main.do"/>
</body>
</html>

