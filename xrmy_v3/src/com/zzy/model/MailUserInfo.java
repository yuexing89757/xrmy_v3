/**    
 * Project name:ads-mail
 *
 * Copyright Pzoomtech.com 2011, All Rights Reserved.
 *  
 */
package com.zzy.model;

import com.zzy.enums.MailState;
import com.zzy.model.supermodel.ModelObject;


/**
 * @name MainInfo
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author lijing
 * 
 * @since 2011-2-17
 * 
 * @version 1.0
 */
//发件人信息
public class MailUserInfo extends ModelObject<Long> {

	private String emailAddress = "";
	private String userName = "";
	private String smtpUserName = "";
	private String password = "";
	private String newPassword = "";
	private MailState state = MailState.ACTIVE;
	private String classify;
	private String userStatus;   //  用户类型,null---common ;  1---adup   ,
	private String platformName;

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	/**
	 * create a recent example MailUserInfo.
	 * 
	 */
	public MailUserInfo() {
	}

	/**
	 * create a recent example MailUserInfo.
	 * 
	 * @param emailAddresss
	 * @param userName
	 * @param password
	 * @param sendMailCount
	 * @param sendMailMaxCount
	 */
	public MailUserInfo(String emailAddresss, String userName, String password,MailState state,
			String classify, Long id, String smtpUserName) {
		super();
		this.emailAddress = emailAddresss;
		this.userName = userName;
		this.password = password;
		this.state = state;
		this.classify = classify;
		this.id = id;
		this.smtpUserName = smtpUserName;
	}

	public MailUserInfo(String emailAddress, String userName, String password,
			String newPassword,
			MailState state) {
		super();
		this.emailAddress = emailAddress;
		this.userName = userName;
		this.password = password;
		this.newPassword = newPassword;
		this.state = state;
	}

	/***************************************************************************
	 * below are get, set methods
	 **************************************************************************/

	/**
	 * emailAddresss
	 * 
	 * @return the emailAddresss
	 * 
	 * @since codingExample Ver 1.0
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddresss
	 *            the emailAddresss to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * userName
	 * 
	 * @return the userName
	 * 
	 * @since codingExample Ver 1.0
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * password
	 * 
	 * @return the password
	 * 
	 * @since codingExample Ver 1.0
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * sendMailCount
	 * 
	 * @return the sendMailCount
	 * 
	 * @since codingExample Ver 1.0
	 */


	public MailState getState() {
		return state;
	}

	public void setState(MailState state) {
		this.state = state;
	}

	public String getSmtpUserName() {
		return smtpUserName;
	}

	public void setSmtpUserName(String smtpUserName) {
		this.smtpUserName = smtpUserName;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	

	
	
	

}
