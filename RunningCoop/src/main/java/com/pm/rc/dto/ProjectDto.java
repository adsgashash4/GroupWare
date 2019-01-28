package com.pm.rc.dto;

public class ProjectDto {

	private String pr_id;
	private String pr_name;
	private String pr_goal;
	private int pr_memCnt;
	private String pr_startDate;
	private String pr_endDate;
	private int pr_proRate;
	private String pr_searchYN;
	private String pr_etc;
	private String pr_regDate;
	private String pr_condition;
	private String pr_delYN;
	private String mem_id; // 실제 DB에는 존재하지 않는 컬럼이나 프로젝트 검색 시 해당 변수가 필요하여 추가함.
	
	private PagingProDto paging;
	
	public ProjectDto(){}

	public String getPr_id() {
		return pr_id;
	}

	public void setPr_id(String pr_id) {
		this.pr_id = pr_id;
	}

	public String getPr_name() {
		return pr_name;
	}

	public void setPr_name(String pr_name) {
		this.pr_name = pr_name;
	}

	public String getPr_goal() {
		return pr_goal;
	}

	public void setPr_goal(String pr_goal) {
		this.pr_goal = pr_goal;
	}

	public int getPr_memCnt() {
		return pr_memCnt;
	}

	public void setPr_memCnt(int pr_memCnt) {
		this.pr_memCnt = pr_memCnt;
	}

	public String getPr_startDate() {
		return pr_startDate;
	}

	public void setPr_startDate(String pr_startDate) {
		this.pr_startDate = pr_startDate;
	}

	public String getPr_endDate() {
		return pr_endDate;
	}

	public void setPr_endDate(String pr_endDate) {
		this.pr_endDate = pr_endDate;
	}

	public int getPr_proRate() {
		return pr_proRate;
	}

	public void setPr_proRate(int pr_proRate) {
		this.pr_proRate = pr_proRate;
	}

	public String getPr_searchYN() {
		return pr_searchYN;
	}

	public void setPr_searchYN(String pr_searchYN) {
		this.pr_searchYN = pr_searchYN;
	}

	public String getPr_etc() {
		return pr_etc;
	}

	public void setPr_etc(String pr_etc) {
		this.pr_etc = pr_etc;
	}

	public String getPr_regDate() {
		return pr_regDate;
	}

	public void setPr_regDate(String pr_regDate) {
		this.pr_regDate = pr_regDate;
	}

	public String getPr_condition() {
		return pr_condition;
	}

	public void setPr_condition(String pr_condition) {
		this.pr_condition = pr_condition;
	}

	public String getPr_delYN() {
		return pr_delYN;
	}

	public void setPr_delYN(String pr_delYN) {
		this.pr_delYN = pr_delYN;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public PagingProDto getPaging() {
		return paging;
	}

	public void setPaging(PagingProDto paging) {
		this.paging = paging;
	}

	@Override
	public String toString() {
		return "ProjectDto [pr_id=" + pr_id + ", pr_name=" + pr_name
				+ ", pr_goal=" + pr_goal + ", pr_memCnt=" + pr_memCnt
				+ ", pr_startDate=" + pr_startDate + ", pr_endDate="
				+ pr_endDate + ", pr_proRate=" + pr_proRate + ", pr_searchYN="
				+ pr_searchYN + ", pr_etc=" + pr_etc + ", pr_regDate="
				+ pr_regDate + ", pr_condition=" + pr_condition + ", pr_delYN="
				+ pr_delYN + "]";
	}

}
