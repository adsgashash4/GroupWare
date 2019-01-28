package com.pm.rc.model.service;

import java.util.List;
import java.util.Map;

import com.pm.rc.dto.GroupDto;
import com.pm.rc.dto.MemberDto;
import com.pm.rc.dto.SystemBoardDto;

/**
 * 시스템 관리자 기능 인터페이스(회원관리는 AccountService로)
 * @author 김혜원
 * */
public interface ManagerService {
	
	/**
	 * 관리자 로그인 화면
	 * @param map
	 * @return
	 */
	public boolean adminLogin(Map<String, String> map);
	
	/**
	 * 회원 목록 출력 
	 * @return list
	 * @author 라한솔
	 */
	public List<Map<String, String>> allMemberSelect(MemberDto dto);
	public int allMemberSelectCount (MemberDto dto);
	
	/**
	 * 회원 목록 출력 (아이디 검색)
	 * @param mem_id
	 * @return list
	 * @author 라한솔
	 */
	public List<MemberDto> allMemberSelectSearch(MemberDto dto);
	
	/**
	 * 회원 정보 조회
	 * @param mem_id
	 * @return MemberDto
	 * @author 김혜원
	 * */
	public MemberDto sysMemView(String mem_id);
	
	/**
	 * 회원 정보 수정
	 * @param dto
	 * @return MemberDto
	 * @author 김혜원
	 * */
	public Boolean sysMemModify(MemberDto dto);
	
	/**
	 * 그룹생성승인요청 리스트 출력  (전부)
	 * @return list 
	 * @author 라한솔
	 */
	public List<GroupDto> grApplySelect (String gr_name);
	
	/**
	 * 그룹 생성 거절 다음 해당 그룹 관리자 삭제
	 * @param gr_id 그룹아이디
	 * @return boolean
	 * @author 라한솔
	 */
	public boolean grDelete (String[] gr_id);
	
	/**
	 * 그룹승인화면에서 그룹선택시 간략정보 출력
	 * @param String gr_Id
	 * @return List&lt;GroupDto&gt;
	 */
	public List<GroupDto> grApplyInfoView(String gr_id);
	
	/**
	 * 그룹 생성 승인
	 * @param gr_id 그룹아이디
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean grAppModify(String[] gr_id);	
	
	/**
	 * 공지 게시글 목록 출력, 페이징처리
	 * @return List&lt;SystemBoardDto&gt;
	 * @author 김혜원
	 * */
	public List<Map<String, String>> noticeListSelect(SystemBoardDto dto);
	public int noticeListSelectCount(SystemBoardDto dto);
	
	/**
	 * 문의 게시글 목록 출력
	 * @return 문의 게시글의 목록을 
	 * */
	public List<Map<String, String>> qnaListSelect(SystemBoardDto dto);
	public int qnaListSelectCount(SystemBoardDto dto);
	
	/**
	 * 공지사항 등록
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean noticeInsert(Map<String, String> map);
	
	/**
	 * 게시글 보기 서비스
	 * @param map sbr_uuid, sbr_pw을 Map객체에 담아 전송
	 * @return 해당하는 게시글의 정보를 Map에 담아 반환
	 */
	public Map<String, String> sysBoardViewSelect(Map<String, String> map);
	
	/**
	 * 첨부파일 조회 메소드
	 * @param sbr_uuid
	 * @return 첨부파일 정보를 Map로 반환
	 */
	public Map<String, String> sysAttachSelect(Map<String, String> map);
	
	/**
	 * 수정 시 게시글 출력
	 * @param sbr_uuid 수정할 게시글 uuid
	 * @return Map 형태로 해당 게시글 반환
	 */
	public Map<String, String> editBoardViewSelect(Map<String, String> map);
	
	/**
	 * 공지사항 수정
	 * @param dto SystemBoardDto객체
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean noticeModify(Map<String, String> map);
	
	/**
	 * 문의 답글 등록
	 * @param dto SystemBoardDto객체
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean qnaReplyInsert(Map<String, String> map);
	
	/**
	 * 문의 게시글 삭제
	 * @param sbr_uuid 시스템게시글 번호
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean sysBoardDelete(String sbr_uuid);
	
}
