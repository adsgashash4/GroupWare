package com.pm.rc.model.service;

import java.util.List;
import java.util.Map;

import com.pm.rc.dto.MemberDto;
import com.pm.rc.dto.ProjectDto;

public interface ProjectService {

	/**
	 * 소속 그룹+내가 참여하는 진행중인 프로젝트(메인) 조회
	 * @param map value:mem_id(회원아이디), gr_id(그룹아이디)
	 * @return List&lt;ProjectDto&gt;
	 * @author 김혜원
	 * */
	public List<ProjectDto> groupProSelect(Map<String, String> map);
	
	/**
	 * 개인 사용자가 소속되어있는 프로젝트 (진행중) 를 조회 (메인) o
	 * @param String mem_id 회원 아이디
	 * @return List&lt;ProjectDto&gt;
	 * @author 라한솔
	 */	
	public List<ProjectDto> myProSelect(String mem_id);
	
	/* 진행중인 프로젝트 조회 service */
	/**
	 * 나의 진행중인 프로젝트 리스트 건 수
	 * 사이드 메뉴의 진행중인 프로젝트 총 갯수 조회 service 
	 * @param mem_id 회원아이디
	 * @return List&lt;ProjectDto&gt;
	 * @author 김혜원
	 * */
	public Map<String, String> myDoingPrSelect(String mem_id);
	
	/**
	 * 내가 진행중인 그룹 프로젝트 리스트 건 수
	 * 사이드 바 메뉴의 진행중 프로젝트 건 수를 조회하는 service
	 * @param mem_id
	 * @return
	 */
	public List<Map<String, String>> myDoingGPrListSelect(ProjectDto dto);
	
	/**
	 * 페이징 - 조회된 진행중인 그룹 프로젝트의 총 갯수
	 * @param mem_id
	 * @return
	 */
	public int myDoingGpTotalcount(ProjectDto dto);
	
	/**
	 * 내가 진행중인 개인 프로젝트 리스트
	 * @param mem_id
	 * @return
	 */
	public List<Map<String, String>> myDoingIPrListSelect(ProjectDto dto);
	
	/**
	 * 페이징 - 조회된 진행중인 개인 프로젝트의 총 갯수
	 * @param mem_id
	 * @return
	 */
	public int myDoingIpTotalcount(ProjectDto dto);
	/* 진행중인 프로젝트 조회 service 끝 */
	
	/* 진행예정인 프로젝트 조회 service */
	/**
	 * 나의 진행예정인 프로젝트 리스트 건 수
	 * 사이드 바 메뉴의 진행예정 프로젝트 건 수를 조회하는 service
	 * @param mem_id 회원아이디
	 * @return List&lt;ProjectDto&gt;
	 * */
	public Map<String, String> myTodoPrSelect(String mem_id);
	
	/**
	 * 나의 진행예정인 그룹 프로젝트 리스트 조회 서비스
	 * @param mem_id
	 * @return 
	 */
	public List<Map<String, String>> myTodoGPrListSelect(ProjectDto dto);
	
	/**
	 * 페이징 - 진행예정인 그룹 프로젝트의 총 갯수
	 * @param mem_id
	 * @return
	 */
	public int myTodoGpTotalcount (ProjectDto dto);
	
	/**
	 * 나의 진행예정인 개인 프로젝트 리스트 조회 서비스
	 * @param mem_id
	 * @return
	 */
	public List<Map<String, String>> myTodoIPrListSelect(ProjectDto dto);
	
	/**
	 * 페이징 - 진행예정인 개인 프로젝트의 총 갯수
	 * @param mem_id
	 * @return
	 */
	public int myTodoIpTotalcount(ProjectDto dto);
	/* 진행예정인 프로젝트 조회 service 끝 */
	
	/* 진행완료인 프로젝트 조회 service */
	/**
	 * 나의 진행완료인 프로젝트 리스트 건 수
	 * 사이드 바 메뉴의 진행완료 프로젝트 건 수를 조회하는 service
	 * @param mem_id 회원아이디
	 * @return List&lt;ProjectDto&gt;
	 * */
	public Map<String, String> myDidPrSelect(String mem_id);
	
	/**
	 * 나의 진행완료인 그룹 프로젝트 리스트 조회 서비스
	 * @param mem_id
	 * @return
	 */
	public List<Map<String, String>> myDidGPrListSelect(ProjectDto dto);
	
