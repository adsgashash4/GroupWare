package com.pm.rc.control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pm.rc.dto.GroupDto;
import com.pm.rc.dto.MemberDto;
import com.pm.rc.dto.PagingProDto;
import com.pm.rc.dto.SystemBoardDto;
import com.pm.rc.model.service.ManagerService;

@Controller
public class sysManagerController {
		
	@Autowired
	private ManagerService service;

	Logger logger = LoggerFactory.getLogger(sysManagerController.class);
	
	// 관리자 로그인 페이지 이동
	@RequestMapping(value="/adminLogin.do")
	public String goAdminLogin(){
		logger.info("================= 관리자 로그인 페이지 이동 ====================");
		return "sysManage/sysMgrLogin";
	}
	
	//그룹 생성 신청 리스트 출력함
	@RequestMapping(value="/grApply.do")
	public String groupApply(Model model){
		logger.info("그룹생성신청리스트출력시작");
		List<GroupDto> lists = service.grApplySelect(null);
		model.addAttribute("Apply",lists);
		return "sysManage/sysGrApplyMgr";
	}
	
	//그룹승인 리스트 검색하여 출력
		@RequestMapping(value="/grApplySch.do")
		public String groupApply(Model model ,String gr_name){//맵
			logger.info("그룹생성신청리스트출력시작");
			List<GroupDto> lists = service.grApplySelect(gr_name);
			model.addAttribute("Apply",lists);
			return "Group/sysGrApplyMgr";
		}

		//그룹승인
		@RequestMapping(value="/grApplyYse.do" , method=RequestMethod.POST)
		public String grApplyYse(String[] gr_id){	//
			logger.info("그룹생성승인시작");
			service.grAppModify(gr_id);
			return "redirect:/grApply.do";
		}
		//그룹거절
		@RequestMapping(value="/grApplyNo.do" , method=RequestMethod.POST)
		public String grApplyNo(String[] gr_id){
			logger.info("그룹승인거절시작");
			service.grDelete(gr_id);
			return "redirect:/grApply.do";
		}

		// 그룹 간략정보 확인하기
		@RequestMapping(value="/groupInfoChild.do" )
		public String groupInfoChild(String gr_id , Model model){
			List<GroupDto> lists = service.grApplySelect(gr_id);
			model.addAttribute("info" , lists);

			return "Group/grCreateInformation";
		}
		
		@RequestMapping(value="/groupInfo.do", method=RequestMethod.POST)
		@ResponseBody
		public List<GroupDto> groupSelectInfoChild (String gr_id){
			List<GroupDto> lists = service.grApplyInfoView(gr_id);
			return lists;
		}

		// 공지 게시판 관리 페이지 이동
		@RequestMapping(value="/sysNoticeMgr.do", method={RequestMethod.GET,RequestMethod.POST})
		public String sysBoardManager(Model model, HttpServletRequest req){
			PagingProDto pgDto = new PagingProDto(req.getParameter("index"), req.getParameter("pageStartNum"), req.getParameter("listCnt"));
			
			String sbr_title = req.getParameter("sbr_title");
			
			SystemBoardDto sDto = new SystemBoardDto();
			sDto.setPaging(pgDto);
			sDto.setSbr_title(sbr_title);
			
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();

			list = service.noticeListSelect(sDto);
			pgDto.setTotal(service.noticeListSelectCount(sDto));

			model.addAttribute("list", list);
			model.addAttribute("paging",pgDto);
			model.addAttribute("sbr_title", sbr_title);

			return "sysManage/sysNoticeMgr";
		}
		
		// 공지 게시글 작성페이지 이동
		@RequestMapping(value="/noticeWriteForm.do", method=RequestMethod.GET)
		public String NoticeWriteForm(){

			logger.info("==================== 공지 게시글 작성 폼으로 이동 =====================");

			return "sysManage/sysNoticeWrite";
		}

