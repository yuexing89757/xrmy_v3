package com.zzy.dao;

import java.util.List;

import com.zzy.enums.NewsType;
import com.zzy.model.News;
import com.zzy.util.Paging;



public interface NewsDao {
	public List<News> findByPage(NewsType newsType,Paging paging);

}
