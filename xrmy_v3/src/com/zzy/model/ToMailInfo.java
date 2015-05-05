/**    
 * Project name:ads-mail
 *
 * Copyright Pzoomtech.com 2011, All Rights Reserved.
 *  
 */
package com.zzy.model;

import java.util.Date;

import com.zzy.enums.MailType;


/**    
 * @name ToMailInfo
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author lijing
 * 
 * @since 2011-3-30
 *       
 * @version 1.0
 */
//收件人信息
public class ToMailInfo {
	private Integer id;
	private String sender;
	private String receiver;
	private String title;
	private MailType mailType;
	private String mailContent;
	private String attachPath;
	private String attachDescription;
	private String attachName;
	private Date sendTime;
	private Date planTime;
	private String copySend;
	private String blindSend;
	

	/**   
	 * create a recent example  ToMailInfo.   
	 */
	public ToMailInfo(){}
	
	/**
	 * 
	 * create a recent example  ToMailInfo.   
	 *   
	 * @param shender
	 * @param receiver
	 * @param title
	 * @param mailContent
	 * @param attachPath
	 * @param attachDescription
	 * @param attachName
	 * @param sendTime
	 */
	

	/**********************************************************************
	 * below are get, set methods
	 **********************************************************************/
	/**
	 * shender
	 * 
	 * @return the shender
	 * 
	 * @since CodingExample Ver 1.0
	 */
	public String getSender() {
		return sender;
	}
	public ToMailInfo(Integer id, String sender, String receiver, String title,
			MailType mailType, String htmlContent, String mailContent,
			String attachPath, String attachDescription, String attachName,
			Date sendTime, String copySend, String blindSend) {
		super();
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.title = title;
		this.mailType = mailType;
		this.mailContent = mailContent;
		this.attachPath = attachPath;
		this.attachDescription = attachDescription;
		this.attachName = attachName;
		this.sendTime = sendTime;
		this.copySend = copySend;
		this.blindSend = blindSend;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	public ToMailInfo(String sender, String receiver, String title,
			MailType mailType, String mailContent, String attachPath,
			String attachDescription, String attachName, Date sendTime
			) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.title = title;
		this.mailType = mailType;
		this.mailContent = mailContent;
		this.attachPath = attachPath;
		this.attachDescription = attachDescription;
		this.attachName = attachName;
		this.sendTime = sendTime;
		
	}

	/**
	 * id
	 * 
	 * @return the id
	 * 
	 * @since CodingExample Ver 1.0
	 */
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param shender
	 *            the shender to set
	 */
	public void setSender(String shender) {
		this.sender = shender;
	}
	
	/**
	 * receiver
	 * 
	 * @return the receiver
	 * 
	 * @since CodingExample Ver 1.0
	 */
	public String getReceiver() {
		return receiver;
	}	
	
	/**
	 * @param receiver
	 *            the receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}	
	
	/**
	 * title
	 * 
	 * @return the title
	 * 
	 * @since CodingExample Ver 1.0
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * mailContent
	 * 
	 * @return the mailContent
	 * 
	 * @since CodingExample Ver 1.0
	 */
	public String getMailContent() {
		return mailContent;
	}
	
	/**
	 * @param mailContent
	 *            the mailContent to set
	 */
	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}
	
	/**
	 * attachPath
	 * 
	 * @return the attachPath
	 * 
	 * @since CodingExample Ver 1.0
	 */
	public String getAttachPath() {
		return attachPath;
	}
	
	/**
	 * @param attachPath
	 *            the attachPath to set
	 */
	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}
	
	/**
	 * attachDescription
	 * 
	 * @return the attachDescription
	 * 
	 * @since CodingExample Ver 1.0
	 */
	public String getAttachDescription() {
		return attachDescription;
	}
	
	/**
	 * @param attachDescription
	 *            the attachDescription to set
	 */
	public void setAttachDescription(String attachDescription) {
		this.attachDescription = attachDescription;
	}
	
	/**
	 * attachName
	 * 
	 * @return the attachName
	 * 
	 * @since CodingExample Ver 1.0
	 */
	public String getAttachName() {
		return attachName;
	}
	
	/**
	 * @param attachName
	 *            the attachName to set
	 */
	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
	
	/**
	 * sendTime
	 * 
	 * @return the sendTime
	 * 
	 * @since CodingExample Ver 1.0
	 */
	public Date getSendTime() {
		return sendTime;
	}
	
	/**
	 * @param sendTime
	 *            the sendTime to set
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public ToMailInfo(Integer id) {
		super();
		this.id = id;
	}

	public String getCopySend() {
		return copySend;
	}

	public void setCopySend(String copySend) {
		this.copySend = copySend;
	}

	public String getBlindSend() {
		return blindSend;
	}

	public void setBlindSend(String blindSend) {
		this.blindSend = blindSend;
	}

	public MailType getMailType() {
		return mailType;
	}

	public void setMailType(MailType mailType) {
		this.mailType = mailType;
	}

	public Date getPlanTime() {
		return planTime;
	}

	public void setPlanTime(Date planTime) {
		this.planTime = planTime;
	}

	public ToMailInfo(String sender, String receiver, String title,
			MailType mailType, String mailContent, String attachPath,
			String attachDescription, String attachName, Date sendTime,
			Date planTime, String copySend, String blindSend) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.title = title;
		this.mailType = mailType;
		this.mailContent = mailContent;
		this.attachPath = attachPath;
		this.attachDescription = attachDescription;
		this.attachName = attachName;
		this.sendTime = sendTime;
		this.planTime = planTime;
		this.copySend = copySend;
		this.blindSend = blindSend;
	}

	
}
