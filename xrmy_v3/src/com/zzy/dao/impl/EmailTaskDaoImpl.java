package com.zzy.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.zzy.dao.EmailTaskDao;
import com.zzy.dao.impl.hibernate.GenericHbmDAO;
import com.zzy.enums.StatusType;
import com.zzy.model.EmailTask;
import com.zzy.util.Paging;
import com.zzy.util.UncheckedException;



public class EmailTaskDaoImpl extends GenericHbmDAO<EmailTask, Long> implements EmailTaskDao {

	public EmailTaskDaoImpl() {
		super();
	}
	
	
	
	public boolean addEmailTask(EmailTask emailTask) {
		try {
			makePersistent(emailTask);
		} catch (HibernateException e) {
			throw new UncheckedException("EmailTaskDaoImpl --> addEmailTask " + e.toString(),
					"");
		}
		return true;
	}

	public List<EmailTask> findByPage(StatusType status,String sortColumns,String sortDir,Paging paging) {
		Criteria criteria = this.createCriteria();
		if(null!=status){
			criteria.add(Restrictions.eq("status",status));
		}else{
			criteria.add(Restrictions.not(Restrictions.isNull("status")));
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
	
	
	public List<EmailTask> findByPageRunPro(StatusType status,String sortColumns,String sortDir,Paging paging) {
		         try {
			        /*  Connection con = this.getSession().connection();
			          String sql = "{call findEmpById(?)}";
			          CallableStatement cs = (CallableStatement) con.prepareCall(sql);
					  cs.setObject(1, 59);
					  ResultSet rs = cs.executeQuery();
			         while(rs.next()){
			             int id = rs.getInt("id");
			             String name = rs.getString("status");
			             System.out.println(id+"\t"+name);
			         }*/
			         Query query =this.getSession().createSQLQuery("{call findEmpById(?)}");
					 query.setInteger(0, 59);
					 System.out.println(query.list().size());
				} catch (Exception e) {
					e.printStackTrace();
				}
		return null;
	}

	public boolean deleteEmailTask(EmailTask emailTask) {
		try {
			makeTransient(emailTask);
			return true;
		} catch (HibernateException e) {
			throw e;
		}
	}

	public List<EmailTask> findByPlanTimeNull(StatusType status,String sortColumns,String sortDir,Paging paging) {
			Criteria criteria = this.createCriteria();
	if(null!=status){
		criteria.add(Restrictions.eq("status",status));
	}else{
		criteria.add(Restrictions.not(Restrictions.isNull("status")));
	}
	criteria.createCriteria("toMailInfo", "tom");
	criteria.add(Restrictions.isNull("tom.planTime"));
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

	public List<EmailTask> findByPlanTimeNotNull(StatusType status,String sortColumns,String sortDir,Paging paging) {
		Criteria criteria = this.createCriteria();
		if(null!=status){
			criteria.add(Restrictions.eq("status",status));
		}else{
			criteria.add(Restrictions.not(Restrictions.isNull("status")));
		}
		criteria.createCriteria("toMailInfo", "tom");
		criteria.add(Restrictions.not(Restrictions.isNull("tom.planTime")));
		criteria.add(Restrictions.lt("tom.planTime",new Date()));
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

}
