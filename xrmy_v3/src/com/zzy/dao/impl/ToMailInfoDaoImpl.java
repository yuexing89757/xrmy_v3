/**    
 * Project name:ads-email
 *
 * Copyright Pzoomtech.com 2011, All Rights Reserved.
 *  
 */
package com.zzy.dao.impl;

import com.zzy.dao.ToMailInfoDao;
import com.zzy.dao.impl.hibernate.GenericHbmDAO;
import com.zzy.model.ToMailInfo;

/**
 * @name ToMailInfoDaoImpl
 * 
 * @description CLASS_DESCRIPTION
 * 
 *              MORE_INFORMATION
 * 
 * @author lijing
 * 
 * @since 2011-5-6
 * 
 * @version 1.0
 */
public class ToMailInfoDaoImpl extends GenericHbmDAO<ToMailInfo, Long> implements ToMailInfoDao {

	
	public boolean addToMailInfo(ToMailInfo toMailInfo) {
		return save(toMailInfo);
	}
	
	

}
