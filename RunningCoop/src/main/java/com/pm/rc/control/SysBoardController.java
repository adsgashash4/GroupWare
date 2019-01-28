package com.pm.rc.control;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pm.rc.dto.PagingProDto;
import com.pm.rc.dto.SystemBoardDto;
import com.pm.rc.model.service.SysBoardService;

@Controller
public class SysBoardController {

	Logger logger = LoggerFactory.getLogger(SysBoardController.class);

	@Autowired
	private SysBoardService service;

	// 공지 게시글 목록
	@RequestMapping(value = "/noticeList.do", method = {RequestMethod.GET,RequestMethod.POST})
	public String noticeList(Model model, HttpSession session, HttpServletRequest req){
		PagingProDto paging = new PagingProDto(req.getParameter("index"), 
														req.getParameter("pageStartNum"), 
														req.getParameter("listCnt"));
		
		SystemBoardDto bDto = new SystemBoardDto();
		bDto.setPaging(paging);

		logger.info("================== 공지 게시판으로 이동 ==================");
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		list = service.noticeListSelect(bDto);
		paging.setTotal(service.noticeListSelectCount(bDto));

		model.addAttribute("list", list);
		model.addAttribute("paging",paging);

		return "sysBoard/noticeList";
	}

	// 공지 게시글 검색 목록
	@RequestMapping(value = "/noticeSList.do", method = RequestMethod.POST)
	public String noticeSList(Model model, HttpServletRequest request){
		
		PagingProDto paging = new PagingProDto(request.getParameter("index"), 
				request.getParameter("pageStartNum"), 
				request.getParameter("listCnt"));

		String sbr_title = request.getParameter("sbr_title");
		
		SystemBoardDto bDto = new SystemBoardDto();
		bDto.setPaging(paging);
		bDto.setSbr_title(sbr_title);

		logger.info("================== 공지 게시글 검색 ==================");
		logger.info("검색할 제목 : "+sbr_title);
		logger.info("================================================");

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list = service.noticeListSelect(bDto);
		
		paging.setTotal(service.noticeListSelectCount(bDto));

		model.addAttribute("slist", list);
		model.addAttribute("paging", paging);
		model.addAttribute("sbr_title", sbr_title);

		return "sysBoard/noticeSList";
	}

	// 공개 게시글 보기 페이지
	@RequestMapping(value="/boardView.do", method = RequestMethod.GET)
	public String boardView(Model model, HttpServletRequest request, HttpSession session){

		String sbr_uuid = request.getParameter("sbr_uuid");
		String sessionId = (String) session.getAttribute("mem_id");
		Map<String, String> uuid = new HashMap<String, String>();
		uuid.put("sbr_uuid", sbr_uuid);

		Map<String, String> view = new HashMap<String, String>();
		view = service.sysBoardViewSelect(uuid);

		Map<String, String> map = new HashMap<String, String>();
		map.put("sbr_uuid", sbr_uuid);

		logger.info("================== 공지 게시글 보기 ==================");
		logger.info("현재 접속 중인 세션 : "+sessionId);
		logger.info("조회할 게시글 번호 : "+sbr_uuid);
		logger.info("================================================");

		Map<String, String> list = new HashMap<String, String>();
		list = service.sysAttachSelect(map);

		model.addAttribute("view", view);
		model.addAttribute("mem_id", sessionId);

		if(list != null){
			model.addAttribute("attach", list);
		}

		return "sysBoard/boardView";
	}

	// 비밀 게시글 보기 페이지
	@RequestMapping(value="/scrBoardView.do", method = RequestMethod.POST)
	public String scrBoardView(Model model, HttpServletRequest request, HttpSession session){

		String sbr_uuid = request.getParameter("sbr_uuid");
		String sbr_pw = request.getParameter("sbr_pw");
		String sessionId = (String) session.getAttribute("mem_id");

		Map<String, String> map = new HashMap<String, String>();
		map.put("sbr_uuid", sbr_uuid);
		map.put("sbr_pw", sbr_pw);

		logger.info("================== 공지 게시글 보기 ==================");
		logger.info("현재 접속 중인 세션 : "+sessionId);
		logger.info("조회할 게시글 번호 : "+sbr_uuid);
		logger.info("게시글 비밀번호 : "+sbr_pw);
		logger.info("================================================");

		Map<String, String> view = new HashMap<String, String>();
		view = service.sysBoardViewSelect(map);

		Map<String, String> list = new HashMap<String, String>();
		list = service.sysAttachSelect(map);

		String page = "";

		if(view == null){
			page =  "sysBoard/nonCorrectPW";
		} else {
			model.addAttribute("view", view);
			model.addAttribute("mem_id", sessionId);
			if(list != null){
				model.addAttribute("attach", list);
			}
			page = "sysBoard/boardView";
		}

		return page;
	}

