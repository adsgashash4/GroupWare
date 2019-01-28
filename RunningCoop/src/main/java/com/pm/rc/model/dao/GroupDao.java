package com.pm.rc.model.dao;

import java.util.List;
import java.util.Map;

import com.pm.rc.dto.GbAttachDto;
import com.pm.rc.dto.GroupBoardDto;
import com.pm.rc.dto.GroupDto;
import com.pm.rc.dto.MemberDto;
import com.pm.rc.dto.PagingDto;

/**
 * 그룹 관련 기능 인터페이스
 * @author 김혜원
 * @version GroupService Beta 1.0
 **/
public interface GroupDao {
	
	/**
	 * 그룹 검색화면 페이징 처리
	 * @author 라한솔
	 */
	public int allGroupSearchSelectCount(GroupDto dto);
	public List<Map<String, String>> allGroupSearchSelect(GroupDto dto);

	/**
	 *내가 가입한 그룹 조회(접속할 그룹 선택)
	 * @param mem_id 회원 아이디
	 * @return List&lt;GroupDto&gt;
	 * @author 김혜원
	 * */
	public List<GroupDto> myGrSelect(Map<String, String> map);
	
	/**
	 *생성한 승인 대기 그룹 조회
	 * @param mem_id 회원 아이디
	 * @return List&lt;GroupDto&gt;
	 * @author 김혜원
	 * */
	public List<GroupDto> waitGrSelect(Map<String, String> map);
	
	/**
	 * 그룹 상세 정보 출력
	 * @param gr_id 그룹아이디
	 * @return List&lt;GroupDto&gt;
	 * @author 김혜원
	 * */
	public Map<String, String> grDetailSelect (Map<String, String> map);
	
	/**
	 *  그룹 검색 조회(그룹명)
	 * @param  gr_name 그룹명
	 * @return GroupDto
	 * @author 김혜원
	 * */
	public  List<GroupDto>  allGrSelect(Map<String, String> map);
	
	/**
	 * 그룹 생성--1.그룹테이블
	 * @param map value: GroupDto, mem_id(회원아이디)
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean grInsert1(Map<String, String> map);
	
	/**
	 * 그룹 생성--2.그룹소속테이블
	 * @param valu: GroupDto, mem_id 회원아이디
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean grInsert2(Map<String, String> map);
	
	/**
	 * 그룹 정보 수정
	 * @param dto GroupDto객체
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean grModify(Map<String, String> map);
	
	/**
	 * 그룹 가입 신청
	 * @param map value:mem_id, gr_id
	 * @return boolean
	 * */
	public boolean grWaitInsert(Map<String, String> map);
	
	/**
	 * 시스템 회원 조회(아이디로 검색)->초대로
	 * @return List&lt;MemberDto&gt;
	 * @param mem_id 회원아이디
	 * @author 김혜원
	 * */
	public List<MemberDto> sysMemSelect(String mem_id);
	
	/**
	 * 그룹 가입 요청 리스트 출력
	 * @param gr_id 그룹아이디
	 * @return List&lt;MemberDto&gt;
	 * */
	public List<MemberDto> grWaitList(String gr_id);
	
	/**
	 * 그룹멤버추가--1.가입승인
	 * @param map value:gr_id(그룹아이디), mem_id(회원아이디)
	 * @author 김혜원
	 * */
	public boolean grMemInsert1(Map<String, String> map);
	
	/**
	 * 그룹멤버추가--2.가입리스트 삭제
	 * @param map value:gr_id(그룹아이디), mem_id(회원아이디)
	 * @author 김혜원
	 * */
	public boolean grMemInsert2(Map<String, String> map);
	
	/**
	 * 그룹멤버추가--3.그룹멤버인원수 증가
	 * @param gr_id 그룹아이디
	 * @author 김혜원
	 * */
	public boolean grMemInsert3(Map<String, String> map);
	
	/**
	 * 그룹 가입 요청 거절
	 * @param map value:gr_id(그룹아이디), mem_id(회원아이디)
	 * @return boolean
	 * */
	public boolean grMemReject(Map<String, String> map);
	
	/**
	 * 그룹멤버삭제(강제탈퇴)--1.테이블에서 지우기
	 * @param map value:gr_id(그룹아이디), mem_id(회원아이디)
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean grMemDelete1(Map<String, String> map);
	
	/**
	 * 그룹멤버삭제(강제탈퇴)--2.그룹 인원 수 수정
	 * @param gr_id 그룹아이디
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean grMemDelete2(Map<String, String> map);
	
	/**
	 * 그룹멤버조회(멤버리스트 출력)
	 * @param gr_id 그룹아이디
	 * @return List&lt;MemberDto&gt;
	 * @author 김혜원
	 * */
	public List<MemberDto> grMemSelect(String gr_id);
	
