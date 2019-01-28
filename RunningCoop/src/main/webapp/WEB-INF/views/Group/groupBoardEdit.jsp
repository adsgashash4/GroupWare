<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 그룹 게시글 수정</title>
<link rel="stylesheet" href="daumOpenEditor/css/editor.css" type="text/css"/>
<script src="daumOpenEditor/js/editor_loader.js" type="text/javascript" charset="utf-8"></script>
<script src="daumOpenEditor/js/editor_creator.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<link rel="stylesheet" href="css/main.css" type="text/css"/>
<script type="text/javascript">
	function back() {
		history.back();
	}
	
	/* 비밀글 체크 시 비밀번호 입력가능한 인풋박스 출력 script */
	$(document).ready(function() {
	$("#file").bind('change', function() {
		var maxFileSize = 1024 * 1024 * 5;
		var InputFileSize = this.files[0].size;
		if (InputFileSize > maxFileSize) {
			alert("파일이 너무 큽니다");
			$(this).val("");
			} else {
				$("#filesize").val(InputFileSize);
			}
		});
	});
</script>

</head>
<body>
<div id = "header">
	<jsp:include page="../header.jsp" flush="false"/>
</div>

<div id = "container">
	<h3>그룹 게시글 수정하기</h3>
	<div class="editor_div">
		<c:set var="view" value="${ view }" />
		<form id="boardEdit" action="./grBoardEdit.do" method="post" enctype="multipart/form-data">
		<div class="editTitle_area">
				<input type="hidden" name="br_uuid" value="${ view.get('BR_UUID') }"/>
				<div>제목</div>
				<div><input type="text" name="br_title" value="${ view.get('BR_TITLE') }" /></div>
		</div>
		<div class="editAttach_area">
			<div><span>첨부파일</span></div>
<!-- 			<div> -->
<%-- 				<c:set var="attach" value="${ attach }" /> --%>
<%-- 				<c:if test="${ fn:length( attach.get('SATT_NAME')) != 0 }"> --%>
<%-- 					<span>기존파일 : ${ attach.get("SATT_NAME") }(${ attach.get("SATT_SIZE")/1024 }KB)</span> --%>
<%-- 				</c:if>&nbsp;&nbsp; --%>
<!-- 				<input type="file" id="file" name=gatt_name/> -->
<!-- 			</div> -->
		</div>
		<textarea id="content" name="br_content"></textarea>
		</form>
	</div>
	
<script>
	var config = {
		initializedId : "",
		wrapper : "tx_trex_container",
		form : 'boardEdit',
		txIconPath : "daumOpenEditor/images/icon/editor/",
		txDecoPath : "daumOpenEditor/images/deco/contents/",
		events : {
			preventUnload : false
		},
		sidebar : {
			attachbox : {
				show : true
			}
		}
	};

	EditorCreator.convert(document.getElementById("content"),
			'daumOpenEditor/pages/template/daumOpenEditor.jsp',
			function() {
				var content = '${ view.get("BR_CONTENT") }';
				EditorJSLoader.ready(function(Editor) {
					new Editor(config);
					Editor.modify({
						content : content
					});
				});
			});
</script>
	
<script type="text/javascript">
	/* 예제용 함수 */
	function saveContent() {
		Editor.save(); // 이 함수를 호출하여 글을 등록하면 된다.
	}

	function validForm(editor) {
		var validator = new Trex.Validator();
		var content = editor.getContent();
		if (!validator.exists(content)) {
			alert('내용을 입력하세요');
			return false;
		}

		return true;
	}

	function setForm(editor) {
		var i, input;
        var form = editor.getForm();
        var content = editor.getContent();

        var field = document.getElementById("content");
        field.value = content;

		return true;
	}
</script>

	<div class="btn_area">
		<button class="body_btn edit_submit_btn" onclick='saveContent()'>등록</button>
		<input type="button" class="body_btn edit_collback_btn" value="목록으로" onclick="back()" />
	</div>
</div>

<div id = "footer">
	<jsp:include page="../footer.jsp" flush="false"/>
</div>
</body>
</html>