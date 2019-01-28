package com.pm.rc.control;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class SubFunctionController {
	
	// 검색화면으로 이동
	@RequestMapping(value="/go_SearchForm.do")
	public String goSearch(){
		return "searchUnion";
	}
	
	// 홈버튼 홈으로 이동
	@RequestMapping(value="/goHome.do")
	public String goHome(HttpSession session){
		String mem_id = (String)session.getAttribute("mem_id");

		return "redirect:/myGrSelect.do?mem_id="+mem_id;
	}
	
	// 개발자 정보 보기 화면으로 이동
	@RequestMapping(value="/developerInfo.do")
	public String goDeveloperInfo(){
		return "developerInfo";
	}
}