		// 공지 게시글 작성
		@RequestMapping(value="/noticeWrite.do", method=RequestMethod.POST)
		public String NoticeWriete(MultipartHttpServletRequest multipartRequest){
			String sbr_title = multipartRequest.getParameter("sbr_title");
			String sbr_content = multipartRequest.getParameter("sbr_content");
			String mem_id = multipartRequest.getParameter("mem_id");

			//UUID 생성 메소드
			String uuid = createUUID();
			int indexnum = uuid.lastIndexOf("-");
			String sbr_uuid = uuid.substring(indexnum+1);

			logger.info("===================== 공지 게시글 작성 =======================");
			logger.info("작성할 게시글 uuid : "+sbr_uuid);
			logger.info("게시글 제목 : "+sbr_title);
			logger.info("관리자 아이디 : "+mem_id);
			logger.info("게시글 내용 : "+sbr_content);
			logger.info("========================================================");

			Map<String, String> map = new HashMap<String, String>();
			map.put("sbr_uuid", sbr_uuid);

			MultipartFile file = multipartRequest.getFile("satt_name");
			
			if (file != null){
				String oldFileName = file.getOriginalFilename();
				
				if (oldFileName.length() > 0){
					String savePath = "C:\\RC_fileSave\\";

					String fuuid = createUUID();
					int indexNum = fuuid.lastIndexOf("-");

					String satt_size = ""+file.getSize();

					String newFileName = fuuid.substring(indexNum+1) + oldFileName;

					// 첨부파일 실제경로 저장
					try {
						file.transferTo(new File(savePath + newFileName));
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
					}

					logger.info("=============== 공지게시판 첨부파일 추가 ===================");
					logger.info("첨부파일 명 : "+oldFileName);
					logger.info("첨부파일 크기 : "+satt_size);
					logger.info("첨부파일 경로 : "+savePath);
					logger.info("첨부파일 실제이름 : "+newFileName);
					logger.info("====================================================");

					map.put("satt_name", oldFileName);
					map.put("satt_rname", newFileName);
					map.put("satt_size", satt_size);
					map.put("satt_path", savePath);

				}
			}

			map.put("sbr_title", sbr_title);
			map.put("sbr_content", sbr_content);
			map.put("mem_id", mem_id);

			boolean isc = false;
			isc = service.noticeInsert(map);

			if(isc){
				System.out.println("공지게시판 게시글 등록 완료");
			} else {
				System.out.println("공지게시판 게시글 등록 실패");
			}

			return "redirect:/sysNoticeMgr.do";
		}

		// 공지 게시글 수정 폼 이동
		@RequestMapping(value="/sysBoardEditMove.do", method=RequestMethod.GET)
		public String noticeEditForm(HttpServletRequest request, Model model){
			String sbr_uuid = request.getParameter("sbr_uuid");

			Map<String, String> map = new HashMap<String, String>();
			map.put("sbr_uuid", sbr_uuid);

			Map<String, String> view = new HashMap<String, String>();
			view = service.editBoardViewSelect(map);

			Map<String, String> attach = null;
			attach = service.sysAttachSelect(map);

			model.addAttribute("view", view);
			model.addAttribute("attach", attach);

			return "sysManage/sysBoardEdit";
		}


