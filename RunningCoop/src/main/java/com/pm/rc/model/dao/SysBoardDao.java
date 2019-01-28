package com.pm.rc.model.dao;

import java.util.List;
import java.util.Map;

import com.pm.rc.dto.SystemBoardDto;

/**
 * 시스템 게시판 관련 기능 인터페이스
 * @author 김호빈
 * @version SysBoardService Beta 1.0
 */
public interface SysBoardDao {
	
	/**
	 * 공지게시판 게시글 목록 출력 +페이징
	 * @return 게시글의 목록을 list에 담아 반환
	 */
	public List<Map<String, String>> noticeListSelect(SystemBoardDto dto);
	public int noticeListSelectCount (SystemBoardDto dto);
	
	/**
	 * 문의게시판 게시글 목록 출력
	 * @return 게시글의 목록을 list에 담아 반환
	 */
	public List<Map<String, String>> qnaListSelect(SystemBoardDto dto);
	public int qnaListSelectCount(SystemBoardDto dto);
	
	/**
	 * 공개 게시글 내용 출력
	 * @param map sbr_uuid에 게시글 고유값을 담아 전송
	 * @return SystemBoardDto에 해당하는 게시글의 정보를 담아 반환
	 */
	public Map<String, String> openViewSelect(Map<String, String> map);
	
	/**
	 * 비공개 게시글 내용 출력
	 * @param dto : sbr_uuid와 sbr_pw를 담아 전송
	 * @return 해당하는 게시글의 정보를 Map형태로 반환
	 */
	public Map<String, String> scrViewSelect (Map<String, String> map);
	
	/**
	 * 수정 시 게시글 내용 출력
	 * @param sbr_uuid : 해당하는 게시글의 uuid
	 * @return 해당하는 게시글의 정보를 Map형태로 반환
	 */
	public Map<String, String> editBoardViewSelect(Map<String, String> map);
	
	/**
	 * 관리자가 게시글을 조회할 때 게시글 내용 출력
	 * @param map sbr_uuid에 게시글 고유값을 담아 전송
	 * @return 해당하는 게시글의 정보를 dto형태로 반환
	 */
	public Map<String, String> sysViewSelect (Map<String, String> map);
	
	/**
	 * 해당하는 게시글의 주소를 가진 첨부파일 목록을 출력
	 * @param map sbr_uuid에 게시글 고유값을 담아 전송
	 * @return 해당하는 조건의 첨부파일을 list로 반환
	 */
	public Map<String, String> sysAttachSelect(Map<String, String> map);
	
	/**
	 * 문의게시판 게시글 작성
	 * @param dto 입력받은 정보를 dto형태로 전송
	 * @return 작성 성공하면 true, 실패하면 false
	 */
	public boolean qnaBoardInsert (Map<String, String> map);
	
	/**
	 * 게시글 작성 시 파일 업로드
	 * @param dto 입력받은 정보를 dto형태로 전송
	 * @return 작성 성공하면 true, 실패하면 false
	 */
	public boolean FileInsert (Map<String, String> map);
	
	/**
	 * 게시글 수정 (공지/문의 통합)
	 * @param dto : 입력받은 정보를 dto형태로 전송
	 * @return 수정 성공하면 true, 실패하면 false
	 */
	public boolean sysBoardUpdate (Map<String, String> map);
	
	/**
	 * 수정되는 게시글의 첨부파일을 수정 (공지/문의 통합)
	 * @param dto : 입력받은 정보를 dto형태로 전송
	 * @return 수정 성공하면 true, 실패하면 false
	 */
	public boolean FileUpdate (Map<String, String> map);
	
	/**
	 * 게시글 삭제 (공지/문의 통합)
	 * @param sbr_uuid 해당하는 게시글의 게시글 uuid를 값으로 전송
	 * @return 삭제 성공하면 true, 실패하면 false
	 */
	public boolean sysBoardDelete (String sbr_uuid);
	
	/**
	 * 게시글에 첨부파일이 있는지 확인
	 * @param sbr_uuid
	 * @return 있으면 true, 없으면 false
	 */
	public boolean FileCheck (String sbr_uuid);
	
	/**
	 * 삭제되는 게시글의 첨부파일을 삭제
	 * @param sbr_uuid 삭제되는 게시글의  uuid를 담아 전송
	 * @return 삭제 성공하면 true, 실패하면 false
	 */
	public boolean FileDelete (String sbr_uuid);
	

}
