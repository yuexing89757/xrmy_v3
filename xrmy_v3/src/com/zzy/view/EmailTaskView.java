package com.zzy.view;

import java.util.Date;

import com.zzy.enums.StatusType;


public class EmailTaskView {
     private StatusType mailStatus;
     private String failCode;
     private String platform;
     
     private String sender;
     private String receiver;
     private Date createTime;
     private Date functionTime;
     
     
	public StatusType getMailStatus() {
		return mailStatus;
	}
	public void setMailStatus(StatusType mailStatus) {
		this.mailStatus = mailStatus;
	}
	public String getFailCode() {
		return failCode;
	}
	public void setFailCode(String failCode) {
		this.failCode = failCode;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getFunctionTime() {
		return functionTime;
	}
	public void setFunctionTime(Date functionTime) {
		this.functionTime = functionTime;
	}
     
     
}
