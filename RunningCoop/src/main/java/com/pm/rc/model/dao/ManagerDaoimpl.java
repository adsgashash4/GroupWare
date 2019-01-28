package com.pm.rc.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pm.rc.dto.GroupDto;
import com.pm.rc.dto.PagingProDto;
import com.pm.rc.dto.MemberDto;
import com.pm.rc.dto.SystemBoardDto;

@Repository
public class ManagerDaoimpl implements ManagerDao {
	
	private Logger logger = LoggerFactory.getLogger(ManagerDaoimpl.class); 
	
	private final String NAMESPACE = "com.pm.rc.manager.";
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public boolean adminLogin(Map<String, String> map){
		int num = sqlSession.selectOne(NAMESPACE+"adminLogin", map);
		return num>0 ? true : false;
	}
	
	@Override
	public List<Map<String, String>> allMemberSelect(MemberDto dto) {
		return sqlSession.selectList(NAMESPACE+"allMemberSelect", dto);
	}
	
	@Override
	public int allMemberSelectCount(MemberDto dto) {
		return sqlSession.selectOne(NAMESPACE+"allMemberSelectCount", dto);
	}

	@Override
	public List<MemberDto> allMemberSelectSearch(MemberDto dto) {
		return sqlSession.selectList(NAMESPACE+"allMemberSelect", dto);
	}
	
	@Override
	public MemberDto sysMemView(String mem_id) {
		return sqlSession.selectOne(NAMESPACE+"sysMemView", mem_id);
	}

	@Override
	public Boolean sysMemModify(MemberDto dto) {
		int rst = sqlSession.update(NAMESPACE+"sysMemModify", dto);
		return rst > 0 ? true: false;
	}
	
	@Override
	public List<GroupDto> grApplySelect(String gr_name) {
		logger.info("그룹생성신청daoimp 시작");
		Map<String, String> map = new HashMap<String, String>();
		map.put("gr_name", gr_name);
		List<GroupDto> lists = sqlSession.selectList(NAMESPACE+"grApplySelect",map);
		return lists;
	}
	
	@Override
	public List<GroupDto> grApplyInfoView(String gr_id) {
		logger.info("그룹신청간략정보");
		List<GroupDto> lists = sqlSession.selectList(NAMESPACE+"grApplyInfoView" , gr_id);
		return lists;
	}

	@Override
	public boolean grDelete(String gr_id) {
		int n = sqlSession.delete(NAMESPACE+"grDelete",gr_id);
		return n>0? true:false;
	}

	@Override
	public boolean grDelete2(String gr_id) {
		int n = sqlSession.delete(NAMESPACE+"grDelete2",gr_id);
		return n>0? true:false;
	}

	@Override
	public boolean grAppModify(String gr_id) {
		int rst = sqlSession.update(NAMESPACE+"grAppModify" , gr_id);
		return rst>0? true:false;
	}

	@Override
	public boolean noticeInsert_1(Map<String, String> map) {
		int num = sqlSession.insert(NAMESPACE+"noticeInsert_1", map);
		return num>0?true:false;
	}
	
	@Override
	public boolean noticeInsert_2(Map<String, String> map){
		int num = sqlSession.insert(NAMESPACE+"noticeInsert_2", map);
		return num>0?true:false;
	}

	@Override
	public boolean noticeModify_1(Map<String, String> map) {
		int num = sqlSession.update(NAMESPACE+"noticeModify_1", map);
		return num>0?true:false;
	}
	
	@Override
	public boolean noticeModify_2(Map<String, String> map){
		int num = sqlSession.update(NAMESPACE+"noticeModify_2", map);
		return num>0?true:false;
	}
	
	@Override
	public Map<String, String> editBoardViewSelect(Map<String, String> map) {
		return sqlSession.selectOne(NAMESPACE+"editViewSelect", map);
	}

	@Override
	public boolean qnaReplyInsert_1(Map<String, String> map) {
		int num = sqlSession.insert(NAMESPACE+"qnaReplyInsert_1", map);
		return num>0?true:false;
	}
	
	@Override
	public boolean qnaReplyInsert_2(Map<String, String> map){
		int num = sqlSession.insert(NAMESPACE+"qnaReplyInsert_2", map);
		return num>0?true:false;
	}

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
	public Map<String, String> sysBoardViewSelect(Map<String, String> map) {
		return sqlSession.selectOne(NAMESPACE+"sysBoardViewSelect", map);
	}

	@Override
	public Map<String, String> sysAttachSelect(Map<String, String> map) {
		return sqlSession.selectOne(NAMESPACE+"sysAttachSelect", map);
	}

	@Override
	public boolean sysBoardDelete(String sbr_uuid) {
		int num = sqlSession.update(NAMESPACE+"sysBoardDelete", sbr_uuid);
		return num>0 ? true:false;
	}

}
