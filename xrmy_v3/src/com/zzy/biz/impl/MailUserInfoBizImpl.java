/**    
 * Project name:ads-mail
 *
 * Copyright Pzoomtech.com 2011, All Rights Reserved.
 *  
 */
package com.zzy.biz.impl;

import java.util.List;

import com.zzy.biz.IMailUserInfoBiz;
import com.zzy.dao.MailUserInfoDao;
import com.zzy.dao.dbtool.HibernateTransactionStrategy;
import com.zzy.dao.dbtool.TransactionStrategy;
import com.zzy.dao.impl.MailUserInfoDaoImpl;
import com.zzy.model.MailUserInfo;
import com.zzy.util.Log;
import com.zzy.util.Paging;



/**
 * @name MailUserInfoBizImpl
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
public class MailUserInfoBizImpl implements IMailUserInfoBiz {
	private Log log = Log.getLogger(MailUserInfoBizImpl.class);
	MailUserInfoDao userInfoDao = new MailUserInfoDaoImpl();

	public boolean addMailUserInfo(MailUserInfo mailUserInfo) {

		boolean result = false;
		if (mailUserInfo != null) {
			TransactionStrategy transaction = HibernateTransactionStrategy.getInstance();
			transaction.begin();
			try {
				result = userInfoDao.addMailUserInfo(mailUserInfo);
				transaction.commit();
			} catch (Exception e) {
				log.error("add mailUserInfo error" + e.getMessage());
				transaction.rollback();
				return false;
			} finally {
				transaction.close();
			}
		}
		return result;
	}

	public boolean deleteMailUserInfo(String mailAddress) {
		if (mailAddress == null || mailAddress.length() <= 0) {
			log.error("deleteMailUserInfo error by mailAddress is null or empty");
			return false;
		}
		boolean result = false;
		if (mailAddress != null) {
			TransactionStrategy transaction = HibernateTransactionStrategy.getInstance();
			transaction.begin();
			try {
				result = userInfoDao.deleteMailUserInfoById(mailAddress);
				transaction.commit();
			} catch (Exception e) {
				log.error("add mailUserInfo error" + e.getMessage());
				transaction.rollback();
			} finally {
				transaction.close();
			}
		}
		return result;
	}

	public List<MailUserInfo> getMailUserInfo(String emailAddress, Paging paging,String sortColumns,String sortDir) {
		if (emailAddress == null) {
			emailAddress = "";
		}
		List<MailUserInfo> resultList = userInfoDao.getMailUserInfo(emailAddress, paging,sortColumns,sortDir);
		return resultList;
	}

	
	public List<MailUserInfo> getMailUserInfo(String emailAddress, Paging paging,String sortColumns,String sortDir,String userStatus) {
		if (emailAddress == null) {
			emailAddress = "";
		}
		List<MailUserInfo> resultList = userInfoDao.getMailUserInfo(emailAddress, paging,sortColumns,sortDir,userStatus);
		return resultList;
	}

	public MailUserInfo getAvailableMailUser(String classify) {
		return userInfoDao.getAvailableMailUser(classify);

	}

	public Integer getMainUserCount(String emailAddress) {
		if (emailAddress == null) {
			emailAddress = "";
		}
		return userInfoDao.getMailUserCount(emailAddress);
	}

	public boolean updateMailUserInfo(MailUserInfo mailUserInfo) {
		boolean result = false;
		if (mailUserInfo != null) {
			TransactionStrategy transaction = HibernateTransactionStrategy.getInstance();
			transaction.begin();
			try {
				result = userInfoDao.updateMailUserInfo(mailUserInfo);
				transaction.commit();
			} catch (Exception e) {
				log.error(" update mailUserInfo error" + e.getMessage());
				e.printStackTrace();
				transaction.rollback();
			} finally {
				transaction.close();
			}
		}
		return result;
	}

	public MailUserInfo getMailUserInfoByAddress(String mailAddress) {
		return userInfoDao.getMailUserInfoByAddress(mailAddress);
	}

	public boolean updateMailUserState(String[] mailAddress, String state) {

		boolean result = false;
		if (mailAddress != null && mailAddress.length > 0) {
			TransactionStrategy transaction = HibernateTransactionStrategy.getInstance();
			transaction.begin();
			try {
				result = userInfoDao.updateMailUserState(mailAddress, state);
				transaction.commit();
			} catch (Exception e) {
				log.error("add mailUserInfo error" + e.getMessage());
				transaction.rollback();
			} finally {
				transaction.close();
			}
		}

		return result;
	}

	public boolean updateMailUserStatebyID(String[] id, String state) {

		boolean result = false;
		if (id != null && id.length > 0) {
			TransactionStrategy transaction = HibernateTransactionStrategy.getInstance();
			transaction.begin();
			try {
				result = userInfoDao.updateMailUserStateById(id, state);
				transaction.commit();
			} catch (Exception e) {
				log.error("add mailUserInfo error" + e.getMessage());
				transaction.rollback();
			} finally {
				transaction.close();
			}
		}

		return result;
	}

	public MailUserInfo getMailUserInfoByID(long id) {
		MailUserInfo user = userInfoDao.getMailUserInfoByID(id);
		return user;
	}



}
