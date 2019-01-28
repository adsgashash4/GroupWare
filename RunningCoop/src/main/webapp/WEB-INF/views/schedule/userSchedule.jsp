<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.pm.rc.dto.ScheduleDto"%>
<%@ page import="java.util.Calendar"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>개인 일정 관리</title>
<link rel="stylesheet" href="css/main.css" type="text/css"/>
<link rel="stylesheet" href="css/body/user_account.css" type="text/css"/>
<link rel="stylesheet" href="css/body/user_schedule.css" type="text/css"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src = "js/schedule/schedule.js"></script>
</head>

<%!
	//요일별 색깔(토요일:파랑, 일요일:빨강)
	public String weekColor(int i, int dayOfWeek){
	String color = "";
	if((i+dayOfWeek-1)%7==0){
		color = "blue";
	}else if((i+dayOfWeek-1)%7==1){
		color = "red";
	}
	return color;
	}

	//일정작성
	public String schWrite(int year, String month, String date){
		 /* "<a href = './writeSchedule.do?year="+year+"&month="+month+"&date="+date+"'>" */
		return	"<img class = 'plus' alt = '일정등록' src = 'images/plus.png' onclick='openWriteForm(\""+year+"\",\""+month+"\",\""+date+"\")'>";
	}
	
	//날짜 형식 맞추기(두자리:0x~31)
	public String dateForm(String date){
		return date.trim().length()<2 ? "0"+date:date;
	}
	
	//달력에 일정목록 출력
	public String schListView(int year, int month, int day, List<ScheduleDto> lists){
		String result = "";
		String s_year = dateForm(String.valueOf(year));
		String s_month = dateForm(String.valueOf(month));
		String s_day = dateForm(String.valueOf(day));
		String date = s_year+s_month;	//출력되는 날짜(년/월 부분)
		for(ScheduleDto dto:lists){
			System.out.println(dto);
			String title = dto.getSch_title();
			
			int startDate = Integer.parseInt(dto.getSch_startDate().substring(6, 8));	//일정 시작 날짜(일) 부분
			int endDate = Integer.parseInt(dto.getSch_endDate().substring(6, 8));	//일정 종료 날짜(일) 부분
			if(dto.getSch_startDate().substring(0, 6).equals(date)&&dto.getSch_endDate().substring(0, 6).equals(date)&&startDate<=day&&day<=endDate){
				//일정 시작&종료날짜(년/월) == 출력되는 날짜(년/월), 시작 날짜(일)<=출력되는 일<=종료 날짜(일)
				if(dto.getSch_prosYN().equals("Y")){	//팀 일정 여부
					title = "<img src = 'images/pIcon.png' alt = '프로젝트일정' style = 'width: 15px; height: 15px;'>"
							+ dto.getProjectDto().getPr_name()+":"+title;
				}
				String event = "<span class = 'listChk' onclick = 'showDetail("
								+dto.getSch_seq()+",\""+s_year+"\",\""+s_month+"\",\""+s_day+"\")'>"
								+title+"</span><br>";
				result += event+"\n";
			}
		}
		return result;
	}
%>
<%
	//회원 계정 정보
	String mem_id = (String)session.getAttribute("mem_id");

	//원하는 달로 이동하기 위해 현재 연도와 달 값 전달받음
	String cyear = (String)request.getParameter("year");
	String cmonth = (String)request.getParameter("month");
	System.out.println("year:"+cyear+"/month:"+cmonth);
	
	//회원 일정 목록
	ArrayList<ScheduleDto> lists = (ArrayList<ScheduleDto>)request.getAttribute("list");
	
	//칼랜더 객체 생성
	Calendar cal = Calendar.getInstance();
	
	//원하는 달로 이동하기 위한 처리
	int year = cal.get(Calendar.YEAR);	//현재 년도
	if(cyear!=null){
		year = Integer.parseInt(cyear);	//전달받은 년도로 재설정
	}
	int month = cal.get(Calendar.MONTH)+1;
	if(cmonth!=null){
		month = Integer.parseInt(cmonth);	//전달받은 달로 재설정
	}
	
	if(month<1){
		month = 12;
		year--;
	}
	if(month>12){
		month = 1;
		year++;
	}
	cal.set(year, month-1, 1);	//수정된 날짜로 cal 객체 셋팅
	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);	//수정된 년/월/1일에 대한 요일 구하기(그달의 첫날 요일)
	int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH); 	//해당 달의 마지막 날 구하기
