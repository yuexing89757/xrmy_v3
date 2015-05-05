package com.zzy.model;

import com.zzy.model.supermodel.ModelObject;

public class Event extends ModelObject<Long> {

	private String contents;
	private String personNames;
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getPersonNames() {
		return personNames;
	}
	public void setPersonNames(String personNames) {
		this.personNames = personNames;
	}

	
}
