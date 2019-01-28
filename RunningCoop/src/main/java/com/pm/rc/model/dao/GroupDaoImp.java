 package com.pm.rc.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pm.rc.dto.GroupBoardDto;
import com.pm.rc.dto.GroupDto;
import com.pm.rc.dto.MemberDto;
import com.pm.rc.dto.PagingDto;
import com.pm.rc.util.logExecute;

@Repository
public class GroupDaoImp implements GroupDao{
	
	private Logger logger = LoggerFactory.getLogger(GroupDaoImp.class);
	
	private final String  NAMESPACE = "com.pm.rc.groupMapper.";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<GroupDto> myGrSelect(Map<String, String> map) {
		List<GroupDto> lists = sqlSession.selectList(NAMESPACE+"myGrSelect" , map);
		logger.info("myGrSelect 작동결과");
		return lists;
	}
	
	@Override
	public List<GroupDto> waitGrSelect(Map<String, String> map) {
		List<GroupDto> lists = sqlSession.selectList(NAMESPACE+"waitGrSelect" , map);
		logger.info("waitGrSelect 작동결과");
		return lists;
	}

	@Override
	public Map<String, String> grDetailSelect (Map<String,String> map) {
		logger.info("grDetailSelect 작동결과");
		return sqlSession.selectOne(NAMESPACE +"grDetailSelect" , map);
	}

	@Override
	public List<GroupDto> allGrSelect(Map<String, String> map) {
		System.err.println(map.get("gr_name"));
		List<GroupDto> lists = sqlSession.selectList(NAMESPACE+"allGrSelect", map);
		logger.info("allGrSelect 작동결과 (그룹검색)");
		return lists;
	}

	@Override
	public boolean grInsert1(Map<String, String> map) {
		logger.info("grInsert1 작동결과 그룹생성" );
		int rst = sqlSession.insert(NAMESPACE+"grInsert1", map);
		return rst>0? true:false;
	}

	@Override
	public boolean grInsert2(Map<String, String> map) {
		logger.info("grInsert2 작동결과 그룹생성2");
		int rst = sqlSession.insert(NAMESPACE+"grInsert2",map);
		return rst>0? true:false;
	}

	@Override
	public boolean grModify(Map<String, String> map) {
		int n = sqlSession.update(NAMESPACE+"grModify", map);
		logger.info("grModify 작동결과");
		return n>0 ? true : false;
	}

	@Override
	public boolean grWaitInsert(Map<String, String> map) {
		System.out.println("mem_id:"+map.get("mem_id"));
		System.out.println("gr_id:"+map.get("gr_id"));
		int n  = sqlSession.insert(NAMESPACE+"grWaitInsert" , map);
	return n>0 ? true : false;
	}

