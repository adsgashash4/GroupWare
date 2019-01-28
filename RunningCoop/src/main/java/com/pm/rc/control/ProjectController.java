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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pm.rc.dto.GbAttachDto;
import com.pm.rc.dto.MemberDto;
import com.pm.rc.dto.PagingProDto;
import com.pm.rc.dto.ProjectDto;
import com.pm.rc.dto.WorkCommentDto;
import com.pm.rc.dto.WorkDetailDto;
import com.pm.rc.dto.WorkListDto;
import com.pm.rc.model.service.GroupService;
import com.pm.rc.model.service.ProjectService;
import com.pm.rc.model.service.WorkListService;

@Controller
public class ProjectController {

	Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	private ProjectService service;

	@Autowired
	private WorkListService wService;
	
	@Autowired
	private GroupService gService;

	// 메인화면에서 그룹 프로젝트 선택
	@RequestMapping(value="/gProSelect.do", method=RequestMethod.GET)
	public String grProjectList(Model model, HttpServletRequest request, HttpSession session){

		session.removeAttribute("pr_level");

		String mem_id = (String)session.getAttribute("mem_id");
		String gr_id = request.getParameter("gr_id");

		session.setAttribute("gr_id", gr_id);

		List<ProjectDto> list = new ArrayList<ProjectDto>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("mem_id", mem_id);
		map.put("gr_id", gr_id);

		list = service.groupProSelect(map);
		
		Map<String, String> gr_level = new HashMap<String, String>();
		gr_level = gService.groupManagerSearch(map);
		
		model.addAttribute("list", list);
		
		session.setAttribute("gr_level", gr_level.get("GR_LEVEL"));
		
		return "project/gProjectSelect";
	}

	// 메인화면에서 개인 프로젝트 선택
	@RequestMapping(value="/iProSelect.do", method=RequestMethod.GET)
	public String myProjectList(Model model, HttpServletRequest request, HttpSession session){
		
		session.removeAttribute("gr_level");
		session.removeAttribute("pr_level");
		session.removeAttribute("gr_id");

		List<ProjectDto> list = new ArrayList<ProjectDto>();
		String mem_id = (String) session.getAttribute("mem_id");

		list = service.myProSelect(mem_id);
		model.addAttribute("list", list);

		return "project/mProjectSelect";
	}

	// 프로젝트 생성화면 연결
	@RequestMapping(value="/createMPro.do", method=RequestMethod.GET)
	public String myProCreateMove(HttpSession session, Model model){
		String gr_id = (String)session.getAttribute("gr_id");

		if(gr_id == null){
			return "project/mProCreate";
		} else {
			model.addAttribute("gr_id", gr_id);
			return "project/gProCreate";
		}
	}

	// 개인 프로젝트 생성 프로세스
	@RequestMapping(value="/mProCreate.do", method=RequestMethod.POST)
	public String myProCreate(HttpSession session, HttpServletRequest request){

		Map<String, String> map = new HashMap<String, String>();

		map.put("mem_id", (String) session.getAttribute("mem_id"));
		map.put("pr_name", request.getParameter("pr_name"));
		map.put("pr_startdate", request.getParameter("pr_startdate"));
		map.put("pr_enddate", request.getParameter("pr_enddate"));
		map.put("pr_goal", request.getParameter("pr_goal"));
		map.put("pr_etc", request.getParameter("pr_etc"));

		logger.info("=================== 개인 프로젝트 생성 =====================");
		logger.info("프로젝트 생성자 아이디 : "+(String)session.getAttribute("mem_id"));
		logger.info("=====================================================");

		boolean isc = false;
		isc = service.iPrInsert(map);
		if(isc){
			System.out.println("개인 프로젝트 등록 성공");
		} else {
			System.out.println("개인 프로젝트 등록 실패");
		}
		return "redirect:/iProSelect.do";
	}

	// 그룹 프로젝트 생성 프로세스
	@RequestMapping(value="/gProCreate.do", method=RequestMethod.POST)
	public String grProCreate(HttpSession session, HttpServletRequest request){

		Map<String, String> map = new HashMap<String, String>();

		map.put("gr_id", request.getParameter("gr_id"));
		map.put("pr_name", request.getParameter("pr_name"));
		map.put("pr_startdate", request.getParameter("pr_startdate"));
		map.put("pr_enddate", request.getParameter("pr_enddate"));
		map.put("pr_goal", request.getParameter("pr_goal"));
		map.put("pr_etc", request.getParameter("pr_etc"));
		map.put("mem_id", (String)session.getAttribute("mem_id"));

		logger.info("=================== 그룹 프로젝트 생성 =====================");
		logger.info("소속 그룹 아이디 : "+request.getParameter("gr_id"));
		logger.info("프로젝트 생성자 아이디 : "+(String)session.getAttribute("mem_id"));
		logger.info("=====================================================");

		boolean isc = false;
		isc = service.gPrInsert(map);

		if(isc){
			System.out.println("그룹 프로젝트 생성 성공");
			return "redirect:/gProSelect.do?gr_id="+request.getParameter("gr_id");
		} else {
			System.out.println("그룹 프로젝트 생성 실패");
			return "redirect:/createMPro.do";
		}
	}

	// 프로젝트 정보 화면
	@RequestMapping(value="/detailPro.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> proDetail(HttpServletRequest request){
		logger.info("proDetail실행");
		String pr_id = request.getParameter("pr_id");

		logger.info("=================== 프로젝트 상세정보 보기 =======================");
		logger.info("상세정보를 볼 프로젝트 id :"+pr_id);
		logger.info("==========================================================");

		Map<String, String> map = new HashMap<String, String>();
		map = service.prDetailSelect(pr_id);
		return map;
	}

