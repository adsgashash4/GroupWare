package com.pm.rc.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.xmlbeans.impl.xb.xsdschema.GroupDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pm.rc.dto.GroupBoardDto;
import com.pm.rc.dto.GroupDto;
import com.pm.rc.dto.MemberDto;
import com.pm.rc.dto.PagingDto;
import com.pm.rc.model.dao.GroupBoardDao;
import com.pm.rc.model.dao.GroupDao;

@Service
public class GroupServiceImp implements GroupService{
	
	@Autowired
	private GroupDao groupdao; 
	@Autowired
	private GroupBoardDao groupboarddao;

	@Override
	public List<GroupDto> myGrSelect(Map<String, String> map) {
		return groupdao.myGrSelect(map);
	}
	
	@Override
	public List<GroupDto> waitGrSelect(Map<String, String> map) {
		return groupdao.waitGrSelect(map);
	}

	@Override
	public Map<String, String> grDetailSelect (Map<String, String> map) {
		return groupdao.grDetailSelect(map);
	}

	@Override
	public  List<GroupDto>  allGrSelect(Map<String, String> map) {
		return groupdao.allGrSelect(map);
	}

	@Override
	@Transactional
	public boolean grInsert(Map<String, String> map) {
		boolean rst = false;
		rst = groupdao.grInsert1(map);
		rst = groupdao.grInsert2(map);
		return rst;
	}

	@Override
	public boolean grModify(Map<String, String> map) {
		return groupdao.grModify(map);
	}

	@Override
	public boolean grWaitInsert(Map<String, String> map) {
		
		return groupdao.grWaitInsert(map);
	}

	@Override
	public List<MemberDto> sysMemSelect(String mem_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberDto> grWaitList(String gr_id) {
		return groupdao.grWaitList(gr_id);
	}

	@Override
	@Transactional
	public boolean grMemInsert(Map<String, String> map) {
		boolean isc = false;
		isc = groupdao.grMemInsert1(map);
		isc = groupdao.grMemInsert2(map);
		isc = groupdao.grMemInsert3(map);
		return isc;
	}

	@Override
	public boolean grMemReject(Map<String, String> map) {
		boolean isc =false;
		isc = groupdao.grMemReject(map);
		return isc;
	}

	@Override
	@Transactional
	public boolean grMemDelete(Map<String, String> map) {
		boolean isc = false;
		isc = groupdao.grMemDelete1(map);
		isc = groupdao.grMemDelete2(map);
		return isc;
	}

	@Override
	public List<MemberDto> grMemSelect(String gr_id) {
		return groupdao.grMemSelect(gr_id);
	}

	@Override
	public List<GroupBoardDto> gbListSelect(String gr_id) {
		
		return groupboarddao.gbListSelect(gr_id);
	}

	@Override
	public List<GroupBoardDto> gbNameSearch(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> gbViewSelect(Map<String, String> map) {
		return groupdao.gbViewSelect(map);
	}

	@Override
	public boolean grBoardInsert(Map<String, Object> map) {
	return groupdao.grBoardInsert(map);
	}

	@Override
	public boolean gboardModify(GroupBoardDto dto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional
	public boolean gboardDelete(String[] br_uuid) {
			boolean isc = false;
			for(int i = 0; i<br_uuid.length; i++){
				isc=groupboarddao.gboardDelete(br_uuid[i]);
				if(isc==false){
					break;
				}
			}
		return isc;
	}

	@Override
	public boolean gbCommentInsert(GroupBoardDto dto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gbCommentModify(Map<String, String> map) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean bgCommentDelete(String br_uuid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean grMgrModify(Map<String, String> map) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Map<String, String>> allGroupSearchSelect(GroupDto dto) {
		return groupdao.allGroupSearchSelect(dto);
	}

	@Override
	public int allGroupSearchSelectCount(GroupDto dto) {
		return groupdao.allGroupSearchSelectCount(dto);
	}

	@Override
	@Transactional
	public boolean groupDelete(String gr_id) {
		boolean isc = false;
		isc = groupdao.groupDelete1(gr_id);
		isc = groupdao.groupDelete2(gr_id);
		isc = groupdao.groupDelete3(gr_id);
		isc = groupdao.groupDelete4(gr_id);
		isc = groupdao.groupDelete5(gr_id);
		isc = groupdao.groupDelete6(gr_id);
		isc = groupdao.groupDelete7(gr_id);
		isc = groupdao.groupDelete8(gr_id);
		isc = groupdao.groupDelete9(gr_id);
		isc = groupdao.groupDelete10(gr_id);
		isc = groupdao.groupDelete11(gr_id);
		isc = groupdao.groupDelete12(gr_id);
		isc = groupdao.groupDelete13(gr_id);
		return isc;
	}

	@Override
	public boolean grManagerChange(Map<String, String> map) {
		boolean isc = false;
		isc = groupdao.newGrMgChange(map);
		return isc;
	}
	
	@Override
	public boolean grManagerChange2(Map<String, String> map){
		boolean isc =false;
		isc = groupdao.oldGrMaChange(map);
		return isc;
	}

	@Override
	public int groupCheck(Map<String, String> map) {
		return groupdao.groupCheck(map);
	}

	@Override
	public int grNameCheck(String gr_name) {
		return groupdao.grNameCheck(gr_name);
	}

	@Override
	public List<Map<String, String>> grBoradList(GroupBoardDto gDto) {
		List<Map<String, String>> lists = groupdao.grBoradList(gDto);
		return lists;
	}

	@Override
	public int groupImg(String gr_id) {
//		List<GroupDto> lists = groupdao.groupImg(gr_id);
		int n = groupdao.groupImg(gr_id);
		return n;
	}

	@Override
	@Transactional
	public boolean waitGrSelectDelete(String gr_id) {
		boolean isc = false;
		isc = groupdao.groupDelete12(gr_id);
		isc = groupdao.groupDelete13(gr_id);
		return isc;
	}
	
	@Override
	public Map<String, String> groupManagerSearch(Map<String, String> map){
		return groupdao.groupManagerSearch(map);
	}

	@Override
	public Map<String, String> grEditBoardViewSelect(Map<String, String> map) {
		Map<String, String> view = new HashMap<String, String>();
		view = groupboarddao.grEditBoardViewSelect(map);
		return view;
	}

	@Override
	public boolean grBoardEdit(Map<String, String> map) {
		boolean isc = groupboarddao.grBoardUpdate(map);
		return isc;
	}

	@Override
	public boolean grBoardDelete2(String br_uuid) {
		boolean isc = groupboarddao.gboardDelete(br_uuid);
		return isc;
	}

	@Override
	public String sessionGrSelect(String gr_id) {
		String gr_name = groupdao.sessionGrSelect(gr_id);
		return gr_name;
	}

	@Override
	public int grBoradListCnt(GroupBoardDto gDto) {
		int n = groupdao.grBoradListCnt(gDto);
		return n;
	};



}
