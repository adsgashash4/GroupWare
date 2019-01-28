package com.pm.rc.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pm.rc.dto.SbAttachDto;
import com.pm.rc.dto.SystemBoardDto;

@Repository
public class SysBoardDaoImpl implements SysBoardDao {
	
	private final String NAMESPACE = "com.pm.rc.sysBoard.";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<Map<String, String>> noticeListSelect(SystemBoardDto dto) {
		return sqlSession.selectList(NAMESPACE+"noticeListSelect", dto);
	}
	
	@Override
	public int noticeListSelectCount(SystemBoardDto dto) {
		return sqlSession.selectOne(NAMESPACE+"noticeListSelectCount", dto);
	}

	@Override
	public List<Map<String, String>> qnaListSelect(SystemBoardDto dto) {
		return sqlSession.selectList(NAMESPACE+"qnaListSelect", dto);
	}
	
	@Override
	public int qnaListSelectCount(SystemBoardDto dto) {
		return sqlSession.selectOne(NAMESPACE+"qnaListSelectCount", dto);
	}

	@Override
	public Map<String, String> openViewSelect(Map<String, String> map) {
		Map<String, String> view = new HashMap<String, String>();
		view = sqlSession.selectOne(NAMESPACE+"openViewSelect", map);
		return view;
	}

	@Override
	public Map<String, String> scrViewSelect(Map<String, String> map) {
		Map<String, String> view = new HashMap<String, String>();
		view = sqlSession.selectOne(NAMESPACE+"scrViewSelect", map);
		return view;
	}
	
	@Override
	public Map<String, String> editBoardViewSelect(Map<String, String> map){
		Map<String, String> view = new HashMap<String, String>();
		view = sqlSession.selectOne(NAMESPACE+"editViewSelect", map);
		return view;
	};

	@Override
	public Map<String, String> sysViewSelect(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> sysAttachSelect(Map<String, String> map) {
		Map<String, String> attach = null;
		attach = sqlSession.selectOne(NAMESPACE+"sysAttachSelect", map);
		return attach;
	}

	@Override
	public boolean qnaBoardInsert(Map<String, String> map) {
		boolean isc = false;
		int num = sqlSession.insert(NAMESPACE+"qnaBoardInsert", map);
		if(num > 0){
			isc = true;
		} else {
			isc = false;
		}
		return isc; 
	}

	@Override
	public boolean FileInsert(Map<String, String> map) {
		boolean isc = false;
		int num = sqlSession.insert(NAMESPACE+"FileInsert", map);
		if(num > 0){
			isc = true;
		}
		return isc;
	}

	@Override
	public boolean sysBoardUpdate(Map<String, String> map) {
		boolean isc = false;
		int num = sqlSession.update(NAMESPACE+"sysBoardUpdate", map);
		if(num > 0){
			isc = true;
		}else{
			isc = false;
		}
		return isc;
	}

	@Override
	public boolean FileUpdate(Map<String, String> map) {
		int num = sqlSession.update(NAMESPACE+"fileUpdate", map);
		if(num > 0){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean sysBoardDelete(String sbr_uuid) {
		boolean isc = false;
		int num = sqlSession.update(NAMESPACE+"sysBoardDelete", sbr_uuid);
		if(num > 0){
			isc = true;
		}
		return isc;
	}
	
	@Override
	public boolean FileCheck(String sbr_uuid) {
		boolean isc = false;
		Map<String, String> uuid = new HashMap<String, String>();
		uuid.put("sbr_uuid", sbr_uuid);
		Map<String, SbAttachDto> attach = new HashMap<String, SbAttachDto>();
		attach = sqlSession.selectOne(NAMESPACE+"sysAttachSelect", uuid);
		if( attach != null){
			isc = true;
		}
		return isc;
	}

	@Override
	public boolean FileDelete(String sbr_uuid) {
		boolean isc = false;
		int num = sqlSession.delete(NAMESPACE+"sysAttachDelete", sbr_uuid);
		if (num > 0){
			isc = true;
		}
		return isc;
	}
}
