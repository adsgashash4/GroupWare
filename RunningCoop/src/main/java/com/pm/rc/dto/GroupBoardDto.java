package com.pm.rc.dto;

public class GroupBoardDto {

	private String br_uuid;
	private String gr_id;
	private String mem_id;
	private String br_title;
	private String br_content;
	private String br_regDate;
	private String br_delYN;
	private String br_noticeYN;
	private String br_comYN;
	private String br_refer;
	private MemberDto memberdto;
	private GbAttachDto gbAttach;
	
	private PagingProDto paging;
	
	public MemberDto getMemberdto() {
		return memberdto;
	}

	public void setMemberdto(MemberDto memberdto) {
		this.memberdto = memberdto;
	}
	
	public GbAttachDto getGbAttach() {
		return gbAttach;
	}

	public void setGbAttach(GbAttachDto gbAttach) {
		this.gbAttach = gbAttach;
	}

	public GroupBoardDto(){}

	public String getBr_uuid() {
		return br_uuid;
	}
	
	public void setBr_uuid(String br_uuid) {
		this.br_uuid = br_uuid;
	}

	public String getGr_id() {
		return gr_id;
	}

	public void setGr_id(String gr_id) {
		this.gr_id = gr_id;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getBr_title() {
		return br_title;
	}

	public void setBr_title(String br_title) {
		this.br_title = br_title;
	}

	public String getBr_content() {
		return br_content;
	}

	public void setBr_content(String br_content) {
		this.br_content = br_content;
	}

	public String getBr_regDate() {
		return br_regDate;
	}

	public void setBr_regDate(String br_regDate) {
		this.br_regDate = br_regDate;
	}

	public String getBr_delYN() {
		return br_delYN;
	}

	public void setBr_delYN(String br_delYN) {
		this.br_delYN = br_delYN;
	}

	public String getBr_noticeYN() {
		return br_noticeYN;
	}

	public void setBr_noticeYN(String br_noticeYN) {
		this.br_noticeYN = br_noticeYN;
	}

	public String getBr_comYN() {
		return br_comYN;
	}

	public void setBr_comYN(String br_comYN) {
		this.br_comYN = br_comYN;
	}

	public String getBr_refer() {
		return br_refer;
	}

	public void setBr_refer(String br_refer) {
		this.br_refer = br_refer;
	}
	
	public PagingProDto getPaging() {
		return paging;
	}

	public void setPaging(PagingProDto paging) {
		this.paging = paging;
	}

	@Override
	public String toString() {
		return "GroupBoardDto [br_uuid=" + br_uuid + ", gr_id=" + gr_id
				+ ", mem_id=" + mem_id + ", br_title=" + br_title
				+ ", br_content=" + br_content + ", br_regDate=" + br_regDate
				+ ", br_delYN=" + br_delYN + ", br_noticeYN=" + br_noticeYN
				+ ", br_comYN=" + br_comYN + ", br_refer=" + br_refer
				+ ", memberdto=" + memberdto + ", gbAttach=" + gbAttach
				+ ", paging=" + paging + "]";
	}

}