	/**
	 * 그룹 관리자 변경
	 * @param map value: gr_id(그룹아이디), mem_id(회원아이디)
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean grMgrModify(Map<String, String> map);
	
	/**
	 * 그룹 삭제 프로젝트 관련 1-1 (팀일정삭제)
	 * @param gr_id
	 * @return boolean
	 * @author 라한솔
	 */
	public boolean groupDelete1(String gr_id);
	
	/**
	 * 그룹 삭제 프로젝트 관련 1-2 프로젝트 첨부파일 삭제
	 * @param gr_id
	 * @return boolean
	 * @author 라한솔
	 */
	public boolean groupDelete2(String gr_id);
	
	/**
	 * 그룹삭제 프로젝트 관련 1-3 프로젝트 하위 업무 삭제
	 * @param gr_id
	 * @return boolean
	 * @author 라한솔
	 */
	public boolean groupDelete3(String gr_id);
	
	/**
	 * 그룹삭제 프로젝트 관련 1-4 프로젝트 업무 코멘트 삭제
	 * @param gr_id
	 * @return boolean
	 * @author 라한솔
	 */
	public boolean groupDelete4(String gr_id);
	
	/**
	 * 그룹삭제 프로젝트 관련 1-5 프로젝트 업무 삭제
	 * @param gr_id
	 * @return boolean
	 * @author 라한솔
	 */
	public boolean groupDelete5(String gr_id);
	
	/**
	 * 그룹삭제 프로젝트 관련 1-6 프로젝트 멤버 삭제
	 * @param gr_id
	 * @return boolean
	 * @author 라한솔
	 */
	public boolean groupDelete6(String gr_id);
	
	/**
	 * 그룹삭제 프로젝트 관련 1-7 프로젝트 인원 수정 및 그룹 비활성화
	 * @param gr_id
	 * @return boolean
	 * @author 라한솔
	 */
	public boolean groupDelete7(String gr_id);
	
	/**
	 * 그룹삭제 게시판 관련 2-1 그룹 게시판 첨부파일 삭제
	 * @param gr_id
	 * @return boolean
	 * @author 라한솔
	 */
	public boolean groupDelete8(String gr_id);
	
	/**
	 * 그룹삭제 게시판 관련 2-2 그룹게시판 코멘트 삭제
	 * @param gr_id
	 * @return boolean
	 * @author 라한솔
	 */
	public boolean groupDelete9(String gr_id);
	
	/**
	 * 그룹삭제 게시판 관련 2-3 그룹게시판 게시글 삭제
	 * @param gr_id
	 * @return boolean
	 * @author 라한솔
	 */
	public boolean groupDelete10(String gr_id);
	
	/**
	 * 그룹삭제 게시판 관련 2-4 그룹 가입신청 리스트 삭제
	 * @param gr_id
	 * @return boolean
	 * @author 라한솔
	 */
	public boolean groupDelete11(String gr_id);
	
	/**
	 * 그룹삭제 게시판 관련 2-5 그룹멤버 삭제
	 * @param gr_id
	 * @return boolean
	 * @author 라한솔
	 */
	public boolean groupDelete12(String gr_id);
	
	/**
	 * 그룹삭제 게시판 관련 2-6 그룹 인원 업데이트 및 비활상화
	 * @param gr_id
	 * @return boolean
	 * @author 라한솔
	 */
	public boolean groupDelete13(String gr_id);
	
	/**
	 * 그룹관리자변경
	 * @param mem_id , mem_id2
	 * @return boolean
	 * @author 라한솔
	 */
	public boolean newGrMgChange(Map<String, String> map);
	public boolean oldGrMaChange(Map<String, String> map);
	
	/**
	 * 그룹가입시 중복체크
	 * @param gr_id ,mem_id
	 * @return list
	 * @author 라한솔
	 */
	public int groupCheck (Map<String, String> map);
	
	/**
	 * 그룹생성시 그룹이름 중복체크
	 * @param gr_name
	 * @return int
	 * @author 라한솔
	 */
	public int grNameCheck(String gr_name);
	
	public List<Map<String, String>> grBoradList (GroupBoardDto gDto);
	public int grBoradListCnt (GroupBoardDto gDto);
	
	/**
	 *그룹선택시 이미지 출력
	 *@param gr_id
	 *@return String
	 *@author 라한솔
	 */
	public int groupImg (String gr_id);
	
	/**
	 * 그룹 내 사용자 권한 조회
	 * @param mem_id
	 * @return
	 */
	public Map<String, String> groupManagerSearch(Map<String, String> map);
	
	/**
	 * 그룹게시판 게시글 등록
	 * @param dto GroupBoardDto객체
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean grBoardInsert(Map<String, Object> map);
	
	/**
	 * 그룹게시판 게시글 조회
	 * @param map value: gr_id, br_uuid, GroupBoardDto
	 * @return map
	 * @author 라한솔
	 * */
	public Map<String, String> gbViewSelect(Map<String, String> map);
	
	/**
	 * 접속 중인 그룹명 조회
	 * @param gr_id
	 * @result gr_name
	 * @author 김혜원
	 * */
	public String sessionGrSelect(String gr_id);
}

