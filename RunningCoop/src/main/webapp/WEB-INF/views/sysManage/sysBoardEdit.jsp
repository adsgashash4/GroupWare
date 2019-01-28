<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 시스템 관리자 - 게시글 수정</title>
<link rel="stylesheet" href="css/main.css" type="text/css"/>
<link rel="stylesheet" href="daumOpenEditor/css/editor.css" type="text/css"/>
<script src="daumOpenEditor/js/editor_loader.js" type="text/javascript" charset="utf-8"></script>
<script src="daumOpenEditor/js/editor_creator.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>

<script type="text/javascript">
	function back() {
		history.back();
	}
</script>
</head>
<body>
	<div id="sys_header">
		<jsp:include page="../sysHeader.jsp" flush="false" />
	</div>
	
	<div id="sys_container">
		<h3>게시글 수정</h3>
		<div class="editor_div">
			<c:set var="view" value="${ view }" />
				<form id="noticeEdit" action="./sysboardEdit.do" method="post" enctype="multipart/form-data">
				<div class="editTitle_area">
					<input type="hidden" name="sbr_uuid" value="${ view.get('SBR_UUID') }" />
					<div>제목</div>
					<div><input type="text" name="sbr_title" value="${ view.get('SBR_TITLE') }" /></div>
				</div>
				<div class="editAttach_area">
					<div><span>첨부파일</span></div>
					<div>
						<c:set var="attach" value="${ attach }" />
						<c:if test="${ fn:length( attach.get('SATT_NAME')) != 0 }">
							<span>기존파일 : ${ attach.get("SATT_NAME") }(${ attach.get("SATT_SIZE")/1024 }KB)</span>
						</c:if>&nbsp;&nbsp;
						<input type="file" id="file" name="satt_name"/>
					</div>
				</div>
				<textarea name="sbr_content" id="content"></textarea>
				</form>
		</div>
		<div>
			<button class="body_btn edit_submit_btn" onclick='saveContent()'>등록</button>
			<input type="button" class="body_btn edit_collback_btn" value="이전으로" onclick="back()" />
		</div> 
	</div>
	
<!-- 다음 에디터 함수 -->
<script type="text/javascript">
	var config = {
		initializedId : "",
		wrapper : "tx_trex_container",
		form : "noticeEdit",
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
				var content = '${ view.get("SBR_CONTENT") }';
				EditorJSLoader.ready(function(Editor) {
					new Editor(config);
					Editor.modify({
						content : content
					});
				});
			});
</script>
<script type="text/javascript">
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

	<div id="sys_footer">
		<jsp:include page="../sysFooter.jsp" flush="false"/>
	</div>
</body>
</html>