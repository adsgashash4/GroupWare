	$(window).load(function(){
		var wk_id2 = $("#wk_id").val();
		if ( wd_erroryn == 'N'){
			$("#"+wk_id2).next().css("display","none");
		} else {
			$("#"+wk_id2).next().css("display","");
		}

		alramCh = $(val1).children("span").eq(2).html();
		
		
	})
	
	/* 업무 상세 페이지 조회 메소드 */
	function viewWork(val, val2, val3){
		$("#wk_id").val(val);
		var wd_title = "업무 상세 화면 : : "+ val3;
		$("#header_title").html("업무 상세 화면 - "+val3);
		$.ajax({
			type : "POST",
			url : "./detailWork.do",
			data : "wk_id="+val,
			async : false,
			success : function(msg){
				$(".wd_modal").css("display", "block");
				showWorkDetail(msg)
				changeProgress(val2);
			}
		});
		/*Progress bar 생성*/
		 var width = 0;
		 function changeProgress(val){
			 var progress = parseInt(val);
			 var elem = $("#partProgress");
			 var id = setInterval(frame, 10);
			 
			 function frame(){
				 if(width >= progress){
					 clearInterval(id);
				 }else{
					 width+=1;
					 elem.css("width",width+"%");
					 elem.html(width*1+"%");
				 }
			 }
		 }
	}
	
	/* 업무 상세 페이지 - 하위 업무 리스트 출력 */
	function showWorkDetail(nodes){
		$("#wd_field_table").children().remove();
		 if(nodes.length == 0){
			$("#wd_field_table").append("<tr><td colspan='7'>하위업무가 없습니다.</td></tr>");
		} else {
			  for(var i = 0; i < nodes.length; i++){
				var wd_id = nodes[i].wd_id;
				var wd_title = nodes[i].wd_title;
				var wd_erroryn = nodes[i].wd_errorYN;
				var wd_endDate = nodes[i].wd_endDate;
				var wd_complyn = nodes[i].wd_complYN;
				var wk_id2 = $("#wk_id").val();
				var erroryn = "";
				if ( wd_erroryn == 'N'){
					erroryn = "";
				} else {
					erroryn = "<img alt='애로알림' src='images/project/wd_error_btn.png'/>";
				}
				
				if ( wd_complyn == 'Y' ){
					wd_complyn = "<img alt='업무완료' src='images/project/wd_complete_btn.png'/>";
				} else {
					wd_complyn = " ";
				}
				
				$("#wd_field_table").append("<tr><td style='width:10% text-align:center;;'>"+wd_complyn+"</td>" +
						"<td style='width:30%;'><span class='wd_title' onclick='wdEditForm(this,\""+wd_id+"\")'>"+wd_title+"</span></td>" +
						"<td style='width:10%; text-align:center;'>"+erroryn+"</td>" +
						"<td style='width:20%;'><span>"+wd_endDate+"</span></td>" +
						"<td style='width:10%; text-align:center;'><img alt='완료버튼' src='images/project/wd_complete_btn.png' onclick='wdComplete(\""+wd_id+"\")'/></td>" +
						"<td style='width:10%; text-align:center;'><img alt='애로알림버튼' src='images/project/wd_error_btn.png' onclick='wdError(\""+wd_id+"\",\""+wd_erroryn+"\")'/></td>" +
						"<td style='width:10%; text-align:center;'><img alt='삭제버튼' src='images/project/wd_delete_btn.png' onclick='wdDelete(\""+wd_id+"\")'/></td></tr>");
			}
		}
		 
		 /* 댓글 목록 출력 */
		wcomListView()
		/* 첨부파일 목록 출력 */
		attachListView()
		
	}
	
	function new_wd_title_length(val){
		var title = $(val).val();
		if(title.length > 25 ){
			alert("업무내용은 25자를 넘을 수 없습니다!");
			$(val).val(title.substring(0,24));
		}
	}
	
	/* 업무 상세 페이지 - 하위 업무 추가 기능 */
	function wd_Insert(){
		var wd_title = $("#insert_wd_title").val();
		var wd_endDate = $("#insert_wd_endDate").val();
		if(wd_title.length == 0 || wd_endDate == ""){
			alert("업무내용과 마감기한을 확인해주세요!");
		} else {
			var wk_id = $("#wk_id").val();
			$.ajax({
				type : "POST",
				url : "./wdInsert.do",
				data : {"wd_title":wd_title, "wd_endDate":wd_endDate, "wk_id":wk_id},
				async : false,
				success : function(msg){
					$("#wd_Field").children("p").remove().children("div").remove();
					$("#insert_wd_title").val("");
					$("#insert_wd_endDate").val("");
					showWorkDetail(msg)
				}
				
			});
		}
	}
	
	/* 업무 상세 페이지 - 하위 업무 수정 폼 출력 */
	function wdEditForm(val, wd_id){
		$(".tr_edit").remove();
		$(val).parent().parent().after("<tr class='tr_edit'><td colspan='2'>업무내용<input type='text' id='newWdTitle' style='margin-left:5px;' name='wd_title' onkeyup='edit_wd_title_lenght(this)'/></td>" +
				"<td colspan='3'>마감일자<input type='date' id='newWdEndDate' style='margin-left:5px;' name='wd_endDate' value=''/></td>" +
				"<td colspan='2'><input type='button' value='수정' class='body_btn wd_edit_btn' onclick='wdEdit(\""+wd_id+"\")'/>" +
				"<input type='button' value='취소' class='body_btn wd_edit_cancle_btn' onclick='wdEdit_Cancle()'/></td>" +
				"</tr>");
	}
	
	function edit_wd_title_lenght(val){
		var value = $(val).val();
		if(value.length > 25){
			alert("업무내용은 25자를 넘을 수 없습니다!");
			$(val).val(value.substring(0,24));
		}
	}
	
	/* 업무 상세 페이지 - 하위 업무 수정 기능 */
	function wdEdit(val){
		var wd_title = $("#newWdTitle").val();
		var wd_endDate = $("#newWdEndDate").val();
		var wk_id = $("#wk_id").val();
		var wd_id = val;
		
		if(wd_title.length == 0 || wd_endDate == ""){
			alert("업무내용과 마감기한을 확인해주세요!");
		} else {
			$.ajax({
				type : "POST",
				url : "./wdEdit.do",
				data : {"wd_title":wd_title, "wd_endDate":wd_endDate, "wd_id":wd_id, "wk_id":wk_id},
				async : false,
				success : function(msg){
					$(".tr_edit").remove();
					showWorkDetail(msg);
				}
			});
		}
	}
	
	/* 업무 상세 페이지 - 하위 업무 수정 폼 닫기 */
	function wdEdit_Cancle(){
		$(".tr_edit").remove();
	}
	
	/* 업무 상세 페이지 - 하위 업무 완료 기능 */
	function wdComplete(val){
		var wd_id = val;
		var wk_id = $("#wk_id").val();
		
		$.ajax({
			type : "POST",
			url : "./wdComplete.do",
			data : {"wd_id": wd_id, "wk_id": wk_id},
			async : false,
			success : function(msg){
				$("#wd_Field").children("p").remove().children("div").remove();
				showWorkDetail(msg)
			}
		});
	}
	
	/* 업무 상세 페이지 - 하위 업무 애로사항 표시 기능 */
	function wdError(val1, val2){
		var wd_id = val1;
		var wd_erroryn = val2;
		
		var wk_id = $("#wk_id").val();
		$.ajax({
			type : "POST",
			url : "./wdError.do",
			data : {"wd_id": wd_id, "wk_id": wk_id,"wd_erroryn" : wd_erroryn},
			async : false,
			success : function(msg){
				$("#wd_Field").children("p").remove().children("div").remove();
				showWorkDetail(msg)
			}
		});
	}
	
	/* 업무 상세 페이지 - 하위 업무 삭제 기능 */
	function wdDelete(val){
		var wd_id = val;
		var wk_id = $("#wk_id").val();
		
		$.ajax({
			type : "POST",
			url : "./wdDelete.do",
			data : {"wd_id": wd_id, "wk_id": wk_id},
			async : false,
			success : function(msg){
				$("#wd_Field").children("p").remove().children("div").remove();
				showWorkDetail(msg)
			}
		});
	}
	
	/* 업무 상세 페이지 - 업무 상세 페이지 닫기 */
	function backToProject(){
		var pr_id = $("#pr_id").val();
		location.href="./goProject.do?pr_id="+pr_id;
	}
	
	/* 업무 상세 페이지 - 업무 코멘트 목록 조회 기능 */
	function wcomListView(){
		var wk_id = $("#wk_id").val();
		
		$.ajax({
			type : "POST",
			url : "./wcomlist.do",
			data : "wk_id="+wk_id,
			async : false,
			success : function(msg){
				showcommentList(msg)
			}
		})
	}
	
	function new_commentLength(val){
		var value = $(val).val();
		if(value.length > 200){
			alert("댓글은 200자를 넘을 수 없습니다!");
			$(val).val(value.substring(0,199));
		}
	}
	
	/* 업무 상세 페이지 - 업무 코멘트 추가 기능 */
	function wcom_Insert(){
		var wk_id = $("#wk_id").val();
		var wcom_content = $("#new_wcom_content").val();
		if(wcom_content.length > 0 && wcom_content.length <= 200){
			$("#new_wcom_content").val("");
			
			$.ajax({
				type : "POST",
				url : "./wcominsert.do",
				data : {"wk_id": wk_id, "wcom_content": wcom_content},
				async : false,
				success : function(nodes){
					showcommentList(nodes)
				}
			})
		} else {
			alert("댓글 내용을 작성해주세요!");
		}
	}
	
	/* 업무 상세 페이지 - 업무 코멘트 수정 폼 출력 */
	function wcom_Edit(val, val2, val3){
		var wcom_id = val2;
		var wcom_content = val3;
		
		$(".commentEditForm").remove();
		$(val).parent().parent().after("<tr class='commentEditForm'><td><input type='hidden' name='wcom_id' value='"+wcom_id+"'/></td>"+
				"<td><input type='text' name='wcom_content' style='width: 80%;' value='"+wcom_content+"'/></td>"+
				"<td><input type='button' value='수정' class='body_btn wcom_delete_btn' style='margin-right:5px;' onclick='commentEdit()'/>"+
				"<input type='button' value='취소' class='body_btn wcom_delete_btn' onclick='wcom_edit_cancle()'/></td></tr>");
	}
	
	/* 업무 상세 페이지 - 업무 코멘트 수정 기능  */
	function commentEdit(){
		var wk_id = $("#wk_id").val();
		var wcom_id = $(".commentEditForm").eq(0).find("input[name=wcom_id]").val();
		var wcom_content = $(".commentEditForm").eq(0).find("input[name=wcom_content]").val();
		
		$(".commentEditForm").remove();
		$.ajax({
			type : "POST",
			url : "./wcomEdit.do",
			data : {"wk_id": wk_id, "wcom_id": wcom_id, "wcom_content": wcom_content},
			async : false,
			success : function(nodes){
				showcommentList(nodes)
			}
		});
	}
	
	/* 업무 상세 페이지 - 업무 코멘트 수정 취소 기능 */
	function wcom_edit_cancle(){
		$(".commentEditForm").remove();
	}
	
	/* 업무 상세 페이지 - 업무 코멘트 삭제 기능 */
	function wcom_Delete(val){
		var wk_id = $("#wk_id").val();
		var wcom_id = val;
		
		$.ajax({
			type : "POST",
			url : "./wcomDelete.do",
			data : {"wk_id":wk_id, "wcom_id":wcom_id},
			async : false,
			success : function(nodes){
				showcommentList(nodes)
			}
		});
	}
	
	/* 업무 상세 페이지 - 첨부 파일 리스트 조회 */
	function attachListView(){
		var wk_id = $("#wk_id").val();
		
		$.ajax({
			type : "POST",
			url : "./attachlist.do",
			data : "wk_id="+wk_id,
			async : false,
			success : function(msg){
				showattachList(msg)
			}
		})
	}
	
	/* 업무 상세 페이지 - 첨부파일 리스트 출력 기능 */
	function showattachList(nodes){
		$("#wk_Attach_List").children("p").remove();
		if(nodes.length == 0){
			$("#wk_Attach_List").append("<p>첨부파일이 없습니다</p>");
		} else {
			for(var i = 0; i < nodes.length; i++){
				var gatt_seq = nodes[i].gatt_seq;
				var gatt_name = nodes[i].gatt_name;
				var gatt_size = nodes[i].gatt_size;
				$("#wk_Attach_List").append("<p><a href='./gbfileDown.do?gatt_seq="+gatt_seq+"' class='wd_file_list'>"+gatt_name+"</a>&nbsp;<img alt='파일삭제' src='images/project/wd_delete_btn.png' onclick='attachDelete(\""+gatt_seq+"\")'/></p>");
			}
		}
	}
	
	function file_insert(val){
		wkAttach_Insert(val);
	}
	
	/* 업무 상세 페이지 - 첨부파일 추가 */
	function wkAttach_Insert(val){
		var wk_id = $("#wk_id").val();
		$(val).siblings("input[name=wk_id]").eq(0).val(wk_id);
		
		var formData = new FormData(document.getElementById("file_attach_form"));
		
		$("#file_attach_form").children("input[type=hidden]").val("").end().children("input[type=file]").val("");
		
		$.ajax({
			type : "POST",
			url : "./attachInsert.do",
			data : formData,
			processData : false,
			contentType : false,
			async : false,
			success : function(msg){
				showattachList(msg)
			}
		});
		
	}
	
	/* 첨부파일 삭제 기능 */
	function attachDelete(val){
		var wk_id = $("#wk_id").val();
		$.ajax({
			type : "POST",
			url : "./attachdelete.do",
			data : "gatt_seq="+val+"&wk_id="+wk_id,
			async : false,
			success : function(msg){
				showattachList(msg)
			}
		})
	}