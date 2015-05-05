/**    
 * Project name:ads-mail
 *
 * Copyright Pzoomtech.com 2011, All Rights Reserved.
 *  
 */
package com.zzy.dao;

import java.util.List;

import com.zzy.model.MailUserInfo;
import com.zzy.util.Paging;



/**    
 * @name ISendMailDao
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author lijing
 * 
 * @since 2011-2-18
 *       
 * @version 1.0
 */
public interface MailUserInfoDao {
	
	/**
	 * 
	 * getMailUserByAvailable(Send an email query account)   
	 * 
	 * TODO(When sending mail)
	 * 
	 * @param The number of recipients
	 * @return 
	 * 
	 * MailUserInfo
	 */
	public MailUserInfo getAvailableMailUser(String classify);
	
	/**
	 * 
	 * getAllMailUserInfo(Access to all mail users)   
	 * 
	 * TODO(View users)
	 * 
	 * @return All mail users
	 * 
	 * List<MailUserInfo>
	 */
	public List<MailUserInfo> getMailUserInfo(String emailAddress,Paging paging,String sortColumns,String sortDir);
	
	/**
	 * 
	 * getMailUserCount(Here describes this method function with a few words)   
	 * 
	 * TODO(Here describes this method to be suitable the condition - to be possible to elect)
	 * 
	 * @return 
	 * 
	 * Integer
	 */
	public Integer getMailUserCount(String emailAddress);
	
	/**
	 * 
	 * getMailUserInfoByAddress(Here describes this method function with a few words)   
	 * 
	 * TODO(Here describes this method to be suitable the condition - to be possible to elect)
	 * 
	 * @param mailAddress
	 * @return 
	 * 
	 * MailUserInfo
	 */
	public MailUserInfo getMailUserInfoByAddress(String mailAddress);
	
	/**
	 * 
	 * addMailUserInfo(Add email users)   
	 * 
	 * TODO(When the user is not enough time)
	 * 
	 * @return boolean Add user success? 
	 * 
	 */
	public boolean addMailUserInfo(MailUserInfo mailUserInfo);
	
	/**
	 * 
	 * updateMailUserInfo(update email users)   
	 * 
	 * @param mailUserInfo Modified user information
	 * @return 
	 * 
	 * boolean
	 */
	public boolean updateMailUserInfo(MailUserInfo mailUserInfo);
	
	/**
	 * 
	 * updateMailUserState(Here describes this method function with a few words)   
	 * 
	 * TODO(Here describes this method to be suitable the condition - to be possible to elect)
	 * 
	 * @param mailAddress
	 * @return 
	 * 
	 * boolean
	 */
	public boolean updateMailUserState(String[] mailAddress,String state);
	/**
	 * 
	 * deleteMailUserInfo(delete email users)   
	 * 
	 * @param users Users need to be removed
	 * @return 
	 * 
	 * boolean
	 */
	public boolean deleteMailUserInfo(String mailAddress);
	
	public boolean deleteMailUserInfoById(String mailAddress) ;

	public MailUserInfo getMailUserInfoByID(long id);

	public boolean updateMailUserStateById(String[] id, String state);
	
	public List<MailUserInfo> getMailUserInfo(String emailAddress, Paging paging,String sortColumns,String sortDir,String userStatus) ;
}
