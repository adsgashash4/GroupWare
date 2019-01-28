//package com.pm.rc.control;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import javax.servlet.http.HttpSession;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.pm.rc.dto.GroupDto;
//import com.pm.rc.dto.PagingProDto;
//import com.pm.rc.dto.MemberDto;
//import com.pm.rc.dto.SystemBoardDto;
//import com.pm.rc.model.service.ManagerService;
//
//@RestController
//public class Ws5_sysManagerController {
//
//	@Autowired
//	private ManagerService service;
//
//	Logger logger = LoggerFactory.getLogger(sysManagerController.class);
//
//	//시스템관리자 로그인
//	@RequestMapping(value="/adminLogin.do", method=RequestMethod.POST)
//	public Map<String, Boolean> adminLogin(@RequestBody(required = false) Map param){
//		logger.info("시스템 관리자 로그인");
//		String sParam1 =(String)param.get("mem_id");
//		String sParam2 =(String)param.get("mem_pw");
//		Boolean isc = false;
//		
//		Map<String, String> pMap = new HashMap<String, String>();
//		pMap.put("mem_id", sParam1);
//		pMap.put("mem_pw", sParam2);
//		
////		isc = service.adminLogin(pMap);
//		
//		Map<String, Boolean> map = new HashMap<String, Boolean>();
//		map.put("result", isc);
//		
//		return map;
//	}
//	
//	//그룹 생성 신청 리스트 출력함
//	@RequestMapping(value="/grApply.do")
//	public Map<String, List<GroupDto>> groupApply(@RequestBody(required = false) Map param){	//param: gr_name
//		logger.info("그룹생성신청리스트출력시작");
//		Map<String, List<GroupDto>> map = new HashMap<String, List<GroupDto>>();
//		List<GroupDto> lists = new ArrayList<GroupDto>() ;
//		String sParam = null;
//		sParam = (String) param.get("gr_name");
//		if(sParam == null){
//			lists = service.grApplySelect(null);
//		}else{
////			lists = service.grApplySelect(sParam);
//		}
//		map.put("result", lists);
//		return map;
//	}
//
//	//그룹승인 리스트 검색하여 출력
///*	@RequestMapping(value="/grApplySch.do")
//	public String groupApply(Model model ,String gr_name){//맵
//		logger.info("그룹생성신청리스트출력시작");
//		List<GroupDto> lists = service.grApplySelect(gr_name);
//		model.addAttribute("Apply",lists);
//		return "Group/grApply";
//	}*/
//
//	//그룹승인
//	@RequestMapping(value="/grApplyYse.do" , method=RequestMethod.POST)
//	public Map<String, Boolean> grApplyYse(@RequestBody(required = false) Map param){	//gr_id 배열
//		logger.info("그룹생성승인시작");
//		Boolean isc = false;
//		String[] sParam = null;
//		Map<String, Boolean> map = new HashMap<String, Boolean>();
//		sParam = (String[])param.get("gr__id");
//		isc = service.grAppModify(sParam);
//		map.put("result", isc);
//		return map;
//	}
//	
//	//그룹거절
//	@RequestMapping(value="/grApplyNo.do" , method=RequestMethod.POST)
//	public Map<String, Boolean> grApplyNo(@RequestBody(required = false) Map param){	//gr_id 배열
//		logger.info("그룹승인거절시작");
//		Boolean isc = false;
//		String[] sParam = null;
//		Map<String, Boolean> map = new HashMap<String, Boolean>();
//		sParam = (String[])param.get("gr__id");
//		isc = service.grDelete(sParam);
//		map.put("result", isc);
//		return map;
//	}
//
//	// 그룹 간략정보 확인하기
//	@RequestMapping(value="/groupInfoChild.do" )
//	public Map<String, List<GroupDto>> groupInfoChild(@RequestBody(required = false) Map param){	//param: gr_id
//		logger.info("그룹 간략정보 확인");
//		String sParam = null;
//		List<GroupDto> lists = new ArrayList<GroupDto>();
//		Map<String, List<GroupDto>> map = new HashMap<String, List<GroupDto>>();
//		sParam = (String)param.get("gr_id");
//		lists = service.grApplyInfoView(sParam);
//		map.put("result", lists);
//
//		return map;
//	}
//
//	// 공지 게시판 관리 페이지 이동
//	@RequestMapping(value="/sysNoticeMgr.do", method={RequestMethod.GET,RequestMethod.POST})
//	public Map<String, Object> sysBoardManager(@RequestBody(required = false) Map param){
//		logger.info("공지 게시판 관리 페이지 이동");
//		SystemBoardDto dto = new SystemBoardDto();
//		String sParam1 = (String)param.get("index");
//		String sParam2 = (String)param.get("pageStartNum");
//		String sParam3 = (String)param.get("listCnt");
//		PagingProDto pgDto = new PagingProDto(sParam1, sParam2, sParam3);
//		
//		logger.info("========================= 관리자 - 공지게시판 관리 페이지 이동 ===============================");
//		
//		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//
//		dto.setPaging(pgDto);
//		list = service.noticeListSelect(dto);
//		pgDto.setTotal(service.noticeListSelectCount(dto));
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		/*list = service.noticeListSelect(dto);
//		pgDto.setTotal(service.noticeListSelectCount(dto));
//		*/
//		map.put("result1", list);
//		map.put("result2", pgDto);
//
//		return map;
//	}
//	
//	// 공지 게시판 게시글 검색
//	@RequestMapping(value="/sysNoticeSearch.do", method=RequestMethod.POST)
//	public Map<String, Object> sysNoticeSearch(@RequestBody(required = false) Map<String, Object> reqParam){
//		logger.info("공지 게시판 관리 페이지 이동");
//		Map<String, Object> param = (Map<String, Object>)reqParam.get("dc_noticePostParam");
//		String sParam1= (String)param.get("sbr_title");
//		String sParam2 = (String)param.get("index");
//		String sParam3 = (String)param.get("pageStartNum");
//		String sParam4 = (String)param.get("listCnt");
//		
//		logger.info("=================== 공지 게시판 게시글 검색 =====================");
//		logger.info("검색할 게시글 제목 : "+sParam1);
//		logger.info("=========================================================");
//		
//		SystemBoardDto sbDto = new SystemBoardDto();
//		sbDto.setSbr_title(sParam1);
//		
//		PagingProDto pgDto = new PagingProDto(sParam2, sParam3, sParam4);
//		
//		sbDto.setPaging(pgDto);
//		
//		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//		list = service.noticeListSelect(sbDto);
//		
//		pgDto.setTotal(service.noticeListSelectCount(sbDto));
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("result1", list);
//		map.put("result2", pgDto);
//		
//		return map;
//	}
//
//	// 공지 게시글 작성페이지 이동(?)
//	@RequestMapping(value="/noticeWriteForm.do", method=RequestMethod.GET)
//	public String NoticeWriteForm(){
//		logger.info("==================== 공지 게시글 작성 폼으로 이동 =====================");
//		return "sysManage/sysNoticeWrite";
//	}
//
//	// 공지 게시글 작성(질문)
//	@RequestMapping(value="/noticeWrite.do", method=RequestMethod.POST)
//	public Map<String, Boolean> NoticeWriete(@RequestBody(required = false) Map param){
//		String sParam1 = (String)param.get("sbr_title");
//		String sParam2 = (String)param.get("sbr_content");
//		String sParam3 = (String)param.get("mem_id");
//
//		//UUID 생성 메소드
//		String uuid = createUUID();
//		int indexnum = uuid.lastIndexOf("-");
//		String sbr_uuid = uuid.substring(indexnum+1);
//
//		logger.info("===================== 공지 게시글 작성 =======================");
//		logger.info("작성할 게시글 uuid : "+sbr_uuid);
//		logger.info("게시글 제목 : "+sParam1);
//		logger.info("관리자 아이디 : "+sParam3);
//		logger.info("게시글 내용 : "+sParam2);
//		logger.info("========================================================");
//
//		Map<String, Boolean> map = new HashMap<String, Boolean>();
//		
//		Map<String, String> sMap = new HashMap<String, String>();
//		sMap.put("sbr_uuid", sbr_uuid);
//
//	//	MultipartFile file = multipartRequest.getFile("satt_name");
//		String sParam4 = (String)param.get("satt_name");
//		String sParam5 = (String)param.get("satt_size");
//
//		if (sParam4 != null){
//			String savePath = "C:\\RC_fileSave\\";
//
//			String fuuid = createUUID();
//			int indexNum = fuuid.lastIndexOf("-");
//
//			String oldFileName = sParam4;
//			String satt_size =sParam5;
//
//			String newFileName = fuuid.substring(indexNum+1) + oldFileName;
//
//			// 첨부파일 실제경로 저장
///*			try {
//				file.transferTo(new File(savePath + newFileName));
//			} catch (IllegalStateException | IOException e) {
//				e.printStackTrace();
//			}*/
//
//			logger.info("=============== 공지게시판 첨부파일 추가 ===================");
//			logger.info("첨부파일 명 : "+newFileName);
//			logger.info("첨부파일 크기 : "+sParam5);
//			logger.info("첨부파일 경로 : "+savePath);
//			logger.info("첨부파일 실제이름 : "+sParam4);
//			logger.info("====================================================");
//
//			sMap.put("satt_name", oldFileName);
//			sMap.put("satt_rname", newFileName);
//			sMap.put("satt_size", satt_size);
//			sMap.put("satt_path", savePath);
//
//		}
//
//		sMap.put("sbr_title", sParam1);
//		sMap.put("sbr_content", sParam2);
//		sMap.put("mem_id", sParam3);
//
//		boolean isc = false;
//		isc = service.noticeInsert(sMap);
//		
//		map.put("result", isc);
//
//		if(isc){
//			System.out.println("공지게시판 게시글 등록 완료");
//		} else {
//			System.out.println("공지게시판 게시글 등록 실패");
//		}
//
//		return map;
//	}
//
//	// 공지 게시글 수정 폼 이동
//	@RequestMapping(value="/sysBoardEditMove.do", method=RequestMethod.GET)
//	public Map<String, Map<String, String>> noticeEditForm(@RequestBody(required = false) Map param){
//		String sParam = (String)param.get("sbr_uuid");
//		
//		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
//		
//		Map<String, String> pMap1 = new HashMap<String, String>();
//		pMap1.put("sbr_uuid", sParam);
//
//		Map<String, String> pMap2 = new HashMap<String, String>();
//		pMap2 = service.editBoardViewSelect(pMap1);
//		map.put("result1", pMap2);
//
//		Map<String, String> pMap3 = new HashMap<String, String>();
//		pMap3 = service.sysAttachSelect(pMap1);
//		map.put("result2", pMap3);
//
//		return map;
//	}
//
//
//	// 공지 게시글 수정(질문)
//	@RequestMapping(value="/sysboardEdit.do", method=RequestMethod.POST)
//	public Map<String, Boolean> noticeEdit(@RequestBody(required = false) Map param){
//		String sParam1 = (String)param.get("sbr_uuid");
//		String sParam2 = (String)param.get("sbr_title");
//		String sParam3 = (String)param.get("sbr_content");
//
//		logger.info("=============== 공지 게시판 : 게시글 수정 =========================");
//		logger.info("수정할 게시글 아이디 : "+sParam1);
//		logger.info("수정할 게시글 제목 : "+sParam2);
//		logger.info("수정할 게시글 내용 : "+sParam3);
//		logger.info("===========================================================");
//
//		Map<String, Boolean> map = new HashMap<String, Boolean>();
//		
//		Map<String, String> sMap = new HashMap<String, String>();
//		sMap.put("sbr_uuid", sParam1);
//
//		String sParam4 = (String)param.get("satt_name");
//		String sParam5 = (String)param.get("satt_size");
//
//		logger.info(sParam4);
//
//		if (!sParam4.equals("")){
//			String savePath = "C:\\RC_fileSave\\";
//
//			// 기존 파일 삭제
//			Map<String, String> originFile = new HashMap<String, String>();
//			originFile = service.sysAttachSelect(sMap);
//
//		//	File delFile = new File(originFile.get("SATT_PATH")+originFile.get("SATT_RNAME"));
//		//	delFile.delete();
//
//			// 새 파일 등록
//			String fuuid = createUUID();
//			int indexNum = fuuid.lastIndexOf("-");
//
//			String oldFileName = sParam4;
//			String satt_size = ""+sParam5;
//
//			String newFileName = fuuid.substring(indexNum+1) + oldFileName;
//
//			// 첨부파일 실제경로 저장
///*			try {
//				file.transferTo(new File(savePath + newFileName));
//			} catch (IllegalStateException | IOException e) {
//				e.printStackTrace();
//			}*/
//
//			logger.info("=============== 공지게시판 첨부파일 추가 ===================");
//			logger.info("첨부파일 명 : "+oldFileName);
//			logger.info("첨부파일 크기 : "+satt_size);
//			logger.info("첨부파일 경로 : "+savePath);
//			logger.info("첨부파일 실제이름 : "+newFileName);
//			logger.info("====================================================");
//
//			sMap.put("satt_name", oldFileName);
//			sMap.put("satt_rname", newFileName);
//			sMap.put("satt_size", satt_size);
//			sMap.put("satt_path", savePath);
//
//		}
//
//		sMap.put("sbr_title", sParam2);
//		sMap.put("sbr_content", sParam3);
//
//		boolean isc = false;
//
//		isc = service.noticeModify(sMap);
//		
//		map.put("result", isc);
//
//		if(isc == true){
//			System.out.println("공지게시판 게시글 수정 성공");
//		} else {
//			System.out.println("공지게시판 게시글 수정 실패");
//		}
//
//		return map;
//	}
//
//	// 공지 게시글 보기
//	@RequestMapping(value="/viewNotice.do", method=RequestMethod.GET)
//	public Map<String, Map<String, String>> viewNotice(@RequestBody(required = false) Map param){
//
//		String sbr_uuid = (String)param.get("sbr_uuid");
//		
//		logger.info("================== 공지 게시글 보기 ==================");
//		logger.info("조회할 게시글 번호 : "+sbr_uuid);
//		logger.info("================================================");
//		
//		Map<String, String> uuid = new HashMap<String, String>();
//		uuid.put("sbr_uuid", sbr_uuid);
//		Map<String, String> view = new HashMap<String, String>();
//		view = service.sysBoardViewSelect(uuid);
//		Map<String, String> attach = new HashMap<String, String>();
//		attach = service.sysAttachSelect(uuid);
//
//		Map<String, Map<String, String>> map = new HashMap<String, Map<String,String>>();
//		map.put("result1", view);
//
//		if(attach != null){
//			map.put("result2", attach);
//		}
//
//		return map;
//	}
//
//	// 게시글 삭제
//	@RequestMapping(value="/sysboardDelete.do", method=RequestMethod.GET)
//	public Map<String, Boolean> noticeDelete(@RequestBody(required = false) Map param){
//		String sbr_uuid = (String)param.get("sbr_uuid");
//		String noticeyn = (String)param.get("noticeyn");
//		Map<String, Boolean> map = new HashMap<String, Boolean>();
//		
//		Map<String, String> uuid = new HashMap<String, String>();
//		uuid.put("sbr_uuid", sbr_uuid);
//
//		Map<String, String> attach = null;
//		attach = service.sysAttachSelect(uuid);
//
//		if(attach != null){
//			String savePath = ""+attach.get("SATT_PATH");
//			String fileName = ""+attach.get("SATT_RNAME");
//
//			File file = new File(savePath+fileName);
//
//			file.delete();
//		}
//
//		boolean isc = false;
//		isc = service.sysBoardDelete(sbr_uuid);
//
//		if(isc == true){
//			System.out.println("문의 게시글 삭제 성공");
//		} else {
//			System.out.println("문의 게시글 삭제 실패");
//		}
//		
//		map.put("result", isc);
//		return map;
//		
///*		if(noticeyn.equals("Y")){
//			return "redirect:/sysNoticeMgr.do";
//		} else {
//			return "redirect:/sysQnaMgr.do";
//		}*/
//
//	}
//
//	// 문의 게시판 관리 페이지 이동
//	@RequestMapping(value="/sysQnaMgr.do", method={RequestMethod.GET,RequestMethod.POST})
//	public Map<String, Object> sysQnaManager(@RequestBody(required = false) Map<String, Object> reqParam){
//		Map<String, Object> param = (Map<String, Object>) reqParam.get("dc_qnaSearchParam");
//		String sParam1 = (String)param.get("index");
//		String sParam2 = (String)param.get("pageStartNum");
//		String sParam3 = (String)param.get("listCnt");
//		
//		System.out.println(param);
//		PagingProDto maPaging = new PagingProDto(sParam1, sParam2, sParam3);
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		
//		logger.info("====================== 문의 게시판 관리 페이지 이동 ========================");
//
//		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//		SystemBoardDto sbDto = new SystemBoardDto();
//		sbDto.setPaging(maPaging);
//		
//		list = service.qnaListSelect(sbDto);
//		maPaging.setTotal(service.qnaListSelectCount(sbDto));
//		
//		System.out.println("관리자문의게시판:"+maPaging.getPageCnt());
//		
//		map.put("result1", list);
//		map.put("result2", maPaging);
//
//		return map;
//	}
//	
//	// 문의 게시판 게시글 검색 리스트 조회
//	@RequestMapping(value="/sysQnaSearch.do", method=RequestMethod.POST)
//	public Map<String, Object> sysQnaSearch(@RequestBody(required = false) Map<String, Object> reqparam){
//		Map<String, Object> param = (Map<String, Object>)reqparam.get("dc_qnaSearchParam");
//		String sParam1 = (String)param.get("SBR_TITLE");
//		String sParam2 = (String)param.get("index");
//		String sParam3 = (String)param.get("pageStartNum");
//		String sParam4 = (String)param.get("listCnt");
//		
//		PagingProDto pgDto = new PagingProDto(sParam2, sParam3, sParam4);
//		
//		logger.info("==================== 문의 게시판 게시글 제목 검색 ======================");
//		logger.info("검색할 게시글 제목 : "+sParam1);
//		logger.info("==============================================================");
//
//		 Map<String, Object> map = new HashMap<String, Object>();
//		
//		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//		
//		SystemBoardDto sbDto = new SystemBoardDto();
//		sbDto.setPaging(pgDto);
//		sbDto.setSbr_title(sParam1);
//		
//		
//		list = service.qnaListSelect(sbDto);	//sbDto 만들어야함
//		pgDto.setTotal(service.qnaListSelectCount(sbDto));
//		
//		map.put("result1", list);
//		map.put("result2", pgDto);
//		
//		return map;
//	}
//	
//	// 문의 게시판 게시글 조회
//	@RequestMapping(value="/viewQna.do", method=RequestMethod.GET)
//	public Map<String, Map<String, String>> sysQnaView(@RequestBody(required = false) Map<String, Object> reqParam){
//		Map<String, Object> param = (Map<String, Object>)reqParam.get("dc_qnaViewParam");
//		String sParam = (String)param.get("sbr_uuid");
//		
//		logger.info("================== 문의 게시글 보기 ==================");
//		logger.info("조회할 게시글 번호 : "+sParam);
//		logger.info("================================================");
//
//		Map<String, Map<String, String>> map = new HashMap<String, Map<String,String>>();
//		
//		Map<String, String> uuid = new HashMap<String, String>();
//		uuid.put("sbr_uuid", sParam);
//
//		Map<String, String> view = new HashMap<String, String>();
//		view = service.sysBoardViewSelect(uuid);
//		Map<String, String> attach = new HashMap<String, String>();
//		attach = service.sysAttachSelect(uuid);
//
//		map.put("result1", view);
//
//		if(attach != null){
//			map.put("result2", attach);
//		}
//		
//		return map;
//	}
//	
//	// 답글 작성 폼으로 연결하기
//	@RequestMapping(value="/writeReply.do", method=RequestMethod.GET)
//	public Map<String, String> moveWriteRepleForm(@RequestBody(required = false) Map param){
//		
//		String sParam = (String)param.get("sbr_uuid"); 
//		
//		logger.info("================== 문의 게시판 답글 작성 폼 ==================");
//		logger.info("답글 작성할 게시글 uuid : "+sParam);
//		logger.info("=====================================================");
//		
//		Map<String, String> map = new HashMap<String, String>();
//		
//		map.put("result", sParam);
//		
//		return map;
//	}
//	
//	// 문의 게시판 답글 작성 처리(질문)
//	@RequestMapping(value="/qnaRepleWrite.do", method=RequestMethod.POST)
//	public String WriteQnaReple(@RequestBody(required = false) Map param){
//		
//		String sParam1 = (String)param.get("Rsbr_uuid");
//		String sParam2 = (String)param.get("sbr_title");
//		String sParam3 = (String)param.get("mem_id");
//		String sParam4 = (String)param.get("sbr_content");
//		
//		//UUID 생성 메소드
//		String uuid = createUUID();
//		int indexnum = uuid.lastIndexOf("-");
//		String sbr_uuid = uuid.substring(indexnum+1);
//		
//		logger.info("===================== 문의 게시판 답글 작성 =======================");
//		logger.info("작성할 게시글 uuid : "+sbr_uuid);
//		logger.info("답글 작성할 게시글 uuid : "+sParam1);
//		logger.info("게시글 제목 : "+sParam2);
//		logger.info("관리자 아이디 : "+sParam3);
//		logger.info("게시글 내용 : "+sParam4);
//		logger.info("===========================================================");
//		
//		Map<String, Boolean> map = new HashMap<String, Boolean>();
//		
//		Map<String, String> sMap = new HashMap<String, String>();
//		
//		String sParam5 = (String)param.get("satt-_name");
//		String sParam6 = (String)param.get("satt_size");
//
//		if (sParam4 != null){
//			String savePath = "C:\\RC_fileSave\\";
//
//			String fuuid = createUUID();
//			int indexNum = fuuid.lastIndexOf("-");
//
//			String oldFileName = sParam5;
//			String satt_size = ""+sParam6;
//
//			String newFileName = fuuid.substring(indexNum+1) + oldFileName;
//
//			// 첨부파일 실제경로 저장
///*			try {
//				file.transferTo(new File(savePath + newFileName));
//			} catch (IllegalStateException | IOException e) {
//				e.printStackTrace();
//			}*/
//
//			logger.info("=============== 공지게시판 첨부파일 추가 ===================");
//			logger.info("첨부파일 명 : "+oldFileName);
//			logger.info("첨부파일 크기 : "+satt_size);
//			logger.info("첨부파일 경로 : "+savePath);
//			logger.info("첨부파일 실제이름 : "+newFileName);
//			logger.info("====================================================");
//
//			sMap.put("satt_name", oldFileName);
//			sMap.put("satt_rname", newFileName);
//			sMap.put("satt_size", satt_size);
//			sMap.put("satt_path", savePath);
//
//		}
//		
//		sMap.put("sbr_uuid", sbr_uuid);
//		sMap.put("sbr_title", sParam2);
//		sMap.put("Rsbr_uuid", sParam1);
//		sMap.put("sbr_content", sParam4);
//		sMap.put("mem_id", sParam3);
//		
//		boolean isc = false;
//		
//		isc = service.qnaReplyInsert(sMap);
//		map.put("result", isc);
//		
//		if(isc){
//			System.out.println("문의 게시판 답글 작성 성공");
//		} else {
//			System.out.println("문의 게시판 답글 작성 실패");
//		}
//		
//		return "redirect:/sysQnaMgr.do";
//	}
//	
//	// 게시글 선택 삭제
//	@RequestMapping(value="/select_PostDelete.do", method=RequestMethod.POST)
//	public Map<String, Boolean> select_PostDelete(@RequestBody(required = false) Map param){
//		
//		String[] sParam = (String[])param.get("numList");
//		Map<String, Boolean> map = new HashMap<String, Boolean>();
//		
//		logger.info("=================== 게시글 선택 삭제 ========================");
//		logger.info(Arrays.toString(sParam));
//		logger.info("=======================================================");
//		
//		
//		for(int i = 0; i < sParam.length; i++){
//			
//			String sbr_uuid = sParam[i];
//			
//			System.out.println(sbr_uuid);
//			
//			Map<String, String> uuid = new HashMap<String, String>();
//			uuid.put("sbr_uuid", sbr_uuid);
//
//			Map<String, String> attach = null;
//			attach = service.sysAttachSelect(uuid);
//
//			if(attach != null){
//				String savePath = ""+attach.get("SATT_PATH");
//				String fileName = ""+attach.get("SATT_RNAME");
//
//				File file = new File(savePath+fileName);
//
//				file.delete();
//			}
//
//			boolean isc = false;
//			
//			isc = service.sysBoardDelete(sbr_uuid);
//			map.put("result", isc);
//
//			if(isc == true){
//				System.out.println("게시글 삭제 성공");
//			} else {
//				System.out.println("게시글 삭제 실패");
//			}
//		}
//		return map;
//	}
//	
//	// 회원 관리 페이지 이동
//	@RequestMapping(value="/sysMemMgr.do", method={RequestMethod.GET,RequestMethod.POST})
//	public Map<String, Object> sysMemberMgr(@RequestBody(required = false) Map param){
//		
//		logger.info("========================= 회원 관리 페이지 이동 =======================");
//		
//		String sParam1 = (String)param.get("index");
//		String sParam2 = (String)param.get("pageStartNum");
//		String sParam3 = (String)param.get("listCnt");
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		
//		PagingProDto maPaging = new PagingProDto(sParam1, sParam2, sParam3);
//		List<MemberDto> list = new ArrayList<MemberDto>();
//		
//		list = service.allMemberSelect(maPaging);
//		maPaging.setTotal(service.allMemberSelectCount());
//		
//		map.put("result1", list);
//		map.put("result2", maPaging);
//		
//		return map;
//	}
//	
//	// 회원 검색
//	@RequestMapping(value="/sysMemSearch.do", method=RequestMethod.POST)
//	public Map<String, List<MemberDto>> sysMemberSearch(@RequestBody(required = false) Map param){
//		
//		Map<String, List<MemberDto>> map = new HashMap<String, List<MemberDto>>();
//		String sParam1 = (String)param.get("mem_name");
//		
//		logger.info("===================== 회원 검색 ========================");
//		logger.info("검색할 내용 : "+sParam1);
//		logger.info("====================================================");
//		
//		Map<String, String> sParam2 = new HashMap<String, String>();
//		sParam2.put("mem_id", sParam1);
//		
//		List<MemberDto> list = new ArrayList<MemberDto>();
//		
//		list = service.allMemberSelectSearch(sParam2);
//		
//		map.put("result", list);
//		
//		return map;
//	}
//	
//	//회원정보조회
//	@RequestMapping(value = "/sysMemView.do", method = RequestMethod.GET)
//	public Map<String, MemberDto> sysMemView(@RequestBody(required = false) Map param){
//		logger.info("===================== 관리자 회원정보 조회 ============================");
//		String sParam = (String)param.get("mem_id");
//		Map<String, MemberDto> map = new HashMap<String, MemberDto>();
//		MemberDto dto = new MemberDto();
//		dto = service.sysMemView(sParam);
//		map.put("info", dto);
//		System.out.println(map);
//		return map;
//	}
//	
//	//회원정보 수정
//	@RequestMapping(value = "/sysMemModify.do", method = RequestMethod.POST)
//	public Map<String, Boolean> sysMemModify(@RequestBody(required = false) Map param){
//		logger.info("===================== 관리자 회원정보 수정 ============================");
//		
//		Map<String, Boolean> map  = new HashMap<String, Boolean>();
//		
//		MemberDto sParam = new MemberDto();
//		sParam = (MemberDto)param.get("dto");
//		boolean isc = false;
//		isc = service.sysMemModify(sParam);
//		map.put("result", isc);
//	
//		return map;
//	}
//	
//	// 관리자 로그아웃(질문)
//	@RequestMapping(value="/adminLogout.do", method=RequestMethod.GET)
//	public String adminLogout(HttpSession session){
//		
//		logger.info("===================== 관리자 로그아웃 ============================");
//		
//		session.removeAttribute("mem_id");
//		session.removeAttribute("mem_name");
//		
//		return "redirect:/main.do";
//	}
//
//	
//
//	// UUID 생성 메소드
//	public String createUUID(){
//		return UUID.randomUUID().toString();
//	}
//
//}
