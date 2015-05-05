/**    
 * Project name:ads-mail
 *
 * Copyright Pzoomtech.com 2011, All Rights Reserved.
 *  
 */
package com.zzy.model;

import com.zzy.enums.MailType;


/**
 * @name MailParams
 * 
 * @description CLASS_DESCRIPTION
 * 
 *              MORE_INFORMATION
 * 
 * @author lijing
 * 
 * @since 2011-2-17
 * 
 * @version 1.0
 */
//邮件信息
public class MailParams {
	private MailUserInfo mainInfo;   
	private String[] toMail;
	private String title;
	private String mailContent;
	private String[] attachPath;
	private String[] attachDescription;
	private String[] attachName;
	private String htmlContext;
	private MailType mailType;
	private String[] Cc;
	private String[] Bcc;

	public String[] getCc() {
		return Cc;
	}

	public void setCc(String[] cc) {
		Cc = cc;
	}

	public String[] getBcc() {
		return Bcc;
	}

	public void setBcc(String[] bcc) {
		Bcc = bcc;
	}

	public String getHtmlContext() {
		return htmlContext;
	}

	public void setHtmlContext(String htmlContext) {
		this.htmlContext = htmlContext;
	}

	public MailType getMailType() {
		return mailType;
	}

	public void setMailType(MailType mailType) {
		this.mailType = mailType;
	}

	/**
	 * create a recent example MailParams.
	 */
	public MailParams() {

	}

	/**
	 * create a recent example MailParams.
	 * 
	 * @param mainInfo
	 * @param noticeemail
	 * @param title
	 * @param mailConent
	 * @param attachPath
	 * @param attachDescription
	 * @param attachName
	 */
	public MailParams(MailUserInfo mainInfo, String[] toMail, String title,
			String mailConent, String[] attachPath, String[] attachDescription,
			String[] attachName) {
		super();
		this.mainInfo = mainInfo;
		this.toMail = toMail;
		this.title = title;
		this.mailContent = mailConent;
		this.attachPath = attachPath;
		this.attachDescription = attachDescription;
		this.attachName = attachName;
	}

	/**********************************************************************
	 * below are get, set methods
	 **********************************************************************/
	/**
	 * mainInfo
	 * 
	 * @return the mainInfo
	 * 
	 * @since CodingExample Ver 1.0
	 */
	public MailUserInfo getMainInfo() {
		return mainInfo;
	}

	/**
	 * @param mainInfo
	 *            the mainInfo to set
	 */
	public void setMainInfo(MailUserInfo mainInfo) {
		this.mainInfo = mainInfo;
	}

	/**
	 * toMail
	 * 
	 * @return the toMail
	 * 
	 * @since CodingExample Ver 1.0
	 */
	public String[] getToMail() {
		return toMail;
	}

	/**
	 * @param toMail
	 *            the toMail to set
	 */
	public void setToMail(String[] toMail) {
		this.toMail = toMail;
	}

	/**
	 * title
	 * 
	 * @return the title
	 * 
	 * @since codingExample Ver 1.0
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
	 * mailConent
	 * 
	 * @return the mailConent
	 * 
	 * @since codingExample Ver 1.0
	 */
	public String getMailContent() {
		return mailContent;
	}

	/**
	 * @param mailConent
	 *            the mailConent to set
	 */
	public void setMailContent(String mailConent) {
		this.mailContent = mailConent;
	}

	/**
	 * attachPath
	 * 
	 * @return the attachPath
	 * 
	 * @since codingExample Ver 1.0
	 */
	public String[] getAttachPath() {
		return attachPath;
	}

	/**
	 * @param attachPath
	 *            the attachPath to set
	 */
	public void setAttachPath(String[] attachPath) {
		this.attachPath = attachPath;
	}

	/**
	 * attachDescription
	 * 
	 * @return the attachDescription
	 * 
	 * @since codingExample Ver 1.0
	 */
	public String[] getAttachDescription() {
		return attachDescription;
	}

	/**
	 * @param attachDescription
	 *            the attachDescription to set
	 */
	public void setAttachDescription(String[] attachDescription) {
		this.attachDescription = attachDescription;
	}

	/**
	 * attachName
	 * 
	 * @return the attachName
	 * 
	 * @since codingExample Ver 1.0
	 */
	public String[] getAttachName() {
		return attachName;
	}

	/**
	 * @param attachName
	 *            the attachName to set
	 */
	public void setAttachName(String[] attachName) {
		this.attachName = attachName;
	}

}
