/**    
 * Project name:ads-mail
 *
 * Copyright Pzoomtech.com 2011, All Rights Reserved.
 *  
 */
package com.zzy.biz;

import java.util.List;

import com.zzy.enums.NewsType;
import com.zzy.model.News;
import com.zzy.util.Paging;





public interface INewsBiz{
	public List<News> findProduct(NewsType newsType,Paging paging);
}
