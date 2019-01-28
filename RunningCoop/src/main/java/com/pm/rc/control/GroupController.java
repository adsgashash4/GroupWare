package com.pm.rc.control;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
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
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pm.rc.dto.GroupBoardDto;
import com.pm.rc.dto.GroupDto;
import com.pm.rc.dto.MemberDto;
import com.pm.rc.dto.PagingDto;
import com.pm.rc.dto.PagingProDto;
import com.pm.rc.model.service.AccountService;
import com.pm.rc.model.service.GroupService;
import com.pm.rc.model.service.ManagerService;

//4. dispatcher에 의해 연결된 Class @Controller 생성
@Controller
public class GroupController implements ServletConfigAware {
	
	/**
	 * 채팅에 관련된 정보를 담기 위해 Application 객체 생성
	 */
	private ServletContext servletContext;
	
	public String createUUID(){
		return UUID.randomUUID().toString();
	}
	
	//8. log처리를 위한 logger객체 생성
	Logger logger = LoggerFactory.getLogger(GroupController.class);
	
	private String GroupId = "";
	
	// Group 서비스
	@Autowired
	private GroupService service;
	
	// ManaGer 서비스
	@Autowired
	private ManagerService managserService;
	
	// Mail 서비스
/*	@Autowired
	private JavaMailSender mailSend;*/
	
	@Autowired
	private AccountService accountService;
	/*
	 *Application 객체를 사용하기 위해 객체를 담아줌
	 * */
	@Override
	public void setServletConfig(ServletConfig servletConfig) {
		servletContext = servletConfig.getServletContext();
	}
	
	// 그룹선택 초기 화면 
	@RequestMapping(value= "/myGrSelect.do" , method=RequestMethod.GET)
	public String myGrSelect(Model model, HttpSession session){
		
		session.removeAttribute("gr_level"); 
		session.removeAttribute("gr_id");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("mem_id", (String)session.getAttribute("mem_id"));
		logger.info("=================== 그룹 선택 화면 이동 =======================");
		
		List<GroupDto> lists = service.myGrSelect(map);
		List<GroupDto> watiLists = service.waitGrSelect(map);
		
		model.addAttribute("lists" , lists);
		model.addAttribute("watiLists" , watiLists);
		
		return "Group/myGrSelect";
	}
	
