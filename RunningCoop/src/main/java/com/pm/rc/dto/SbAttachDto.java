package com.pm.rc.dto;

public class SbAttachDto {
	
	private int satt_seq;
	private String satt_name;
	private String satt_size;
	private String satt_path;
	private String satt_regDate;
	private String sbr_uuid;
	private String satt_rname;
	

	public SbAttachDto(){}

	public int getSatt_seq() {
		return satt_seq;
	}

	public void setSatt_seq(int satt_seq) {
		this.satt_seq = satt_seq;
	}

	public String getSatt_name() {
		return satt_name;
	}

	public void setSatt_name(String satt_name) {
		this.satt_name = satt_name;
	}

	public String getSatt_size() {
		return satt_size;
	}

	public void setSatt_size(String satt_size) {
		this.satt_size = satt_size;
	}

	public String getSatt_path() {
		return satt_path;
	}

	public void setSatt_path(String satt_path) {
		this.satt_path = satt_path;
	}

	public String getSatt_regDate() {
		return satt_regDate;
	}

	public void setSatt_regDate(String satt_regDate) {
		this.satt_regDate = satt_regDate;
	}

	public String getSbr_uuid() {
		return sbr_uuid;
	}

	public void setSbr_uuid(String sbr_uuid) {
		this.sbr_uuid = sbr_uuid;
	}

	public String getSatt_rname() {
		return satt_rname;
	}
	
	public void setSatt_rname(String satt_rname) {
		this.satt_rname = satt_rname;
	}

	@Override
	public String toString() {
		return "SbAttachDto [satt_seq=" + satt_seq + ", satt_name=" + satt_name
				+ ", satt_size=" + satt_size + ", satt_path=" + satt_path
				+ ", satt_regDate=" + satt_regDate + ", sbr_uuid=" + sbr_uuid
				+ ", satt_rname=" + satt_rname + "]";
	}
	
	
}