	// 프로젝트 진행화면 이동
	@RequestMapping(value="/goProject.do", method=RequestMethod.GET)
	public String goToProject(Model model, HttpServletRequest request, HttpSession session){
		String pr_id = request.getParameter("pr_id");

		logger.info("=================== 프로젝트 업무리스트 보기 =======================");
		logger.info("업무리스트를 볼 프로젝트 id :"+pr_id);
		logger.info("===========================================================");

		List<Map<String, String>> todo = new ArrayList<Map<String, String>>();
		List<Map<String, String>> doing = new ArrayList<Map<String, String>>();
		List<Map<String, String>> done = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();
		
		map.put("pr_id", pr_id);
		map.put("wk_condition", "todo");
		todo = wService.wkListSelect(map);
		map.replace("wk_condition", "doing");
		doing = wService.wkListSelect(map);
		map.replace("wk_condition", "done");
		done = wService.wkListSelect(map);
		
		model.addAttribute("todo", todo);
		model.addAttribute("doing", doing);
		model.addAttribute("done", done);

		model.addAttribute("pr_id", pr_id);

		String mem_id = (String)session.getAttribute("mem_id");

		Map<String, String> value = new HashMap<String, String>();
		
		value.put("pr_id", pr_id);
		value.put("mem_id", mem_id);

		String pr_level = service.myLevelSelect(value);
		
		session.setAttribute("pr_level", pr_level);
		
		Map<String, String> pr_detail = new HashMap<String, String>();
		pr_detail = service.prDetailSelect(pr_id);
		model.addAttribute("pr_detail", pr_detail);

		return "project/workList";
	}

	// 하위업무리스트 조회 메소드
	@RequestMapping(value="/detailWork.do", method=RequestMethod.POST)
	@ResponseBody
	public List<WorkDetailDto> workDetailView(Model model, HttpServletRequest request){
		String wk_id = request.getParameter("wk_id");

		logger.info("=============== 업무 상세 조회 ================");
		logger.info("조회할 업무 아이디 : "+wk_id);
		logger.info("=========================================");

		List<WorkDetailDto> dto = new ArrayList<WorkDetailDto>();
		dto = wService.wdSelect(wk_id);

		return dto;
	}

	// 하위 업무 추가 후 출력 메소드
	@RequestMapping(value="/wdInsert.do", method=RequestMethod.POST)
	@ResponseBody
	public List<WorkDetailDto> workDetailInset(Model model, HttpServletRequest request){
		String wd_title = request.getParameter("wd_title");
		String wd_endDate = request.getParameter("wd_endDate");
		String wk_id = request.getParameter("wk_id");

		logger.info("=============== 하위 업무 추가 ================");
		logger.info("상위 업무 id :"+wk_id);
		logger.info("추가할 하위 업무 내용 : "+wd_title);
		logger.info("추가할 하위 업무 마감기한 : "+wd_endDate);
		logger.info("==========================================");

		WorkDetailDto dto = new WorkDetailDto();
		dto.setWk_id(wk_id);
		dto.setWd_title(wd_title);
		dto.setWd_endDate(wd_endDate);

		boolean isc = false;
		isc = wService.wdInsert(dto);

		boolean isc2 = false;
		isc2 = wService.wkRateModify(wk_id);

		if (isc && isc2){
			System.out.println("하위 업무 추가 성공");
		} else {
			System.out.println("하위 업무 추가 실패");
		}

		List<WorkDetailDto> list = new ArrayList<WorkDetailDto>();
		list = wService.wdSelect(wk_id);

		return list;
	}

	//하위 업무 수정
	@RequestMapping(value="/wdEdit.do", method=RequestMethod.POST)
	@ResponseBody
	public List<WorkDetailDto> workDetailEdit(HttpServletRequest request){
		String wd_id = request.getParameter("wd_id");
		String wd_title = request.getParameter("wd_title");
		String wd_endDate = request.getParameter("wd_endDate");
		String wk_id = request.getParameter("wk_id");

		logger.info("=============== 하위 업무 수정 ================");
		logger.info("수정할 하위업무 id :"+wd_id);
		logger.info("수정할 하위 업무 내용 : "+wd_title);
		logger.info("수정할 하위 업무 마감기한 : "+wd_endDate);
		logger.info("==========================================");

		WorkDetailDto dto = new WorkDetailDto();
		dto.setWd_id(wd_id);
		dto.setWd_title(wd_title);
		dto.setWd_endDate(wd_endDate);

		boolean isc = false;
		isc = wService.wdModify(dto);

		if(isc){
			System.out.println("하위 업무 수정 성공");
		} else {
			System.out.println("하위 업무 수정 실패");
		}

		List<WorkDetailDto> list = new ArrayList<WorkDetailDto>();
		list = wService.wdSelect(wk_id);

		return list;
	}

	//하위 업무 완료 처리
	@RequestMapping(value="/wdComplete.do", method=RequestMethod.POST)
	@ResponseBody
	public List<WorkDetailDto> workDetailComplete(HttpServletRequest request){
		String wd_id = request.getParameter("wd_id");
		String wk_id = request.getParameter("wk_id");

		logger.info("=============== 하위 업무 완료 처리 ===============");
		logger.info("완료할 하위 업무 id : "+wd_id);
		logger.info("============================================");

		boolean isc = false;
		isc = wService.wdComplModify(wd_id);

		boolean isc2 = false;
		isc2 = wService.wkRateModify(wk_id);

		if(isc && isc2){
			System.out.println("하위 업무 완료 수정 성공");
		} else {
			System.out.println("하위 업무 완료 수정 실패");
		}

		List<WorkDetailDto> list = new ArrayList<WorkDetailDto>();
		list = wService.wdSelect(wk_id);

		return list;
	}

