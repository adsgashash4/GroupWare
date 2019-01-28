package com.pm.rc.model.dao;

import java.util.List;
import java.util.Map;

import com.pm.rc.dto.GbAttachDto;
import com.pm.rc.dto.MemberDto;
import com.pm.rc.dto.ProjectDto;

public interface ProjectDao {
	
	/**
	 * 그룹에 소속된 내가 참여하는 진행중인 프로젝트 조회(메인에서 프로젝트 선택 시 출력화면)
	 * @param map (mem_id, gr_id를 담아 전송)
	 * @return
	 */
	public List<ProjectDto> groupProSelect(Map<String, String> map);
	
	/**
	 * 내가 진행중인 프로젝트 조회(그룹없는 개인사용자/ 메인에서 프로젝트 선택 시 출력화면)
	 * @param mem_id
	 * @return
	 */
	public List<ProjectDto> myProSelect(String mem_id);
	
	/**
	 * 나의 진행중인 프로젝트 조회
	 * 사이드 메뉴의 진행중인 프로젝트 건 수 조회 dao
	 * @param mem_id
	 * @return
	 */
	public Map<String, String> myDoingPrSelect(String mem_id);
	
	/**
	 * 나의 진행예정 프로젝트 조회
	 * 사이드 메뉴의 진행예정 프로젝트 건 수 조회 dao
	 * @param mem_id
	 * @return
	 */
	public Map<String, String> myToDoPrSelect(String mem_id);
	
	/**
	 * 나의 진행완료 프로젝트 조회
	 * 사이드 메뉴의 진행완료 프로젝트 건 수 조회 dao
	 * @param mem_id
	 * @return
	 */
	public Map<String, String> myDidPrSelect(String mem_id);
	
	/* 나의 진행중인 프로젝트 조회 dao */
	/**
	 * 나의 진행중인 그룹 프로젝트 리스트 조회
	 * @param mem_id
	 * @return
	 */
	public List<Map<String, String>> myDoingGPrListSelect(ProjectDto dto);
	
	/**
	 * 페이징 - 나의 진행중인 그룹 프로젝트 리스트 총 갯수
	 * @param mem_id
	 * @return
	 */
	public int myDoingGpTotalcount(ProjectDto dto);
	
	/**
	 * 나의 진행중인 개인 프로젝트 리스트 조회
	 * @param mem_id
	 * @return
	 */
	public List<Map<String, String>> myDoingIPrListSelect(ProjectDto dto);
	
	/**
	 * 페이징 - 나의 진행중인 개인 프로젝트 리스트 총 갯수
	 * @param mem_id
	 * @return
	 */
	public int myDoingIpTotalcount(ProjectDto dto);
	/* 나의 진행중인 프로젝트 조회 dao 끝 */
	
	/* 나의 진행예정 프로젝트 조회 dao */
	/**
	 * 나의 진행예정 그룹 프로젝트 리스트 조회
	 * @param mem_id
	 * @return
	 */
	public List<Map<String, String>> myTodoGPrListSelect(ProjectDto dto);
	
	/**
	 * 페이징 - 나의 진행예정 그룹 프로젝트 총 갯수
	 * @param mem_id
	 * @return
	 */
	public int myTodoGpTotalcount(ProjectDto dto);
	
	/**
	 * 나의 진행예정 개인 프로젝트 리스트 조회
	 * @param mem_id
	 * @return
	 */
	public List<Map<String, String>> myTodoIPrListSelect(ProjectDto dto);
	
	/**
	 * 페이징 - 나의 진행예정 개인 프로젝트 리스트 총 갯수
	 * @param mem_id
	 * @return
	 */
	public int myTodoIpTotalcount(ProjectDto dto);
	/* 나의 진행예정 프로젝트 dao 끝 */
	
	/* 나의 진행완료 프로젝트 dao */
	/**
	 * 나의 진행완료 그룹 프로젝트 리스트 조회
	 * @param mem_id
	 * @return
	 */
	public List<Map<String, String>> myDidGPrListSelect(ProjectDto dto);
	
