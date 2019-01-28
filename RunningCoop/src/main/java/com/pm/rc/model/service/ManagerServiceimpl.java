package com.pm.rc.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pm.rc.dto.GroupDto;
import com.pm.rc.dto.MemberDto;
import com.pm.rc.dto.SystemBoardDto;
import com.pm.rc.model.dao.ManagerDao;

@Service
public class ManagerServiceimpl implements ManagerService {

	@Autowired
	private ManagerDao managerdao;
	
	@Override
	public boolean adminLogin(Map<String, String> map){
		return managerdao.adminLogin(map);
	}
	
	@Override
	public List<GroupDto> grApplySelect(String	gr_name) {
		return managerdao.grApplySelect(gr_name);
	}
	
	@Override
	public List<GroupDto> grApplyInfoView(String gr_id) {
		return managerdao.grApplyInfoView(gr_id);
	}

	@Override
	@Transactional
	public boolean grDelete(String[] gr_id) {
		boolean rst = false;
		for(int i = 0 ; i< gr_id.length; i++){
			rst = managerdao.grDelete2(gr_id[i]);
			rst = managerdao.grDelete(gr_id[i]);
		}
		return rst;
	}

	@Override
	public List<Map<String, String>> allMemberSelect(MemberDto dto) {
		return managerdao.allMemberSelect(dto);
	}
	
	@Override
	public int allMemberSelectCount(MemberDto dto) {
		return managerdao.allMemberSelectCount(dto);
	}

	@Override
	public List<MemberDto> allMemberSelectSearch(MemberDto dto) {
		return managerdao.allMemberSelectSearch(dto);
	}

	@Override
	public MemberDto sysMemView(String mem_id) {
		return managerdao.sysMemView(mem_id);
	}
	
	@Override
	public Boolean sysMemModify(MemberDto dto) {
		return managerdao.sysMemModify(dto);
	}
	
	@Override
	@Transactional
	public boolean grAppModify(String[] gr_id) {
		boolean isc = false;
		for(int i = 0 ; i < gr_id.length; i++){
			isc = managerdao.grAppModify(gr_id[i]);
		}
		return isc;
	}

	@Override
	@Transactional
	public boolean noticeInsert(Map<String, String> map) {
		boolean isc = false;
		isc = managerdao.noticeInsert_2(map);
		if(map.get("satt_name")!=null){
			isc = managerdao.noticeInsert_1(map);
		}
		return isc;
	}

	@Override
	public boolean noticeModify(Map<String, String> map) {
		boolean isc = false;
		String satt_name = map.get("satt_name");
		String attachYN = map.get("attachYN");
		if(attachYN.equals("N")){
			/* 기존에 첨부파일이 없을 경우 새롭게 insert해야함.*/
			isc = managerdao.noticeInsert_1(map); 
		} else if (satt_name != null){
			isc = managerdao.noticeModify_1(map);
		}
		isc = managerdao.noticeModify_2(map);
		return isc;
	}

	@Override
	public boolean qnaReplyInsert(Map<String, String> map) {
		boolean isc = false;
		String satt_name = (String)map.get("satt_name");
		isc = managerdao.qnaReplyInsert_2(map);
		if(satt_name != null){
			isc = managerdao.qnaReplyInsert_1(map);
		}
		return isc;
	}

	@Override
	public List<Map<String, String>> noticeListSelect(SystemBoardDto dto) {
		return managerdao.noticeListSelect(dto);
	}

	@Override
	public int noticeListSelectCount(SystemBoardDto dto) {
		return managerdao.noticeListSelectCount(dto);
	}

	@Override
	public List<Map<String, String>> qnaListSelect(SystemBoardDto dto) {
		return managerdao.qnaListSelect(dto);
	}

	@Override
	public int qnaListSelectCount(SystemBoardDto dto) {
		return managerdao.qnaListSelectCount(dto);
	}

	@Override
	public Map<String, String> sysBoardViewSelect(Map<String, String> map) {
		return managerdao.sysBoardViewSelect(map);
	}

	@Override
	public Map<String, String> sysAttachSelect(Map<String, String> map) {
		return managerdao.sysAttachSelect(map);
	}

	@Override
	public Map<String, String> editBoardViewSelect(Map<String, String> map) {
		return managerdao.editBoardViewSelect(map);
	}

	@Override
	public boolean sysBoardDelete(String sbr_uuid) {
		return managerdao.sysBoardDelete(sbr_uuid);
	}

	/*@Override
	public List<MemberDto> allMemberSelectWeb(Map<String, Object> map) {
		return managerdao.allMemberSelectWeb(map);
	}*/

}