	/**
	 * 페이징 - 진행완료인 그룹 프로젝트 총 갯수
	 * @param mem_id
	 * @return
	 */
	public int myDidGpTotalcount (ProjectDto dto);
	
	/**
	 * 나의 진행완료인 개인 프로젝트 리스트 조회 서비스
	 * @param mem_id
	 * @return
	 */
	public List<Map<String, String>> myDidIPrListSelect(ProjectDto dto);
	
	/**
	 * 나의 진행완료인 개인 프로젝트 총 갯수
	 * @param mem_id
	 * @return
	 */
	public int myDidIpTotalcount(ProjectDto dto);
	/* 진행완료인 프로젝트 조회 service 끝 */

	/**
	 * 시스템 전체  프로젝트 조회(검색)
	 * @param pr_name 프로젝트명
	 * @return List&lt;ProjectDto&gt;
	 * @author 김혜원
	 * */
	public List<Map<String, String>> allPrSearchSelect(ProjectDto dto);
	public int allPrSearchTotalCount (ProjectDto dto);
	
	/**
	 * 프로젝트 상세정보 조회
	 * @param pr_id 프로젝트 아이디
	 * @return ProjectDto
	 * @author 김혜원
	 * */
	public Map<String, String> prDetailSelect(String pr_id);
	
	/**
	 * 그룹 프로젝트 생성
	 * @param map, group 정보, project정보, mem_id를 담아 전송
	 * @return boolean
	 * */
	public boolean gPrInsert(Map<String, String>  map);
	
	/**
	 * 개인 프로젝트 생성
	 * @param map value:mem_id(회원아이디), project 정보
	 */
	public boolean iPrInsert(Map<String, String> map);
	
	/**
	 * 프로젝트 매니저 조회
	 * @param pr_id
	 * @return
	 */
	public MemberDto prManagerSelect(String pr_id);
	
	/**
	 * 프로젝트 기본정보 수정
	 * @param ProjectDto
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean projectEdit(ProjectDto dto);
	
	/**
	 * 프로젝트 삭제
	 * @param map pr_id, mem_id 등을 담아 전송
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean projectDelete(String pr_id);
	
	/**
	 * 프로젝트 초대 가능 멤버 조회 (그룹 내 프로젝트에 참여하지 않는 멤버)
	 * @param map
	 * @return
	 */
	public List<MemberDto> prMemInsertSearch(Map<String, String> map);
	
	/**
	 * 프로젝트 멤버 추가
	 * @param map value:mem_id(회원아이디), pr_id(프로젝트아이디) 
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean prMemInsert(Map<String, Object> map);
	
	/**
	 * 프로젝트 멤버 삭제
	 * @param map value:mem_id(회원아이디), pr_id(프로젝트아이디)
	 * @return boolean 
	 * @author 김혜원
	 * */
	public boolean prMemDelete(Map<String, Object> map);
	
	/**
	 * 프로젝트 멤버 조회
	 * @param pr_id 프로젝트아이디
	 * @return List&lt;MemberDto&gt;
	 * @author 김혜원
	 * */
	public List<Map<String, String>> prMemListSelect(String pr_id);
	
	/**
	 * 프로젝트 담당자 수정(pr_id, mem_id)
	 * @param map value:pr_id(프로젝트아이디), mem_id(기존 매니저 회원아이디), mem_id(새로운 매니저 회원 아이디)
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean prMgrModify(Map<String, String> map);
	
	/**
	 * 프로젝트 진행률
	 * @param pr_id 프로젝트 아이디
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean prRateEdit(String pr_id);
	
	/**
	 * 프로젝트 내 권한 조회
	 * @param map : 조회할 pr_id와 mem_id를 담은 map
	 * @return
	 */
	public String myLevelSelect(Map<String, String> map);
	
	/**
	 * 프로젝트 및 그룹 내 멤버 정보 조회 : 멤버정보 조회
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
	 * 프로젝트 탈퇴
	 * @param map : pr_id, mem_id 포함
	 * @return
	 */
	public boolean leaveProject(Map<String, String> map);
	
	/**
	 * 그룹 프로젝트 상태 조정 기능
	 * @return
	 */
	public void editGProCondition(Map<String, String> map);
	
	/**
	 * 개인 프로젝트 상태 조정 기능
	 * @return
	 */
	public void editIProCondition(String mem_id);
	
}
