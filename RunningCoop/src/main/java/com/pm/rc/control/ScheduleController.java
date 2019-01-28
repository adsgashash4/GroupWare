package com.pm.rc.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.pm.rc.dto.ProjectDto;
import com.pm.rc.dto.ScheduleDto;
import com.pm.rc.model.service.ScheduleService;

@Controller
public class ScheduleController {

	Logger logger = LoggerFactory.getLogger(ScheduleController.class);
	
	@Autowired
	private ScheduleService scheduleService;
	
	//달력 띄우기
	@RequestMapping(value = "/viewSchedule.do")
	public String viewSchedule(HttpSession session, HttpServletRequest req){
		logger.info("viewSchedule실행");
		String mem_id = (String)session.getAttribute("mem_id");
		List<ScheduleDto> list = new ArrayList<ScheduleDto>();
		list = scheduleService.mySchSelect(mem_id);
		req.setAttribute("list", list);
		return "schedule/userSchedule";
	}
	
	//지정일 일정목록 조회
	@RequestMapping(value = "/dailySchSelect.do", method = RequestMethod.POST)
	@ResponseBody
	public List<ScheduleDto> dailySchSelect(HttpSession session, HttpServletRequest req, String date){
		logger.info("DailySchSelect실행");
		Map<String, String> map = new HashMap<String, String>();
		String mem_id = (String)session.getAttribute("mem_id");
		map.put("mem_id", mem_id);
		map.put("date", date);
		List<ScheduleDto> list = new ArrayList<ScheduleDto>();
		list = scheduleService.dailySchSelect(map);
		return list;
	}
	
	//일정 상세정보 조회
	@RequestMapping(value = "/detailSchedule.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ScheduleDto> detailSchedule(HttpServletRequest req){
		logger.info("mySchView실행");
		String sch_seq = req.getParameter("sch_seq");
		Map<String, ScheduleDto> map = new HashMap<String, ScheduleDto>();
		ScheduleDto dto = new ScheduleDto();
		dto = scheduleService.mySchView(sch_seq);
		map.put("dto", dto);
		return map;
	}
	
	//일정등록
	@RequestMapping(value = "/insertSchedule.do", method = RequestMethod.POST)
	public String insertSchedule(ScheduleDto dto){
		logger.info("insertSchedule실행");
		boolean isc = scheduleService.schInsert(dto);
		if(isc == false){
			return "schedule/error";
		}else{
			String year = dto.getSch_startDate().substring(0, 4);
			String month = dto.getSch_startDate().substring(5, 7);
			return "redirect:/viewSchedule.do?year="+year+"&month="+month;
		}
	}
	
	//일정수정
	@RequestMapping(value = "/modifySchedule.do", method = RequestMethod.POST)
	public String modifySchedule(ScheduleDto dto){
		logger.info("modifySchedule실행");
		boolean isc = scheduleService.schModify(dto);
		if(isc == false){
			return "schedule/error";
		}else{
			String year = dto.getSch_startDate().substring(0, 4);
			String month = dto.getSch_startDate().substring(5, 7);
			return "redirect:/viewSchedule.do?year="+year+"&month="+month;
		}
	}
	
	//일정삭제
	@RequestMapping(value = "/deleteSchedule.do", method = RequestMethod.GET)
	public String deleteSchedule(String sch_seq){
		logger.info("deleteSchedule실행");
		boolean isc = scheduleService.schDelete(sch_seq);
		if(isc == false){
			return "schedule/error";
		}else{
			return "redirect:/viewSchedule.do";
		}
	}
	
	//팀일정화면이동(PM권한)
	@RequestMapping(value = "/viewTeamSchedule.do")
	public String viewTeamSchedule(HttpServletRequest req){
		logger.info("viewTeamSchedule실행");
		String pr_id = req.getParameter("pr_id");
		List<ScheduleDto> list = new ArrayList<ScheduleDto>();
		list = scheduleService.teamSchSelect(pr_id);
		req.setAttribute("list", list);
		req.setAttribute("pr_id", pr_id);
		logger.info("viewSchedule실행");
		return "schedule/teamSchedule";
	}	
	
	//지정일 팀일정 목록 조회
	@RequestMapping(value = "/teamDailySchSelect.do")
	@ResponseBody
	public List<ScheduleDto> teamDailySchSelect(String date, String pr_id){
		logger.info("teamDailySchSelect실행");
		Map<String, String> map = new HashMap<String, String>();
		map.put("pr_id", pr_id);
		map.put("date", date);
		List<ScheduleDto> list = new ArrayList<ScheduleDto>();
		list = scheduleService.teamDailySchSelect(map);
		System.out.println(list.size());
		return list;
	}
	
	//팀일정 상세정보 조회
	@RequestMapping(value = "/detailTeamSchedule.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ScheduleDto> detailTeamSchedule(HttpServletRequest req){
		logger.info("detailTeamSchedule실행");
		String sch_seq = req.getParameter("sch_seq");
		Map<String, ScheduleDto> map = new HashMap<String, ScheduleDto>();
		ScheduleDto dto = new ScheduleDto();
		dto = scheduleService.teamSchView(sch_seq);
		map.put("dto", dto);
		return map;
	}
	
	//팀일정 등록
	@RequestMapping(value = "/insertTeamSchedule.do", method = RequestMethod.POST)
	public String insertTeamSchedule(ScheduleDto dto){
		logger.info("insertSchedule실행");
		boolean isc = scheduleService.teamSchInsert(dto);
		if(isc == false){
			return "schedule/error";
		}else{
			String year = dto.getSch_startDate().substring(0, 4);
			String month = dto.getSch_startDate().substring(5, 7);
			String pr_id = dto.getPr_id();
			return "redirect:/viewTeamSchedule.do?pr_id="+pr_id+"&year="+year+"&month="+month;
		}
	}
	
	//팀일정 수정
	@RequestMapping(value = "/modifyTeamSchedule.do", method = RequestMethod.POST)
	public String modifyTeamSchedule(ScheduleDto dto){
		logger.info("modifyTeamSchedule실행");
		boolean isc = scheduleService.schModify(dto);
		if(isc == false){
			return "schedule/error";
		}else{
			String year = dto.getSch_startDate().substring(0, 4);
			String month = dto.getSch_startDate().substring(5, 7);
			String pr_id = dto.getPr_id();
			return "redirect:/viewTeamSchedule.do?pr_id="+pr_id+"&year="+year+"&month="+month;
		}
	}
	
	//팀일정 삭제
	@RequestMapping(value = "/deleteTeamSchedule.do", method = RequestMethod.GET)
	public String deleteTeamSchedule(String pr_id, String sch_seq){
		logger.info("deleteTeamSchedule실행");
		boolean isc = scheduleService.schDelete(sch_seq);
		if(isc == false){
			return "schedule/error";
		}else{
			return "redirect:/viewTeamSchedule.do?pr_id="+pr_id;
		}
	}
	
}
