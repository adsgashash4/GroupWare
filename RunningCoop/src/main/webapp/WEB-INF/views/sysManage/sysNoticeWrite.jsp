<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 관리자 - 공지 작성</title>
<link rel="stylesheet" href="css/main.css" type="text/css"/>
<link rel="stylesheet" href="daumOpenEditor/css/editor.css" type="text/css"/>
<script src="daumOpenEditor/js/editor_loader.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		/* file의 크기를 구하는 script */
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

	/* 목록으로 돌아가는 script */
	function back() {
		history.back();
	}
</script>
</head>
<body>
<% String mem_id = (String)session.getAttribute("mem_id"); %>
	<div id="sys_header">
		<jsp:include page="../sysHeader.jsp" flush="false" />
	</div>
	
	<div id="sys_container">
		<h3>공지 게시글 작성</h3>
		<div class="editor_div">
			<form id="noticeWrite" action="./noticeWrite.do" method="post" enctype="multipart/form-data">
				<input type="hidden" name="mem_id" value="<%=mem_id %>" />
				<div class="noticetitle_area">
					<div>제목</div>
					<div><input type="text" name="sbr_title"/></div>
				</div>
				<div class="noticeAttach_area">
					<div>첨부파일</div>
					<div>
						<input type="file" id="file" name="satt_name" /> 
					</div>
				</div>
				<jsp:include page="../daumOpenEditor.jsp" flush="false"></jsp:include>
			</form>
		</div>
		<div class="btn_area">
			<button class="body_btn notice_submit_btn" onclick='saveContent()'>등록</button>
			<input type="button" class="body_btn adm_notice_back" value="목록으로" onclick="back()" />
		</div>
		<!-- 다음 에디터 함수 -->

<script type="text/javascript">
	var config = {
		txHost: '', /* 런타임 시 리소스들을 로딩할 때 필요한 부분으로, 경로가 변경되면 이 부분 수정이 필요. ex) http://xxx.xxx.com */
		txPath: '', /* 런타임 시 리소스들을 로딩할 때 필요한 부분으로, 경로가 변경되면 이 부분 수정이 필요. ex) /xxx/xxx/ */
		txService: 'sample', /* 수정필요없음. */
		txProject: 'sample', /* 수정필요없음. 프로젝트가 여러개일 경우만 수정한다. */
		initializedId: "", /* 대부분의 경우에 빈문자열 */
		wrapper: "tx_trex_container", /* 에디터를 둘러싸고 있는 레이어 이름(에디터 컨테이너) */
		form: 'noticeWrite', /* 등록하기 위한 Form 이름 */
		txIconPath: "daumOpenEditor/images/icon/editor/", /*에디터에 사용되는 이미지 디렉터리, 필요에 따라 수정한다. */
		txDecoPath: "daumOpenEditor/images/deco/contents/", /*본문에 사용되는 이미지 디렉터리, 서비스에서 사용할 때는 완성된 컨텐츠로 배포되기 위해 절대경로로 수정한다. */
		canvas: {
            exitEditor:{
            },
			styles: {
				color: "#123456", /* 기본 글자색 */
				fontFamily: "굴림", /* 기본 글자체 */
				fontSize: "10pt", /* 기본 글자크기 */
				backgroundColor: "#fff", /*기본 배경색 */
				lineHeight: "1.5", /*기본 줄간격 */
				padding: "8px" /* 위지윅 영역의 여백 */
			},
			showGuideArea: false
		},
		events: {
			preventUnload: false
		},
		sidebar: {
			attachbox: {
				show: true,
				confirmForDeleteAll: true
			}
		},
		size: {
			contentWidth: 700 /* 지정된 본문영역의 넓이가 있을 경우에 설정 */
		}
	};

	EditorJSLoader.ready(function(Editor) {
		var editor = new Editor(config);
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

        var textarea = document.createElement('textarea');
        textarea.name = 'sbr_content';
        textarea.value = content;
        form.createField(textarea);

        return true;
	}
</script>
	</div>
	
	<div id="sys_footer">
		<jsp:include page="../sysFooter.jsp" flush="false"/>
	</div>
</body>
</html>