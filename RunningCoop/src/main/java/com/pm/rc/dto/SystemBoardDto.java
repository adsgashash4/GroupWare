package com.pm.rc.dto;

public class SystemBoardDto {

	private String sbr_uuid;
	private String mem_id;
	private String sbr_title;
	private String sbr_content;
	private int sbr_refer;
	private int sbr_step;
	private int sbr_depth;
	private String sbr_scrYN;
	private String sbr_pw;
	private String sbr_noticeYN;
	private String sbr_regDate;
	
	private PagingProDto paging;
	
	public SystemBoardDto(){}

	public String getSbr_uuid() {
		return sbr_uuid;
	}

	public void setSbr_uuid(String sbr_uuid) {
		this.sbr_uuid = sbr_uuid;
	}

	public String getmem_id() {
		return mem_id;
	}

	public void setmem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getSbr_title() {
		return sbr_title;
	}

	public void setSbr_title(String sbr_title) {
		this.sbr_title = sbr_title;
	}

	public String getSbr_content() {
		return sbr_content;
	}

	public void setSbr_content(String sbr_content) {
		this.sbr_content = sbr_content;
	}

	public int getSbr_refer() {
		return sbr_refer;
	}

	public void setSbr_refer(int sbr_refer) {
		this.sbr_refer = sbr_refer;
	}

	public int getSbr_step() {
		return sbr_step;
	}

	public void setSbr_step(int sbr_step) {
		this.sbr_step = sbr_step;
	}

	public int getSbr_depth() {
		return sbr_depth;
	}

	public void setSbr_depth(int sbr_depth) {
		this.sbr_depth = sbr_depth;
	}

	public String getSbr_scrYN() {
		return sbr_scrYN;
	}

	public void setSbr_scrYN(String sbr_scrYN) {
		this.sbr_scrYN = sbr_scrYN;
	}

	public String getSbr_pw() {
		return sbr_pw;
	}

	public void setSbr_pw(String sbr_pw) {
		this.sbr_pw = sbr_pw;
	}

	public String getSbr_noticeYN() {
		return sbr_noticeYN;
	}

	public void setSbr_noticeYN(String sbr_noticeYN) {
		this.sbr_noticeYN = sbr_noticeYN;
	}

	public String getSbr_regDate() {
		return sbr_regDate;
	}

	public void setSbr_regDate(String sbr_regDate) {
		this.sbr_regDate = sbr_regDate;
	}

	public PagingProDto getPaging() {
		return paging;
	}

	public void setPaging(PagingProDto paging) {
		this.paging = paging;
	}

	@Override
	public String toString() {
		return "SystemBoardDto [sbr_uuid=" + sbr_uuid + ", mem_id=" + mem_id
				+ ", sbr_title=" + sbr_title + ", sbr_content=" + sbr_content
				+ ", sbr_refer=" + sbr_refer + ", sbr_step=" + sbr_step
				+ ", sbr_depth=" + sbr_depth + ", sbr_scrYN=" + sbr_scrYN
				+ ", sbr_pw=" + sbr_pw + ", sbr_noticeYN=" + sbr_noticeYN
				+ ", sbr_regDate=" + sbr_regDate + ", paging=" + paging + "]";
	}

}
