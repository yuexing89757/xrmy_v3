package com.zzy.model;

import com.zzy.model.supermodel.ModelObject;

public class PersonAcc extends ModelObject<Long> {

	private String contents;
	private String personName;
	
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonNames(String personName) {
		this.personName = personName;
	}

	
}