	// 문의 게시판 목록 출력
	@RequestMapping(value="/qnaList.do", method = {RequestMethod.GET,RequestMethod.POST})
	public String qnaList(Model model, HttpServletRequest req){
		PagingProDto paging = new PagingProDto(req.getParameter("index"), 
												req.getParameter("pageStartNum"),
												req.getParameter("listCnt"));
		SystemBoardDto bDto = new SystemBoardDto();
		bDto.setPaging(paging);
		
		logger.info("================== 문의 게시판으로 이동 ==================");

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list = service.qnaListSelect(bDto);
		paging.setTotal(service.qnaListSelectCount(bDto));

		model.addAttribute("list", list);
		model.addAttribute("paging", paging);

		return "sysBoard/qnaList";
	}

	// 문의 게시글 검색 목록 출력
	@RequestMapping(value="/qnaSList.do", method = RequestMethod.POST)
	public String qnaSList(Model model, HttpServletRequest request){
		
		PagingProDto paging = new PagingProDto(request.getParameter("index"), 
												request.getParameter("pageStartNum"),
												request.getParameter("listCnt"));

		String sbr_title = request.getParameter("sbr_title");
		
		SystemBoardDto bDto = new SystemBoardDto();
		bDto.setPaging(paging);
		bDto.setSbr_title(sbr_title);

		logger.info("================== 문의 게시글 검색 ==================");
		logger.info("검색할 제목 : "+sbr_title);
		logger.info("================================================");

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		list = service.qnaListSelect(bDto);
		paging.setTotal(service.qnaListSelectCount(bDto));

		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		model.addAttribute("sbr_title", sbr_title);

		return "sysBoard/qnaSList";
	}

	// 게시글 작성페이지로 이동
	@RequestMapping(value="/writeForm.do", method = RequestMethod.GET)
	public String boardWrite(){
		logger.info("================== 게시글 작성 페이지 이동 ==================");
		return "sysBoard/qnaWrite";
	}

