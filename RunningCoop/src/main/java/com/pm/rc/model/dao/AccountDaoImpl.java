package com.pm.rc.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pm.rc.dto.MemberDto;

@Repository
public class AccountDaoImpl implements AccountDao {
	
	private Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);
	private final String NAMESPACE = "com.pm.rc.accountMapper.";
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public MemberDto loginPro(Map<String, String> map) {
		logger.info("loginPro실행");
		MemberDto loginDto = new MemberDto();
		loginDto = sqlSession.selectOne(NAMESPACE+"loginPro", map);
		return loginDto;
	}

	@Override
	public String memIdSelect(String mem_id) {
		logger.info("memIdSelect실행");
		String result = sqlSession.selectOne(NAMESPACE+"memIdSelect", mem_id);
		return result;
	}
	
	@Override
	public boolean memEmailSelect(String mem_email) {
		logger.info("memIdSelect실행");
		String result = sqlSession.selectOne(NAMESPACE+"memEmailSelect", mem_email);
		return result==null ? true:false;
	}

	@Override
	public boolean memInsert(MemberDto dto) {
		logger.info("memInsert실행");
		int n = sqlSession.insert(NAMESPACE+"memInsert", dto);
		return n>0 ? true:false;
	}

	@Override
	public String memIdSearch(Map<String, String> map) {
		logger.info("memIdSearch실행");
		String result = sqlSession.selectOne(NAMESPACE+"memIdSearch", map);
		return result;
	}
	
	@Override
	public boolean memIdEmailChk(Map<String, String> map) {
		logger.info("memIdEmailChk실행");
		int result = sqlSession.selectOne(NAMESPACE+"memIdEmailChk", map);
		System.out.println("<<<<result>>>>:"+result);
		return result==1 ? true:false;
	}
	
	@Override
	public String memPwSelect(String mem_id) {
		logger.info("ckPwInfo실행");
		String mem_pw = sqlSession.selectOne(NAMESPACE+"memPwSelect", mem_id);
		return mem_pw;
	}

	@Override
	public boolean memPwModify(Map<String, String> map) {
		logger.info("memPwModify실행");
		int n = sqlSession.update(NAMESPACE+"memPwModify", map);
		return n>0 ? true:false;
	}
	
	@Override
	public MemberDto memSelect(String mem_id) {
		logger.info("memSelect실행");
		MemberDto dto = sqlSession.selectOne(NAMESPACE+"memSelect", mem_id);
		return dto;
	}

	@Override
	public boolean memInfoModify(MemberDto dto) {
		logger.info("memInfoModify실행");
		int n = sqlSession.update(NAMESPACE+"memInfoModify", dto);
		return n>0 ? true:false;
	}

	@Override
	public List<Map<String, String>> levelGmSelect(String mem_id) {
		logger.info("levelGmSelect실행");
		List<Map<String, String>> result;
		result = sqlSession.selectList(NAMESPACE+"levelGmSelect", mem_id);
		return result;
	}

	@Override
	public List<Map<String, String>> levelPmSelect(String mem_id) {
		logger.info("levelPmSelect실행");
		List<Map<String, String>> result;
		result = sqlSession.selectList(NAMESPACE+"levelPmSelect", mem_id);
		return result;
	}

	@Override
	public boolean memDelete_1(String mem_id) {
		logger.info("memDelete_1실행");
		int n = sqlSession.delete(NAMESPACE+"memDelete_1", mem_id);
		return n>0 ? true:false;
	}

	@Override
	public boolean memDelete_2() {
		logger.info("memDelete_2실행");
		int n = sqlSession.update(NAMESPACE+"memDelete_2");
		return n>0 ? true:false;
	}

	@Override
	public boolean memDelete_3(String mem_id) {
		logger.info("memDelete_3실행");
		int n = sqlSession.delete(NAMESPACE+"memDelete_3", mem_id);
		return n>0 ? true:false;
	}

	@Override
	public boolean memDelete_4() {
		logger.info("memDelete_4실행");
		int n = sqlSession.update(NAMESPACE+"memDelete_4");
		return n>0 ? true:false;
	}

	@Override
	public boolean memDelete_5(String mem_id) {
		logger.info("memDelete_5실행");
		int n = sqlSession.update(NAMESPACE+"memDelete_5", mem_id);
		return n>0 ? true:false;
	}

	@Override
	public boolean memDelete_6(String mem_id) {
		logger.info("memDelete_6실행");
		int n = sqlSession.delete(NAMESPACE+"memDelete_6", mem_id);
		return n>0 ? true:false;
	}

	@Override
	public boolean memDelete_7(String mem_id) {
		logger.info("memDelete_7실행");
		int n = sqlSession.delete(NAMESPACE+"memDelete_7", mem_id);
		return n>0 ? true:false;
	}

	@Override
	public boolean memDelete_8(String mem_id) {
		logger.info("memDelete_8실행");
		int n = sqlSession.update(NAMESPACE+"memDelete_8", mem_id);
		return n>0 ? true:false;
	}

}