	/**
	 * 페이징 - 나의 진행완료 그룹 프로젝트 리스트 총 갯수
	 * @param mem_id
	 * @return
	 */
	public int myDidGpTotalcount (ProjectDto dto);
	
	/**
	 * 나의 진행완료 개인 프로젝트 리스트 조회
	 * @param mem_id
	 * @return
	 */
	public List<Map<String, String>> myDidIPrListSelect(ProjectDto dto);
	
	/**
	 * 페이징 - 나의 진행완료 개인 프로젝트 리스트 총 갯수
	 * @param mem_id
	 * @return
	 */
	public int myDidIpTotalcount(ProjectDto dto);
	
	/**
	 * 시스템 전체  프로젝트 조회(검색)
	 * @param pr_name 프로젝트명
	 * @return List&lt;ProjectDto&gt;
	 * @author 김혜원
	 * */
	public List<Map<String, String>> allPrSearchSelect(ProjectDto dto);
	public int allPrSearchTotalCount (ProjectDto dto);

	/**
	 * 해당하는 프로젝트의 상세정보를 조회
	 * @param pr_id
	 * @return
	 */
	public Map<String, String> prDetailSelect(String pr_id);
	
	/**
	 * 그룹 프로젝트 생성 절차 1 (프로젝트 생성)
	 * @param map
	 * @return
	 */
	public boolean gPrInsert_1(Map<String, String> map);
	
	/**
	 * 그룹 프로젝트 생성 절차2 (프로젝트 매니저 등록)
	 * @param map
	 * @return
	 */
	public boolean gPrInsert_2(Map<String, String> map);
	
	/**
	 * 그룹 프로젝트 생성 절차3 (그룹 프로젝트 등록)
	 * @param map
	 * @return
	 */
	public boolean gPrInsert_3(Map<String, String> map);
	
	/**
	 * 개인 프로젝트 생성 절차1 (프로젝트 생성)
	 * @param map
	 * @return
	 */
	public boolean iPrInsert_1(Map<String, String> map);
	
	/**
	 * 개인 프로젝트 생성 절차2 (프로젝트 매니저 등록)
	 * @param map
	 * @return
	 */
	public boolean iPrInsert_2(Map<String, String> map);
	
	/**
	 * 프로젝트 매니저 조회
	 * @param pr_id
	 * @return
	 */
	public MemberDto prManagerSelect(String pr_id);
	
	/**
	 * 프로젝트 기본 정보 수정
	 * @param dto
	 * @return
	 */
	public boolean projectEdit(ProjectDto dto);
	
	/**
	 * 프로젝트에 초대가능한 그룹멤버 조회
	 * @param map
	 * @return
	 */
	public List<MemberDto> prMemInsertSearch(Map<String, String> map);
	
	/**
	 * 프로젝트 멤버 추가 절차 1 (프로젝트 멤버 데이터 삽입)
	 * @param map
	 * @return
	 */
	public boolean prMemInsert_1(Map<String, String> map);
	
	/**
	 * 프로젝트 멤버 추가 절차 2 (프로젝트 인원 수정)
	 * @param map
	 * @return
	 */
	public boolean prMemInsert_2(String pr_id);
	
	/**
	 * 프로젝트 매니저 수정 절차 1 (프로젝트 매니저 권한 변경)
	 * @param map
	 * @return
	 */
	public boolean prMgrEdit_1(Map<String, String> map);
	
	/**
	 * 프로젝트 매니저 수정 절차 2 (새로운 프로젝트 매니저 권한 변경)
	 * @param map
	 * @return
	 */
	public boolean prMgrEdit_2(Map<String, String> map);
	
	/**
	 * 프로젝트 멤버 리스트 조회
	 * @param pr_id 해당하는 프로젝트 아이디
	 * @return
	 */
	public List<Map<String, String>> prMemListSelect(String pr_id);
	