		// 공지 게시글 수정
		@RequestMapping(value="/sysboardEdit.do", method=RequestMethod.POST)
		public String noticeEdit(MultipartHttpServletRequest multipartRequest){
			String sbr_uuid = multipartRequest.getParameter("sbr_uuid");
			String sbr_title = multipartRequest.getParameter("sbr_title");
			String sbr_content = multipartRequest.getParameter("sbr_content");

			logger.info("=============== 공지 게시판 : 게시글 수정 =========================");
			logger.info("수정할 게시글 아이디 : "+sbr_uuid);
			logger.info("수정할 게시글 제목 : "+sbr_title);
			logger.info("수정할 게시글 내용 : "+sbr_content);
			logger.info("===========================================================");

			Map<String, String> map = new HashMap<String, String>();
			map.put("sbr_uuid", sbr_uuid);

			MultipartFile file = multipartRequest.getFile("satt_name");

			String oldFileName = file.getOriginalFilename();
			
			if (oldFileName.length() > 0){
				String savePath = "C:\\RC_fileSave\\";

				// 기존 파일 삭제
				Map<String, String> originFile = new HashMap<String, String>();
				originFile = service.sysAttachSelect(map);
				
				if(originFile != null){
					File delFile = new File(originFile.get("SATT_PATH")+originFile.get("SATT_RNAME"));
					delFile.delete();
				} else {
					map.put("attachYN", "N");
				}

				// 새 파일 등록
				String fuuid = createUUID();
				int indexNum = fuuid.lastIndexOf("-");

				String satt_size = ""+file.getSize();

				String newFileName = fuuid.substring(indexNum+1) + oldFileName;

				// 첨부파일 실제경로 저장
				try {
					file.transferTo(new File(savePath + newFileName));
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}

				logger.info("=============== 공지게시판 첨부파일 추가 ===================");
				logger.info("첨부파일 명 : "+oldFileName);
				logger.info("첨부파일 크기 : "+satt_size);
				logger.info("첨부파일 경로 : "+savePath);
				logger.info("첨부파일 실제이름 : "+newFileName);
				logger.info("====================================================");

				map.put("satt_name", oldFileName);
				map.put("satt_rname", newFileName);
				map.put("satt_size", satt_size);
				map.put("satt_path", savePath);
				
			}

			map.put("sbr_title", sbr_title);
			map.put("sbr_content", sbr_content);

			boolean isc = false;

			isc = service.noticeModify(map);

			if(isc == true){
				System.out.println("공지게시판 게시글 수정 성공");
			} else {
				System.out.println("공지게시판 게시글 수정 실패");
			}

			return "redirect:/viewNotice.do?sbr_uuid="+sbr_uuid;
		}

		// 공지 게시글 보기
		@RequestMapping(value="/viewNotice.do", method=RequestMethod.GET)
		public String viewNotice(HttpServletRequest request, Model model){

			String sbr_uuid = request.getParameter("sbr_uuid");

			logger.info("================== 공지 게시글 보기 ==================");
			logger.info("조회할 게시글 번호 : "+sbr_uuid);
			logger.info("================================================");

			Map<String, String> uuid = new HashMap<String, String>();
			uuid.put("sbr_uuid", sbr_uuid);

			Map<String, String> view = new HashMap<String, String>();
			view = service.sysBoardViewSelect(uuid);
			Map<String, String> attach = new HashMap<String, String>();
			attach = service.sysAttachSelect(uuid);

			model.addAttribute("view", view);

			if(attach != null){
				model.addAttribute("attach", attach);
			}

			return "sysManage/sysNoticeView";
		}

		// 게시글 삭제
		@RequestMapping(value="/sysboardDelete.do", method=RequestMethod.GET)
		public String noticeDelete(HttpServletRequest request){
			String sbr_uuid = request.getParameter("sbr_uuid");
			String noticeyn = request.getParameter("noticeyn");
			
			Map<String, String> uuid = new HashMap<String, String>();
			uuid.put("sbr_uuid", sbr_uuid);

			Map<String, String> attach = null;
			attach = service.sysAttachSelect(uuid);

			if(attach != null){
				String savePath = ""+attach.get("SATT_PATH");
				String fileName = ""+attach.get("SATT_RNAME");

				File file = new File(savePath+fileName);

				file.delete();
			}

			boolean isc = false;
			isc = service.sysBoardDelete(sbr_uuid);

			if(isc == true){
				System.out.println("문의 게시글 삭제 성공");
			} else {
				System.out.println("문의 게시글 삭제 실패");
			}
			
			if(noticeyn.equals("Y")){
				return "redirect:/sysNoticeMgr.do";
			} else {
				return "redirect:/sysQnaMgr.do";
			}

		}

		// 문의 게시판 관리 페이지 이동
		@RequestMapping(value="/sysQnaMgr.do", method={RequestMethod.GET,RequestMethod.POST})
		public String sysQnaManager(HttpServletRequest req, Model model){
			PagingProDto pgDto = new PagingProDto(req.getParameter("index"), 
														  req.getParameter("pageStartNum"), req.getParameter("listCnt"));
			
			String sbr_title = req.getParameter("sbr_title");
			
			SystemBoardDto sDto = new SystemBoardDto();
			sDto.setPaging(pgDto);
			sDto.setSbr_title(sbr_title);
			
			logger.info("====================== 문의 게시판 관리 페이지 이동 ========================");

			List<Map<String, String>> list = new ArrayList<Map<String, String>>();

			list = service.qnaListSelect(sDto);
			pgDto.setTotal(service.qnaListSelectCount(sDto));

			model.addAttribute("list", list);
			model.addAttribute("paging", pgDto);
			model.addAttribute("sbr_title", sbr_title);

			return "sysManage/sysQnaMgr";
		}
		
