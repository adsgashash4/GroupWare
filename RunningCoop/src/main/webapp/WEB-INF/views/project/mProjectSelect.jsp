<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 개인 프로젝트</title>

<link rel="stylesheet" href="css/main.css" type="text/css"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="js/canvasjs.min.js"></script>

<script type="text/javascript">
	$(function(){
		$(".pr_rate").each(function(){
			var idName=$(this).attr("id");
			var titleVal=$(this).attr("title");
			startChart(idName,titleVal);
		});
		
		$("form").submit(function(event){
			var pr_name = $("input[name=pr_name]").val();
			var pr_startdate = $("input[name=pr_startdate]").val();
			var pr_enddate = $("input[name=pr_enddate]").val();
			var pr_goal =  $("input[name=pr_goal]").val();
			
			var date1 = new Date(pr_startdate);
			var date2 = new Date(pr_enddate);
			var pr_startdate = Date.parse(date1);
			var pr_enddate   = Date.parse(date2); 
			
			var dateCal = pr_startdate-pr_enddate;
			
			if(dateCal>0 || pr_name.length == 0 || pr_startdate=="" || pr_enddate==""){
				alert("내용을 입력해주세요");			
				return false;
			}
				alert("프로젝트 생성 완료");
			return true;
		})
		
		
	})
	
	function startChart(sel,val){
			CanvasJS.addColorSet("greenShades",
                [
                "green",
                "#D8D8D8"
                ]);
			var chart = new CanvasJS.Chart(sel, {
				interactivityEnabled: false,
				colorSet : "greenShades",
				title : {
					text : val+"%",
					horizontalAlign : "center",
					verticalAlign : "center",
					fontSize : 30,
				},
				toolTip : {
					enabled : false,
				},
				animationEnabled: true,
				data: [
				{
					type: "doughnut",
					explodeOnClick: false,
					indexLabelFontFamily: "Garamond",
					indexLabelFontSize: 20,
					startAngle: 270,
					toolTipContent: "{y} %",

					dataPoints: [
					{ y: val},
					{ y: (100-val)}
					]
				}
				]
			});
			chart.render();
	}

	function detailPro(val, event){
		event.stopPropagation();
		$.ajax({
			type : "POST",
			url : "./detailPro.do",
			data : "pr_id="+val,
			async : false,
			success : function(msg){
				showProDetail(msg)
			}
		});
	}
	
	function showProDetail(nodes){
		var pr_name = nodes.PR_NAME;
		var mem_name = nodes.MEM_NAME;
		var pr_memcnt = nodes.PR_MEMCNT;
		var pr_goal = nodes.PR_GOAL;
		var pr_enddate = nodes.PR_ENDDATE;
		var pr_etc = nodes.PR_ETC;
		
		$("#pr_name").text(pr_name);
		$("#mem_name").text(mem_name);
		$("#pr_memcnt").text(pr_memcnt);
		$("#pr_goal").text(pr_goal);
		$("#pr_enddate").text(pr_enddate);
		$("#pr_etc").text(pr_etc);
		
		$(".pr_detail_view").css("display","block");
		$(".pr_detail_view").dialog({
			title : "프로젝트 정보 보기",
			height : 400,
			width : 500,
			position : {my : "center", at : "center"},
			resizable : false,
			modal : true
		});
	}
	
	function createPro(){
		$("#reset_create").click();
		document.getElementById("new_startdate").valueAsDate = new Date();
		$("#create_Form").dialog({
			title : "개인 프로젝트 생성",
			height : 350,
			width : 500,
			position : {my : "center", at : "center"},
			resizable : false,
			modal : true,
		});
		
	}
	
	function goToProject(val){
		location.href = "./goProject.do?pr_id="+val;
	}
	
	function pr_name_length(val){
		var value = $(val).val();
		if(value.length >= 25){
			alert("25자 이상 작성할 수 없습니다!");
			$(val).val(value.substring(0, 24));
		}
	}
</script>
</head>
<body>
<div id = "header">
	<jsp:include page="../header.jsp" flush="false"/>
</div>
<div id = "container">
	<button class="body_btn pr_create" onclick="createPro()">프로젝트 생성</button>
	
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
	
	<div class = "project_list_view">
		<c:choose>
			<c:when test="${ fn:length(list) == 0 }">
				<p> 진행 중인 프로젝트가 없습니다</p>
			</c:when>
			<c:otherwise>
				<c:forEach var="list" items="${ list }">
					<div class="pr_list" onclick="goToProject('${ list.pr_id }')">
						<div class="pr_btn info_div">
						<img alt="프로젝트 정보" class="pr_detail" src="images/project/pr_information.png" onclick="detailPro('${ list.pr_id }', event)"/>
						</div>
						<div class="pr_btn name_div" >
							<span>${ list.pr_name }</span>
						</div>
						<div class="pr_btn rate_div">
							<div id="${ list.pr_id }_chart" class="pr_rate" title="${ list.pr_proRate }"></div>
						</div>
						<div class="pr_btn date_div">
							<span style="margin-right: 10px;">D
						<c:if test="${ fn:startsWith(list.pr_endDate, '-') == true }">${ fn:replace(list.pr_endDate, '-', '+') }</c:if>
						<c:if test="${ fn:startsWith(list.pr_endDate, '-') == false }">-${ list.pr_endDate }</c:if>
							</span>
						</div>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
	
	<div id="create_Form">
		<form action="./mProCreate.do" method="POST">
		<table>
			<tr>
				<th>프로젝트 명</th>
				<td>
					<input type="text" name="pr_name" required="required" onkeyup="pr_name_length(this)"/>
				</td>
			</tr>
			<tr>
				<th>프로젝트 시작일자</th>
				<td>
					<input type="date" name="pr_startdate" id="new_startdate"/>
				</td>
			</tr>
			<tr>
				<th>프로젝트 종료일자</th>
				<td>
					<input type="date" name="pr_enddate" required="required"/>
				</td>
			</tr>
			<tr>
				<th>프로젝트 목적</th>
				<td>
					<input type="text" name="pr_goal"/>
				</td>
			</tr>
			<tr>
				<th>비고</th>
				<td>
					<input type="text" name="pr_etc"/>
				</td>
			</tr>
		</table>
		<input type="reset" class="body_btn create_btn" id="reset_create" value="초기화"/>
		<input type="submit" class="body_btn create_btn" value="프로젝트 생성"/>
	</form>
	</div>
</div>
<div id = "footer">
	<jsp:include page="../footer.jsp" flush="false"/>
</div>

</body>
</html>