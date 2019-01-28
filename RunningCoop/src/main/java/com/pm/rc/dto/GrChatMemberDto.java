package com.pm.rc.dto;

import java.util.ArrayList;
import java.util.HashMap;

public class GrChatMemberDto {
	
	private HashMap<String, String> map;
	private ArrayList<String> memLists;
	
	public GrChatMemberDto(){
		memLists = new ArrayList<String>();
		map = new HashMap<String, String>();
	}

	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	public ArrayList<String> getMemLists() {
		return memLists;
	}

	public void setMemLists(ArrayList<String> memLists) {
		this.memLists = memLists;
	}
	
}
