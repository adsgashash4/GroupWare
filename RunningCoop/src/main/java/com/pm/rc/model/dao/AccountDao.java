package com.pm.rc.model.dao;

import java.util.List;
import java.util.Map;

import com.pm.rc.dto.MemberDto;

/**
 * 회원계정 관련 기능 인터페이스
 * @author 김호빈
 * @version AccountService Beta 1.0
 **/
public interface AccountDao {
	
	/**
	 * 로그인 프로세스
	 * @param map = 입력받은 mem_id와 mem_pw를 담은 map으로 값 전송
	 * @return MemberDto로 회원의 아이디와 이름을 받아 세션을 생성할 때 사용
	 * @author 김호빈
	 */
	public MemberDto loginPro(Map<String, String> map);
	
	/**
	 * 회원가입 시 아이디 중복 조회 프로세스
	 * @param id - 회원이 입력한 아이디를 value로 전송
	 * @return 중복되면 사용불가, 중복이 없으면 사용가능
	 * @author 김호빈
	 */
	public String memIdSelect(String mem_id);
	
	/**
	 * 이메일 중복 확인
	 * @param mem_email 회원이메일
	 * @return boolean(true: 중복 메일 없음, false: 중복메일 있음)
	 * @author 김혜원
	 * */
	public boolean memEmailSelect(String mem_email);
	
	/**
	 * 회원 등록 프로세스
	 * @param dto 회원이 폼에 작성한 데이터를 MemberDto로 value 전송
	 * @return 정상적으로 등록되면 true, 등록되지 않으면 false 반환
	 * @author 김호빈
	 */
	public boolean memInsert(MemberDto dto);
	
	/**
	 * 아이디 찾기(아이디 분실했을 때)
	 * @param map - value:mem_name(회원이름), mem_email(회원이메일)
	 * @return 두가지 값을 비교하여 나오는 아이디를 String으로 반환
	 * @author 김호빈
	 */
	public String memIdSearch(Map<String, String> map);
	
	/**
	 * 본인확인(비밀번호 재발급 받을을 때)
	 * @param map - value:mem_id(회원아이디), mem_email(회원이메일)
	 * @return 조건에 일치하는 결과값 개수가 1개이면 true, 아니면(0개) false
	 * @author 김혜원
	 */
	public boolean memIdEmailChk(Map<String, String> map);
	
	/**
	 * 비밀번호 변경
	 * @param id - 회원이 입력한 값을 value로 전송하여 해당하는 id에 해당하는 pw column을 변경
	 * @return update 성공하면 true, 실패하면 false 반환
	 * @author 김호빈
	 */
	public boolean memPwModify(Map<String, String> map);
	
	/**
	 * 계정정보 수정을 위한 본인확인
	 * @param mem_id 회원아이디
	 * @return String(mem_pw:회원 비밀번호)
	 * @author 김혜원
	 * */
	public String memPwSelect(String mem_id);
	
	/**
	 * 계정정보조회 (개인정보수정페이지 들어갈 때 깔리는 정보)
	 * @param id - 세션에 입력되어있는 아이디 값을 value로 전송하여 해당하는 컬럼 select
	 * @return id값에 해당하는 정보를 MemberDto에 담아 반환함
	 * @author 김호빈
	 */
	public MemberDto memSelect(String mem_id);
	
	/**
	 * 계정정보수정 (개인정보 수정페이지에서 수정 버튼 클릭 시 처리)
	 * @param map - 회원이 수정하기위해 입력한 정보를 map에 담아 전송 
	 * @return 수정 성공하면 true, 실패하면 false 반환
	 * @author 김호빈
	 */
	public boolean memInfoModify(MemberDto dto);
	
	/**
	 * 서비스 탈퇴 전 GM인 그룹 찾기
	 * @param mem_id - 세션에서 id값 받아오기
	 * @return mem_id를 포함하는 group 정보 리스트 반환
	 */
	public List<Map<String, String>> levelGmSelect (String mem_id);
	
	/**
	 * 서비스 탈퇴 전 PM인 그룹 프로젝트 찾기
	 * @param mem_id - 세션에서 id값 받아오기
	 * @return mem_id를 포함하는 project 정보 리스트 반환
	 */
	public List<Map<String, String>> levelPmSelect (String mem_id);
	
/*	*//**
	 * 회원이 가입한 그룹 목록 출력
	 * @param mem_id - 세션에서 id값 받아오기
	 * @return mem_id를 포함하는 gr_id 리스트 반환
	 *//*
	public Map<String, String[]> memDelete_1 (String mem_id);
	
	*//**
	 * 소속된 그룹의 참여 프로젝트 목록 출력
	 * @param map = mem_id, gr_id를 Map에 담아 값 전달
	 * @return mem_id 와 pr_id 배열을 Map으로 반환
	 *//*
	public Map<String, String[]> memDelete_2 (Map<String, String[]> map);*/
	
	/**
	 * 그룹 프로젝트 탈퇴 처리 
	 * @param mem_id 회원아이디
	 * @return 탈퇴 정상 처리되면 true, 실패하면 false
	 */
	public boolean memDelete_1 (String mem_id);
	
	/**
	 * 프로젝트 멤버 수 조정
	 * @return 멤버수 조정이 정상 처리되면 true, 실패하면 false
	 */
	public boolean memDelete_2 ();
	
	/**
	 * 그룹 탈퇴 처리
	 * @param mem_id 회원아이디
	 * @return 탈퇴정상 처리되면 true, 실패하면 false
	 */
	public boolean memDelete_3 (String mem_id);
	
	/**
	 * 그룹 멤버 수 조정
	 * @return 멤버수 조정이 모두 정상 처리되면 true, 실패하면 false
	 */
	public boolean memDelete_4 ();
	
/*	*//**
	 * 개인 프로젝트 목록 조회
	 * @param mem_id - session에서 받아온 mem_id 투입
	 * @return mem_id 와 pr_id String배열을 Map으로 반환
	 *//*
	public Map<String, String[]> memDelete_5 (String mem_id);*/
	
	/**
	 * 개인 프로젝트 비활성화
	 * @param mem_id 회원아이디
	 * @return 모두 비활성화 처리되면 true, 하나라도 실패하면 false;
	 */
	public boolean memDelete_5 (String mem_id);
	
	/**
	 * 개인 프로젝트 관계 테이블 컬럼 삭제 
	 * @param mem_id 회원아이디
	 * @return 탈퇴 정상 처리되면 true, 실패하면 false
	 */
	public boolean memDelete_6 (String mem_id);
	
	/**
	 * 그룹 가입 신청리스트에서 멤버정보 삭제
	 * @param mem_id - session에서 받아온 mem_id값(회원아이디)
	 * @return 삭제 되면 true, 실패하면 false
	 */
	public boolean memDelete_7 (String mem_id);
	
	/**
	 * 회원 비활성화
	 * @param mem_id - session에서 받아온 mem_id값(회원아이디)
	 * @return -회원 비활성화 성공하면 true, 실패하면 false
	 */
	public boolean memDelete_8 (String mem_id);
	

}
