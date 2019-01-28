package com.pm.rc.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pm.rc.dto.MemberDto;
import com.pm.rc.model.service.AccountService;

@Controller
public class AccountController {

	Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	//로그인 페이지 접속
	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public String enterMain(){
		logger.info("main실행");
		return "account/main";
	}

	//로그인 체크
	@RequestMapping(value = "/ckLogin.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> ckLogin(HttpServletRequest req, HttpSession session){
		logger.info("ckLogin실행");
		String mem_id = (String)req.getParameter("mem_id");
		String mem_pw = (String)req.getParameter("mem_pw");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("mem_id", mem_id);
		map.put("mem_pw", mem_pw);
		MemberDto dto = new MemberDto();
		dto = accountService.loginPro(map);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		String result = null;
		if(dto != null){
			if(dto.getMem_level().equals("Y")){
				session.setAttribute("mem_id", dto.getMem_id());
				session.setAttribute("mem_name", dto.getMem_name());
				session.setAttribute("mem_level", dto.getMem_level());
				result = "mgr";
			} else{
				session.setAttribute("mem_id", dto.getMem_id());
				session.setAttribute("mem_name", dto.getMem_name());
				result = "user";
			}
		}
		resultMap.put("login", result);
		return resultMap;
	}
	
	//시스템관리자 접속
	@RequestMapping(value = "/enterMgr.do")
	public String enterMgr(){
		logger.info("enterMgr실행");
		return "redirect:/grApply.do";
	}
	
	//아이디 찾기(화면이동)
	@RequestMapping(value = "/searchAccount.do")
	public String searchAccount(){
		logger.info("searchAccount 실행");
		return "account/searchId";
	}

	//아이디찾기
	@RequestMapping(value = "/searchId.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> searchId(Model model, HttpServletRequest req){
		logger.info("searchId 실행");
		String name = req.getParameter("mem_name");
		String email =req.getParameter("mem_email");
		Map<String, String> map = new HashMap<String, String>();
		map.put("mem_name", name);
		map.put("mem_email", email);
		String id = accountService.memIdSearch(map);
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("resultId", id);
		return resultMap;
	}
	
	//로그아웃
	@RequestMapping(value = "/ckLogout.do", method = RequestMethod.GET)
	public String ckLogout(HttpSession session){
		logger.info("ckLogout");
		session.invalidate();
		return "account/main";
	}

	//회원가입 페이지 접속
	@RequestMapping(value = "/goJoin.do", method = RequestMethod.GET)
	public String goJoin(){
		logger.info("goJoin실행");
		return "account/writeJoinForm";
	}

	//아이디 중복 조회
	@RequestMapping(value = "/memIdSelect.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Boolean> memIdSelect(String mem_id){
		logger.info("memIdSelect실행");
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		String isc = accountService.memIdSelect(mem_id);
		if(isc!=null){
			map.put("result", false);
		}else{
			map.put("result", true);
		}
		return map;
	}
	
	//이메일 중복 조회
	@RequestMapping(value = "/memEmailSelect.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Boolean> memEmailSelect(String mem_email){
		logger.info("memEmailSelect실행");
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		boolean isc = accountService.memEmailSelect(mem_email);
		map.put("result", isc);
		return map;
	}
	
	//본인인증 번호(세션) 삭제 : 시간 종료 or 사용자가 번호 입력할 때
	@RequestMapping(value = "/removeIdentify.do")
	@ResponseBody
	public void removeIdentify(HttpSession session){
		logger.info("removeIdentify실행");
		session.removeAttribute("identifyNum");
	}

	//본인인증 번호 확인
	@RequestMapping(value = "/ckIdentifyNum.do")
	@ResponseBody
	public Map<String, Boolean> ckIdentifyNum(HttpSession session,String input){
		logger.info("ckIdentifyNum실행");
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		String identifyNum = (String)session.getAttribute("identifyNum");
		if(input.equals(identifyNum)){
			logger.info("번호 일치, 성공");
			map.put("result",true);
		}else{
			logger.info("번호 불일치, 실패");
			map.put("result",false);
		}
		return map;
	}

