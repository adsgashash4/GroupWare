package com.pm.rc.dto;

public class ScheduleDto {

	private int sch_seq;
	private String sch_title;
	private String sch_content;
	private String sch_startDate;
	private String sch_endDate;
	private String sch_prosYN;
	private String pr_id;
	private String mem_id;
	private String sch_delYN;
	private String sch_regDate;
	private ProjectDto projectDto;
	
	public ScheduleDto(){}

	public int getSch_seq() {
		return sch_seq;
	}

	public void setSch_seq(int sch_seq) {
		this.sch_seq = sch_seq;
	}

	public String getSch_title() {
		return sch_title;
	}

	public void setSch_title(String sch_title) {
		this.sch_title = sch_title;
	}

	public String getSch_content() {
		return sch_content;
	}

	public void setSch_content(String sch_content) {
		this.sch_content = sch_content;
	}

	public String getSch_startDate() {
		return sch_startDate;
	}

	public void setSch_startDate(String sch_startDate) {
		this.sch_startDate = sch_startDate;
	}

	public String getSch_endDate() {
		return sch_endDate;
	}

	public void setSch_endDate(String sch_endDate) {
		this.sch_endDate = sch_endDate;
	}

	public String getSch_prosYN() {
		return sch_prosYN;
	}

	public void setSch_prosYN(String sch_prosYN) {
		this.sch_prosYN = sch_prosYN;
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

	public String getSch_delYN() {
		return sch_delYN;
	}

	public void setSch_delYN(String sch_delYN) {
		this.sch_delYN = sch_delYN;
	}

	public String getSch_regDate() {
		return sch_regDate;
	}

	public void setSch_regDate(String sch_regDate) {
		this.sch_regDate = sch_regDate;
	}
	

	public ProjectDto getProjectDto() {
		return projectDto;
	}

	public void setProjectDto(ProjectDto projectDto) {
		this.projectDto = projectDto;
	}

	@Override
	public String toString() {
		return "ScheduleDto [sch_seq=" + sch_seq + ", sch_title=" + sch_title
				+ ", sch_content=" + sch_content + ", sch_startDate="
				+ sch_startDate + ", sch_endDate=" + sch_endDate
				+ ", sch_prosYN=" + sch_prosYN + ", pr_id=" + pr_id
				+ ", mem_id=" + mem_id + ", sch_delYN=" + sch_delYN
				+ ", sch_regDate=" + sch_regDate + ", projectDto=" + projectDto
				+ "]";
	}
	
}
