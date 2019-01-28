<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js">
</script>
<script type="text/javascript">
	$(function(){
		$(".scheduleBox").submit(function(){
			var startTotal = $("#sch_startDate").val()+" "+$("#sch_startTime").val();
			$("#startTotal").val(startTotal);
			var endTotal = $("#sch_endDate").val()+" "+$("#sch_endTime").val();
			$("#endTotal").val(endTotal);
		});
	});
</script>
<body>
<div id = "header">
	<jsp:include page="../header.jsp" flush="flase"/>
</div>
<div id = "container">
	<%
		String mem_id = (String)session.getAttribute("mem_id");
	%>
	
	<form class = "scheduleBox" action="./insertSchedule.do" method="post">	
		<div>
		<input type = "hidden" name = "mem_id" value = <%=mem_id%>>
		시작
		<input type="date" id = "sch_startDate" value = "${day}">
		<input type = "time" id = "sch_startTime">
		<input type = "hidden" id = "startTotal" name = "sch_startDate">
		~
		종료<input type="date" id = "sch_endDate" value = "${day}">
		<input type = "time" id = "sch_endTime">
		<input type = "hidden" id = "endTotal" name = "sch_endDate">
		<br>
		제목:<input type="text" name = "sch_title"><br>
		내용:<input type="text" name = "sch_content">
		</div>
		<div>
			<input type = "submit" value = "등록">
		</div>
	</form>
</div>
<div id = "footer">
	<jsp:include page="../footer.jsp" flush="false"/>
</div>
</body>
</html>