<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Running Co-op :: 그룹 선택</title>
<link rel="stylesheet" href="css/main.css" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<style>
  .ui-autocomplete {
    max-height: 100px;
    overflow-y: auto;
    /* prevent horizontal scrollbar */
    overflow-x: hidden;
  }
  * html .ui-autocomplete {
    height: 100px;
  }
</style>
<script type="text/javascript">
   $(function(){
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
   });

</script>
</head>
<body>
<div id="header">
	<jsp:include page="../header.jsp" flush="false"/>
</div>
<div id="container">
	<% 
	   String userName = (String)session.getAttribute("mem_name"); 
	   String userId = (String)session.getAttribute("mem_id");
	%>
	<form action="./allGrSelect.do" method="post">
	<div class="ui-widget">
	   <input type="text" name="gr_name" id="gr_name"/>
	   <input type="submit" value="그룹검색"/>
	</div>
	</form>
</div>
<div id="footer">
	<jsp:include page="../footer.jsp" flush="false"/>
</div>
</body>
</html>