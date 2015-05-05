/**    
 * Project name:ads-email
 *
 * Copyright Pzoomtech.com 2011, All Rights Reserved.
 *  
 */
package com.zzy.enums;

/**    
 * @name MailState
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author lijing
 * 
 * @since 2011-5-6
 *       
 * @version 1.0
 */
public enum MailState {
	ACTIVE("启用"), DISABLED("禁用"), DELETED("删除");
	
	private String code;
	
	private MailState(String code){
		this.code = code;
	}
	
	public String getCode(){
		return this.code;
	}
}