	// 게시글 작성 폼
	@RequestMapping(value="/boardWrite.do", method = RequestMethod.POST)
	public String doWriteForm(MultipartHttpServletRequest multipartRequest, HttpSession session){
		String sbr_title = multipartRequest.getParameter("sbr_title");
		String sbr_content = multipartRequest.getParameter("sbr_content");
		String mem_id = (String) session.getAttribute("mem_id");

		//UUID 생성 메소드
		String uuid = createUUID();
		int indexnum = uuid.lastIndexOf("-");
		String sbr_uuid = uuid.substring(indexnum+1);

		Map<String, String> map = new HashMap<String, String>();
		map.put("sbr_uuid", sbr_uuid);

		MultipartFile file = multipartRequest.getFile("satt_name");

		String oldFileName = file.getOriginalFilename();
		
		if (oldFileName.length()>0){
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

		map.put("sbr_title", sbr_title);
		map.put("sbr_content", sbr_content);
		map.put("mem_id", mem_id);

		String scrpw = multipartRequest.getParameter("sbr_pw");

		boolean isc = false;

		// scrpw에 값이 들어오면 비밀글로 등록, 값이 없으면 공개글로 등록
		if(scrpw != null && scrpw.length() > 0){
			String sbr_scryn = "Y";
			map.put("sbr_scryn", sbr_scryn);
			map.put("sbr_pw", scrpw);
			isc = service.qnaBoardInsert(map);
		} else if (scrpw == null || scrpw.length() == 0){
			String sbr_scryn = "N";
			map.put("sbr_scryn", sbr_scryn);
			map.put("sbr_pw", scrpw);
			isc = service.qnaBoardInsert(map);
		}

		if(isc == true){
			System.out.println("문의게시판 게시글 등록 성공");
		} else {
			System.out.println("문의게시판 게시글 등록 실패");
		}

		return "redirect:/qnaList.do";
	}

	// 문의 수정페이지 이동
	@RequestMapping(value="/boardEditMove.do", method = RequestMethod.GET)
	public String boardEditPageMove(HttpServletRequest request, Model model){
		String sbr_uuid = request.getParameter("sbr_uuid");
		
		logger.info("===================== 문의 게시글 수정 폼 이동 ======================");
		logger.info("수정할 게시글 아이디 : "+sbr_uuid);
		logger.info("============================================================");

		Map<String, String> map = new HashMap<String, String>();
		map.put("sbr_uuid", sbr_uuid);

		Map<String, String> view = new HashMap<String, String>();
		view = service.editBoardViewSelect(map);

		Map<String, String> attach = new HashMap<String, String>();
		attach = service.sysAttachSelect(map);

		model.addAttribute("view", view);
		model.addAttribute("attach", attach);

		return "sysBoard/qnaEdit";
	}

	// 게시글 수정
	@RequestMapping(value="/boardEdit.do", method = RequestMethod.POST)
	public String BoardEdit(MultipartHttpServletRequest multipartRequest){
		String sbr_uuid = multipartRequest.getParameter("sbr_uuid");
		String sbr_title = multipartRequest.getParameter("sbr_title");
		String sbr_content = multipartRequest.getParameter("sbr_content");

		logger.info("=============== 문의 게시판 : 게시글 수정 =========================");
		logger.info("수정할 게시글 아이디 : "+sbr_uuid);
		logger.info("수정할 게시글 제목 : "+sbr_title);
		logger.info("수정할 게시글 내용 : "+sbr_content);
		logger.info("===========================================================");

		Map<String, String> map = new HashMap<String, String>();
		map.put("sbr_uuid", sbr_uuid);

		MultipartFile file = multipartRequest.getFile("satt_name");

		if (!file.getOriginalFilename().equals("")){
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

			String oldFileName = file.getOriginalFilename();
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
		
		String sbr_scrYN = multipartRequest.getParameter("scryn");
		String sbr_scrPW = multipartRequest.getParameter("sbr_pw");
		
		/* 비밀글 여부 */
		if(sbr_scrYN.equals("Y")){
			map.put("sbr_scryn", sbr_scrYN);
			map.put("sbr_pw", sbr_scrPW);
		} else {
			map.put("sbr_scryn", "N");
			map.put("sbr_pw", "");
		}
		
		// 서비스 실행
		boolean isc = false;

		isc = service.qnaBoardEdit(map);
		
		if(isc == true){
			System.out.println("문의게시판 게시글 수정 성공");
		} else {
			System.out.println("문의게시판 게시글 수정 실패");
		}

		return "redirect:/boardView.do?sbr_uuid="+sbr_uuid;
	}

	// 게시글 삭제
	@RequestMapping(value="/boardDelete.do", method = RequestMethod.GET)
	public String BoardDelete(HttpServletRequest request){

		String sbr_uuid = request.getParameter("sbr_uuid");
		Map<String, String> uuid = new HashMap<String, String>();
		uuid.put("sbr_uuid", sbr_uuid);

		Map<String, String> attach = new HashMap<String, String>();
		attach = service.sysAttachSelect(uuid);
		
		if(attach != null){
			String savePath = ""+attach.get("SATT_PATH");
			String fileName = ""+attach.get("SATT_RNAME");
			
			File file = new File(savePath+fileName);
			
			file.delete();
		}

		boolean isc = false;
		isc = service.qnaBoardDelete(sbr_uuid);
		
		if(isc == true){
			System.out.println("문의 게시글 삭제 성공");
		} else {
			System.out.println("문의 게시글 삭제 실패");
		}

		return "redirect:/qnaList.do";
	}

	@RequestMapping(value="/fileDown.do", method=RequestMethod.GET)
	public void fileDownload(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String sbr_uuid = request.getParameter("sbr_uuid");
		Map<String, String> map = new HashMap<String, String>();
		map.put("sbr_uuid", sbr_uuid);

		Map<String, String> file = new HashMap<String, String>();
		file = service.sysAttachSelect(map);

		String path = file.get("SATT_PATH");
		String fileName = file.get("SATT_RNAME");
		String newFileName = file.get("SATT_NAME");

		String filePath = path + fileName;

		logger.info("================== 파일 다운로드  ==================");
		logger.info("다운로드할 파일명 :"+fileName);
		logger.info("파일 경로 :"+path);
		logger.info("반환할 파일 명 :"+newFileName);
		logger.info("===============================================");

		byte fileByte[] = FileUtils.readFileToByteArray(new File(filePath));

		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(newFileName, "UTF-8")+"\";");
		response.setHeader("Content-Transfer_Encoding", "binary");

		response.getOutputStream().write(fileByte);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	// UUID 생성 메소드
	public String createUUID(){
		return UUID.randomUUID().toString();
	}

}