	//하위 업무 애로사항 표시
	@RequestMapping(value="/wdError.do", method=RequestMethod.POST)
	@ResponseBody
	public List<WorkDetailDto> workDetailError(HttpServletRequest request){
		String wd_id = request.getParameter("wd_id");
		String wk_id = request.getParameter("wk_id");
		String wd_erroryn = request.getParameter("wd_erroryn");

		logger.info("=============== 하위 업무 애로사항 표시 처리 =================");
		logger.info("애로사항 표시할 하위 업무 id :"+wd_id);
		logger.info("====================================================");
		
		if(wd_erroryn.equals("Y")){
			wd_erroryn = "N";
		} else {
			wd_erroryn = "Y";
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("wd_id", wd_id);
		map.put("wd_erroryn", wd_erroryn);

		boolean isc = false;
		isc = wService.wdErrorChk(map);

		if(isc){
			System.out.println("하위 업무 애로사항 표시 성공");
		} else {
			System.out.println("하위 업무 애로사항 표시 실패");
		}

		List<WorkDetailDto> list = new ArrayList<WorkDetailDto>();
		list = wService.wdSelect(wk_id);

		return list;
	}

	//하위 업무 삭제
	@RequestMapping(value="/wdDelete.do", method=RequestMethod.POST)
	@ResponseBody
	public List<WorkDetailDto> workDetailDelete(HttpServletRequest request){
		String wd_id = request.getParameter("wd_id");
		String wk_id = request.getParameter("wk_id");

		logger.info("=============== 하위 업무 삭제 ===================");
		logger.info("삭제할 하위 업무 아이디 : "+wd_id);
		logger.info("=============================================");

		boolean isc = false;
		isc = wService.wdDelete(wd_id);

		boolean isc2 = false;
		isc2 = wService.wkRateModify(wk_id);

		if(isc && isc2){
			System.out.println("하위 업무 삭제 성공");
		} else {
			System.out.println("하위 업무 삭제 실패");
		}

		List<WorkDetailDto> list = new ArrayList<WorkDetailDto>();
		list = wService.wdSelect(wk_id);

		return list;
	}

	//프로젝트 멤버 조회
	@RequestMapping(value="/searchmem.do", method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> promemSelect(HttpServletRequest request){
		String pr_id = request.getParameter("pr_id");

		logger.info("=============== 프로젝트 참여 멤버 조회 ===================");
		logger.info("멤버 조회할 프로젝트 아이디 : "+pr_id);
		logger.info("==================================================");

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list = service.prMemListSelect(pr_id);

		return list;
	}

	// 업무 수정
	@RequestMapping(value="/workEdit.do", method=RequestMethod.POST)
	public String work_Edit(HttpServletRequest request){
		String pr_id = request.getParameter("pr_id");
		String wk_id = request.getParameter("wk_id");
		String wk_title = request.getParameter("wk_title");
		String wk_endDate = request.getParameter("wk_endDate");
		String mem_id = request.getParameter("mem_id");

		logger.info("=============== 업무 정보 변경 ===================");
		logger.info("수정 할 업무 아이디 : "+wk_id);
		logger.info("수정할 업무 내용 : "+wk_title);
		logger.info("수정할 업무 마감기한 : "+wk_endDate);
		logger.info("수정할 업무 담당자  : "+mem_id);
		logger.info("=============================================");

		WorkListDto dto = new WorkListDto();
		dto.setWk_id(wk_id);
		dto.setMem_id(mem_id);
		dto.setWk_title(wk_title);
		dto.setWk_endDate(wk_endDate);

		boolean isc = false;
		isc = wService.wkListModify(dto);

		if(isc){
			System.out.println("업무 정보 변경 성공");
		} else {
			System.out.println("업무 정보 변경 실패");
		}
		return "redirect:/goProject.do?pr_id="+pr_id;
	}

	// 업무 추가
	@RequestMapping(value="/workInsert.do", method=RequestMethod.POST)
	public String work_Insert(HttpServletRequest request){
		String pr_id = request.getParameter("pr_id");
		String wk_title = request.getParameter("wk_title");
		String wk_endDate = request.getParameter("wk_endDate");
		String mem_id = request.getParameter("mem_id");

		logger.info("================= 업무 추가 =====================");
		logger.info("업무 추가할 프로젝트 : "+pr_id);
		logger.info("추가할 업무 내용 : "+wk_title);
		logger.info("추가할 업무 마감기한 : "+wk_endDate);
		logger.info("추가할 업무 담당자 : "+mem_id);
		logger.info("=============================================");

		WorkListDto dto = new WorkListDto();
		dto.setWk_title(wk_title);
		dto.setWk_endDate(wk_endDate);
		dto.setMem_id(mem_id);
		dto.setPr_id(pr_id);

		boolean isc = false;
		isc = wService.wkListInsert(dto);

		if(isc){
			System.out.println("업무 등록 성공");
		} else {
			System.out.println("업무 등록 실패");
		}

		service.prRateEdit(pr_id);

		return "redirect:/goProject.do?pr_id="+pr_id;
	}

	//업무 삭제
	@RequestMapping(value="/workDelete.do", method=RequestMethod.GET)
	public String work_Delete(HttpServletRequest request){
		String pr_id = request.getParameter("pr_id");
		String wk_id = request.getParameter("wk_id");

		logger.info("=============== 업무 정보 삭제 ===================");
		logger.info("업무를 삭제할 프로젝트아이디 : "+pr_id);
		logger.info("삭제할 업무 아이디 : "+wk_id);
		logger.info("=============================================");

		boolean isc = false;
		isc = wService.wkListDelete(wk_id);

		if(isc){
			System.out.println("업무 삭제 성공");
		} else {
			System.out.println("업무 삭제 실패");
		}

		service.prRateEdit(pr_id);

		return "redirect:/goProject.do?pr_id="+pr_id;
	}

	//업무 코멘트 출력
	@RequestMapping(value="/wcomlist.do", method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> work_Comment_View(HttpServletRequest request){
		String wk_id = request.getParameter("wk_id");

		logger.info("=============== 업무 코멘트 출력 ===================");
		logger.info("코멘트 출력할 업무 아이디 : "+wk_id);
		logger.info("=============================================");

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		list = wService.wCommListSelect(wk_id);

		return list;
	}

	//업무 코멘트 추가
	@RequestMapping(value="/wcominsert.do", method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> work_Comment_Insert(HttpServletRequest request, HttpSession session){
		String wk_id = request.getParameter("wk_id");
		String wcom_content = request.getParameter("wcom_content");
		String mem_id = (String)session.getAttribute("mem_id");

		logger.info("=============== 업무 코멘트 추가 ===================");
		logger.info("코멘트 추가할 업무 아이디 : "+wk_id);
		logger.info("코멘트 다는 회원 아이디 : "+mem_id);
		logger.info("추가할 업무 코멘트 내용 : "+wcom_content);
		logger.info("=============================================");

		WorkCommentDto dto = new WorkCommentDto();
		dto.setMem_id(mem_id);
		dto.setWcom_content(wcom_content);
		dto.setWk_id(wk_id);

		boolean isc = false;
		isc = wService.wCommentInsert(dto);

		if(isc){
			System.out.println("업무 코멘트 추가 성공");
		} else {
			System.out.println("업무 코멘트 추가 실패");
		}

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		list = wService.wCommListSelect(wk_id);

		return list;
	}

	// 업무 코멘트 수정 기능
	@RequestMapping(value="/wcomEdit.do", method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> work_Comment_Edit(HttpServletRequest request){
		String wk_id = request.getParameter("wk_id");
		String wcom_id = request.getParameter("wcom_id");
		String wcom_content = request.getParameter("wcom_content");

		logger.info("=============== 업무 코멘트 수정 ===================");
		logger.info("코멘트가 포함된 업무 아이디 : "+wk_id);
		logger.info("수정할 코멘트 아이디 : "+wcom_id);
		logger.info("수정할 코멘트 내용 : "+wcom_content);
		logger.info("=============================================");

		WorkCommentDto dto = new WorkCommentDto();
		dto.setWcom_id(wcom_id);
		dto.setWcom_content(wcom_content);

		boolean isc = false;
		isc = wService.wCommentModify(dto);

		if(isc){
			System.out.println("업무 코멘트 수정 성공");
		} else {
			System.out.println("업무 코멘트 수정 실패");
		}

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list = wService.wCommListSelect(wk_id);

		return list;
	}

	// 업무 코멘트 삭제 기능
	@RequestMapping(value="/wcomDelete.do", method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> work_Comment_Delete(HttpServletRequest request){
		String wk_id = request.getParameter("wk_id");
		String wcom_id = request.getParameter("wcom_id");

		logger.info("=============== 업무 코멘트 삭제 ===================");
		logger.info("코멘트가 포함된 업무 아이디 : "+wk_id);
		logger.info("삭제할 코멘트 아이디 : "+wcom_id);
		logger.info("=============================================");

		boolean isc = false;
		isc = wService.wCommentDelete(wcom_id);

		if(isc){
			System.out.println("업무 코멘트 삭제 성공");
		} else {
			System.out.println("업무 코멘트 삭제 실패");
		}

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list = wService.wCommListSelect(wk_id);

		return list;
	}

	// 업무 첨부파일 목록 조회 및 출력 기능
	@RequestMapping(value="/attachlist.do", method=RequestMethod.POST)
	@ResponseBody
	public List<GbAttachDto> work_Attach_View(HttpServletRequest request){
		String wk_id = request.getParameter("wk_id");

		logger.info("=============== 업무 첨부파일 조회 ===================");
		logger.info("첨부파일 조회할 업무 아이디 : "+wk_id);
		logger.info("===============================================");

		List<GbAttachDto> list = new ArrayList<GbAttachDto>();
		list = wService.gbAttachSelect(wk_id);


		return list;
	}

	// 업무 첨부파일 추가 기능
	@RequestMapping(value="/attachInsert.do", method=RequestMethod.POST)
	@ResponseBody
	public List<GbAttachDto> work_Attach_Insert(HttpServletRequest request, MultipartHttpServletRequest multipartRequest){

		String wk_id = multipartRequest.getParameter("wk_id");

		MultipartFile file = multipartRequest.getFile("gatt_name");

		String savePath = "C:\\RC_fileSave\\";

		String uuid = createUUID();
		int indexNum = uuid.lastIndexOf("-");

		String oldFileName = file.getOriginalFilename();
		String gatt_size = ""+file.getSize();

		String newFileName = uuid.substring(indexNum+1) + oldFileName;

		// 첨부파일 실제경로 저장
		try {
			file.transferTo(new File(savePath + newFileName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}

		logger.info("=============== 업무 첨부파일 추가 ===================");
		logger.info("첨부파일 추가할 업무 아이디 : "+wk_id);
		logger.info("첨부파일 명 : "+oldFileName);
		logger.info("첨부파일 크기 : "+gatt_size);
		logger.info("첨부파일 경로 : "+savePath);
		logger.info("첨부파일 실제이름 : "+newFileName);
		logger.info("===============================================");

		GbAttachDto dto = new GbAttachDto();
		dto.setWk_id(wk_id);
		dto.setGatt_name(oldFileName);
		dto.setGatt_rname(newFileName);
		dto.setGatt_size(gatt_size);
		dto.setGatt_path(savePath);

		boolean isc = false;
		isc = wService.gbAttachInsert(dto);

		if(isc){
			System.out.println("파일 추가 성공");
		} else {
			System.out.println("파일 추가 실패");
		}

		List<GbAttachDto> list = new ArrayList<GbAttachDto>();

		list = wService.gbAttachSelect(wk_id);

		return list;
	}

	//파일 다운로드
	@RequestMapping(value="/gbfileDown.do", method=RequestMethod.GET)
	public void fileDownload(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String gatt_seq = request.getParameter("gatt_seq");

		GbAttachDto file = new GbAttachDto();
		file = wService.attachDownSelect(gatt_seq);

		String path = file.getGatt_path();
		String fileName = file.getGatt_rname();
		String newFileName = file.getGatt_name();

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

	// 첨부파일 삭제
	@RequestMapping(value="/attachdelete.do", method=RequestMethod.POST)
	@ResponseBody
	public List<GbAttachDto> attach_Delete(HttpServletRequest request){
		String gatt_seq = request.getParameter("gatt_seq");
		String wk_id = request.getParameter("wk_id");

		logger.info("================== 파일 삭제  ==================");
		logger.info("삭제할 파일이 포함된 업무 아이디 : "+wk_id);
		logger.info("삭제할 파일 번호 :"+gatt_seq);
		logger.info("============================================");

		GbAttachDto file = new GbAttachDto();
		file = wService.attachDownSelect(gatt_seq);

		String fileName = file.getGatt_rname();
		String savePath = file.getGatt_path();

		File dFile = new File(savePath + fileName);
		dFile.delete();

		boolean isc = false;
		isc = wService.gbAttachDelete(gatt_seq);

		if(isc){
			System.out.println("파일 삭제 성공");
		} else {
			System.out.println("파일 삭제 실패");
		}

		List<GbAttachDto> list = new ArrayList<GbAttachDto>();
		list = wService.gbAttachSelect(wk_id);

		return list;
	}

	// 프로젝트 관리 페이지 이동
	@RequestMapping(value="/goProManage.do", method=RequestMethod.GET)
	public String goProject_Manager(Model model, HttpServletRequest request, HttpSession session){
		String pr_id = request.getParameter("pr_id");

		logger.info("=================== 프로젝트 상세정보 보기 =======================");
		logger.info("상세정보를 볼 프로젝트 id :"+pr_id);
		logger.info("==========================================================");

		Map<String, String> map = new HashMap<String, String>();
		map = service.prDetailSelect(pr_id);

		model.addAttribute("pr_id", pr_id);
		model.addAttribute("detail", map);

		String gr_id = (String)session.getAttribute("gr_id");
		String gr_id2 =request.getParameter("gr_id");
		logger.info("=======gr_id: "+gr_id2);

		if (gr_id == null  &&  gr_id2 == null){
			return "project/mProjectManage";
		} else if(gr_id == null){
			session.setAttribute("gr_id", gr_id2);
			return "project/gProjectManage";
		}else{
			return "project/gProjectManage";
		}
	}

	// 프로젝트 정보 수정
	@RequestMapping(value="/projectEdit.do", method=RequestMethod.POST)
	@ResponseBody
	public String project_Edit(HttpServletRequest request){
		String pr_id = request.getParameter("pr_id");
		String pr_name = request.getParameter("pr_name");
		String pr_endDate = request.getParameter("pr_endDate");
		String pr_goal = request.getParameter("pr_goal");
		String pr_searchYN = request.getParameter("pr_searchYN");
		String pr_condition = request.getParameter("pr_condition");

		logger.info("=================== 프로젝트 상세정보 수정 =======================");
		logger.info("수정할 프로젝트 id :"+pr_id);
		logger.info("수정된 프로젝트 명 : "+pr_name);
		logger.info("수정된 프로젝트 마감기한 : "+pr_endDate);
		logger.info("수정된 프로젝트 목적 : "+pr_goal);
		logger.info("수정된 프로젝트 공개여부 : "+pr_searchYN);
		logger.info("수정된 프로젝트 상태 : "+pr_condition);
		logger.info("==========================================================");

		ProjectDto dto = new ProjectDto();
		dto.setPr_id(pr_id);
		dto.setPr_name(pr_name);
		dto.setPr_endDate(pr_endDate);
		dto.setPr_goal(pr_goal);
		dto.setPr_searchYN(pr_searchYN);
		dto.setPr_condition(pr_condition);

		boolean isc = false;
		isc = service.projectEdit(dto);

		if (isc) {
			System.out.println("수정 성공");
			return "success";
		} else {
			System.out.println("수정 실패");
			return "fail";
		} 
	}

	// 프로젝트 멤버 로드
	@RequestMapping(value="/loadMember.do", method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> load_Member(HttpServletRequest request){
		String pr_id = request.getParameter("pr_id");

		logger.info("=================== 프로젝트 멤버 조회 =======================");
		logger.info("멤버 조회할 프로젝트 id :"+pr_id);
		logger.info("========================================================");

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list = service.prMemListSelect(pr_id);

		return list;
	}

	// 그룹 프로젝트 - 초대 가능한 그룹 멤버 보기
	@RequestMapping(value="/invite_memList.do", method=RequestMethod.POST)
	@ResponseBody
	public List<MemberDto> invitable_Mem_List(HttpServletRequest request, HttpSession session){
		String pr_id = request.getParameter("pr_id");
		String gr_id = (String)session.getAttribute("gr_id");

		logger.info("=================== 초대가능한 멤버 조회 =======================");
		logger.info("멤버 조회할 프로젝트 id :"+pr_id);
		logger.info("========================================================");

		Map<String, String> map = new HashMap<String, String>();
		map.put("pr_id", pr_id);
		map.put("gr_id", gr_id);

		List<MemberDto> list = new ArrayList<MemberDto>();
		list = service.prMemInsertSearch(map);

		return list;
	}

	// 그룹 프로젝트 - 멤버 초대하기
	@RequestMapping(value="/invite_Member.do", method=RequestMethod.POST)
	@ResponseBody
	public String invite_Member(HttpServletRequest request){
		String pr_id = request.getParameter("pr_id");
		String[] mem_id = request.getParameterValues("mem_id");

		logger.info("=================== 초대할 멤버 조회 =======================");
		logger.info("멤버 초대할 프로젝트 id :"+pr_id);
		for(int i = 0; i < mem_id.length; i++){
			logger.info("초대할 멤버 id : "+mem_id[i]);
		}
		logger.info("========================================================");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pr_id", pr_id);
		map.put("mem_list", mem_id);

		boolean isc = false;
		isc = service.prMemInsert(map);

		if(isc){
			return "success";
		} else {
			return "fail";
		}
	}

	// 그룹 프로젝트 - 멤버 강제 탈퇴 기능
	@RequestMapping(value="/delete_Member.do", method=RequestMethod.POST)
	@ResponseBody
	public String delete_Member(HttpServletRequest request){
		String pr_id = request.getParameter("pr_id");
		String[] mem_list = request.getParameterValues("mem_list");

		logger.info("=================== 강제탈퇴할 멤버 조회 =======================");
		logger.info("멤버 강제탈퇴할 프로젝트 id :"+pr_id);
		for(int i = 0; i < mem_list.length; i++){
			logger.info("강제탈퇴할 멤버 id : "+mem_list[i]);
		}
		logger.info("========================================================");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pr_id", pr_id);
		map.put("mem_list", mem_list);

		boolean isc = false;
		isc = service.prMemDelete(map);

		if(isc){
			return "success";
		} else {
			return "fail";
		}
	}

	// 그룹 프로젝트 - 담당자 위임
	@RequestMapping(value="/commissionPM.do", method=RequestMethod.POST)
	@ResponseBody
	public String commission_PM(HttpServletRequest request, HttpSession session){
		String pr_id = request.getParameter("pr_id");
		String new_id = request.getParameter("mem_id");
		String mem_id = (String)session.getAttribute("mem_id");

		logger.info("=================== 프로젝트 담당자 위임 =======================");
		logger.info("담당자 위임할 프로젝트 아이디 :"+pr_id);
		logger.info("담당자 위임할 회원 아이디 : "+mem_id);
		logger.info("=========================================================");

		Map<String, String> map = new HashMap<String, String>();
		map.put("new_id", new_id);
		map.put("pr_id", pr_id);
		map.put("mem_id", mem_id);

		boolean isc = false;
		isc = service.prMgrModify(map);

		if(isc){
			session.removeAttribute("pr_level");
			String pr_level = service.myLevelSelect(map);
			session.setAttribute("pr_level", pr_level);

			return "success";
		} else {
			return "fail";
		}
	}

	// 그룹 내 멤버 정보 조회 : 멤버정보 조회
	@RequestMapping(value="/view_MemberInfo_1.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> view_MemberInfo(HttpServletRequest request, HttpSession session){
		String mem_id = request.getParameter("mem_id");

		logger.info("=================== 그룹 내 회원정보 조회 =======================");
		logger.info("해당 회원의 아이디 :"+mem_id);
		logger.info("=========================================================");

		Map<String, String> map = new HashMap<String, String>();
		map.put("mem_id", mem_id);

		Map<String, String> info = new HashMap<String, String>();

		info = service.memInfoSelect_1(map);

		return info;
	}

	// 그룹 내 멤버 정보 조회 : 프로젝트 정보 조회
	@RequestMapping(value="/view_MemberInfo_2.do", method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> view_MemberInfo_2(HttpServletRequest request, HttpSession session){
		String gr_id = (String)session.getAttribute("gr_id");
		String mem_id = request.getParameter("mem_id");

		logger.info("===================== 참여 프로젝트 조회 =======================");
		logger.info("해당 회원의 아이디 :"+mem_id);
		logger.info("회원의 소속 그룹 아이디 : "+gr_id);
		logger.info("=========================================================");

		Map<String, String> map = new HashMap<String, String>();
		map.put("mem_id", mem_id);
		map.put("gr_id", gr_id);

		List<Map<String, String>> prolist = new ArrayList<Map<String, String>>();
		prolist = service.memInfoSelect_2(map);

		return prolist;
	}

	// 프로젝트 해체 기능
	@RequestMapping(value="/project_Delete.do", method=RequestMethod.POST)
	@ResponseBody
	public String project_Delete(HttpServletRequest request, HttpSession session){
		String pr_id = request.getParameter("pr_id");

		logger.info("====================== 프로젝트 해체 =========================");
		logger.info("해체할 프로젝트 아이디 : "+pr_id);
		logger.info("=========================================================");

		boolean isc = false;
		isc = service.projectDelete(pr_id);

		if(isc){
			return "success";
		} else {
			return "fail";
		}
	}

	// 프로젝트 탈퇴 기능
	@RequestMapping(value="/leaveProject.do", method=RequestMethod.POST)
	@ResponseBody
	public String leave_Project(HttpServletRequest request, HttpSession session){
		String pr_id = request.getParameter("pr_id");
		String mem_id = (String)session.getAttribute("mem_id");

		logger.info("====================== 프로젝트 탈퇴 =========================");
		logger.info("프로젝트 탈퇴할 멤버 아이디 : "+mem_id);
		logger.info("탈퇴할 프로젝트 아이디 : "+pr_id);
		logger.info("=========================================================");

		Map<String, String> map = new HashMap<String, String>();
		map.put("mem_id", mem_id);
		map.put("pr_id", pr_id);

		boolean isc = false;
		isc = service.leaveProject(map);

		if(isc){
			return "success";
		} else {
			return "fail";
		}
	}

	// 진행중인 프로젝트 건 수 출력
	@RequestMapping(value="/selectDoingPro.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> selectDoingPro(HttpServletRequest request){
		String mem_id = request.getParameter("mem_id");

		logger.info("====================== 진행중인 프로젝트 조회 =========================");
		logger.info("프로젝트 조회할 멤버 아이디 : "+mem_id);
		logger.info("===============================================================");

		Map<String, String> result = new HashMap<String, String>();
		result = service.myDoingPrSelect(mem_id);

		return result;
	}

	// 진행예정인 프로젝트 건 수 출력
	@RequestMapping(value="/selectTodoPro.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> selectTodoPro(HttpServletRequest request){
		String mem_id = request.getParameter("mem_id");

		logger.info("====================== 진행 예정인 프로젝트 조회 =========================");
		logger.info("프로젝트 조회할 멤버 아이디 : "+mem_id);
		logger.info("=================================================================");

		Map<String, String> result = new HashMap<String, String>();
		result = service.myTodoPrSelect(mem_id);

		return result;
	}

	// 진행완료인 프로젝트 건 수 출력
	@RequestMapping(value="/selectDonePro.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> selectDonePro(HttpServletRequest request){
		String mem_id = request.getParameter("mem_id");

		logger.info("====================== 진행 완료인 프로젝트 조회 =========================");
		logger.info("프로젝트 조회할 멤버 아이디 : "+mem_id);
		logger.info("=================================================================");

		Map<String, String> result = new HashMap<String, String>();
		result = service.myDidPrSelect(mem_id);

		return result;
	}
	
	// 진행중인 프로젝트 목록 출력
	@RequestMapping(value="/goDoingSelect.do", method={RequestMethod.GET,RequestMethod.POST})
	public String goDoingSelect(HttpSession session, Model model , HttpServletRequest req){
		String mem_id = (String)session.getAttribute("mem_id");
		String pr_name = req.getParameter("pr_name");
		
		PagingProDto gPrPaging = new PagingProDto(req.getParameter("gIndex"),
				 req.getParameter("gPageStartNum"),
				 req.getParameter("gListCnt"));
		PagingProDto iPrPaging = new PagingProDto(req.getParameter("iIndex"),
				req.getParameter("iPageStartNum"),
				req.getParameter("iListCnt"));
				
		
		logger.info("====================== 진행 중인 프로젝트 목록 조회 =========================");
		logger.info("프로젝트 조회할 멤버 아이디 : "+mem_id);
		logger.info("===================================================================");
		
		ProjectDto gDto = new ProjectDto();
		gDto.setPaging(gPrPaging);
		gDto.setMem_id(mem_id);
		gDto.setPr_name(pr_name);
		
		ProjectDto iDto = new ProjectDto();
		iDto.setPaging(iPrPaging);
		iDto.setMem_id(mem_id);
		iDto.setPr_name(pr_name);
		
		List<Map<String, String>> GPrlist = new ArrayList<Map<String, String>>();
		List<Map<String, String>> IPrlist = new ArrayList<Map<String, String>>();
		
		GPrlist = service.myDoingGPrListSelect(gDto);
		gPrPaging.setTotal(service.myDoingGpTotalcount(gDto));
		IPrlist = service.myDoingIPrListSelect(iDto);
		iPrPaging.setTotal(service.myDoingIpTotalcount(iDto));
		
		
		model.addAttribute("gPrList", GPrlist);
		model.addAttribute("gPaging",gPrPaging);
		model.addAttribute("iPrList", IPrlist);
		model.addAttribute("iPaging", iPrPaging);
		model.addAttribute("pr_name", pr_name);
		
		return "project/doingProList";
	}
	
	// 진행예정인 프로젝트 목록 출력
	@RequestMapping(value="/goTodoSelect.do", method={RequestMethod.GET,RequestMethod.POST})
	public String goTodoSelect(HttpSession session, Model model, HttpServletRequest req){
		String mem_id = (String)session.getAttribute("mem_id");
		String pr_name = req.getParameter("pr_name");
		
		PagingProDto gPrPaging = new PagingProDto(req.getParameter("gIndex"),
				 req.getParameter("gPageStartNum"),
				 req.getParameter("gListCnt"));
		PagingProDto iPrPaging = new PagingProDto(req.getParameter("iIndex"),
				req.getParameter("iPageStartNum"),
				req.getParameter("iListCnt"));
		
		logger.info("====================== 진행 예정인 프로젝트 목록 조회 =========================");
		logger.info("프로젝트 조회할 멤버 아이디 : "+mem_id);
		logger.info("====================================================================");
		
		ProjectDto gDto = new ProjectDto();
		gDto.setPaging(gPrPaging);
		gDto.setMem_id(mem_id);
		gDto.setPr_name(pr_name);
		
		ProjectDto iDto = new ProjectDto();
		iDto.setPaging(iPrPaging);
		iDto.setMem_id(mem_id);
		iDto.setPr_name(pr_name);
		
		List<Map<String, String>> GPrlist = new ArrayList<Map<String, String>>();
		List<Map<String, String>> IPrlist = new ArrayList<Map<String, String>>();
		
		GPrlist = service.myTodoGPrListSelect(gDto);
		gPrPaging.setTotal(service.myTodoGpTotalcount(gDto));
		IPrlist = service.myTodoIPrListSelect(iDto);
		iPrPaging.setTotal(service.myTodoIpTotalcount(iDto));
		
		model.addAttribute("gPrList", GPrlist);
		model.addAttribute("gPaging", gPrPaging);
		model.addAttribute("iPrList", IPrlist);
		model.addAttribute("iPaging", iPrPaging);
		model.addAttribute("pr_name", pr_name);
		
		return "project/todoProList";
	}
	
	// 진행완료인 프로젝트 목록 출력
	@RequestMapping(value="/goDoneSelect.do", method={RequestMethod.GET,RequestMethod.POST})
	public String goDoneSelect(HttpSession session, Model model, HttpServletRequest req){
		String mem_id = (String)session.getAttribute("mem_id");
		String pr_name = req.getParameter("pr_name");
		
		
		PagingProDto gPrPaging = new PagingProDto(req.getParameter("gIndex"),
														 req.getParameter("gPageStartNum"),
														 req.getParameter("gListCnt"));
		PagingProDto iPrPaging = new PagingProDto(req.getParameter("iIndex"),
														req.getParameter("iPageStartNum"),
														req.getParameter("iListCnt"));
		
		logger.info("====================== 진행 완료인 프로젝트 목록 조회 =========================");
		logger.info("프로젝트 조회할 멤버 아이디 : "+mem_id);
		logger.info("====================================================================");
		
		ProjectDto gDto = new ProjectDto();
		gDto.setPaging(gPrPaging);
		gDto.setMem_id(mem_id);
		gDto.setPr_name(pr_name);
		
		ProjectDto iDto = new ProjectDto();
		iDto.setPaging(iPrPaging);
		iDto.setMem_id(mem_id);
		iDto.setPr_name(pr_name);
		
		List<Map<String, String>> GPrlist = new ArrayList<Map<String, String>>();
		List<Map<String, String>> IPrlist = new ArrayList<Map<String, String>>();
		
		GPrlist = service.myDidGPrListSelect(gDto);
		gPrPaging.setTotal(service.myDidGpTotalcount(gDto));
		IPrlist = service.myDidIPrListSelect(iDto);
		iPrPaging.setTotal(service.myDidIpTotalcount(iDto));
		
		model.addAttribute("gPrList", GPrlist);
		model.addAttribute("gPaging",gPrPaging);
		model.addAttribute("iPrList", IPrlist);
		model.addAttribute("iPaging", iPrPaging);
		model.addAttribute("pr_name", pr_name);
		
		return "project/doneProList";
	}
	
	//오토컴플리트 처리
		@RequestMapping(value="/autoprcomplete.do" , method=RequestMethod.POST)
		@ResponseBody
		public List<Map<String, String>> autoTest (String value){
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("pr_name", value);
			
			List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
			
			lists = service.allPrSearchSelect((ProjectDto) map);
			
			return lists;
		}
		
	// 전체 프로젝트 검색
	@RequestMapping(value="/allPrSelect.do", method={RequestMethod.GET,RequestMethod.POST})
	public String allPrSelect(HttpServletRequest request, Model model, HttpServletRequest req){
		String pr_name = request.getParameter("pr_name");
		
		PagingProDto paging = new PagingProDto(req.getParameter("index"),
				 								req.getParameter("pageStartNum"),
				 								req.getParameter("listCnt"));
		
		ProjectDto dto = new ProjectDto();
		dto.setPaging(paging);
		dto.setPr_name(pr_name);
		
		logger.info("================== 전체 프로젝트 검색 목록 조회 ===================");
		logger.info("조회할 프로젝트 명 : "+pr_name);
		logger.info("=========================================================");
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		list = service.allPrSearchSelect(dto);
		paging.setTotal(service.allPrSearchTotalCount(dto));
		
		model.addAttribute("list", list);
		model.addAttribute("paging", paging);
		model.addAttribute("pr_name", pr_name);
		
		return "project/prSearchList";
	}
	
	// UUID 생성 메소드
	public String createUUID(){
		return UUID.randomUUID().toString();
	}

}
