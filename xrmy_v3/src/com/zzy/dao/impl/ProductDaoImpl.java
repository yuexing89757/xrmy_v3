package com.zzy.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import com.zzy.dao.ProductDao;
import com.zzy.dao.impl.hibernate.GenericHbmDAO;
import com.zzy.enums.ProductType;
import com.zzy.model.Product;
import com.zzy.util.Paging;


public class ProductDaoImpl extends GenericHbmDAO<Product, Long> implements ProductDao {

	
	public List<Product> findByPage(ProductType productType, Paging paging) {
		Criteria criteria = this.createCriteria();
		if(null!=productType){
			criteria.add(Restrictions.eq("productType",productType));
		}else{
			criteria.add(Restrictions.not(Restrictions.isNull("productType")));
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