	@Override
	public List<MemberDto> sysMemSelect(String mem_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberDto> grWaitList(String gr_id) {
		List<MemberDto> lists = sqlSession.selectList(NAMESPACE+"grWaitList", gr_id);
		return lists;
	}

	@Override
	public boolean grMemInsert1(Map<String, String> map) {
		int rst = sqlSession.insert(NAMESPACE+"grMemInsert1",map);
		return rst>0? true:false;
	}

	@Override
	public boolean grMemInsert2(Map<String, String> map) {
		int rst = sqlSession.delete(NAMESPACE+"grMemInsert2" , map);
		return rst>0? true:false;
	}

	@Override
	public boolean grMemInsert3(Map<String, String> map) {
		int rst = sqlSession.update(NAMESPACE+"grMemInsert3" , map);
		return rst>0? true:false;
	}

	@Override
	public boolean grMemReject(Map<String, String> map) {
		int rst = sqlSession.delete(NAMESPACE+"grMemReject", map);
		return rst>0? true:false;
	}

	@Override
	public boolean grMemDelete1(Map<String, String> map) {
		logger.info("grMemDelete1 작동결과 ");
		int n = sqlSession.delete(NAMESPACE+"grMemDelete1" ,map);
		return n>0 ? true : false;
	}

	@Override
	public boolean grMemDelete2(Map<String, String> map) {
		logger.info("그룹인원삭제2 작동중"); 
		int n = sqlSession.update(NAMESPACE+"grMemDelete2" , map);
		return n>0? true:false;
	}

	@Override
	public List<MemberDto> grMemSelect(String gr_id) {
		List<MemberDto> list = sqlSession.selectList(NAMESPACE+"grMemSelect", gr_id);
		logger.info("그룹멤버출력기능시작");
		return list;
	}

	@Override
	public boolean grMgrModify(Map<String, String> map) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int allGroupSearchSelectCount(GroupDto dto) {
		return sqlSession.selectOne(NAMESPACE+"allGroupSearchSelectCount" , dto);
	}

	@Override
	public List<Map<String, String>> allGroupSearchSelect(GroupDto dto) {
		return sqlSession.selectList(NAMESPACE+"allGroupSearchSelect" , dto);
	}
	
	//그룹삭제 시작
	@Override
	public boolean groupDelete1(String gr_id) {
		int n = sqlSession.update(NAMESPACE+"grProSueDelete" , gr_id);
		return n>0? true:false;
	}

	@Override
	public boolean groupDelete2(String gr_id) {
		int n = sqlSession.delete(NAMESPACE+"grProAttDelete",gr_id);
		return n>0? true:false;
	}

	@Override
	public boolean groupDelete3(String gr_id) {
		int n =sqlSession.update(NAMESPACE+"grProWorkDetailDelete",gr_id);
		return n>0? true:false;
	}

	@Override
	public boolean groupDelete4(String gr_id) {
		int n =sqlSession.update(NAMESPACE+"grProWorkCommDelete", gr_id);
		return n>0? true:false;
	}

	@Override
	public boolean groupDelete5(String gr_id) {
		int n = sqlSession.update(NAMESPACE+"grProWorkListDelete",gr_id);
		return n>0? true:false;
	}

	@Override
	public boolean groupDelete6(String gr_id) {
		int n = sqlSession.delete(NAMESPACE+"grProMemDelete",gr_id);
		return n>0? true:false;
	}

	@Override
	public boolean groupDelete7(String gr_id) {
		int n = sqlSession.update(NAMESPACE+"grProAllDelete",gr_id);
		return n>0? true:false;
	}

	@Override
	public boolean groupDelete8(String gr_id) {
		int n = sqlSession.delete(NAMESPACE+"grBoardAttDelete",gr_id);
		return n>0? true:false;
	}

	@Override
	public boolean groupDelete9(String gr_id) {
		int n = sqlSession.update(NAMESPACE+"grBoardCommDelete",gr_id);
		return n>0? true:false;
	}

	@Override
	public boolean groupDelete10(String gr_id) {
		int n = sqlSession.update(NAMESPACE+"grBoardDelete",gr_id);
		return n>0? true:false;
	}

	@Override
	public boolean groupDelete11(String gr_id) {
		int n = sqlSession.delete(NAMESPACE+"grJoinListDelete",gr_id);
		return n>0? true:false;
	}
	
	@Override
	public boolean groupDelete12(String gr_id) {
		int n = sqlSession.delete(NAMESPACE+"grMemDelete",gr_id);
		return n>0? true:false;
	}

	@Override
	public boolean groupDelete13(String gr_id) {
		int n = sqlSession.update(NAMESPACE+"grMemCountDelete", gr_id);
		return n>0? true:false;
	}

	@Override
	public boolean newGrMgChange(Map<String, String> map) {
		int n = sqlSession.update(NAMESPACE+"newGrMgChange",map);
		return n>0? true:false;
	}

	@Override
	public boolean oldGrMaChange(Map<String, String> map) {
		int n = sqlSession.update(NAMESPACE+"oldGrMaChange",map);
		return n>0? true:false;
	}

	@Override
	public int groupCheck(Map<String, String> map) {
		int n = sqlSession.selectOne(NAMESPACE+"grCheck",map);
		return n;
		}

	@Override
	public int grNameCheck(String gr_name) {
		int n = sqlSession.selectOne(NAMESPACE+"grNameCheck",gr_name);
		return n;
	}

	@Override
	public List<Map<String, String>> grBoradList(GroupBoardDto gDto) {
		List<Map<String, String>> lists = sqlSession.selectList(NAMESPACE+"grBoradList",gDto);
		return lists;
	}

	@Override
	public int groupImg(String gr_id) {
//		List<GroupDto> lists = sqlSession.selectOne(NAMESPACE+"groupImg",gr_id);
//		System.out.println(lists+"nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
		int n = sqlSession.selectOne(NAMESPACE+"groupImg",gr_id);
		return n;
	}
	
	@Override
	public Map<String, String> groupManagerSearch(Map<String, String> map){
		return sqlSession.selectOne(NAMESPACE+"groupManagerSearch", map);
	}

	@Override
	public boolean grBoardInsert(Map<String, Object> map) {
		int n = sqlSession.insert(NAMESPACE+"grBoardInsert",map);
		return n>0? true:false;
	}

	@Override
	public Map<String, String> gbViewSelect(Map<String, String> map) {
		Map<String, String> view = new HashMap<String, String>();
		view = sqlSession.selectOne(NAMESPACE+"grBoardViewSelect",map);
		return view;
	}

	@Override
	public String sessionGrSelect(String gr_id) {
		String gr_name = sqlSession.selectOne(NAMESPACE+"sessionGrSelect", gr_id);
		return gr_name;
	}

	@Override
	public int grBoradListCnt(GroupBoardDto gDto) {
			int n = sqlSession.selectOne(NAMESPACE+"grBoradListCnt", gDto);
		return n;
	};

}
