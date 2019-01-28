package com.pm.rc.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.rc.dto.SystemBoardDto;
import com.pm.rc.model.dao.SysBoardDao;

@Service
public class SysBoardServiceImpl implements SysBoardService {

	@Autowired
	private SysBoardDao sysBoardDao;
	
	@Override
	public List<Map<String, String>> noticeListSelect(SystemBoardDto dto) {
		return sysBoardDao.noticeListSelect(dto);
	}
	
	@Override
	public int noticeListSelectCount(SystemBoardDto dto) {
		return sysBoardDao.noticeListSelectCount(dto);
	}

	@Override
	public List<Map<String, String>> qnaListSelect(SystemBoardDto dto) {
		return sysBoardDao.qnaListSelect(dto);
	}
	
	@Override
	public int qnaListSelectCount(SystemBoardDto dto) {
		return sysBoardDao.qnaListSelectCount(dto);
	}
	
	@Override
	public Map<String, String> sysBoardViewSelect(Map<String, String> map) {
		String pw = map.get("sbr_pw");
		Map<String, String> view = new HashMap<String, String>();
		if(pw == null){
			view = sysBoardDao.openViewSelect(map);
		} else if (pw != null){
			view = sysBoardDao.scrViewSelect(map);
		}
		return view;
	}

	@Override
	public Map<String, String> editBoardViewSelect(Map<String, String> map){
		Map<String, String> view = new HashMap<String, String>();
		view = sysBoardDao.editBoardViewSelect(map);
		return view;
	};

	@Override
	public boolean qnaBoardInsert(Map<String, String> map) {

		boolean board = sysBoardDao.qnaBoardInsert(map);
		boolean attach = false;

		if(map.get("satt_name") != null){
			attach = sysBoardDao.FileInsert(map);
		} else {
			attach = true;
		}

		if(board == true && attach == true){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean qnaBoardEdit(Map<String, String> map) {
		
		boolean updateBoard = sysBoardDao.sysBoardUpdate(map);
		boolean updateFile = false;
		
		String satt_name = map.get("satt_name");
		String attachYN = map.get("attachYN");
		if(attachYN != null && attachYN.equals("N")){
			updateFile = sysBoardDao.FileInsert(map);
		} else if(satt_name!=null){
			updateFile = sysBoardDao.FileUpdate(map);
		} else {
			updateFile = true;
		}
		
		if( updateBoard == true && updateFile == true){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean qnaBoardDelete(String sbr_uuid) {
		boolean bchk = sysBoardDao.sysBoardDelete(sbr_uuid);
		boolean achk = sysBoardDao.FileCheck(sbr_uuid);
		boolean aDelete = false;
		if(achk){
			aDelete = sysBoardDao.FileDelete(sbr_uuid);
		}
		if( bchk == true && aDelete == true){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Map<String, String> sysAttachSelect(Map<String, String> map) {
		return sysBoardDao.sysAttachSelect(map);
	}
}
