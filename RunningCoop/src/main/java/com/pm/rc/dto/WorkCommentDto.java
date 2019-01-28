package com.pm.rc.dto;

public class WorkCommentDto {

	private String wcom_id;
	private String wk_id;
	private String mem_id;
	private String wcom_content;
	private String wcom_regDate;
	private String wcom_delYN;
	
	public WorkCommentDto(){}

	public String getWcom_id() {
		return wcom_id;
	}

	public void setWcom_id(String wcom_id) {
		this.wcom_id = wcom_id;
	}

	public String getWk_id() {
		return wk_id;
	}

	public void setWk_id(String wk_id) {
		this.wk_id = wk_id;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getWcom_content() {
		return wcom_content;
	}

	public void setWcom_content(String wcom_content) {
		this.wcom_content = wcom_content;
	}

	public String getWcom_regDate() {
		return wcom_regDate;
	}

	public void setWcom_regDate(String wcom_regDate) {
		this.wcom_regDate = wcom_regDate;
	}

	public String getWcom_delYN() {
		return wcom_delYN;
	}

	public void setWcom_delYN(String wcom_delYN) {
		this.wcom_delYN = wcom_delYN;
	}

	@Override
	public String toString() {
		return "WorkCommentDto [wcom_id=" + wcom_id + ", wk_id=" + wk_id
				+ ", mem_id=" + mem_id + ", wcom_content=" + wcom_content
				+ ", wcom_regDate=" + wcom_regDate + ", wcom_delYN="
				+ wcom_delYN + "]";
	}
	
}
