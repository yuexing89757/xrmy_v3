/**    
 * Project name:ads-mail
 *
 * Copyright Pzoomtech.com 2011, All Rights Reserved.
 *  
 */
package com.zzy.biz;

import org.apache.commons.mail.EmailException;

/**
 * @name ISendMail
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
public interface ISendMail
{
	
	/**
	 * sendMail(This method is used to send e-mail)
	 * 
	 * @param toMail
	 * @param title
	 * @param mailConent
	 * @param attachPath
	 * @param attachDescription
	 * @param attachName
	 * @param Cc
	 * @param Bcc
	 * @param mailType(TEXT,HTML)
	 * @return
	 * @throws EmailException
	 * @throws Exception
	 */
	public boolean sendMail(String[] toMail, String title, String mailConent, String attachPath[], String attachDescription[], String attachName[],String[] Cc,String[] Bcc,String mailType,String classify)
	throws EmailException, Exception;

}