		// 문의 게시판 게시글 조회
		@RequestMapping(value="/viewQna.do", method=RequestMethod.GET)
		public String sysQnaView(HttpServletRequest request, Model model){
			
			String sbr_uuid = request.getParameter("sbr_uuid");
			
			logger.info("================== 문의 게시글 보기 ==================");
			logger.info("조회할 게시글 번호 : "+sbr_uuid);
			logger.info("================================================");

			Map<String, String> uuid = new HashMap<String, String>();
			uuid.put("sbr_uuid", sbr_uuid);

			Map<String, String> view = new HashMap<String, String>();
			view = service.sysBoardViewSelect(uuid);
			Map<String, String> attach = new HashMap<String, String>();
			attach = service.sysAttachSelect(uuid);

			model.addAttribute("view", view);

			if(attach != null){
				model.addAttribute("attach", attach);
			}
			
			return "sysManage/sysQnaView";
		}
		
		// 답글 작성 폼으로 연결하기
		@RequestMapping(value="/writeReply.do", method=RequestMethod.GET)
		public String moveWriteRepleForm(HttpServletRequest request, Model model){
			
			String sbr_uuid = request.getParameter("sbr_uuid");
			
			logger.info("================== 문의 게시판 답글 작성 폼 ==================");
			logger.info("답글 작성할 게시글 uuid : "+sbr_uuid);
			logger.info("=====================================================");
			
			model.addAttribute("sbr_uuid", sbr_uuid);
			
			return "sysManage/sysRepleWrite";
		}
		
		// 문의 게시판 답글 작성 처리
		@RequestMapping(value="/qnaRepleWrite.do", method=RequestMethod.POST)
		public String WriteQnaReple(MultipartHttpServletRequest multiRequest){
			
			String Rsbr_uuid = multiRequest.getParameter("Rsbr_uuid");
			String sbr_title = multiRequest.getParameter("sbr_title");
			String mem_id = multiRequest.getParameter("mem_id");
			String sbr_content = multiRequest.getParameter("sbr_content");
			
			//UUID 생성 메소드
			String uuid = createUUID();
			int indexnum = uuid.lastIndexOf("-");
			String sbr_uuid = uuid.substring(indexnum+1);
			
			logger.info("===================== 문의 게시판 답글 작성 =======================");
			logger.info("작성할 게시글 uuid : "+sbr_uuid);
			logger.info("답글 작성할 게시글 uuid : "+Rsbr_uuid);
			logger.info("게시글 제목 : "+sbr_title);
			logger.info("관리자 아이디 : "+mem_id);
			logger.info("게시글 내용 : "+sbr_content);
			logger.info("===========================================================");
			
			Map<String, String> map = new HashMap<String, String>();
			
			MultipartFile file = multiRequest.getFile("satt_name");
			
			String oldFileName = file.getOriginalFilename();

			if (oldFileName.length() > 0){
				String savePath = "C:\\RC_fileSave\\";

				String fuuid = createUUID();
				int indexNum = fuuid.lastIndexOf("-");

				String satt_size = ""+file.getSize();

				String newFileName = fuuid.substring(indexNum+1) + oldFileName;

				// 첨부파일 실제경로 저장
				try {
					file.transferTo(new File(savePath + newFileName));
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}

				logger.info("=============== 공지게시판 첨부파일 추가 ===================");
				logger.info("첨부파일 명 : "+oldFileName);
				logger.info("첨부파일 크기 : "+satt_size);
				logger.info("첨부파일 경로 : "+savePath);
				logger.info("첨부파일 실제이름 : "+newFileName);
				logger.info("====================================================");

				map.put("satt_name", oldFileName);
				map.put("satt_rname", newFileName);
				map.put("satt_size", satt_size);
				map.put("satt_path", savePath);

			}
			
			map.put("sbr_uuid", sbr_uuid);
			map.put("sbr_title", sbr_title);
			map.put("Rsbr_uuid", Rsbr_uuid);
			map.put("sbr_content", sbr_content);
			map.put("mem_id", mem_id);
			
			boolean isc = false;
			
			isc = service.qnaReplyInsert(map);
			
			if(isc){
				System.out.println("문의 게시판 답글 작성 성공");
			} else {
				System.out.println("문의 게시판 답글 작성 실패");
			}
			
			return "redirect:/sysQnaMgr.do";
		}
		
