package com.pm.rc.dto;

public class MemberDto {

	private String mem_id;
	private String mem_pw;
	private String mem_name;
	private String mem_email;
	private String mem_phone;
	private String mem_regDate;
	private String mem_delYN;
	private String mem_level;
	private GroupWaitDto groupwaitdto;
	
	private PagingProDto paging;
	
	public MemberDto(){}
	
	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getMem_pw() {
		return mem_pw;
	}

	public void setMem_pw(String mem_pw) {
		this.mem_pw = mem_pw;
	}

	public String getMem_name() {
		return mem_name;
	}

	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}

	public String getMem_email() {
		return mem_email;
	}

	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}

	public String getMem_phone() {
		return mem_phone;
	}

	public void setMem_phone(String mem_phone) {
		this.mem_phone = mem_phone;
	}

	public String getMem_regDate() {
		return mem_regDate;
	}

	public void setMem_regDate(String mem_regDate) {
		this.mem_regDate = mem_regDate;
	}

	public String getMem_delYN() {
		return mem_delYN;
	}

	public void setMem_delYN(String mem_delYN) {
		this.mem_delYN = mem_delYN;
	}
	
	public String getMem_level() {
		return mem_level;
	}

	public void setMem_level(String mem_level) {
		this.mem_level = mem_level;
	}

	public GroupWaitDto getGroupwaitdto() {
		return groupwaitdto;
	}

	public void setGroupwaitdto(GroupWaitDto groupwaitdto) {
		this.groupwaitdto = groupwaitdto;
	}

	
	public PagingProDto getPaging() {
		return paging;
	}

	public void setPaging(PagingProDto paging) {
		this.paging = paging;
	}

	@Override
	public String toString() {
		return "MemberDto [mem_id=" + mem_id + ", mem_pw=" + mem_pw
				+ ", mem_name=" + mem_name + ", mem_email=" + mem_email
				+ ", mem_phone=" + mem_phone + ", mem_regDate=" + mem_regDate
				+ ", mem_delYN=" + mem_delYN + ", mem_level=" + mem_level + "]";
	}

}