	/**
	 * 프로젝트 멤버 삭제 절차 1 (프로젝트 멤버 삭제)
	 * @param map
	 * @return
	 */
	public boolean prMemDelete_1(Map<String, String> map);
	
	/**
	 * 프로젝트 멤버 삭제 절차2 (프로젝트 인원 정보 수정)
	 * @param map
	 * @return
	 */
	public boolean prMemDelete_2(String pr_id);
	
	/**
	 * 프로젝트 진행률 수정 절차 1 (해당 프로젝트의 전체 업무리스트의 진행률)
	 * @param pr_id
	 * @return (해당하는 프로젝트의 wk_id 리스트를 반환)
	 */
	public List<String> prRateEdit_1(String pr_id);
	
	/**
	 * 프로젝트 진행률 수정 절차 2 (업무 진행률 업데이트)
	 * @param wk_id
	 * @return
	 */
	public boolean prRateEdit_2(Map<String, String> map);
	
	/**
	 * 삭제할 프로젝트의 업무리스트 조회
	 * @param map (pr_id를 담아 전송)
	 * @return 해당하는 프로젝트의 wk_id리스트 반환
	 */
	public List<String> projectDelete_1(String pr_id);
	
	/**
	 * 해당하는 업무리스트에 포함된 첨부파일 리스트 조회 (조회결과로 실제 파일 삭제 service에서)
	 * @param wk_id
	 * @return
	 */
	public List<GbAttachDto> projectDelete_2(String wk_id);
	
	/**
	 * 첨부파일 삭제
	 * @param wk_id
	 * @return
	 */
	public boolean projectDelete_3(String wk_id);
	
	/**
	 * 업무 코멘트 삭제
	 * @param wk_id
	 * @return
	 */
	public boolean projectDelete_4(String wk_id);
	
	/**
	 * 하위업무 삭제
	 * @param wk_id
	 * @return
	 */
	public boolean projectDelete_5(String wk_id);
	
	/**
	 * 프로젝트의 업무 삭제
	 * @param map
	 * @return
	 */
	public boolean projectDelete_6(String pr_id);
	
	/**
	 * 프로젝트 멤버 삭제
	 * @param map
	 * @return
	 */
	public boolean projectDelete_7(String pr_id);
	
	/**
	 * 프로젝트 그룹 관계 삭제
	 * @param map
	 * @return
	 */
	public boolean projectDelete_8(String pr_id);
	
	/**
	 * 프로젝트 멤버 카운트 조정
	 * @param map
	 * @return
	 */
	public boolean projectDelete_9(String pr_id);
	
	/**
	 * 프로젝트 비활성화
	 * @param map
	 * @return
	 */
	public boolean projectDelete_10(String pr_id);
	
	/**
	 * 프로젝트 내 권한 조회
	 * @param map : 조회할 pr_id와 mem_id를 담은 map
	 * @return
	 */
	public Map<String, String> myLevelSelect(Map<String, String> map);
	
	/**
	 * 프로젝트 및 그룹 내 멤버 정보 조회
	 * @param map
	 * @return
	 */
	public Map<String, String> memInfoSelect_1(Map<String, String> map);
	
	/**
	 * 프로젝트 및 그룹 내 멤버 정보 조회 : 그룹 내 참여한 프로젝트 조회
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> memInfoSelect_2(Map<String, String> map);
	
	/**
	 * 그룹 프로젝트 상태 조정 기능 : 진행중
	 * @return
	 */
	public void editGProCondition_1(Map<String, String> map);
	
	/**
	 * 그룹 프로젝트 상태 조정 기능 : 진행완료
	 * @return
	 */
	public void editGProCondition_2(Map<String, String> map);
	
	/**
	 * 그룹 프로젝트 상태 조정 기능 : 진행중
	 * @return
	 */
	public void editIProCondition_1(String mem_id);
	
	/**
	 * 그룹 프로젝트 상태 조정 기능 : 진행완료
	 * @return
	 */
	public void editIProCondition_2(String mem_id);

}