		// 게시글 선택 삭제
		@RequestMapping(value="/select_PostDelete.do", method=RequestMethod.POST)
		@ResponseBody
		public void select_PostDelete(HttpServletRequest request){
			
			String[] numList = (String[])request.getParameterValues("numList");
			
			logger.info("=================== 게시글 선택 삭제 ========================");
			logger.info(Arrays.toString(numList));
			logger.info("=======================================================");
			
			
			for(int i = 0; i < numList.length; i++){
				
				String sbr_uuid = numList[i];
				
				System.out.println(sbr_uuid);
				
				Map<String, String> uuid = new HashMap<String, String>();
				uuid.put("sbr_uuid", sbr_uuid);

				Map<String, String> attach = null;
				attach = service.sysAttachSelect(uuid);

				if(attach != null){
					String savePath = ""+attach.get("SATT_PATH");
					String fileName = ""+attach.get("SATT_RNAME");

					File file = new File(savePath+fileName);

					file.delete();
				}

				boolean isc = false;
				
				isc = service.sysBoardDelete(sbr_uuid);

				if(isc == true){
					System.out.println("게시글 삭제 성공");
				} else {
					System.out.println("게시글 삭제 실패");
				}
			}
			
		}
		
		// 회원 관리 페이지 이동
		@RequestMapping(value="/sysMemMgr.do", method={RequestMethod.GET,RequestMethod.POST})
		public String sysMemberMgr(Model model , HttpServletRequest req){
			
			logger.info("========================= 회원 관리 페이지 이동 =======================");
			
			PagingProDto paging = new PagingProDto(req.getParameter("index"),
														   req.getParameter("pageStartNum"),
														   req.getParameter("listCnt"));
			
			MemberDto mDto = new MemberDto();
			mDto.setPaging(paging);
			
			String mem_name = req.getParameter("mem_name");
			mDto.setMem_name(mem_name);
			
			if(mem_name != null){
				logger.info("===================== 회원 검색 ========================");
				logger.info("검색할 내용 : "+mem_name);
				logger.info("====================================================");
			}
			
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			
			list = service.allMemberSelect(mDto);
			paging.setTotal(service.allMemberSelectCount(mDto));
			
			model.addAttribute("list", list);
			model.addAttribute("paging", paging);
			model.addAttribute("search_value", mem_name);
			
			return "sysManage/sysMemMgr";
		}
		
		//회원정보조회
		@RequestMapping(value = "/sysMemView.do", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, MemberDto> sysMemView(String mem_id){
			logger.info("===================== 관리자 회원정보 조회 ============================");
			Map<String, MemberDto> map = new HashMap<String, MemberDto>();
			MemberDto dto = new MemberDto();
			dto = service.sysMemView(mem_id);
			map.put("info", dto);
			System.out.println(map);
			return map;
		}
		
		//회원정보 수정
		@RequestMapping(value = "/sysMemModify.do", method = RequestMethod.POST)
		public String sysMemModify(MemberDto dto){
			logger.info("===================== 관리자 회원정보 수정 ============================");
			boolean result = false;
			result = service.sysMemModify(dto);
			if(result){
				return "redirect:/sysMemMgr.do";
			}else{
				return "error/error";
			}
		}
		
		// 관리자 로그아웃
		@RequestMapping(value="/adminLogout.do", method=RequestMethod.GET)
		public String adminLogout(HttpSession session){
			
			logger.info("===================== 관리자 로그아웃 ============================");
			
			session.removeAttribute("mem_id");
			session.removeAttribute("mem_name");
			
			return "redirect:/main.do";
		}

		// UUID 생성 메소드
		public String createUUID(){
			return UUID.randomUUID().toString();
		}

}
