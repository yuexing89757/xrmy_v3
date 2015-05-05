package com.zzy.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import com.zzy.dao.NewsDao;
import com.zzy.dao.impl.hibernate.GenericHbmDAO;
import com.zzy.enums.NewsType;
import com.zzy.model.News;
import com.zzy.util.Paging;


public class NewsDaoImpl extends GenericHbmDAO<News, Long> implements NewsDao {

	public List<News> findByPage(NewsType newsType, Paging paging) {
	   Criteria criteria = this.createCriteria();
		if(null!=newsType){
			criteria.add(Restrictions.eq("newsType",newsType));
		}else{
			criteria.add(Restrictions.not(Restrictions.isNull("newsType")));
		}
		if (paging != null) {
			paging.setTotalRecord(getTotalRecords(criteria));
			criteria.setProjection(null);
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
			criteria.setFirstResult(paging.getCurrentRecord()).setMaxResults(paging.getPageSize());
		}
		return criteria.list();
	}

	

}
