package com.zzy.biz.impl;

import java.util.List;

import com.zzy.biz.INewsBiz;
import com.zzy.dao.NewsDao;
import com.zzy.dao.impl.NewsDaoImpl;
import com.zzy.enums.NewsType;
import com.zzy.model.News;
import com.zzy.util.Paging;



public class NewsBizImpl implements INewsBiz{

	NewsDao  newDao=null;
	
	
	public NewsBizImpl() {
		super();
		newDao=new NewsDaoImpl();
		
	}

	public List<News> findProduct(NewsType newsType,Paging paging) {
		List<News> list=newDao.findByPage(newsType, paging);
		return list;
	}

}
