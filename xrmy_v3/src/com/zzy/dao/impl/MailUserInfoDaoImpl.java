/**    
 * Project name:ads-mail
 *
 * Copyright Pzoomtech.com 2011, All Rights Reserved.
 *  
 */
package com.zzy.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.zzy.dao.MailUserInfoDao;
import com.zzy.dao.impl.hibernate.GenericHbmDAO;
import com.zzy.enums.MailState;
import com.zzy.model.MailUserInfo;
import com.zzy.util.Log;
import com.zzy.util.ObjectUtils;
import com.zzy.util.Paging;
import com.zzy.util.UncheckedException;



public class MailUserInfoDaoImpl extends GenericHbmDAO<MailUserInfo, Long> implements MailUserInfoDao {
	private Log log = Log.getLogger(MailUserInfoDaoImpl.class);

	public MailUserInfo getAvailableMailUser(String classify) {
		log.info("MailUserInfoDaoImpl -> getAvailableMailUser");
		log.debug("MailUserInfoDaoImpl -> getAvailableMailUser parameters: classify={0}", classify);
		try {
			if (classify == null || "".equals(classify)) {
				classify = "0";
			}
			Query query =this.getSession().createSQLQuery(
				"select emailAddress,userName,password,state,ID,smtpusername"
				+ " from mailuserInfo where  state ='ACTIVE' and classify=? limit 0,1 ");
			query.setString(0, classify);
			List<?> list = query.list(); //拿到一个可用邮箱
			if (!ObjectUtils.isEmpty(list)) {
				Object[] arr = (Object[]) list.get(0);
				return new MailUserInfo(String.valueOf(arr[0]), String.valueOf(arr[1]), String.valueOf(arr[2]),
						MailState.valueOf(arr[3].toString()), classify, Long.parseLong(String.valueOf(arr[4])),
						String.valueOf(arr[5]));  //返回一个MailUserInfo对象
			} else
				return null;
		} catch (HibernateException e) {
			      throw new UncheckedException("MailUserInfoDaoImpl --> getAvailableMailUser " + e.toString(),
				  "");
		} catch (Exception ex) {
				log.error("MailUserInfoDaoImpl -> getAvailableMailUser" + ex.toString());
				return null;
		}

	}

	public MailUserInfo getMailUserInfoByAddress(String mailAddress) {
		log.info("MailUserInfoDaoImpl -> getMailUserInfoByAddress");
		log.debug("MailUserInfoDaoImpl -> getMailUserInfoByAddress parameters: mailAddress={0}", mailAddress);
		Criteria criteria = this.createCriteria();
		criteria.add(Restrictions.eq("emailAddress", mailAddress));
		MailUserInfo userInfo = null;
		List<MailUserInfo> list = criteria.list();
		if (list != null && list.size() > 0) {
			userInfo = list.get(0);
		}
		return userInfo;
	}

	public List<MailUserInfo> getMailUserInfo(String emailAddress, Paging paging,String sortColumns,String sortDir) {
		log.info("MailUserInfoDaoImpl -> getMailUserInfo");
		log.debug("MailUserInfoDaoImpl -> getMailUserInfo parameters: mailAddress={0}", emailAddress);
		Criteria criteria = this.createCriteria();
		criteria.add(Restrictions.like("emailAddress", "%" + emailAddress + "%"));
		if (paging != null) {
			paging.setTotalRecord(getTotalRecords(criteria));
			criteria.setProjection(null);
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
			criteria.setFirstResult(paging.getCurrentRecord()).setMaxResults(paging.getPageSize());
		}
		if(!StringUtils.isEmpty(sortColumns)){   //排序哇
			if(StringUtils.isEmpty(sortDir)||"asc".equalsIgnoreCase(sortDir)){
				criteria.addOrder(Order.asc(sortColumns));
			}else{
				criteria.addOrder(Order.desc(sortColumns));
			}
		}
		return criteria.list();
	}
	