	//그룹선택하여 정보 확인 및 그룹관리 화면
	@RequestMapping(value="/grselect.do", method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody	
	public Map<String, String> grDetailSelect(Model model, HttpServletRequest request){
		Map<String,String> map = new HashMap<String, String>();
		map.put("gr_id", request.getParameter("gr_id"));
		logger.info("그룹정보 띄우기");
		Map<String, String> map2 = new HashMap<String, String>();
		map2 = service.grDetailSelect(map);
		
		return map2;
	}
	
	// 승인대기 그룹 삭제후 다시 승인대기 그룹 뿌려주는 메소드
	@RequestMapping(value="/waitGrSelect.do", method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public boolean waitGrSelect(Model model , HttpServletRequest req){
		String gr_id = req.getParameter("gr_id");
		boolean isc = false;
		isc = service.waitGrSelectDelete(gr_id);
			return isc;
	}
	//그룹관리 화면
	@RequestMapping(value="/grmodify.do" , method=RequestMethod.GET)
	public String 	grmodifyMove(Model model, HttpServletRequest request, HttpSession session){
		String gr_id = request.getParameter("gr_id");
		
		logger.info("그룹상세정보 띄우기");
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("gr_id", gr_id);
		
		Map<String, String> lists = new HashMap<String, String>();
		lists = service.grDetailSelect(map);
		
		//회원탈퇴할때 바로 그룹삭제하기 위해 세션에 그룹아이디 넣기(세션값에 값이 없을 경우)
		String ss_gr_id = (String)session.getAttribute("gr_id");
		
		if(ss_gr_id == null){
			session.setAttribute("gr_id", gr_id);
		}
		model.addAttribute("grSelect" , lists);
		
		return "Group/groupInfoEdit";
	}
	
	//그룹 정보 수정 하는 곳
	@RequestMapping(value="/grInfoEdit.do", method= RequestMethod.POST)
	public String grmodify (Model model, HttpServletRequest request){
		String gr_id = request.getParameter("gr_id");
		
		logger.info("============== 그룹 수정 ===================");
		logger.info("수정할 그룹 아이디 :"+gr_id);
		logger.info("========================================");
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("gr_id", gr_id);
		map.put("gr_goal", request.getParameter("gr_goal"));
		map.put("gr_searchyn", request.getParameter("gr_searchyn"));
		map.put("gr_joinyn", request.getParameter("gr_joinyn"));
		map.put("gr_img", request.getParameter("gr_img"));
		
		service.grModify(map);
		
		return "redirect:/grmodify.do?gr_id="+gr_id;
	}
	
	// 전체 검색 창 : 그룹명으로 검색하기 + 페이징
	@RequestMapping(value="/allGrSelect.do", method={RequestMethod.POST ,RequestMethod.GET})
	public String allGrSelect (Model model , HttpServletRequest request) {
		
		String gr_name = request.getParameter("gr_name");
		
		logger.info("그룹검색하기 그룹이름이름으로");
		
		PagingProDto paging = new PagingProDto(request.getParameter("index"),
									     request.getParameter("pageStartNum"),
									     request.getParameter("listCnt"));
		
		GroupDto dto = new GroupDto();
		dto.setPaging(paging);
		dto.setGr_name(gr_name);
		
		List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
		lists = service.allGroupSearchSelect(dto);
		
		paging.setTotal(service.allGroupSearchSelectCount(dto));
		
		model.addAttribute("lists",lists);
		model.addAttribute("paging",paging);
		model.addAttribute("gr_name",gr_name);
		
		lists.forEach(System.err::println);
		
		return "Group/grSearchList";
	}
	// 멤버 관리 화면
	@RequestMapping(value="/memModi.do" , method={RequestMethod.GET,RequestMethod.POST})
	public String grMemSelect(String gr_id,Model model) {
		logger.info("그룹멤버리스트 출력");
		List<MemberDto> lists = service.grMemSelect(gr_id);
		model.addAttribute("memList",lists);
		model.addAttribute("grid",gr_id);
		List<MemberDto> lists2 = service.grWaitList(gr_id);
		model.addAttribute("grWait" , lists2);
		
		return "Group/group_MemEdit";	
	}	

	// 그룹 가입신청화면으로 연결
	@RequestMapping(value="/grListChild.do" , method=RequestMethod.GET)
	public String test (HttpServletRequest req , Model model){
		logger.info("그룹리스트 에서 ");
		model.addAttribute("grid", req.getParameter("gr_id"));
		return "Group/grJoinRequest";
	}
	
	// 그룹 가입신청 시작
	@RequestMapping(value="/grWaitInsert.do" , method={RequestMethod.POST,RequestMethod.GET})
	public String grWaitInsert(String mem_id , String gr_id , String wait_content, String gr_name, Model model){
		logger.info("그룹가입신청 시작");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("mem_id", mem_id);
		map.put("gr_id", gr_id);
		map.put("wait_content", wait_content);
		boolean n = service.grWaitInsert(map);
		model.addAttribute("result", n);
		if(n=true){
			return "redirect:/allGrSelect.do?gr_name="+gr_name;
		}else{
			return "account/error/error";
		}
		
	}
	// 그룹가입신청 수락
	@RequestMapping(value="/groupAccept.do", method=RequestMethod.GET )
	public String grWaitAccept (HttpServletRequest req){
		String gr_id = req.getParameter("gr_id");
		String mem_id = req.getParameter("mem_id");
		
		logger.info("그룹 가입신청 수락 시작");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("gr_id", gr_id);
		map.put("mem_id", mem_id);
		
		boolean isc = false;
		isc = service.grMemInsert(map);
		
		if(isc){
			logger.info("그룹 가입신청 수락 성공");
		} else {
			logger.info("그룹 가입신청 수락 실패");
		}
		
		return "redirect:/memModi.do?gr_id="+gr_id;
	}
	
	// 그룹가입신청 거절
	@RequestMapping(value="/grouprefusal.do", method=RequestMethod.GET)
	public String groupAccept (HttpServletRequest req){
		String gr_id = req.getParameter("gr_id");
		String mem_id = req.getParameter("mem_id");
		
		logger.info("그룹거절 시작");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("mem_id", mem_id);
		map.put("gr_id", gr_id);
		
		boolean n = service.grMemReject(map);
		
		if(n){
			logger.info("그룹 가입 거절 성공");
		} else {
			logger.info("그룹 가입 거절 실패");
		}
		
		return "redirect:/memModi.do?gr_id="+gr_id;
	}
	
	// 그룹 게시판 목록 출력함
	@RequestMapping(value="/gbListSelect.do")
	public String gbListSelect(String gr_id , Model model){
		logger.info("그룹게시판 목록출력");
		GroupId = gr_id;
		List<GroupBoardDto> lists = service.gbListSelect(gr_id);
		model.addAttribute("gblist" , lists);
		return "Group/grboradList";
	}
	
	// 그룹 게시글 다중삭제
	@RequestMapping(value="/bordeMultDelet.do" ,method=RequestMethod.GET)
	public String grbdMultDelet(String[] cnkUuid ){
		logger.info("그룹게시판 다중삭제");
		service.gboardDelete(cnkUuid);
		
		return "redirect:/gbListSelect.do?gr_id="+GroupId;
	}
	
	//그룹 생성으로 연결합니다
	@RequestMapping(value="/showGrCreate.do")
	public String groupCreateChilde(){
		return "redirect:/myGrSelect.do";
	}
	
	// 그룹 생성시작
	@RequestMapping(value="/groupCreate.do" , method=RequestMethod.POST)
	public String grCreate(Map<String, String> map , HttpServletRequest req , Model model){
		logger.info("그룹생성 시작");
		Date date = new Date();
	      SimpleDateFormat dateForm = new SimpleDateFormat("yyMMdd");
	      String id_1 = "GR";
	      String id_2 = dateForm.format(date).toString();
	      String uuid = createUUID();
	      String id_3 = uuid.substring(uuid.lastIndexOf("-")+9);
	      String gr_id = id_1+id_2+id_3;
	      
	      logger.info("================================"+req.getParameter("gr_joinyn")+"|||||"+req.getParameter("gr_searchyn")+"||"+req.getParameter("gr_img")+"================================");
	      logger.info("================================"+gr_id+"================================");
	      
	      map.put("gr_id", gr_id);
	      map.put("gr_name", req.getParameter("gr_name"));
	      map.put("gr_goal", req.getParameter("gr_goal"));
	      map.put("mem_id", req.getParameter("mem_id"));
	      map.put("gr_img",req.getParameter("gr_img"));
	      map.put("gr_searchyn",req.getParameter("gr_searchyn"));
	      map.put("gr_joinyn",req.getParameter("gr_joinyn"));
	      
	      boolean n = service.grInsert(map);
	   
			model.addAttribute("result", n);
			if(n=true){
				return "redirect:/myGrSelect.do";
			}else{
				return "account/error/error";
			}
	      
	}
	
	
	//오토컴플리트 처리
	@RequestMapping(value="/autoauto.do" , method=RequestMethod.POST)
	@ResponseBody
	public List<GroupDto> autoTest (String value){
		Map<String, String> map = new HashMap<String, String>();
		System.err.println("ㅎㅎ2");
		map.put("gr_name", value);
		List<GroupDto> lists = service.allGrSelect(map);
		return lists;
	}
	
	// 그룹삭제 
	@RequestMapping(value="/groupDelete.do" , method=RequestMethod.GET)
	public String groupDelete(String gr_id,HttpSession session){
		logger.info("그룹삭제 시작합니다");
		
		boolean isc = false;
		isc = service.groupDelete(gr_id);
		
		if(isc){
			return "redirect:/myGrSelect.do";
		}else{
			return "account/error/error";
		}
	}
	//그룹멤버 삭제
	@RequestMapping(value="/groupMemDelete.do" , method=RequestMethod.GET)
	public String groupMemDelete(String gr_id , String memID){
		logger.info("그룹멤버 삭제하기 시작합니다 ");
		Map<String, String> map = new HashMap<String, String>();
		map.put("gr_id", gr_id);
		map.put("mem_id", memID);
		boolean isc = false;
		isc = service.grMemDelete(map);
		return "redirect:/memModi.do?gr_id="+gr_id;
	}
	
	// 회원가입 후에 그룹가입신청 연결
	@RequestMapping(value="/groupGoGo.do")
	public String groupGoGo(){
		return "account/groupJoinRequest";
	}
	
	// WebSocket 채팅 접속했을 때
	@RequestMapping(value="/socketOpen.do" , method=RequestMethod.GET)
	public String socketOpen(HttpSession session, Model model){
		logger.info("socketOpen 소켓 화면 이동 1)리스트에 접속자 값 넣기");
		String mem_id = (String)session.getAttribute("mem_id");
		String gr_id = (String)session.getAttribute("gr_id");
		HashMap<String, String> chatList = (HashMap<String, String>)servletContext.getAttribute("chatList");
		if(chatList == null){
			chatList = new HashMap<String, String>();
			chatList.put(mem_id, gr_id);
			servletContext.setAttribute("chatList", chatList);
		}else{
			chatList.put(mem_id, gr_id);
			servletContext.setAttribute("chatList", chatList);
		}
		logger.info("socketOpen 소켓 화면 이동 2)리스트 값 전달");
		
		return "groupChat";
	}
	
	//WebSocket 채팅 종료했을 때
	@RequestMapping(value = "/socketOut.do", method={RequestMethod.GET, RequestMethod.POST})
	public void socketOut(HttpSession session){
		logger.info("socketOut 소켓에서 나오기");
		String mem_id = (String)session.getAttribute("mem_id");
		HashMap<String, String> chatList = (HashMap<String, String>)servletContext.getAttribute("chatList");
		System.out.println("기존 접속 회원 리스트:"+chatList);
		if(chatList != null){
			chatList.remove(mem_id);
		}
		System.out.println("갱신 후 접속 회원 리스트:"+chatList);
		servletContext.setAttribute("chatList", chatList);
		
	}
	
	//채팅 접속자 리스트 출력
	@RequestMapping(value = "/viewChatList.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Map<String, String>> viewChatList(){
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		Map<String, String> chatList = (HashMap<String, String>)servletContext.getAttribute("chatList");
		map.put("list", chatList);
		return map;
	}
	
	@RequestMapping(value="/createGrManagerCh.do",method=RequestMethod.GET)
	public String createGrManagerCh(String mem_id, Model model, HttpSession session){
		model.addAttribute("mem_id",mem_id);
		String gr_id = (String)session.getAttribute("gr_id");
		List<MemberDto> lists = service.grMemSelect(gr_id);
		model.addAttribute("memlists" , lists);
		
		return "Group/grManagerChange";
	}
	
	//그룹 관리자 위임
	@RequestMapping(value="/grMgChange.do", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Boolean> grMgChange(HttpServletRequest req, HttpSession session){
		Map<String, String> newmap = new HashMap<String, String>();
		Map<String, String> oldmap = new HashMap<String, String>();
		String gr_id = (String)session.getAttribute("gr_id");
		String mem_id = req.getParameter("mem_id");
		String mem_id2 = req.getParameter("mem_id2");
		newmap.put("gr_id", gr_id);
		newmap.put("mem_id",mem_id);
		
		oldmap.put("gr_id",gr_id);
		oldmap.put("mem_id",mem_id2);
		
		boolean isc1 = false;
		boolean isc2 = false;
		isc1 = service.grManagerChange(newmap);
		isc2 = service.grManagerChange2(oldmap);

		Map<String, Boolean> map = new HashMap<String, Boolean>();
		
		if(isc1&&isc2){
			map.put("result", true);
		}else{
			map.put("result", false);
		}
		return map;
	}
	
	// 그룹 중복가입 처리
	@RequestMapping(value="/grCheck.do" , method=RequestMethod.POST)
	@ResponseBody
	public int grCheck(HttpServletRequest req, Model model){
		logger.info("grCheck 시작");
		Map<String, String> map = new HashMap<String, String>();
		map.put("mem_id", req.getParameter("mem_id"));
		map.put("gr_id", req.getParameter("gr_id"));
		int n = service.groupCheck(map);
		return n;
	}
	
	@RequestMapping(value="/grNameCk.do", method=RequestMethod.POST)
	@ResponseBody
	public int grNameCk(HttpServletRequest req){
		logger.info("=========그룹생성 신청 그룹이름 중복체크 시작 ========");
		int n = service.grNameCheck(req.getParameter("gr_name"));
		return n;
	}
	
	// 그룹 게시판 목록 출력 시작
	@RequestMapping(value="/grBoradList.do" , method={RequestMethod.GET, RequestMethod.POST})
	public String grBoradList(Model model,HttpSession session, HttpServletRequest req){
		
		PagingProDto paging = new PagingProDto(req.getParameter("index"), req.getParameter("pageStartNum"), req.getParameter("listCnt"));
		String gr = (String)session.getAttribute("gr_id");
		
		String br_title = req.getParameter("br_title");
		
		GroupBoardDto gDto = new GroupBoardDto();
		gDto.setBr_title(br_title);
		gDto.setPaging(paging);
		gDto.setGr_id(gr);
		
		logger.info("=========그룹게시판 목록 시작 ========");
		logger.info("그룹아이디 받아옴 값="+gr);
		
		List<Map<String, String>> lists = service.grBoradList(gDto);
		paging.setTotal(service.grBoradListCnt(gDto));
		
		model.addAttribute("grlists", lists);		
		model.addAttribute("paging" , paging);
		model.addAttribute("br_title", br_title);
		
		return "Group/groupBoard";
	}
	
	@RequestMapping(value="groupImg.do", method=RequestMethod.POST)
	@ResponseBody
	public int groupImgCk (String gr_id){
		logger.info("=========그룹선택시 이미지 출력 시작 ========");
		int n = service.groupImg(gr_id);
	return n;	
	}
	
	// 그룹게시판 글 작성
	@RequestMapping(value="/grBoradWriteForm.do" , method={RequestMethod.POST, RequestMethod.GET})
	public String grBoradWriteForm(){
		logger.info("================== 게시글 작성 페이지 이동 ==================");
		return "Group/groupBoardWrite";
	}
	
	// 게시글 작성 폼
		@RequestMapping(value="/grboardWrite.do", method = RequestMethod.POST)
		public String doWriteForm(MultipartHttpServletRequest multipartRequest, HttpSession session){
			String br_title = multipartRequest.getParameter("br_title");
			String br_content = multipartRequest.getParameter("br_content");
			String mem_id = (String) session.getAttribute("mem_id");
			String gr_id = (String)session.getAttribute("gr_id");
			//UUID 생성 메소드
			String uuid = createUUID();
			int indexnum = uuid.lastIndexOf("-");
			String br_uuid = uuid.substring(indexnum+1);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("br_uuid", br_uuid);

			MultipartFile file = multipartRequest.getFile("gatt_name");
			
			logger.info("file: "+file);

			String oldFileName = file.getOriginalFilename();
			if (oldFileName.length()>0){
				String gatt_path = "C:\\RC_fileSave\\";

				String fuuid = createUUID();
				int indexNum = fuuid.lastIndexOf("-");

				String gatt_size = ""+file.getSize();

				String newFileName = fuuid.substring(indexNum+1) + oldFileName;

				// 첨부파일 실제경로 저장
				try {
					file.transferTo(new File(gatt_path + newFileName));
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}

				logger.info("=============== 공지게시판 첨부파일 추가 ===================");
				logger.info("첨부파일 명 : "+oldFileName);
				logger.info("첨부파일 크기 : "+gatt_size);
				logger.info("첨부파일 경로 : "+gatt_path);
				logger.info("첨부파일 실제이름 : "+newFileName);
				logger.info("====================================================");


				map.put("gatt_name", oldFileName);
				map.put("gatt_rname", newFileName);
				map.put("satt_size", gatt_size);
				map.put("gatt_path", gatt_path);

			}

			map.put("br_title", br_title);
			map.put("br_content", br_content);
			map.put("mem_id", mem_id);
			map.put("gr_id", gr_id);

			boolean isc = false;
			isc = service.grBoardInsert(map);

			if(isc == true){
				logger.info("문의게시판 게시글 등록 성공");
			} else {
				logger.info("문의게시판 게시글 등록 실패");
			}

			return "redirect:/grBoradList.do";
		}
		
		// 문의 게시판 상세보기 
		@RequestMapping(value="/grBoardView.do", method = RequestMethod.GET)
		public String boardView(Model model, HttpServletRequest request, HttpSession session){

			String br_uuid = request.getParameter("br_uuid");
			String sessionId = (String) session.getAttribute("mem_id");
			Map<String, String> uuid = new HashMap<String, String>();
			uuid.put("br_uuid", br_uuid);

			Map<String, String> view = new HashMap<String, String>();
			view = service.gbViewSelect(uuid);

			Map<String, String> map = new HashMap<String, String>();
			map.put("br_uuid", br_uuid);

			logger.info("================== 공지 게시글 보기 ==================");
			logger.info("현재 접속 중인 세션 : "+sessionId);
			logger.info("조회할 게시글 번호 : "+br_uuid);
			logger.info("================================================");

			Map<String, String> list = new HashMap<String, String>();
//			list = service.sysAttachSelect(map);
			
			model.addAttribute("view", view);
			model.addAttribute("mem_id", sessionId);

//			if(list != null){
//				model.addAttribute("attach", list);
//			}

			return "Group/groupBoardView";
		}
		

		// 수정페이지 이동
		@RequestMapping(value="/grboardEditMove.do", method = RequestMethod.GET)
		public String boardEditPageMove(HttpServletRequest request, Model model){
			String br_uuid = request.getParameter("br_uuid");
			
			logger.info("===================== 문의 게시글 수정 폼 이동 ======================");
			logger.info("수정할 게시글 아이디 : "+br_uuid);
			logger.info("============================================================");

			Map<String, String> map = new HashMap<String, String>();
			map.put("br_uuid", br_uuid);

			Map<String, String> view = new HashMap<String, String>();
			view = service.grEditBoardViewSelect(map);

//			Map<String, String> attach = null;
//			attach = service.sysAttachSelect(map);

			model.addAttribute("view", view);
//			model.addAttribute("attach", attach);

			return "Group/groupBoardEdit";
		}
	
		// 게시글 수정
		@RequestMapping(value="/grBoardEdit.do", method = RequestMethod.POST)
		public String BoardEdit(MultipartHttpServletRequest multipartRequest){
			String br_uuid = multipartRequest.getParameter("br_uuid");
			String br_title = multipartRequest.getParameter("br_title");
			String br_content = multipartRequest.getParameter("br_content");

			logger.info("=============== 문의 게시판 : 게시글 수정 =========================");
			logger.info("수정할 게시글 아이디 : "+br_uuid);
			logger.info("수정할 게시글 제목 : "+br_title);
			logger.info("수정할 게시글 내용 : "+br_content);
			logger.info("===========================================================");

			Map<String, String> map = new HashMap<String, String>();
			map.put("br_uuid", br_uuid);

//			MultipartFile file = multipartRequest.getFile("satt_name");

//			if (!file.getOriginalFilename().equals("")){
//				String savePath = "C:\\RC_fileSave\\";
//
//				// 기존 파일 삭제
//				Map<String, String> originFile = new HashMap<String, String>();
//				originFile = service.sysAttachSelect(map);
//
//				File delFile = new File(originFile.get("SATT_PATH")+originFile.get("SATT_RNAME"));
//				delFile.delete();
//
//				// 새 파일 등록
//				String fuuid = createUUID();
//				int indexNum = fuuid.lastIndexOf("-");
//
//				String oldFileName = file.getOriginalFilename();
//				String satt_size = ""+file.getSize();
//
//				String newFileName = fuuid.substring(indexNum+1) + oldFileName;
//
//				// 첨부파일 실제경로 저장
//				try {
//					file.transferTo(new File(savePath + newFileName));
//				} catch (IllegalStateException | IOException e) {
//					e.printStackTrace();
//				}
//
//				logger.info("=============== 공지게시판 첨부파일 추가 ===================");
//				logger.info("첨부파일 명 : "+oldFileName);
//				logger.info("첨부파일 크기 : "+satt_size);
//				logger.info("첨부파일 경로 : "+savePath);
//				logger.info("첨부파일 실제이름 : "+newFileName);
//				logger.info("====================================================");
//
//				map.put("satt_name", oldFileName);
//				map.put("satt_rname", newFileName);
//				map.put("satt_size", satt_size);
//				map.put("satt_path", savePath);
//
//			}

			map.put("br_title", br_title);
			map.put("br_content", br_content);
			
			// 비밀글 여부
//			String scrpw = multipartRequest.getParameter("sbr_pw");
//			String scryn = multipartRequest.getParameter("scryn");
//			if(scryn.equals("Y")){
//				map.put("sbr_scryn", scryn);
//				map.put("sbr_pw", scrpw);
//			} else {
//				map.put("sbr_scryn", scryn);
//			}
			
			// 서비스 실행
			boolean isc = false;

			isc = service.grBoardEdit(map);
			
			if(isc == true){
				logger.info("문의게시판 게시글 수정 성공");
			} else {
				logger.info("문의게시판 게시글 수정 실패");
			}

			return "redirect:/grBoardView.do?br_uuid="+br_uuid;
		}
		
		// 게시글 삭제
		@RequestMapping(value="/grBoardDelete.do", method = RequestMethod.GET)
		public String BoardDelete(HttpServletRequest request){

			String br_uuid = request.getParameter("br_uuid");
//			Map<String, String> uuid = new HashMap<String, String>();
//			uuid.put("br_uuid", br_uuid);

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

			boolean isc = false;
			isc = service.grBoardDelete2(br_uuid);
			
			if(isc == true){
				logger.info("문의 게시글 삭제 성공");
			} else {
				logger.info("문의 게시글 삭제 실패");
			}

			return "redirect:/grBoradList.do";
		}
		
		//탈퇴처리
		@RequestMapping(value = "/groupOut.do", method=RequestMethod.POST)
		@ResponseBody
		public Map<String, Boolean> grdoLeaveService(HttpSession session){
			logger.info("groupOut실행");
			
			Map<String, Boolean> map = new HashMap<String, Boolean>();

			//탈퇴전 gm,pm 재확인
			String mem_id = (String)session.getAttribute("mem_id");
			List<Map<String, String>> pmSearchList = accountService.levelPmSelect(mem_id);
			if(pmSearchList.size()!=0){
				map.put("result", false);
			}else{
				map.put("result", true);
			}
			return map;
		}

		//서비스 탈퇴
		@RequestMapping(value = "/groupDeleteMem.do")
		@ResponseBody
		public Map<String, Boolean> deleteMem(HttpSession session){
			logger.info("deleteMem 실행");
			Map<String, Boolean> map = new HashMap<String, Boolean>();
			String mem_id = (String)session.getAttribute("mem_id");
			boolean isc = accountService.memDelete(mem_id);
			map.put("result", isc);
			return map;
		}
	
}
