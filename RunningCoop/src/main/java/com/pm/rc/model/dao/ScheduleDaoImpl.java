package com.pm.rc.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pm.rc.dto.ProjectDto;
import com.pm.rc.dto.ScheduleDto;

@Repository
public class ScheduleDaoImpl implements ScheduleDao {
	
	private Logger logger = LoggerFactory.getLogger(ScheduleDaoImpl.class);
	private final String NAMESPACE = "com.pm.rc.scheduleMapper.";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<ScheduleDto> teamDailySchSelect(Map<String, String> map) {
		logger.info("teamDailySchSelect 실행");
		List<ScheduleDto> lists = new ArrayList<ScheduleDto>();
		lists = sqlSession.selectList(NAMESPACE+"teamDailySchSelect", map);
		return lists;
	}

	@Override
	public List<ScheduleDto> teamSchSelect(String pr_id) {
		logger.info("teamSchSelect실행");
		List<ScheduleDto> lists = new ArrayList<ScheduleDto>();
		lists = sqlSession.selectList(NAMESPACE+"teamSchSelect", pr_id);
		return lists;
	}

	@Override
	public boolean teamSchInsert(ScheduleDto dto) {
		logger.info("teamSchInsert실행");
		int n = sqlSession.insert(NAMESPACE+"teamSchInsert", dto);
		return n>0 ? true:false;
	}
	
	@Override
	public List<ScheduleDto> dailySchSelect(Map<String, String> map) {
		logger.info("dailySchSelect 실행");
		List<ScheduleDto> lists = new ArrayList<ScheduleDto>();
		lists = sqlSession.selectList(NAMESPACE+"dailySchSelect", map);
		return lists;
	}
	
	@Override
	public ScheduleDto teamSchView(String sch_seq) {
		logger.info("teamSchView실행");
		ScheduleDto dto = new ScheduleDto();
		dto = sqlSession.selectOne(NAMESPACE+"teamSchView", sch_seq);
		return dto;
	}

	@Override
	public List<ScheduleDto> mySchSelect(String mem_id) {
		logger.info("mySchSelect실행");
		List<ScheduleDto> lists = new ArrayList<ScheduleDto>();
		lists = sqlSession.selectList(NAMESPACE+"mySchSelect", mem_id);
		return lists;
	}
	
	@Override
	public ScheduleDto mySchView(String sch_seq) {
		logger.info("mySchView실행");
		ScheduleDto dto = new ScheduleDto();
		dto = sqlSession.selectOne(NAMESPACE+"mySchView", sch_seq);
		return dto;
	}

	@Override
	public boolean schInsert(ScheduleDto dto) {
		logger.info("schInsert실행");
		int n = sqlSession.insert(NAMESPACE+"schInsert", dto);
		return n>0 ? true:false;
	}

	@Override
	public boolean schModify(ScheduleDto dto) {
		logger.info("schModify실행");
		int n = sqlSession.update(NAMESPACE+"schModify", dto);
		return n>0 ? true:false;
	}

	@Override
	public boolean schDelete(String seq) {
		logger.info("schDelete실행");
		int n = sqlSession.delete(NAMESPACE+"schDelete", seq);
		return n>0 ? true:false;
	}

}
