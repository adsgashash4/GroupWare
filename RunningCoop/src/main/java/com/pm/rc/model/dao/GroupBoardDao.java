package com.pm.rc.model.dao;

import java.util.List;
import java.util.Map;

import com.pm.rc.dto.GbAttachDto;
import com.pm.rc.dto.GroupBoardDto;


/**
 * 그룹 게시판 관련 기능 인터페이스
 * @author 김호빈
 * @version GroupService Beta 1.0
 */
public interface GroupBoardDao {
	
	/**
	 * 그룹게시판 게시글 목록
	 * @param map value:gr_id(그룹아이디), mem_id(회원아이디)
	 * @return List&lt;GroupBoardDto&gt;
	 * @author 김혜원
	 * */
	public List<GroupBoardDto> gbListSelect(String gr_id);
	
	/**
	 * 그룹게시판 게시글 검색
	 * @param map gr_id(그룹아이디), br_title(게시글 제목)
	 * @return List&lt;GroupBoardDto&gt;
	 * @author 김혜원
	 * */
	public List<GroupBoardDto> gbTitleSearch(Map<String, String> map);
	
	/**
	 * 그룹게시판 게시글 조회--1.게시글 출력
	 * @param map value:gr_id(그룹아이디), br_uuid(그룹게시글번호)
	 * @return GroupBoardDto
	 * @author 김혜원
	 * */
	public GroupBoardDto gbViewSelect1(Map<String,String> map);
	
	/**
	 * 그룹게시판 게시글 조회--2.첨부파일 출력
	 * @param br_uuid 그룹게시글번호
	 * @return GbAttachDto
	 * @author 김혜원
	 * */
	public List<GbAttachDto> gbViewSelect2(String br_uuid);
	
	/**
	 * 그룹게시판 게시글 조회--3.댓글 출력
	 * @param br_uuid를 받아서 br_refer에 입력
	 * @return GroupBoardDto
	 * @author 김혜원
	 * */
	public GroupBoardDto gbViewSelect3(String br_uuid);
	
	/**
	 * 그룹게시판 게시글 등록-
	 * @param dto GroupBoardDto객체
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean gboardInsert(GroupBoardDto dto);

	/**
	 * 그룹게시판 게시글 수정
	 * @param map value:br_uuid(그룹게시글 번호), GroupBoardDto(회원아이디)
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean gboardModify(GroupBoardDto dto);
	
	/**
	 * 게시글 첨부파일 업데이트 
	 * @param dto : 게시글의 첨부파일이 수정되면 해당 파일을 수정한다.
	 * @return 수정 성공하면 true, 실패하면 false
	 */
	public boolean gbAttachUpdate (GbAttachDto dto);
	
	/**
	 * 첨부파일 등록(그룹게시글 등록 or 수정)
	 * @param dto GbAttachDto객체
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean gbAttachInsert(GbAttachDto dto);
	
	/**
	 * 첨부파일 삭제:그룹게시글 삭제할 때
	 * @param br_uuid 그룹게시글 번호
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean gbAttachDelete(String br_uuid);
	
	/**
	 * 그룹게시판 게시글 삭제
	 * @param br_uuid 그룹게시글번호
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean gboardDelete(String br_uuid);
	
	/**
	 * 그룹게시판 댓글 등록
	 * @param dto GroupBoardDto객체
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean gbCommentInsert(GroupBoardDto dto);
	
	/**
	 * 그룹게시판 댓글 수정
	 * @param dto GroupBoartDto 객체에 담아서 값 전송
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean gbCommentModify(GroupBoardDto dto);
	
	/**
	 * 그룹게시판 댓글 삭제
	 * @param br_uuid 그룹게시글번호
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean bgCommentDelete(String br_uuid);
	
	/**
	 * 수정 시 게시글 출력
	 * @param sbr_uuid 수정할 게시글 uuid
	 * @return Map 형태로 해당 게시글 반환
	 */
	public Map<String, String> grEditBoardViewSelect(Map<String, String> map);
	
	/**
	 * 문의 게시글 수정
	 * @param dto SystemBoardDto객체
	 * @return boolean
	 * @author 라한솔
	 * */
	public boolean grBoardUpdate(Map<String, String> map);
}
