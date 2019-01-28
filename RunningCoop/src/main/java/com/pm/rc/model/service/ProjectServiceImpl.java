package com.pm.rc.model.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pm.rc.dto.GbAttachDto;
import com.pm.rc.dto.MemberDto;
import com.pm.rc.dto.ProjectDto;
import com.pm.rc.model.dao.ProjectDao;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectDao dao;

	@Override
	public List<ProjectDto> groupProSelect(Map<String, String> map) {
		editGProCondition(map);
		return dao.groupProSelect(map);
	}

	@Override
	public List<ProjectDto> myProSelect(String mem_id) {
		editIProCondition(mem_id);
		return dao.myProSelect(mem_id);
	}
	
	@Override
	public Map<String, String> myTodoPrSelect(String mem_id) {
		return dao.myToDoPrSelect(mem_id);
	}

	@Override
	public Map<String, String> myDidPrSelect(String mem_id) {
		return dao.myDidPrSelect(mem_id);
	}

	@Override
	public Map<String, String> myDoingPrSelect(String mem_id) {
		return dao.myDoingPrSelect(mem_id);
	}

	@Override
	public List<Map<String, String>> myDoingGPrListSelect(ProjectDto dto) {
		return dao.myDoingGPrListSelect(dto);
	}
	
	@Override
	public int myDoingGpTotalcount(ProjectDto dto) {
		return dao.myDoingGpTotalcount(dto);
	}
	
	@Override
	public List<Map<String, String>> myDoingIPrListSelect(ProjectDto dto) {
		return dao.myDoingIPrListSelect(dto);
	}
	
	@Override
	public int myDoingIpTotalcount(ProjectDto dto){
		return dao.myDoingIpTotalcount(dto);
	}
	
	@Override
	public List<Map<String, String>> myTodoGPrListSelect(ProjectDto dto) {
		return dao.myTodoGPrListSelect(dto);
	}
	
	@Override
	public int myTodoGpTotalcount(ProjectDto dto) {
		return dao.myTodoGpTotalcount(dto);
	}
	
	@Override
	public List<Map<String, String>> myTodoIPrListSelect(ProjectDto dto) {
		return dao.myTodoIPrListSelect(dto);
	}
	
	@Override
	public int myTodoIpTotalcount(ProjectDto dto){
		return dao.myTodoIpTotalcount(dto);
	}
	
	@Override
	public List<Map<String, String>> myDidGPrListSelect(ProjectDto dto) {
		return dao.myDidGPrListSelect(dto);
	}
	
	@Override
	public int myDidGpTotalcount(ProjectDto dto) {
		return dao.myDidGpTotalcount(dto);
	}

	@Override
	public List<Map<String, String>> myDidIPrListSelect(ProjectDto dto) {
		return dao.myDidIPrListSelect(dto);
	}
	
	@Override
	public int myDidIpTotalcount(ProjectDto dto){
		return dao.myDidIpTotalcount(dto);
	}

	@Override
	public List<Map<String, String>> allPrSearchSelect(ProjectDto dto) {
		return dao.allPrSearchSelect(dto);
	}
	
	@Override
	public int allPrSearchTotalCount(ProjectDto dto) {
		return dao.allPrSearchTotalCount(dto);
	}
	
	@Override
	public Map<String, String> prDetailSelect(String pr_id) {
		prRateEdit(pr_id);
		return dao.prDetailSelect(pr_id);
	}

	@Override
	public boolean gPrInsert(Map<String, String> map) {
		Date date = new Date();
		SimpleDateFormat dateForm = new SimpleDateFormat("yyMMdd");
		String id_1 = "PR";
		String id_2 = dateForm.format(date).toString();
		String uuid = createUUID();
		System.out.println(uuid);
		String id_3 = uuid.substring(uuid.lastIndexOf("-")+9);
		System.out.println(id_3);
		String pr_id = id_1+id_2+id_3;
		
		map.put("pr_id", pr_id);
		
		boolean isc = false;
		boolean pisc = false;
		boolean misc = false;
		boolean gisc = false;
		
		pisc = dao.gPrInsert_1(map);
		
		if(pisc){
			misc = dao.gPrInsert_2(map);
		}
		
		if(misc){
			gisc = dao.gPrInsert_3(map);
		}
		
		if(pisc == true && misc == true && gisc == true){
			isc = true;
		}
		return isc;
	}

	@Override
	public boolean iPrInsert(Map<String, String> map) {
		Date date = new Date();
		SimpleDateFormat dateForm = new SimpleDateFormat("yyMMdd");
		String id_1 = "PR";
		String id_2 = dateForm.format(date).toString();
		String uuid = createUUID();
		System.out.println(uuid);
		String id_3 = uuid.substring(uuid.lastIndexOf("-")+9);
		System.out.println(id_3);
		String pr_id = id_1+id_2+id_3;
		
		map.put("pr_id", pr_id);
		boolean isc = false;
		boolean pisc = false;
		boolean misc = false;
		
		pisc = dao.iPrInsert_1(map);
		
		if(pisc){
			misc = dao.iPrInsert_2(map);
		}
		
		if(pisc == true && misc == true){
			isc = true;
		}
		return isc;
	}
	
	@Override
	public MemberDto prManagerSelect(String pr_id){
		return dao.prManagerSelect(pr_id);
	}

	@Override
	public boolean projectEdit(ProjectDto dto) {
		return dao.projectEdit(dto);
	}

	@Override
	@Transactional
	public boolean projectDelete(String pr_id) {
		List<String> wklist = dao.projectDelete_1(pr_id);
		
		boolean isc = false;
		
		
		if(wklist.size() != 0){ // 업무가 생성된 경우
			
			for (int i = 0; i < wklist.size(); i++){
				String wk_id = wklist.get(i);
				
				List<GbAttachDto> attList = dao.projectDelete_2(wk_id); // 해당 업무에 포함된 첨부파일 리스트
				
				// 실제 첨부파일 삭제하는 for문
				if (attList.size() != 0){
					for(int j = 0; j < attList.size(); j++){
						File file = new File(attList.get(j).getGatt_path()+attList.get(j).getGatt_rname());
						file.delete();
					}
					isc = dao.projectDelete_3(wk_id); // DB에서 첨부파일 정보 삭제
				}
				
				isc = dao.projectDelete_4(wk_id); // DB에서 코멘트 정보 삭제
				
				isc = dao.projectDelete_5(wk_id); // DB에서 하위 업무 정보 삭제
			}
			
			isc = dao.projectDelete_6(pr_id); // DB에서 업무 정보 삭제
			isc = dao.projectDelete_7(pr_id); // DB에서 프로젝트 멤버 정보 삭제
			isc = dao.projectDelete_8(pr_id); // DB에서 그룹 프로젝트 관계 삭제
			isc = dao.projectDelete_9(pr_id); // DB에서 프로젝트 멤버 수 조정
			isc = dao.projectDelete_10(pr_id); // DB에서 프로젝트 비활성화
			
			return isc;
			
		} else { // 업무가 생성되지 않은 경우
			
			isc = dao.projectDelete_7(pr_id); // DB에서 프로젝트 멤버 정보 삭제
			isc = dao.projectDelete_8(pr_id); // DB에서 그룹 프로젝트 관계 삭제
			isc = dao.projectDelete_9(pr_id); // DB에서 프로젝트 멤버 수 조정
			isc = dao.projectDelete_10(pr_id); // DB에서 프로젝트 비활성화
			
			return isc;
		}
	}

	@Override
	public List<MemberDto> prMemInsertSearch(Map<String, String> map) {
		return dao.prMemInsertSearch(map);
	}

	@Override
	public boolean prMemInsert(Map<String, Object> map) {
		String pr_id = (String) map.get("pr_id");
		String[] memList = (String[]) map.get("mem_list");
		
		Map<String, String> insert = new HashMap<String, String>();
		insert.put("pr_id", pr_id);
		
		boolean isc = false;
		
		for(int i = 0; i < memList.length; i++){
			insert.put("mem_id", memList[i]);
			isc = dao.prMemInsert_1(insert);
			if(isc == false){
				break;
			}
		}
		
		boolean isc2 = false;
		isc2 = dao.prMemInsert_2(pr_id);
		
		if(isc == true && isc2 == true){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean prMemDelete(Map<String, Object> map) {
		String pr_id = (String) map.get("pr_id");
		String[] mem_list = (String[]) map.get("mem_list");
		
		Map<String, String> member = new HashMap<String, String>();
		member.put("pr_id", pr_id);
		
		boolean isc = false;
		
		for(int i = 0; i < mem_list.length; i++){
			member.put("mem_id", mem_list[i]);
			isc = dao.prMemDelete_1(member);
			
			if(isc == false){
				break;
			}
		}
		
		boolean isc2 = false;
		isc2 = dao.prMemDelete_2(pr_id);
		
		if(isc == true && isc2 == true){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Map<String, String>> prMemListSelect(String pr_id) {
		return dao.prMemListSelect(pr_id);
	}

	@Override
	public boolean prMgrModify(Map<String, String> map) {
		boolean isc1 = false;
		isc1 = dao.prMgrEdit_1(map);
		boolean isc2 = false;
		isc2 = dao.prMgrEdit_2(map);
		
		if( isc1 == true && isc2 == true){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean prRateEdit(String pr_id) {
		List<String> rateList = dao.prRateEdit_1(pr_id);
		int scale = rateList.size();
		System.out.println(scale);
		
		int value = 0;
		
		for(int i = 0; i < rateList.size(); i++){
			int val = Integer.parseInt(rateList.get(i));
			value += val;
		}
		
		System.out.println(value);
		
		String proRate = ""+Math.round((double)value/(double)scale);
		System.out.println(proRate);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("pr_id", pr_id);
		map.put("pr_proRate", proRate);
		
		boolean isc = false;
		isc = dao.prRateEdit_2(map);
		
		return isc;
	}

	@Override
	public String myLevelSelect(Map<String, String> map) {
		Map<String, String> level = dao.myLevelSelect(map);
		String pr_level = level.get("PR_LEVEL");
		return pr_level;
	}

	@Override
	public Map<String, String> memInfoSelect_1(Map<String, String> map) {
		return dao.memInfoSelect_1(map);
	}

	@Override
	public List<Map<String, String>> memInfoSelect_2(Map<String, String> map) {
		return dao.memInfoSelect_2(map);
	}

	@Override
	public boolean leaveProject(Map<String, String> map) {
		String pr_id = map.get("pr_id");
		boolean isc = false;
		isc = dao.prMemDelete_1(map);
		isc = dao.prMemDelete_2(pr_id);
		return isc;
	}

	@Override
	public void editGProCondition(Map<String, String> map) {
			// 진행중으로 변경
		dao.editGProCondition_1(map);
			// 진행완료로 변경
		dao.editGProCondition_2(map);
	}

	@Override
	public void editIProCondition(String mem_id) {
		//진행중으로 변경
		dao.editIProCondition_1(mem_id);
		//진행완료로 변경
		dao.editIProCondition_2(mem_id);
	}
	
	public String createUUID(){
		return UUID.randomUUID().toString();
	}

}