%>

<body>
<div id = "header">
	<jsp:include page="../header.jsp" flush="false"/>
</div>
<div id = "container">
	<h1>일정관리</h1>
	<!-- 달력 출력 -->
	<table id="calendar">
		<caption class = "calendar_header">
			<a href="./viewSchedule.do?year=<%=year-1%>&month=<%=month%>"><img alt="이전 년도 이동" class="arrow" src="images/beforeY.png"></a> 
			<a href="./viewSchedule.do?year=<%=year%>&month=<%=month-1%>"><img alt="이전 월 이동" class="arrow" src="images/beforeM.png"></a> 
			<b><%=year %>년<%=month %>월</b> 
			<a href="./viewSchedule.do?year=<%=year%>&month=<%=month+1%>"><img alt="이전 월 이동" class="arrow" src="images/nextM.png"></a> 
			<a href="./viewSchedule.do?year=<%=year+1%>&month=<%=month%>"><img alt="이전 월 이동" class="arrow" src="images/nextY.png"></a>
		</caption>
		<col width="14.28%">
		<col width="14.28%">
		<col width="14.28%">
		<col width="14.28%">
		<col width="14.28%">
		<col width="14.28%">
		<col width="14.28%">
		<tr>
			<th>일</th>
			<th>월</th>
			<th>화</th>
			<th>수</th>
			<th>목</th>
			<th>금</th>
			<th>토</th>
		</tr>
		<tr>
			<%
				//매 달 1일 시작 전 공백 채우기
				for(int i = 1; i < dayOfWeek; i++){
					out.print("<td>&nbsp;</td>");
				}
				//1일부터 구한 달력의 lastDay까지 날짜 출력
				for(int i = 1; i <= lastDay; i++){
			%>
			<td style="color:<%=weekColor(i, dayOfWeek)%>;">
				<% String s_month = dateForm(String.valueOf(month)); %>
				<% String s_i = dateForm(String.valueOf(i)); %> 
				<div class = "dailyTitle">
					<span class = "dailyList" onclick = "dailyList('<%=year%>', '<%=s_month%>', '<%=s_i%>')"><%=i %></span>
					<%=schWrite(year, s_month, s_i)%>
				</div>
				<div class="calendar_list"><%=schListView(year, month, i, lists) %></div></td>
			<%
					//토요일이 되면 줄바꿈
					if((i+dayOfWeek-1)%7==0){
					%>
		</tr>
		<tr>
			<%	
					}%>
			<%
			//출력되는 일이 마지막 일이면 공백 출력
			if(i==lastDay&&(i+dayOfWeek-1)%7!=0){
				for(int j = 0; j < 7-(i+dayOfWeek-1)%7; j++){
					out.print("<td>&nbsp;</td>");
				}
			}
		} 
		%>
		</tr>
	</table>
	
	<div class = "calendarContent" style = "width:300px; display: inline-block;">
		<!-- 일정 상세정보 출력부분 -->
		<div class="scheduleDetailBox">
			<!-- 지정일 리스트 -->
			<div class = "rowGroup" id = "dayList">
				<div class = "join_row" style = "text-align: left; background-color: #5cb85c">
					<span style = "font-weight: bold; color: white;">일정 리스트</span>
				</div>
				<div class = "join_row" style = "border-bottom: none;">
					<div class = "listBox">
					</div>
				</div>
			</div>
			
			<!-- 일정 상세정보 조회 -->
			<div class = "rowGroup" id = "dayDetail">
				<div class  = "detailBox">
					<input type="hidden" id="sch_seq">
					<table style = "width: 298px;">
						<tr>
							<td class = "join_row" id="sch_startDate" style = "width:100%;"></td>
						</tr>
						<tr>
							<td class = "join_row" id="sch_endDate" style = "width:100%;"></td>
						</tr>
						<tr>
							<td class = "join_row" id="sch_title" style = "width:100%;"></td>
						</tr>
						<tr>
							<td class = "join_row" id="sch_content" style = "width:100%;"></td>
						</tr>
					</table>
					<div style = "float: right;">
						<img src = 'images/project/wcom_edit_btn.png' alt = '일정수정' style = 'width: 15px; height: 15px; margin-right:10px;' onclick="goModify()"> 
						<img src = 'images/schDelete.png' alt = '일정삭제' style = 'width: 15px; height: 15px; margin-right:10px;' onclick="goDelete()">
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- 일정 수정 부분 -->
		<form class=" rowGroup scheduleModiBox" action="./modifySchedule.do" method="post">
			<div>
				<input type = "hidden" id = "sch_upSeq" name = "sch_seq"> 
				<input type = "hidden" name = "mem_id" value = <%=mem_id%>>
				<div class = "join_row" style = "text-align: left; background-color: #5cb85c">
					<span style = "font-weight: bold; color: white;">일정 수정</span>
				</div>
				<div class = "join_row" style = "width: 100%;">
					<span style = "font-size:10pt; display: inline-block; float: left; margin-right: 3px;">시작 </span><br>
					<input type = "date" class = "join_row" id = "sch_upStartDate" style = "width:130px; height: 30px; display: inline-block; padding: 0px;">
					<input type = "time" class = "join_row" id = "sch_upStartTime" style = "width:130px; height: 30px; display: inline-block; padding: 0px;">
					<input type = "hidden" id = "upStartTotal" name = "sch_startDate"> 
				</div>
				<div class = "join_row" style = "width: 100%;">
					<span style = "font-size:10pt; display: inline-block; float: left;">종료</span><br>
					<input type = "date" class = "join_row" id = "sch_upEndDate" style = "width:130px; height: 30px; display: inline-block; padding: 0px;">
					<input type = "time" class = "join_row" id = "sch_upEndTime" style = "width:130px; height: 30px; display: inline-block; padding: 0px;">
					<input type = "hidden" id = "upEndTotal" name = "sch_endDate"> 
				</div>
				<div class = "join_row" style = "width: 100%;">
				<span style = "font-size:10pt; display: inline-block; float: left;">제목</span><input type = "text" id = "sch_upTitle" name = "sch_title" style = "width:250px; height: 30px;"> 
				</div>
				<div class = "join_row" style = "width: 100%;">
				<span style = "font-size:10pt; display: inline-block; float: left;">내용</span><input type = "text" id = "sch_upContent" name="sch_content" style = "width:250px; height: 30px;"> 
				</div>
			</div>
			<div>
				<input type="submit" class = "scheduleBtn" value="수정">
			</div>
		</form>	
		
		<!-- 일정 등록 부분 -->
		<form class=" rowGroup scheduleBox" action="./insertSchedule.do" method="post">
			<div>
				<input type = "hidden" name = "mem_id" value = <%=mem_id%>> 
				<div class = "join_row" style = "text-align: left; background-color: #5cb85c">
					<span style = "font-weight: bold; color: white;">일정 등록</span>
				</div>
				<div class = "join_row" style = "width: 100%;">
					<span style = "font-size:10pt; display: inline-block; float: left; margin-right: 3px;">시작 </span><br>
					<input type = "date" class = "join_row" id = "sch_newStartDate" style = "width:130px; height: 30px; display: inline-block; padding: 0px;">
					<input type = "time" class = "join_row" id = "sch_newStartTime" style = "width:130px; height: 30px; display: inline-block; padding: 0px;">
					<input type = "hidden" id = "newStartTotal" name = "sch_startDate">
				</div>
				<div class = "join_row" style = "width: 100%;">
					<span style = "font-size:10pt; display: inline-block; float: left;">종료</span><br>
					<input type = "date" class = "join_row" id = "sch_newEndDate" style = "width:130px; height: 30px; display: inline-block; padding: 0px;">
					<input type = "time" class = "join_row" id = "sch_newEndTime" style = "width:130px; height: 30px; display: inline-block; padding: 0px;">
					<input type = "hidden" id = "newEndTotal" name = "sch_endDate"> 
				</div>
				<div class = "join_row" style = "width: 100%;">
				<span style = "font-size:10pt; display: inline-block; float: left;">제목</span><input type = "text" id = "sch_newTitle" name = "sch_title" style = "width:250px; height: 30px;"> 
				</div>
				<div class = "join_row" style = "width: 100%;">
				<span style = "font-size:10pt; display: inline-block; float: left;">내용</span><input type = "text" id = "sch_newContent" name="sch_content" style = "width:250px; height: 30px;"> 
			</div>
			<div>
				<input type="submit" class = "scheduleBtn" value="등록">
			</div>
		</form>
	
	
	</div>

</div>
<div id = "footer">
	<jsp:include page="../footer.jsp" flush="false"/>
</div>
</body>
</html>