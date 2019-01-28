package com.pm.rc.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pm.rc.dto.MemberDto;
import com.pm.rc.model.dao.AccountDao;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao accountDao;
	
	@Override
	public MemberDto loginPro(Map<String, String> map) {
		return accountDao.loginPro(map);
	}

	@Override
	public String memIdSelect(String mem_id) {
		return accountDao.memIdSelect(mem_id);
	}
	
	@Override
	public boolean memEmailSelect(String mem_email) {
		return accountDao.memEmailSelect(mem_email);
	}

	@Override
	public boolean memInsert(MemberDto dto) {
		return accountDao.memInsert(dto);
	}

	@Override
	public String memIdSearch(Map<String, String> map) {
		return accountDao.memIdSearch(map);
	}
	
	@Override
	@Transactional
	public boolean memPwModify(Map<String, String> map) {
		boolean isc = accountDao.memIdEmailChk(map);
		if(isc==true){
			accountDao.memPwModify(map);
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public String memPwSelect(String mem_id) {
		return accountDao.memPwSelect(mem_id);
	}

	@Override
	public MemberDto memSelect(String mem_id) {
		return accountDao.memSelect(mem_id);
	}

	@Override
	public boolean memInfoModify(MemberDto dto) {
		return accountDao.memInfoModify(dto);
	}

	@Override
	public List<Map<String, String>> levelGmSelect(String mem_id) {
		return accountDao.levelGmSelect(mem_id);
	}

	@Override
	public List<Map<String, String>> levelPmSelect(String mem_id) {
		return accountDao.levelPmSelect(mem_id);
	}

	@Override
	@Transactional
	public boolean memDelete(String mem_id) {
		boolean isc = false;
		isc = accountDao.memDelete_1(mem_id);
		isc = accountDao.memDelete_2();
		isc = accountDao.memDelete_3(mem_id);
		isc = accountDao.memDelete_4();
		isc = accountDao.memDelete_5(mem_id);
		isc = accountDao.memDelete_6(mem_id);
		isc = accountDao.memDelete_2();
		isc = accountDao.memDelete_7(mem_id);
		isc = accountDao.memDelete_8(mem_id);
		return isc;
		
	}

}
