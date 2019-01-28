<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Running Co-op :: 문의글 작성하기</title>
<link rel="stylesheet" href="css/main.css" type="text/css"/>
<link rel="stylesheet" href="daumOpenEditor/css/editor.css" type="text/css"/>
<script src="daumOpenEditor/js/editor_loader.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
	/* 비밀글 체크 시 비밀번호 입력가능한 인풋박스 출력 script */
	$(document).ready(function() {
		$("#sbr_scryn").click(function() {
			var chkyn = $(this).is(":checked");
			if (chkyn == true) {
				$("#sbr_pw").css("display", "inline-block");
				$("#sbr_pw").attr("required", "required");
			} else if (chkyn == false) {
				$("#sbr_pw").css("display", "none");
				$("#sbr_pw").attr("required", "none");
			}
		});
		
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
		location.href="./qnaList.do";
	}
	
	function new_sbr_title_length(val){
		var title = $(val).val();
		if(title.length >= 50){
			alert("제목은 50자를 넘을 수 없습니다!");
			$(val).val(title.substring(0,49));
		}
	}
	
</script>
<style type="text/css">
.formTable {
	width: 100%;
	display: table;
}

.tableBody {
	display: table-row-group;
}

.tableRow {
	border: 1px solid black;
	display: table-row;
}

.tableCol {
	border: 1px solid black;
	display: table-cell;
}
</style>
</head>
<body>
<div id = "header">
	<jsp:include page="../header.jsp" flush="false"/>
</div>

<div id = "container">
	<h3>문의 게시글 작성하기</h3>
	<div class="editor_div">
		<form id="boardWrite" action="./boardWrite.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="mem_id" value="${ mem_id }" />
			<input type="hidden" name="sbr_noticeyn" value="N"/>
				<div class="qnatitle_area">
					<div>제목</div>
					<div><input type="text" name="sbr_title" required="required" onkeyup="new_sbr_title_length(this)"/></div>
				</div>
				<div class="qnaattach_area">
					<div>첨부파일</div>
					<div>
						<input type="file" id="file" name="satt_name"/>
					</div>
					<div class="qnascr_area">
						<input type="checkbox" id="sbr_scryn" name="sbr_scryn" /><span>비밀글</span>
						<input type="text" id="sbr_pw" name="sbr_pw" style="display: none" />
					</div>
				</div>
				<jsp:include page="../daumOpenEditor.jsp" flush="false"></jsp:include>
		</form>
	</div>
<script type="text/javascript">
		var config = {
			txHost : '', /* 런타임 시 리소스들을 로딩할 때 필요한 부분으로, 경로가 변경되면 이 부분 수정이 필요. ex) http://xxx.xxx.com */
			txPath : '', /* 런타임 시 리소스들을 로딩할 때 필요한 부분으로, 경로가 변경되면 이 부분 수정이 필요. ex) /xxx/xxx/ */
			txService : 'sample', /* 수정필요없음. */
			txProject : 'sample', /* 수정필요없음. 프로젝트가 여러개일 경우만 수정한다. */
			initializedId : "", /* 대부분의 경우에 빈문자열 */
			wrapper : "tx_trex_container", /* 에디터를 둘러싸고 있는 레이어 이름(에디터 컨테이너) */
			form : 'boardWrite', /* 등록하기 위한 Form 이름 */
			txIconPath : "daumOpenEditor/images/icon/editor/", /*에디터에 사용되는 이미지 디렉터리, 필요에 따라 수정한다. */
			txDecoPath : "daumOpenEditor/images/deco/contents/", /*본문에 사용되는 이미지 디렉터리, 서비스에서 사용할 때는 완성된 컨텐츠로 배포되기 위해 절대경로로 수정한다. */
			canvas : {
				exitEditor : {
				},
				styles : {
					color : "#123456", /* 기본 글자색 */
					fontFamily : "굴림", /* 기본 글자체 */
					fontSize : "10pt", /* 기본 글자크기 */
					backgroundColor : "#fff", /*기본 배경색 */
					lineHeight : "1.5", /*기본 줄간격 */
					padding : "8px" /* 위지윅 영역의 여백 */
				},
				showGuideArea : false
			},
			events : {
				preventUnload : false
			},
			sidebar : {
				attachbox : {
					show : true,
					confirmForDeleteAll : true
				}
			},
			size : {
				contentWidth : 700
			/* 지정된 본문영역의 넓이가 있을 경우에 설정 */
			}
		};

		EditorJSLoader.ready(function(Editor) {
			var editor = new Editor(config);
		});
	</script>
<!-- 다음 에디터 함수 -->
<script type="text/javascript">
	/* 예제용 함수 */
	function saveContent() {
		Editor.save(); // 이 함수를 호출하여 글을 등록하면 된다.
	}

	function validForm(editor) {
		// Place your validation logic here

		// sample : validate that content exists
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

        // 본문 내용을 필드를 생성하여 값을 할당하는 부분
        var textarea = document.createElement('textarea');
        textarea.name = 'sbr_content';
        textarea.value = content;
        form.createField(textarea);

        var images = editor.getAttachments('image');
        for (i = 0; i < images.length; i++) {
            // existStage는 현재 본문에 존재하는지 여부
            if (images[i].existStage) {
                // data는 팝업에서 execAttach 등을 통해 넘긴 데이터
                alert('attachment information - image[' + i + '] \r\n' + JSON.stringify(images[i].data));
                input = document.createElement('input');
                input.type = 'hidden';
                input.name = 'attach_image';
                input.value = images[i].data.imageurl;  // 예에서는 이미지경로만 받아서 사용
                form.createField(input);
            }
        }

        var files = editor.getAttachments('file');
        for (i = 0; i < files.length; i++) {
            input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'attach_file';
            input.value = files[i].data.attachurl;
            form.createField(input);
        }
        return true;
	}
</script>
	<div class="btn_area">
		<button class="body_btn write_submit_btn" onclick='saveContent()'>등록</button>
		<input type="button" class="body_btn write_collback_btn" value="목록으로" onclick="back()" />
	</div>
</div>

<div id = "footer">
	<jsp:include page="../footer.jsp" flush="false"/>
</div>

</body>
</html>