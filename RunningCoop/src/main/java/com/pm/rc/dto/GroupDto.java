package com.pm.rc.dto;

import java.io.Serializable;

public class GroupDto implements Serializable{


	private static final long serialVersionUID = -6753307874830043392L;
	private String gr_id;  
	private String gr_name;     
	private String gr_searchYN; 
	private String gr_joinYN;   
	private int gr_memCnt;   
	private String gr_goal;     
	private String gr_regDate;  
	private String gr_appYN;
	private String gr_delYN;
	private String gr_img;
	
	private PagingProDto paging;
	
	public GroupDto(){}

	public String getGr_id() {
		return gr_id;
	}

	public void setGr_id(String gr_id) {
		this.gr_id = gr_id;
	}

	public String getGr_name() {
		return gr_name;
	}

	public void setGr_name(String gr_name) {
		this.gr_name = gr_name;
	}

	public String getGr_searchYN() {
		return gr_searchYN;
	}

	public void setGr_searchYN(String gr_searchYN) {
		this.gr_searchYN = gr_searchYN;
	}

	public String getGr_joinYN() {
		return gr_joinYN;
	}

	public void setGr_joinYN(String gr_joinYN) {
		this.gr_joinYN = gr_joinYN;
	}

	public int getGr_memCnt() {
		return gr_memCnt;
	}

	public void setGr_memCnt(int gr_memCnt) {
		this.gr_memCnt = gr_memCnt;
	}

	public String getGr_goal() {
		return gr_goal;
	}

	public void setGr_goal(String gr_goal) {
		this.gr_goal = gr_goal;
	}

	public String getGr_regDate() {
		return gr_regDate;
	}

	public void setGr_regDate(String gr_regDate) {
		this.gr_regDate = gr_regDate;
	}

	public String getGr_appYN() {
		return gr_appYN;
	}

	public void setGr_appYN(String gr_appYN) {
		this.gr_appYN = gr_appYN;
	}

	public String getGr_delYN() {
		return gr_delYN;
	}

	public void setGr_delYN(String gr_delYN) {
		this.gr_delYN = gr_delYN;
	}

	public String getGr_img() {
		return gr_img;
	}

	public void setGr_img(String gr_img) {
		this.gr_img = gr_img;
	}

	public PagingProDto getPaging() {
		return paging;
	}

	public void setPaging(PagingProDto paging) {
		this.paging = paging;
	}

	@Override
	public String toString() {
		return "GroupDto [gr_id=" + gr_id + ", gr_name=" + gr_name
				+ ", gr_searchYN=" + gr_searchYN + ", gr_joinYN=" + gr_joinYN
				+ ", gr_memCnt=" + gr_memCnt + ", gr_goal=" + gr_goal
				+ ", gr_regDate=" + gr_regDate + ", gr_appYN=" + gr_appYN
				+ ", gr_delYN=" + gr_delYN + ", gr_img=" + gr_img + ", paging="
				+ paging + "]";
	}
	
}
