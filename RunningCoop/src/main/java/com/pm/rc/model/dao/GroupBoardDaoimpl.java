package com.pm.rc.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pm.rc.dto.GbAttachDto;
import com.pm.rc.dto.GroupBoardDto;

@Repository
public class GroupBoardDaoimpl implements GroupBoardDao{
	
	private Logger logger = LoggerFactory.getLogger(GroupDaoImp.class);
	
	private final String NAMESPACE = "com.pm.rc.groupboard.";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<GroupBoardDto> gbListSelect(String gr_id) {
		List<GroupBoardDto> lists = sqlSession.selectList(NAMESPACE+"gbListSelect" , gr_id);
		logger.info("gbListSelect 작동결과");
		return lists;
	}

	@Override
	public List<GroupBoardDto> gbTitleSearch(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupBoardDto gbViewSelect1(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GbAttachDto> gbViewSelect2(String br_uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupBoardDto gbViewSelect3(String br_uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean gboardInsert(GroupBoardDto dto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gboardModify(GroupBoardDto dto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gbAttachUpdate(GbAttachDto dto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gbAttachInsert(GbAttachDto dto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gbAttachDelete(String br_uuid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gboardDelete(String br_uuid) {
		int rst  = sqlSession.update(NAMESPACE+"gboardDelete", br_uuid);
		logger.info("gboardDelete 작동결과");
		return rst>0 ? true : false;
	}

	@Override
	public boolean gbCommentInsert(GroupBoardDto dto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gbCommentModify(GroupBoardDto dto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean bgCommentDelete(String br_uuid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, String> grEditBoardViewSelect(Map<String, String> map) {
		Map<String, String> view = new HashMap<String, String>();
		view = sqlSession.selectOne(NAMESPACE+"gBeditViewSelect",map);
		return view;
	}

	@Override
	public boolean grBoardUpdate(Map<String, String> map) {
		int rst = sqlSession.update(NAMESPACE+"grBoardUpdate",map);
		return rst>0 ? true : false;
	}

}