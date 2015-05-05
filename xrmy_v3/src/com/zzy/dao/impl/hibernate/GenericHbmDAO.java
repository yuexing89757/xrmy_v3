package com.zzy.dao.impl.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import com.zzy.dao.dbshard.HibernateManagerShard;
import com.zzy.dao.dbtool.HibernateManager;
import com.zzy.enums.GetShardType;
import com.zzy.util.LanguageKey;
import com.zzy.util.Log;
import com.zzy.util.Paging;
import com.zzy.util.UncheckedException;



@SuppressWarnings("unchecked")
public abstract class GenericHbmDAO<T, ID extends Serializable>
	 implements GenericDAO<T, ID>
{
	
	private static Log log = Log.getLogger(GenericHbmDAO.class.getName());

	private Class<T> persistentClass;
	private Session session;

	public GenericHbmDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	

	protected Session getSession() {
		session = HibernateManager.openSession();
		return session;
	}
	


	
	

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public T findById(ID id, boolean lock) {
		T entity;
		if (lock)
			entity = (T) getSession().load(getPersistentClass(), id,
					LockOptions.UPGRADE);
		else
			entity = (T) getSession().load(getPersistentClass(), id);

		return entity;
	}

	public List<T> findAll() {
		return findByCriteria();
	}

	/**
	 * createCriteria(Here describes this method function with a few words)
	 * 
	 * @return create criteria
	 * 
	 *         Criteria
	 */
	protected Criteria createCriteria() {
		Criteria crit = this.getSession().createCriteria(this.getPersistentClass());
		return crit;
	}

	public List<T> findByExample(T exampleInstance, String[] excludeProperty) {
		if (null == exampleInstance)
			throw new IllegalArgumentException("exampleInstance is null");
		Criteria crit = this.createCriteria();
		Example example = Example.create(exampleInstance);
		for (String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		crit.add(example);
		return crit.list();
	}

	public T makePersistent(T entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public int getTotalRecords(Criterion... criteria) {
		if (criteria == null || criteria.length == 0) {
			log.info("criteria is null or criteria's length is 0.");
			Criteria crit = this.createCriteria();
			crit.setProjection(Projections.rowCount());
			return ((Number) crit.list().get(0)).intValue();
		}
		return findByCriteria(criteria).size();
	}

	public void makeTransient(T entity) {
		getSession().delete(entity);
	}

	public void makeTransient(String idList) {
		if (StringUtils.isNotBlank(idList)) {
			String hql = "DELETE  FROM " + this.getPersistentClass().getName()
					+ " WHERE  ID IN (:idList) ";
			Query query = this.getSession().createQuery(hql);
			query.setParameterList("idList", idList.split(","));
			query.executeUpdate();

		}
	}

	
	public void flush() {
		getSession().flush();
	}

	
	public void clear() {
		getSession().clear();
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	protected List<T> findByCriteria(Criterion... criterion) {
		Criteria crit = this.createCriteria();
		this.addCriterionToCriteria(crit, criterion);
		return crit.list();
	}

	/**
	 * 
	 * findByPage(Here describes this method function with a few words)
	 * 
	 * @param Paging
	 *            the pager info
	 * @return the record list of every page
	 * 
	 */

	protected List<T> findByPage(Paging pager) {
		Criteria crit = this.createCriteria();
		this.addPageToCriteria(crit, pager);
		pager.setTotalRecord(this.getTotalRecord());
		return crit.list();
	}

	/**
	 * findByPage(Here describes this method function with a few words)
	 * 
	 * 
	 * @param pager
	 * @param criterions
	 * @return
	 * 
	 */
	protected List<T> findByPage(Paging pager, Criterion... criterions) {
		if (null == pager)
			throw new UncheckedException("GenericHibernateDAO --> findByPage :pager is null",
					LanguageKey.REPORT_DAO_SQL_ERROR);
		if (null == criterions || criterions.length == 0)
			return this.findByPage(pager);
		else {
			Criteria crit = this.createCriteria();
			this.addCriterionToCriteria(crit, criterions);
			this.addPageToCriteria(crit, pager);
			pager.setTotalRecord(this.getTotalRecord(criterions));
			return crit.list();
		}
	}

	/**
	 * 
	 * getTotalRecord(Here describes this method function with a few words)
	 * 
	 * @return the total record count of the table
	 * 
	 */

	protected int getTotalRecord() {
		return findByCriteria().size();
	}

	/**
	 * getTotalRecord(Here describes this method function with a few words)
	 * 
	 * possible to elect)
	 * 
	 * @param criterions
	 * @return the total record count of the query
	 * 
	 */
	protected int getTotalRecord(Criterion... criterions) {
		return findByCriteria(criterions).size();
	}
	
	
	
	protected int getTotalRecords(Criteria crit) {
		crit.setProjection(Projections.rowCount());
		return ((Number) crit.uniqueResult()).intValue();
	}

	/**
	 * findByLimit(Here describes this method function with a few words)
	 * 
	 * @param maxValue
	 * @param criterions
	 * @param orders
	 * @return List<T>
	 */
	protected List<T> findByLimit(Integer maxValue, Criterion[] criterions,
			Order... orders) {
		if (null == maxValue)
			throw new UncheckedException("GenericHibernateDAO --> findByLimit :maxValue is null",
					LanguageKey.REPORT_DAO_SQL_ERROR);
		Criteria crit = this.createCriteria();
		this.addCriterionToCriteria(crit, criterions);
		crit.setMaxResults(maxValue);
		if (null != orders && orders.length > 0) {
			for (Order order : orders) {
				crit.addOrder(order);
			}
		}
		List<T> list = crit.list();
		return list;
	}

	/**
	 * addCriterionToCriteria(Here describes this method function with a few
	 * words) Add Criterions to Criteria
	 * 
	 * @param criteria
	 * @param criterions
	 * 
	 */
	protected void addCriterionToCriteria(Criteria criteria,
			Criterion... criterions) {
		if (null == criteria)
			throw new UncheckedException("GenericHibernateDAO --> addCriterionToCriteria :criteria is null",
					LanguageKey.REPORT_DAO_SQL_ERROR);
		if (null != criterions && criterions.length > 0) {
			for (Criterion criterion : criterions) {
				criteria.add(criterion);
			}
		}
	}

	/**
	 * addOrderToCriteria(Here describes this method function with a few words)
	 * 
	 * Add Order to Criteria
	 * 
	 * @param criteria
	 * @param orders
	 * 
	 *            void
	 */
	protected void addOrderToCriteria(Criteria criteria, Order... orders) {
		if (null == criteria)
			throw new UncheckedException("GenericHibernateDAO --> addOrderToCriteria :criteria is null",
					LanguageKey.REPORT_DAO_SQL_ERROR);
		if (null != orders && orders.length > 0) {
			for (Order order : orders) {
				criteria.addOrder(order);
			}
		}
	}

	/**
	 * addPageToCriteria(Here describes this method function with a few words)
	 * 
	 * Add Page to Criteria
	 * 
	 * @param criteria
	 * @param pager
	 * 
	 */
	private void addPageToCriteria(Criteria criteria, Paging pager) {
		if (null == criteria)
			throw new UncheckedException("GenericHibernateDAO --> addPageToCriteria :criteria is null",
					LanguageKey.REPORT_DAO_SQL_ERROR);
		if (null == pager)
			throw new UncheckedException("GenericHibernateDAO --> addPageToCriteria :pager is null",
					LanguageKey.REPORT_DAO_SQL_ERROR);
		criteria.setFirstResult(pager.getCurrentRecord()).setMaxResults(
				pager.getPageSize());
	}

	/**
	 * 
	 * deleteOldData(Here describes this method function with a few words)
	 * 
	 * TODO(Here describes this method to be suitable the condition - to be
	 * possible to elect)
	 * 
	 * @param userid
	 * @param 2010-2-4
	 * @param 2010-2-4
	 * @return
	 * 
	 *         boolean
	 */
	public boolean deleteOldData(String userid, String startDate, String endDate) {
		Query query = this.getSession().createSQLQuery(
				"delete from " + this.getPersistentClass().getName()
						+ " where date between '" + startDate + "' and '"
						+ endDate + "'");
		return query.executeUpdate() > 0 ? true : false;
	}

	/**
	 * 
	 * select(query by custom sql)
	 * 
	 * TODO(Here describes this method to be suitable the condition - to be
	 * possible to elect)
	 * 
	 * @param sql
	 * @return
	 * 
	 *         List
	 */
	@SuppressWarnings("rawtypes")
	public List selectBySQL(String sql) {
		if (null == sql || sql.equals(""))
			throw new UncheckedException("GenericHibernateDAO --> selectBySQL :sql is null",
					LanguageKey.REPORT_DAO_SQL_ERROR);
		Query query = this.getSession().createSQLQuery(sql);
		return query.list();
	}

	/**
	 * 
	 * callProcedureGetNewestDataTime(get the newest data download time)
	 * 
	 * TODO(Here describes this method to be suitable the condition - to be
	 * possible to elect)
	 * 
	 * @param reporttype
	 * @return
	 * @throws Exception
	 * 
	 *             String
	 */
	@SuppressWarnings("rawtypes")
	public String callProcedureGetNewestDataTime(String reporttype,
			String customerid, String queryStr) throws Exception {
		try {

			Query query = this.getSession().createSQLQuery(
					"{Call " + queryStr + "(?,?)}");
			query.setString(0, reporttype);
			query.setString(1, customerid);
			List list = query.list();
			if (list != null && list.size() > 0) {
				if (list.get(0) != null) {
					String data = list.get(0).toString();
					return data;
				}
			}
			return "";
		} catch (HibernateException ex) {
			log.error("GenericHibernateDAO->callProcedureGetNewestDataTime: "
					+ ex.toString());
			throw ex;
		}
	}




	
	public boolean save(T entity)
	{
		Session session = getSession();
		try{
			session.save(entity);
			return true;
		}catch(HibernateException e) {
			throw e;
		}
	}

	
	public boolean update(T entity) {
		Session session = getSession();
		try{
			session.merge(entity);
			return true;
		}catch(HibernateException e) {
			log.error("update entrty error" + e.getMessage());
			throw e;
		}
	}

	
	public boolean delete(String idList)
	{
		Session session = getSession();
		try{
			if (StringUtils.isNotBlank(idList))
			{
				String hql = "DELETE  FROM " + this.getPersistentClass().getName()
						+ " WHERE  EMAILADDRESS IN (:idList) ";
				Query query = session.createQuery(hql);
				query.setParameterList("idList", idList.split(","));
				query.executeUpdate();
			}
			return true;
		}catch(HibernateException e) {
			throw e;
		}
	}
}
