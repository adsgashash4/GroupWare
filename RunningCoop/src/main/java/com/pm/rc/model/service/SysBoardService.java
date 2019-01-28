package com.pm.rc.model.service;

import java.util.List;
import java.util.Map;

import com.pm.rc.dto.SystemBoardDto;

/**
 * 회원 입장에서 시스템게시판(문의게시판, 공지게시판)관련 기능 인터페이스
 * @author 김혜원
 * */
public interface SysBoardService {
	
	/**
	 * 공지 게시글 목록 출력, 페이징처리
	 * @return List&lt;SystemBoardDto&gt;
	 * @author 김혜원
	 * */
	public List<Map<String, String>> noticeListSelect(SystemBoardDto dto);
	public int noticeListSelectCount(SystemBoardDto dto);
	
	/**
	 * 문의 게시글 목록 출력, 페이징 처리
	 * @return 문의 게시글의 목록을 
	 * */
	public List<Map<String, String>> qnaListSelect(SystemBoardDto dto);
	public int qnaListSelectCount(SystemBoardDto dto);
	
	/**
	 * 게시글 보기 서비스
	 * @param map sbr_uuid, sbr_pw을 Map객체에 담아 전송
	 * @return 해당하는 게시글의 정보를 Map에 담아 반환
	 */
	public Map<String, String> sysBoardViewSelect(Map<String, String> map);
	
	/**
	 * 수정 시 게시글 출력
	 * @param sbr_uuid 수정할 게시글 uuid
	 * @return Map 형태로 해당 게시글 반환
	 */
	public Map<String, String> editBoardViewSelect(Map<String, String> map);
	
	/**
	 * 문의 게시글 등록
	 * @param dto SystemBoardDto객체
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean qnaBoardInsert(Map<String, String> map);
	
	/**
	 * 문의 게시글 수정
	 * @param dto SystemBoardDto객체
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean qnaBoardEdit(Map<String, String> map);
	
	/**
	 * 문의 게시글 삭제
	 * @param sbr_uuid 시스템게시글 번호
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean qnaBoardDelete(String sbr_uuid);
	
	/**
	 * 첨부파일 조회 메소드
	 * @param sbr_uuid
	 * @return 첨부파일 정보를 Map로 반환
	 */
	public Map<String, String> sysAttachSelect(Map<String, String> map);
	
	
}