	public List<MailUserInfo> getMailUserInfo(String emailAddress, Paging paging,String sortColumns,String sortDir,String userStatus) {
		log.info("MailUserInfoDaoImpl -> getMailUserInfo");
		log.debug("MailUserInfoDaoImpl -> getMailUserInfo parameters: mailAddress={0}", emailAddress);
		Criteria criteria = this.createCriteria();
		criteria.add(Restrictions.like("emailAddress", "%" + emailAddress + "%"));
		if(null!=userStatus){
			criteria.add(Restrictions.eq("userStatus",userStatus));
		}else{
			criteria.add(Restrictions.isNull("userStatus"));
		}
		if (paging != null) {
			paging.setTotalRecord(getTotalRecords(criteria));
			criteria.setProjection(null);
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
			criteria.setFirstResult(paging.getCurrentRecord()).setMaxResults(paging.getPageSize());
		}
		if(!StringUtils.isEmpty(sortColumns)){   //排序哇
			if(StringUtils.isEmpty(sortDir)||"asc".equalsIgnoreCase(sortDir)){
				criteria.addOrder(Order.asc(sortColumns));
			}else{
				criteria.addOrder(Order.desc(sortColumns));
			}
		}
		return criteria.list();
	}
	
	
	
	

	public Integer getMailUserCount(String emailAddress) {
		log.info("MailUserInfoDaoImpl -> getMailUserCount");

		String sql =
				"SELECT COUNT(emailAddress) FROM mailuserInfo WHERE emailAddress like '%" + emailAddress + "%'";
		log.debug("MailUserInfoDaoImpl -> getMailUserCount parameters: sql={0},emailAddress={1}", sql, emailAddress);
		Integer count = Integer.valueOf(this.getSession().createSQLQuery(sql).uniqueResult().toString());
		return count;
	}

	public boolean addMailUserInfo(MailUserInfo mailUserInfo) {
		try {
			makePersistent(mailUserInfo);
		} catch (HibernateException e) {
			throw new UncheckedException("MailUserInfoDaoImpl --> addMailUserInfo " + e.toString(),
					"");
		}
		return true;
	}

	public boolean updateMailUserState(String[] mailAddress, String state) {
		log.info("MailUserInfoDaoImpl -> updateMailUserState");
		log.debug("MailUserInfoDaoImpl -> updateMailUserState parameters: mailAddress={0},state={1}", mailAddress,
				state);
		for (int i = 0; i < mailAddress.length; i++) {
			Query query =
					this.getSession().createSQLQuery("UPDATE mailuserInfo SET state = ? WHERE emailAddress = ?");
			query.setString(0, state);
			query.setString(1, mailAddress[i]);
			int count = query.executeUpdate();
			if (count <= 0) {
				return false;
			}
		}
		return true;
	}

	public boolean updateMailUserStateById(String[] ids, String state) {
		log.info("MailUserInfoDaoImpl -> updateMailUserState");
		log.debug("MailUserInfoDaoImpl -> updateMailUserState parameters: mailAddress={0},state={1}", ids, state);
		for (int i = 0; i < ids.length; i++) {
			Query query = this.getSession().createSQLQuery("UPDATE mailuserInfo SET state = ? WHERE ID = ?");
			query.setString(0, state);
			query.setLong(1, Long.parseLong(ids[i]));
			int count = query.executeUpdate();
			if (count <= 0) {
				return false;
			}
		}
		return true;
	}

	public boolean updateMailUserInfo(MailUserInfo mailUserInfo) {

		Session session = getSession();
		try {
			session.merge(mailUserInfo);
			return true;
		} catch (HibernateException e) {
			log.error("update entrty error" + e.getMessage());
			throw e;
		}
	}

	public boolean deleteMailUserInfo(String mailAddress) {
		Session session = this.getSession();
		try {
			if (StringUtils.isNotBlank(mailAddress)) {
				String hql =
						"DELETE  FROM " + this.getPersistentClass().getName() + " WHERE  EMAILADDRESS IN (:idList) ";
				Query query = session.createQuery(hql);
				query.setParameterList("idList", mailAddress.split(","));
				query.executeUpdate();
			}
			return true;
		} catch (HibernateException e) {
			throw e;
		}
	}

	public boolean deleteMailUserInfoById(String string) {
		Session session = this.getSession();
		try {
			if (StringUtils.isNotBlank(string)) {
				String hql = "DELETE  FROM " + this.getPersistentClass().getName() + " WHERE  ID =:idList ";
				String[] ids = string.split(",");
				for (String value : ids) {
					long id = Long.parseLong(value);
					Query query = session.createQuery(hql);
					query.setLong("idList", id);
					query.executeUpdate();
				}
			}
			return true;
		} catch (HibernateException e) {
			throw e;
		}
	}

	public MailUserInfo getMailUserInfoByID(long id) {
		Criteria criteria = this.createCriteria();
		criteria.add(Restrictions.eq("id", id));
		MailUserInfo userInfo = null;
		List<MailUserInfo> list = criteria.list();
		if (list != null && list.size() > 0) {
			userInfo = list.get(0);
		}
		return userInfo;

	}
}
