package com.zzy.model;


import java.util.Calendar;

import com.zzy.model.supermodel.ModelObject;

public abstract class TaskLog extends ModelObject<Long> {


	private boolean succeed;
	private Calendar logTime;
	private Long totalTime;
	private String message;
	private String data;
	
	public boolean isSucceed() {
		return this.succeed;
	}
	public void setSucceed(boolean succeed) {
		this.succeed = succeed;
	}
	public Calendar getLogTime() {
		return this.logTime;
	}
	public void setLogTime(Calendar logTime) {
		this.logTime = logTime;
	}
	public Long getTotalTime() {
		return this.totalTime;
	}
	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}
	public String getMessage() {
		return this.message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

}
