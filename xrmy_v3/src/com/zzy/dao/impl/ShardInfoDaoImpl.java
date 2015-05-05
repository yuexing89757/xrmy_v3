package com.zzy.dao.impl;


import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import com.zzy.dao.ShardInfoDao;
import com.zzy.dao.impl.hibernate.GenericHbmDAO;
import com.zzy.model.ShardInfo;


public class ShardInfoDaoImpl extends GenericHbmDAO<ShardInfo, Long> implements ShardInfoDao {


	public ShardInfo findByAdId(Long adId) {
		Criteria criteria = this.createCriteria();
		if(null!=adId){
			criteria.add(Restrictions.eq("adId",adId));
		}
		return (ShardInfo) (criteria.list().size()>0?criteria.list().get(0):null);
	}

	

}
