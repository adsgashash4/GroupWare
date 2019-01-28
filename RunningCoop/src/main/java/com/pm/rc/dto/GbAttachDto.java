package com.pm.rc.dto;

public class GbAttachDto {
	
	private int gatt_seq;
	private String gatt_name;
	private String gatt_rname;
	private String gatt_size;
	private String gatt_path;
	private String gatt_regDate;
	private String br_uuid;
	private String wk_id;
	
	public GbAttachDto(){}

	public int getGatt_seq() {
		return gatt_seq;
	}

	public void setGatt_seq(int gatt_seq) {
		this.gatt_seq = gatt_seq;
	}

	public String getGatt_name() {
		return gatt_name;
	}

	public void setGatt_name(String gatt_name) {
		this.gatt_name = gatt_name;
	}

	public String getGatt_rname() {
		return gatt_rname;
	}

	public void setGatt_rname(String gatt_rname) {
		this.gatt_rname = gatt_rname;
	}

	public String getGatt_size() {
		return gatt_size;
	}

	public void setGatt_size(String gatt_size) {
		this.gatt_size = gatt_size;
	}

	public String getGatt_path() {
		return gatt_path;
	}

	public void setGatt_path(String gatt_path) {
		this.gatt_path = gatt_path;
	}

	public String getGatt_regDate() {
		return gatt_regDate;
	}

	public void setGatt_regDate(String gatt_regDate) {
		this.gatt_regDate = gatt_regDate;
	}

	public String getBr_uuid() {
		return br_uuid;
	}

	public void setBr_uuid(String br_uuid) {
		this.br_uuid = br_uuid;
	}

	public String getWk_id() {
		return wk_id;
	}

	public void setWk_id(String wk_id) {
		this.wk_id = wk_id;
	}

	@Override
	public String toString() {
		return "GbAttachDto [gatt_seq=" + gatt_seq + ", gatt_name=" + gatt_name
				+ ", gatt_rname=" + gatt_rname + ", gatt_size=" + gatt_size
				+ ", gatt_path=" + gatt_path + ", gatt_regDate=" + gatt_regDate
				+ ", br_uuid=" + br_uuid + ", wk_id=" + wk_id + "]";
	}
	
}
