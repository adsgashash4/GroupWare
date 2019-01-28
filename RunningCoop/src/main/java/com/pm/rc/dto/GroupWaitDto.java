package com.pm.rc.dto;

public class GroupWaitDto {

	private int wait_seq;
	private String mem_id;
	private String gr_id;
	private String wait_content;
	private String wait_regDate;
	
	public GroupWaitDto(){}

	public int getWait_seq() {
		return wait_seq;
	}

	public void setWait_seq(int wait_seq) {
		this.wait_seq = wait_seq;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getGr_id() {
		return gr_id;
	}

	public void setGr_id(String gr_id) {
		this.gr_id = gr_id;
	}

	public String getWait_content() {
		return wait_content;
	}

	public void setWait_content(String wait_content) {
		this.wait_content = wait_content;
	}

	public String getWait_regDate() {
		return wait_regDate;
	}

	public void setWait_regDate(String wait_regDate) {
		this.wait_regDate = wait_regDate;
	}

	@Override
	public String toString() {
		return "GroupWaitDto [wait_seq=" + wait_seq + ", mem_id=" + mem_id
				+ ", gr_id=" + gr_id + ", wait_content=" + wait_content
				+ ", wait_regDate=" + wait_regDate + "]";
	}
	
}