	//회원가입처리
   @RequestMapping(value = "/chkJoin.do", method = RequestMethod.POST)
   public String ckJoin(HttpSession session, MemberDto dto){
      logger.info("ckJoin실행");
      boolean isc = accountService.memInsert(dto);
      if(isc == false){
         return "account/error/error";
      }else{
         session.setAttribute("mem_id", dto.getMem_id());
         session.setAttribute("mem_pw", dto.getMem_pw());
         session.setAttribute("mem_name", dto.getMem_name());
         return "redirect:/afterJoin.do";
      }
   }
	   
   //회원가입 완료 후 그룹가입신청/메인페이지 선택
   @RequestMapping(value = "/afterJoin.do")
   public String afterJoin(){
	   return "account/joinAfter";
   }

   //탈퇴 전 pm리스트 출력
   @RequestMapping(value = "/viewListPm.do")
   public String viewListPm(HttpSession session, Model model){
	   logger.info("viewListPm실행");
	   String mem_id = (String)session.getAttribute("mem_id");
	   //pm목록 보는 서비스(PM권한 위임)
	   List<Map<String, String>> pmSearchList = accountService.levelPmSelect(mem_id);
	   model.addAttribute("pmSearchList", pmSearchList);
	   return "account/listPmProject";
   }

	//탈퇴 전 gm리스트 출력
	@RequestMapping(value = "/viewListGm.do")
	public String viewListGm(HttpSession session, Model model){
		logger.info("viewListGm실행");
		String mem_id = (String)session.getAttribute("mem_id");
		//pm목록 보는 서비스(PM권한 위임)
		List<Map<String, String>> gmSearchList = accountService.levelGmSelect(mem_id);
		model.addAttribute("gmSearchList", gmSearchList);
		return "account/listGmGroup";
	}

	//탈퇴처리
	@RequestMapping(value = "/doLeaveService.do")
	@ResponseBody
	public Map<String, Boolean> doLeaveService(HttpSession session){
		logger.info("doLeaveService실행");
		
		Map<String, Boolean> map = new HashMap<String, Boolean>();

		//탈퇴전 gm,pm 재확인
		String mem_id = (String)session.getAttribute("mem_id");
		List<Map<String, String>> pmSearchList = accountService.levelPmSelect(mem_id);
		List<Map<String, String>> gmSearchList = accountService.levelGmSelect(mem_id);
		if(pmSearchList.size()!=0||gmSearchList.size()!=0){
			map.put("result", false);
		}else{
			map.put("result", true);
		}
		return map;
	}

	//서비스 탈퇴
	@RequestMapping(value = "/deleteMem.do")
	@ResponseBody
	public Map<String, Boolean> deleteMem(HttpSession session){
		logger.info("deleteMem 실행");
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		String mem_id = (String)session.getAttribute("mem_id");
		boolean isc = accountService.memDelete(mem_id);
		map.put("result", isc);
		return map;
	}
	
	//개인정보 수정(본인확인 페이지) 이동
	@RequestMapping(value = "/enterModify.do", method = {RequestMethod.POST ,RequestMethod.GET})
	public String enterModify(){
		logger.info("enterModify실행");
		return "account/enterModify";
	}
	
	//개인정보 수정(본인확인)+수정 폼으로 이동
	@RequestMapping(value = "/ckPwInfo.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Boolean> ckPwInfo(HttpSession session, String mem_pw){
		logger.info("ckPwInfo실행");
		boolean isc = false;
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		String mem_id = (String)session.getAttribute("mem_id");
		System.out.println("들어온 pw값: "+ mem_pw);
		String pw = accountService.memPwSelect(mem_id);
		if(mem_pw.equals(pw)){
			isc = true;
		}
		map.put("result", isc);
		return map;
	}
	
	//개인정보 수정페이지 이동
	@RequestMapping(value = "/writeModifyForm.do", method = RequestMethod.POST)
	public String writeModify(Model model, HttpSession session){
		logger.info("writeModifyForm실행");
		String mem_id = (String)session.getAttribute("mem_id");
		MemberDto dto = accountService.memSelect(mem_id);
		model.addAttribute("dto", dto);
		return "account/writeModifyForm";
	}
	
	//개인정보 수정
	@RequestMapping(value = "/memInfoModify.do", method = RequestMethod.POST)
	public String memInfoModify(MemberDto dto){
		logger.info("memInfoModify실행");
		boolean result = accountService.memInfoModify(dto);
		if(result==true){
			return "Group/myGrSelect";
		}else{
			return "account/error/error";
		}
	}

}
