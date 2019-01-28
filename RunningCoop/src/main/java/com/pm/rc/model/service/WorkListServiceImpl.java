package com.pm.rc.model.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.rc.dto.GbAttachDto;
import com.pm.rc.dto.WorkCommentDto;
import com.pm.rc.dto.WorkDetailDto;
import com.pm.rc.dto.WorkListDto;
import com.pm.rc.model.dao.WorkListDao;

@Service
public class WorkListServiceImpl implements WorkListService {

	@Autowired
	private WorkListDao dao;
	
	@Autowired
	private ProjectService proService;

	@Override
	public List<Map<String, String>> wkListSelect(Map<String, String> map) {
		String pr_id = map.get("pr_id");
		proService.prRateEdit(pr_id);
		List<Map<String, String>> list = null;

		if(map.get("wk_condition").equals("todo")){
			list = dao.wkListSelect1(map);
		} else if(map.get("wk_condition").equals("doing")){
			list = dao.wkListSelect2(map);
		} else if(map.get("wk_condition").equals("done")){
			list = dao.wkListSelect3(map);
		}

		return list;
	}

	@Override
	public boolean wkListInsert(WorkListDto dto) {
		Date date = new Date();
		SimpleDateFormat dateForm = new SimpleDateFormat("yyMMdd");

		String id_1 = "WK";
		String id_2 = dateForm.format(date).toString();
		String uuid = createUUID();
		String id_3 = uuid.substring(uuid.lastIndexOf("-")+8);
		String wk_id = id_1+id_2+id_3;

		dto.setWk_id(wk_id);

		return dao.wkListInsert(dto);
	}

	@Override
	public boolean wkListDelete(String wk_id) {
		boolean isc1 = false;
		boolean isc2 = false;

		isc1 = dao.wkListDelete_1(wk_id);
		isc2 = dao.wkListDelete_2(wk_id);

		if (isc1 && isc2){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean wkListModify(WorkListDto dto) {
		return dao.wkListModify(dto);
	}

	@Override
	public boolean wkRateModify(String wk_id) {
		int allWork = 0;
		int complWork = 0;
		int proRate = 0;

		allWork = dao.wkRateModify_1(wk_id);
		complWork = dao.wkRateModify_2(wk_id);
		System.out.println(complWork);
		proRate = (int)((double)complWork/(double)allWork * 100);
		System.out.println(proRate);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("wk_id", wk_id);
		map.put("wk_proRate", proRate);

		boolean isc = false;
		isc	= dao.wkRateModify_3(map);

		return isc;
	}

	@Override
	public List<WorkDetailDto> wdSelect(String wk_id) {
		return dao.wdSelect(wk_id);
	}

	@Override
	public boolean wdInsert(WorkDetailDto dto) {
		Date date = new Date();
		SimpleDateFormat dateForm = new SimpleDateFormat("yyMMdd");

		String id_1 = "WD";
		String id_2 = dateForm.format(date).toString();
		String uuid = createUUID();
		String id_3 = uuid.substring(uuid.lastIndexOf("-")+8);
		String wd_id = id_1+id_2+id_3;

		dto.setWd_id(wd_id);

		return dao.wdInsert(dto);
	}

	@Override
	public boolean wdModify(WorkDetailDto dto) {
		return dao.wdModify(dto);
	}

	@Override
	public boolean wdDelete(String wd_id) {
		return dao.wdDelete(wd_id);
	}

	@Override
	public boolean wdErrorChk(Map<String, String> map) {
		return dao.wdErrorChk(map);
	}

	@Override
	public boolean wdComplModify(String wd_id) {
		return dao.wdComplModify(wd_id);
	}

	@Override
	public List<Map<String, String>> wCommListSelect(String wk_id) {
		return dao.wCommListSelect(wk_id);
	}

	@Override
	public boolean wCommentInsert(WorkCommentDto dto) {
		Date date = new Date();
		SimpleDateFormat dateForm = new SimpleDateFormat("yyMMdd");

		String id_1 = "CM";
		String id_2 = dateForm.format(date).toString();
		String uuid = createUUID();
		String id_3 = uuid.substring(uuid.lastIndexOf("-")+8);
		String wcom_id = id_1+id_2+id_3;

		dto.setWcom_id(wcom_id);

		return dao.wCommentInsert(dto);
	}

	@Override
	public boolean wCommentModify(WorkCommentDto dto) {
		return dao.wCommentModify(dto);
	}

	@Override
	public boolean wCommentDelete(String wcom_id) {
		return dao.wCommentDelete(wcom_id);
	}

	@Override
	public List<GbAttachDto> gbAttachSelect(String wk_id) {
		return dao.gbAttachSelect(wk_id);
	}

	@Override
	public boolean gbAttachInsert(GbAttachDto dto) {
		return dao.gbAttachInsert(dto);
	}

	@Override
	public boolean gbAttachDelete(String gatt_seq) {
		return dao.gbAttachDelete(gatt_seq);
	}

	@Override
	public GbAttachDto attachDownSelect(String gatt_seq) {
		return dao.attachDownSelect(gatt_seq);
	}

	// UUID 생성 메소드
	public String createUUID(){
		return UUID.randomUUID().toString();
	}


}
