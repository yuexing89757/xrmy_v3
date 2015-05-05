/**    
 * Project name:ads-mail
 *
 * Copyright Pzoomtech.com 2011, All Rights Reserved.
 *  
 */
package com.zzy.biz;

import java.util.List;

import com.zzy.enums.ProductType;
import com.zzy.model.Product;
import com.zzy.util.Paging;





public interface IProductBiz{
	public List<Product> findProduct(ProductType productType,Paging paging);
}
