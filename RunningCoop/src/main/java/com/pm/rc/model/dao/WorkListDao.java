package com.pm.rc.model.dao;

import java.util.List;
import java.util.Map;

import com.pm.rc.dto.GbAttachDto;
import com.pm.rc.dto.GroupBoardDto;
import com.pm.rc.dto.WorkCommentDto;
import com.pm.rc.dto.WorkDetailDto;
import com.pm.rc.dto.WorkListDto;

/**
 * 업무리스트 관련 기능 인터페이스
 * @author 김혜원
 * */
public interface WorkListDao {

	/**
	 * 업무리스트 조회--1.진행중
	 * @param pr_id 프로젝트아이디
	 * @return List로 업무리스트 반환
	 * @author 김혜원
	 * */
	public List<Map<String, String>> wkListSelect1(Map<String, String> map);
	
	/**
	 * 업무리스트 조회--2.진행예정
	 * @param pr_id 프로젝트아이디
	 * @return List 로 업무리스트 반환
	 * @author 김혜원
	 * */
	public List<Map<String, String>> wkListSelect2(Map<String, String> map);
	
	/**
	 * 업무리스트 조회--3.진행완료
	 * @param pr_id 프로젝트아이디
	 * @return List map 으로 업무리스트 반환
	 * @author 김혜원
	 * */
	public List<Map<String, String>> wkListSelect3(Map<String, String> map);
	
	/**
	 * 프로젝트 업무 리스트 추가
	 * @param dto WorkListDto객체
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean wkListInsert(WorkListDto dto);
	
	/**
	 * 프로젝트 업무 리스트 삭제 프로세스 1 : 하위업무 삭제
	 * @param wk_id 워크리스트 아이디
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean wkListDelete_1(String wk_id);
	
	/**
	 * 프로젝트 업무 리스트 삭제 프로세스 2 : 업무 삭제
	 * @param wk_id
	 * @return
	 */
	public boolean wkListDelete_2(String wk_id);
	
	/**
	 * 프로젝트 업무 리스트 수정 프로세스
	 * @param dto WorkListDto객체
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean wkListModify(WorkListDto dto);

	/**
	 * 워크리스트 작업진행률 수정 프로세스 1 : 해당 업무의 전체 하위 업무 수
	 * @param wk_id 업무 아이디
	 * @return int
	 * @author 김혜원
	 * */
	public int wkRateModify_1(String wk_id);
	
	/**
	 * 업무리스트 작업진행률 수정 프로세스 2 : 해당 업무의 완료 업무 수
	 * @param wk_id 업무 아이디
	 * @return int로 업무 수 반환
	 */
	public int wkRateModify_2(String wk_id);
	
	/**
	 * 업무리스트 작업진행률 수정 프로세스 3 : 해당 업무의 작업진행률 수정
	 * @param wk_id 업무 아이디
	 * @return 수정 성공하면 true, 실패하면 false
	 */
	public boolean wkRateModify_3(Map<String, Object> map);
	
	/**
	 * 하위업무리스트 조회
	 * @param wk_id 워크리스트 아이디
	 * @return WorkDetailDto
	 * @author 김혜원
	 * */
	public List<WorkDetailDto> wdSelect(String wk_id);
	
	/**
	 * 하위업무리스트 추가
	 * @param dto WorkDetailDto객체
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean wdInsert(WorkDetailDto dto);
	
	/**
	 * 하위업무리스트 수정
	 * @param dto WorkDetailDto객체
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean wdModify(WorkDetailDto dto);
	
	/**
	 * 하위업무리스트 삭제
	 * @param wd_id(하위업무리스트 아이디)
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean wdDelete(String wd_id);

	/**
	 * 하위업무 애로사항 체크
	 * @param wd_id 하위업무리스트 아이디
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean wdErrorChk(Map<String, String> map);
	
	/**
	 * 하위업무리스트 완료상태 수정
	 * @param wd_id 하위업무리스트 아이디
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean wdComplModify(String wd_id);
	
	/**
	 * 업무리스트 페이지 댓글 조회
	 * @param wk_id 워크리스트 아이디
	 * @return List&lt;WorkCommentDto&gt;
	 * */
	public List<Map<String, String>> wCommListSelect(String wk_id);
	
	/**
	 * 업무리스트 페이지 댓글 추가
	 * @param dto WorkCommentDto객체
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean wCommentInsert(WorkCommentDto dto);
	
	/**
	 * 업무리스트 페이지 댓글 수정
	 * @param dto WorkCommentDto
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean wCommentModify(WorkCommentDto dto);
	
	/**
	 * 업무리스트 페이지 댓글 삭제
	 * @param wcom_id 워크리스트 코멘트 아이디
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean wCommentDelete(String wcom_id);
	
	/**첨부파일 조회
	 * @param wk_id 워크리스트 아이디
	 * @return List&lt;GbAttachDto&gt;
	 * */
	public List<GbAttachDto> gbAttachSelect(String wk_id);
	
	/**
	 * 첨부파일 등록
	 * @param dto GroupBoardDto객체
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean gbAttachInsert(GbAttachDto dto);
	
	/**
	 * 첨부파일 삭제
	 * @param gatt_seq 그룹/프로젝트 게시판 첨부파일 번호
	 * @return boolean
	 * @author 김혜원
	 * */
	public boolean gbAttachDelete(String gatt_seq);
	
	/**
	 * 첨부파일 다운 시 조회
	 * @param gatt_seq
	 * @return
	 */
	public GbAttachDto attachDownSelect(String gatt_seq);
	
}
