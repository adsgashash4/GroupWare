/* 업무 수정 폼 채우기  */
	function workEdit(val, val2, val3, val4, event){
		$(".workInsert_Form").css("display", "none");
		$(".work_Edit_Form").css("display", "block");
		$("#edit_wk_id").val(val);
		$("#edit_wk_title").val(val2);
		$("#edit_wk_endDate").val(val3);
		$("#edit_mem_id").val(val4);
		
		$("#promem_edit_table").children("tbody").children().remove();
		var pr_id = $("#pr_id").val();
		$.ajax({
			type : "POST",
			url : "./searchmem.do",
			data : "pr_id="+pr_id,
			async : false,
			success : function(nodes){
				if(nodes.length == 0){
					$("#promem_edit_table").children("tbody")
					.append("<tr><td colspan='2'>조회 결과가 없습니다</td></tr>")
				} else {
					for(var i = 0; i < nodes.length; i++){
						var mem_name = nodes[i].MEM_NAME;
						var mem_id = nodes[i].MEM_ID;
						$("#promem_edit_table").children("tbody")
						.append("<tr><td><span>"+mem_name+"</span></td>" +
								"<td><input type='button' value='선택' class='body_btn workInsert_mem_btn' onclick='trans_Memid_edit(\""+mem_id+"\")'/></td></tr>");
					}
				}
			}
		});
		
		
		$(".work_Edit_Form").dialog({
			modal : true,
			title : "업무 수정",
			width : 650,
			height : 350,
			position : {my : "center", at : "center"},
			resizable : false
		});
		
		event.stopPropagation();
	}
	
	/* 멤버 아이디 업무 수정 폼에 전송하기 */
	function trans_Memid_edit(val){
		$("#edit_mem_id").val(val);
	}
	
	/* 멤버 아이디 업무 추가 폼에 전송하기 */
	function trans_Memid(val){
		$("#insert_mem_id").val(val);
	}
	
	/* 업무 추가 폼 표시 */
	function workInsert(){
		$("#work_reset").click();
		document.getElementById("new_workEnddate").valueAsDate = new Date();
		$(".work_Edit_Form").css("display", "none");
		$(".workInsert_Form").css("display", "block");
		$("#promem_insert_table").children("tbody").children().remove();
		var pr_id = $("#pr_id").val();
		$.ajax({
			type : "POST",
			url : "./searchmem.do",
			data : "pr_id="+pr_id,
			async : false,
			success : function(nodes){
				if(nodes.length == 0){
					$("#promem_insert_table").children("tbody")
					.append("<tr><td colspan='2'>조회 결과가 없습니다</td></tr>")
				} else {
					for(var i = 0; i < nodes.length; i++){
						var mem_name = nodes[i].MEM_NAME;
						var mem_id = nodes[i].MEM_ID;
						$("#promem_insert_table").children("tbody")
						.append("<tr><td><span>"+mem_name+"</span></td>" +
								"<td><input type='button' value='선택' class='body_btn workInsert_mem_btn' onclick='trans_Memid(\""+mem_id+"\")'/></td></tr>");
					}
				}
			}
		});
		$(".workInsert_Form").dialog({
			title : "업무 추가",
			height : 350,
			width : 650,
			position : {my : "center", at : "center"},
			resizable : false,
			modal : true,
		});
	}
	
	function wk_title_length(val){
		var value = $(val).val();
		if(value.length >= 25){
			alert("25자 이상 작성할 수 없습니다!");
			$(val).val(value.substring(0, 24));
		}
	}
	
	/* 업무 삭제 기능 */
	function workDelete(val, event){
		var pr_id = $("#pr_id").val();
		location.href="./workDelete.do?pr_id="+pr_id+"&wk_id="+val;
		event.stopPropagation();
	}