package com.pm.rc.dto;

public class WorkDetailDto {

	private String wd_id;
	private String wk_id;
	private String wd_title;
	private String wd_complYN;
	private String wd_errorYN;
	private String wd_delYN;
	private String wd_regDate;
	private String wd_endDate;
	
	public WorkDetailDto(){}

	public String getWd_id() {
		return wd_id;
	}

	public void setWd_id(String wd_id) {
		this.wd_id = wd_id;
	}

	public String getWk_id() {
		return wk_id;
	}

	public void setWk_id(String wk_id) {
		this.wk_id = wk_id;
	}

	public String getWd_title() {
		return wd_title;
	}

	public void setWd_title(String wd_title) {
		this.wd_title = wd_title;
	}

	public String getWd_complYN() {
		return wd_complYN;
	}

	public void setWd_complYN(String wd_complYN) {
		this.wd_complYN = wd_complYN;
	}

	public String getWd_errorYN() {
		return wd_errorYN;
	}

	public void setWd_errorYN(String wd_errorYN) {
		this.wd_errorYN = wd_errorYN;
	}

	public String getWd_delYN() {
		return wd_delYN;
	}

	public void setWd_delYN(String wd_delYN) {
		this.wd_delYN = wd_delYN;
	}

	public String getWd_regDate() {
		return wd_regDate;
	}

	public void setWd_regDate(String wd_regDate) {
		this.wd_regDate = wd_regDate;
	}

	public String getWd_endDate() {
		return wd_endDate;
	}

	public void setWd_endDate(String wd_endDate) {
		this.wd_endDate = wd_endDate;
	}

	@Override
	public String toString() {
		return "WorkDetailDto [wd_id=" + wd_id + ", wk_id=" + wk_id
				+ ", wd_title=" + wd_title + ", wd_complYN=" + wd_complYN
				+ ", wd_errorYN=" + wd_errorYN + ", wd_delYN=" + wd_delYN
				+ ", wd_regDate=" + wd_regDate + ", wd_endDate=" + wd_endDate
				+ "]";
	}
	
}
