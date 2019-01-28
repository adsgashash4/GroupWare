<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
	String ws5Root = request.getContextPath()+"/websquare/";
	String wsPage = (String)request.getAttribute("page");
%><!DOCTYPE html>
<html xmlns='http://www.w3.org/1999/xhtml' xmlns:ev='http://www.w3.org/2001/xml-events' xmlns:w2='http://www.inswave.com/websquare' xmlns:xf='http://www.w3.org/2002/xforms'>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
		<title>	WebSquare5</title>
		<script type="text/javascript">
			var WebSquareExternal = {"baseURI": "<%=ws5Root%>", "w2xPath" : "<%=wsPage%>" };
			if(parent.WebSquare){
				//iframe와 같이 상위에 웹스퀘어 객체가 있고 호출 페이지가 login 페이지가 아닌 경우  파라메터를 빈값으로 설정.
				/*
				if(WebSquareExternal.w2xPath && WebSquareExternal.w2xPath.toLowerCase().indexOf("login")<0){
					WebSquareExternal.w2xPath = "";	
				}
				*/
			}
		</script>
		<script type="text/javascript" src="<%=ws5Root%>javascript.wq?q=/bootloader"></script>
		<script type="text/javascript">
			window.onload = init;

			function init() {
				try{
					WebSquare.startApplication(WebSquareExternal.w2xPath);
				} catch(e) {
					alert(e.message);
				}
			}
		</script>
	</head>
<body></body>
</html>