package com.pm.rc.dto;

public class WorkListDto {

	private String wk_id;
	private String pr_id;
	private String mem_id;
	private String wk_title;
	private int wk_proRate;
	private String wk_endDate;
	private String wk_regDate;
	private String wk_complYN;
	private String wk_delYN;
	
	private GbAttachDto gbAttach;
	
	public WorkListDto(){}

	public String getWk_id() {
		return wk_id;
	}

	public void setWk_id(String wk_id) {
		this.wk_id = wk_id;
	}

	public String getPr_id() {
		return pr_id;
	}

	public void setPr_id(String pr_id) {
		this.pr_id = pr_id;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getWk_title() {
		return wk_title;
	}

	public void setWk_title(String wk_title) {
		this.wk_title = wk_title;
	}

	public int getWk_proRate() {
		return wk_proRate;
	}

	public void setWk_proRate(int wk_proRate) {
		this.wk_proRate = wk_proRate;
	}

	public String getWk_endDate() {
		return wk_endDate;
	}

	public void setWk_endDate(String wk_endDate) {
		this.wk_endDate = wk_endDate;
	}

	public String getWk_regDate() {
		return wk_regDate;
	}

	public void setWk_regDate(String wk_regDate) {
		this.wk_regDate = wk_regDate;
	}

	public String getWk_complYN() {
		return wk_complYN;
	}

	public void setWk_complYN(String wk_complYN) {
		this.wk_complYN = wk_complYN;
	}

	public String getWk_delYN() {
		return wk_delYN;
	}

	public void setWk_delYN(String wk_delYN) {
		this.wk_delYN = wk_delYN;
	}
	
	public GbAttachDto getGbAttach() {
		return gbAttach;
	}

	public void setGbAttach(GbAttachDto gbAttach) {
		this.gbAttach = gbAttach;
	}

	@Override
	public String toString() {
		return "WorkListDto [wk_id=" + wk_id + ", pr_id=" + pr_id + ", mem_id="
				+ mem_id + ", wk_title=" + wk_title + ", wk_proRate="
				+ wk_proRate + ", wk_endDate=" + wk_endDate + ", wk_regDate="
				+ wk_regDate + ", wk_complYN=" + wk_complYN + ", wk_delYN="
				+ wk_delYN + ", gbAttach=" + gbAttach + "]";
	}

}
